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

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @BindView(R.id.login_btn_login)
    Button btn_login;

    @BindView(R.id.login_edt_email)
    EditText edt_email;

    @BindView(R.id.login_edt_password)
    EditText edt_password;

    @BindView(R.id.login_to_register)
    TextView to_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        updateUI();
        btn_login.setOnClickListener(this);
        to_register.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this,"sudah login tadi",Toast.LENGTH_SHORT);
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, String.valueOf(user));
                            isDosen(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn_login){
            if (edt_email.getText().toString().isEmpty() || edt_password.getText().toString().isEmpty()){
                Toast.makeText(this,"harap isi terlebbih dahulu", Toast.LENGTH_SHORT).show();
            }else {
                signIn(edt_email.getText().toString(),edt_password.getText().toString());
            }
        }else if(v.getId() == R.id.login_to_register){
            Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(register);
        }
    }

    private void isDosen(FirebaseUser user){
        if(user != null){
            db.collection("dosen")
                    .whereEqualTo("email",user.getEmail())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){

                        QuerySnapshot doc = task.getResult();
                        Log.d(TAG, "ukuran : " + String.valueOf(doc.size()));
                        if(doc.size() > 0){
                            updateUI();
                        }else{
                            mAuth.signOut();
                            //Todo: buat alert ketika bukan dosen atau gagal login
                        }



                    }else{
                        Log.w(TAG, "Error getting documents.", task.getException());

                    }

                }
            });
        }
    }

    private void updateUI(){

        if (mAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }


}
