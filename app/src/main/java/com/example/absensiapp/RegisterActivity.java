package com.example.absensiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static String TAG = RegisterActivity.class.getSimpleName();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @BindView(R.id.reg_edt_email)
    EditText reg_edt_email;

    @BindView(R.id.reg_edt_password)
    EditText reg_edt_password;

    @BindView(R.id.reg_btn_register)
    Button reg_btn_register;

    @BindView(R.id.reg_edt_nid)
    EditText reg_edt_nid;

    @BindView(R.id.reg_edt_nama)
    EditText reg_edt_nama;

    @BindView(R.id.register_to_login)
    TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        reg_btn_register.setOnClickListener(this);
        toLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reg_btn_register){

            final String email = reg_edt_email.getText().toString();
            String password = reg_edt_password.getText().toString();
            final String NID = reg_edt_nid.getText().toString();
            final String nama = reg_edt_nama.getText().toString();

            if(!email.isEmpty() && !password.isEmpty() && !NID.isEmpty() && !nama.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    createDosen(email, nama, NID);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }

        }else if(v.getId() == R.id.register_to_login){
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    private void createDosen(String email, String nama, String nid){
        Map<String, Object> user = new HashMap<>();
        user.put("nama", nama);
        user.put("email", email);
        user.put("nid",nid);

        db.collection("dosen")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
