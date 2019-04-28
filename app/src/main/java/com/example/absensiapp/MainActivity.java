package com.example.absensiapp;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.nav)
    NavigationView nav;

    @BindView(R.id.activity_main)
    DrawerLayout dl;

    @BindView(R.id.btn_to_daftar_kelas)
    Button btnToKelas;

    @BindView(R.id.btn_to_riwayat_kehadiran)
    Button btnRiwayatKehadiran;

    ActionBarDrawerToggle t;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        t = new ActionBarDrawerToggle(MainActivity.this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnToKelas.setOnClickListener(this);
        btnRiwayatKehadiran.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.logout :
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);

            default:
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_to_daftar_kelas){
            Intent daftarMapel = new Intent(MainActivity.this, DaftarMapelActivity.class);
            startActivity(daftarMapel);
        }else if(v.getId() == R.id.btn_to_riwayat_kehadiran){
            Intent riwayatKehadiran = new Intent(MainActivity.this, RiwayatKehadiranActivity.class);
            startActivity(riwayatKehadiran);
        }
    }
}
