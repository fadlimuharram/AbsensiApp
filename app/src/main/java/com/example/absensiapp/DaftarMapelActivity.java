package com.example.absensiapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.absensiapp.adapter.MapelAdapter;
import com.example.absensiapp.model.MapelModel;
import com.example.absensiapp.utils.ItemClickSupport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaftarMapelActivity extends AppCompatActivity implements ItemClickSupport.OnItemClickListener {

    public static String DATAMAPEL ="DATAMAPEL";
    public static String SELECTEDMAPEL = "SELECTEDMAPEL";
    private static String TAG = DaftarMapelActivity.class.getSimpleName();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    MapelAdapter adapter;
    List<MapelModel> mapelList = new ArrayList<>();

    @BindView(R.id.rv_mapel)
    RecyclerView rvMapel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mapel);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getMapel();
        ItemClickSupport.addTo(rvMapel).setOnItemClickListener(this);


    }

    private void getMapel(){
        db.collection("matapelajaran")
                .whereEqualTo("dosen.email", currentUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            Log.d(TAG,"hitung=>" + task.getResult().size());
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                Log.d(TAG, doc.getId() + " => " + doc.getData());

                                MapelModel mapel = doc.toObject(MapelModel.class);
                                mapel.setId(doc.getId());
                                mapelList.add(mapel);
                            }

                            adapter = new MapelAdapter(getApplicationContext());
                            rvMapel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    Log.d(TAG,"debbb=> " + mapelList);
                            adapter.setmData(mapelList);
                            rvMapel.setAdapter(adapter);
                        }else {
                            Log.d(TAG,"ERRRRRRRRORRR");
                        }
                    }
                });

    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent scanIntent = new Intent(DaftarMapelActivity.this, ScanMahasiswaActivity.class);

        scanIntent.putParcelableArrayListExtra(DATAMAPEL, (ArrayList<? extends Parcelable>) mapelList);
        scanIntent.putExtra(SELECTEDMAPEL, position);

        startActivity(scanIntent);
        Toast.makeText(this,mapelList.get(position).getNama(), Toast.LENGTH_SHORT).show();
        //TODO: kirim id data matapelajaran
    }


}
