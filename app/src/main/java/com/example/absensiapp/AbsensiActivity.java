package com.example.absensiapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.absensiapp.adapter.AbsensiAdapter;
import com.example.absensiapp.model.Absensi;
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

public class AbsensiActivity extends AppCompatActivity {

    private static String TAG = AbsensiActivity.class.getSimpleName();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private String selectedTanggal;

    AbsensiAdapter adapter;

    List<Absensi> absensi = new ArrayList<>();

    @BindView(R.id.rv_absensi)
    RecyclerView rvAbsensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        selectedTanggal = getIntent().getStringExtra(RiwayatKehadiranActivity.TGLDIPILIH);

        getAbsensi();

    }

    private void getAbsensi(){
        db.collection("absensi")
                .whereEqualTo("dosen_email",currentUser.getEmail())
                .whereEqualTo("tanggal", selectedTanggal)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Gson gson = new Gson();
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.wtf(TAG, "data-->" + gson.toJson(document.getData()));
                                Absensi mdl = document.toObject(Absensi.class);

                                absensi.add(mdl);
                            }

                            adapter = new AbsensiAdapter(getApplicationContext());

                            rvAbsensi.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            adapter.setmData(absensi);

                            rvAbsensi.setAdapter(adapter);
                        }
                    }
                });
    }
}
