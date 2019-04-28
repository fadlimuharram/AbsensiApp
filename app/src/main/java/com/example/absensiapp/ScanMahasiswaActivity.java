package com.example.absensiapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.absensiapp.model.JadwalModel;
import com.example.absensiapp.model.MapelModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanMahasiswaActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    private ArrayList<MapelModel> mapel;
    private int selectedId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static String TAG = ScanMahasiswaActivity.class.getSimpleName();

    private int loop = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mapel = getIntent().getParcelableArrayListExtra(DaftarMapelActivity.DATAMAPEL);
        selectedId = getIntent().getIntExtra(DaftarMapelActivity.SELECTEDMAPEL,0);

    }

    @Override
    public void onResume() {
        super.onResume();

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

    }

    @Override
    public void handleResult(Result result) {
        Log.v("TAG", result.getText()); // Prints scan results
        Log.v("TAG", result.getBarcodeFormat().toString());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(result.getText() + " " + mapel.get(selectedId).getNama());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
        loop++;
        absen(result.getText());

        mScannerView.resumeCameraPreview(this);
    }

    private void absen(String npmResult){
        Log.wtf(TAG,"debugloop--> " + loop);
        if (loop==1){
            findJadwalMahasiswa(npmResult, mapel.get(selectedId).getId());
        }
    }

    private void findJadwalMahasiswa(String npm, String idMapel){
        db.collection("jadwal_mahasiswa")
                .whereEqualTo("mahasiswa.npm", npm)
                .whereEqualTo("matapelajaran.id", idMapel)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Log.wtf(TAG,"sizeeee-------> " + task.getResult().size());
                            if (task.getResult().size() == 1){

                                JadwalModel jadwalModel = task.getResult().getDocuments().get(0).toObject(JadwalModel.class);
                                checkIsAbsen(jadwalModel);
                            }else {
                                Toast.makeText(getApplicationContext(), "Mahasiswa Tidak Mengambil Pelajaran Ini",Toast.LENGTH_SHORT).show();
                            }
                            loop = 0;

                        }

                    }
                });

    }

    //TODO: find where absensi is not equal this day, if result 0 than input



    private void checkIsAbsen(final JadwalModel jadwalModel){

        db.collection("absensi")
                .whereEqualTo("npm", jadwalModel.getMahasiswa().getNpm())
                .whereEqualTo("matapelajaran_id", jadwalModel.getMatapelajaran().getId())
                .whereEqualTo("tanggal", getTanggal())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().getDocuments().size() == 0){
                                insertAbsensi(jadwalModel);
                            }else {
                                Toast.makeText(getApplicationContext(), "Mahasiswa " + jadwalModel.getMahasiswa().getNama() + " Sudah absen", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void insertAbsensi(final JadwalModel jadwalModel){
        final Map<String, Object> data = new HashMap<>();
        data.put("nama", jadwalModel.getMahasiswa().getNama());
        data.put("npm", jadwalModel.getMahasiswa().getNpm());
        data.put("mahasiswa_id", jadwalModel.getMahasiswa().getId());
        data.put("matapelajaran", jadwalModel.getMatapelajaran().getNama());
        data.put("matapelajaran_id", jadwalModel.getMatapelajaran().getId());
        data.put("dosen_id",mapel.get(selectedId).getDosen().getId());
        data.put("dosen_email",mapel.get(selectedId).getDosen().getEmail());
        data.put("jam", getJam());
        data.put("tanggal", getTanggal());

        db.collection("absensi").add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.wtf(TAG, "Berhasil Absen added with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Mahasiswa " + jadwalModel.getMahasiswa().getNama() + " Berhasil Absen", Toast.LENGTH_SHORT).show();
                        addGroupTanggal(getTanggal(), documentReference.getId(), mapel.get(selectedId).getDosen().getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    private void addGroupTanggal(final String tanggal, final String absenId, final String dosenId){

        db.collection("groupTanggal")
                .whereEqualTo("tanggal", tanggal)
                .whereEqualTo("dosen_id", dosenId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().getDocuments().size() == 0){
                                Map<String, Object> tgl = new HashMap<>();
                                tgl.put("tanggal", tanggal);
                                tgl.put("dosen_id", dosenId);
                                tgl.put("absensi_id", absenId);
                                tgl.put("dosen_email", mapel.get(selectedId).getDosen().getEmail());

                                db.collection("groupTanggal")
                                        .add(tgl)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.wtf(TAG, "berhasill");
                                            }
                                        });
                            }

                        }
                    }
                });


    }

    private String getTanggal(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c);
    }

    private String getJam(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        return df.format(c);
    }
}
