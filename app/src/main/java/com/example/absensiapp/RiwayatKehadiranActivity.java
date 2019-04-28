package com.example.absensiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.absensiapp.adapter.GroupTanggalAdapter;
import com.example.absensiapp.model.GroupTanggalModel;
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

public class RiwayatKehadiranActivity extends AppCompatActivity implements ItemClickSupport.OnItemClickListener {

    private static String TAG = RiwayatKehadiranActivity.class.getSimpleName();
    public static String TGLDIPILIH = "TGLDIPILIH";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    GroupTanggalAdapter adapter;

    List<GroupTanggalModel> groupTanggalModelList = new ArrayList<>();

    @BindView(R.id.rv_groupTgl)
    RecyclerView rvGroupTgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_kehadiran);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        getGroupTanggal();
        ItemClickSupport.addTo(rvGroupTgl).setOnItemClickListener(this);
    }

    private void getGroupTanggal(){
        Log.wtf(TAG, "emaill " + currentUser.getEmail());
        db.collection("groupTanggal")
                .whereEqualTo("dosen_email", currentUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Gson gson = new Gson();
                            if (task.getResult().getDocuments().size() > 0){
                                Log.wtf(TAG, String.valueOf(task.getResult().getDocuments().size()));
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.wtf(TAG, "json-->" + gson.toJson(document.getData()));
                                    GroupTanggalModel mdl = document.toObject(GroupTanggalModel.class);

                                    groupTanggalModelList.add(mdl);
                                }

                                adapter = new GroupTanggalAdapter(getApplicationContext());
                                rvGroupTgl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                adapter.setmData(groupTanggalModelList);
                                rvGroupTgl.setAdapter(adapter);
                            }

                        }
                    }
                });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent absensiIntent = new Intent(RiwayatKehadiranActivity.this, AbsensiActivity.class);
        absensiIntent.putExtra(TGLDIPILIH, groupTanggalModelList.get(position).getTanggal());
        startActivity(absensiIntent);
    }
}
