package com.study.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Deklarasi Variable
    private Button register;
    private Button login;
    private TextInputEditText myEmail;
    private TextInputEditText myPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private String getEmail;
    private String getPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Inisialisasi Widget
        myEmail = findViewById(R.id.getEmail);
        myPassword = findViewById(R.id.getPassword);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //Menyembunyikan / Hide Password
        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //Instance / Membuat Objek Firebase Authentication
        auth = FirebaseAuth.getInstance();

        //Mengecek Keberadaan User
        listener = firebaseAuth -> {
            //Mengecek apakah ada user yang sudah login / belum logout
            FirebaseUser user = firebaseAuth.getCurrentUser();
            //Jika ada, maka halaman akan langsung berpidah pada MainActivity
            if (user != null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();;
            }
        };
    }

    //Menerapkan Listener
    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    //Melepaskan Litener
    protected void onStop(){
        super.onStop();
        if (listener != null){
            auth.removeAuthStateListener(listener);
        }
    }

    //Method ini digunakan untuk proses autentikasi user menggunakan email dan password
    private void loginUserAccount(){
        auth.signInWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Mengecek status keberhasilan saat login
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Tidak Dapat Login, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.login:
                //Mendapatkan dat yang diinputkan User
                getEmail = myEmail.getText().toString();
                getPassword = myPassword.getText().toString();

                //Mengecek apakah email dan sandi kosong atau tidak
                if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)){
                    Toast.makeText(this, "Email atau Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    loginUserAccount();
                    progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }

    }
}