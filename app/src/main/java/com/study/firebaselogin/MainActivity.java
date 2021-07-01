package com.study.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;

    private Button changePass;
    private Button changeEmail;
    private Button deleteUser;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeEmail = findViewById(R.id.changeEmail);
        changeEmail.setOnClickListener(this);
        changePass = findViewById(R.id.changePass);
        changePass.setOnClickListener(this);
        deleteUser = findViewById(R.id.deleteAccount);
        deleteUser.setOnClickListener(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        //Instance Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Menambahkan Listener untuk mengecek apakah user telah logout / keluar
        listener = firebaseAuth -> {
            //Jika Iya atau Null, maka akan berpindah pada halaman Login
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null){
                Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        };

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fungsi untuk logout
                firebaseAuth.signOut();
            }
        });
    }

    //Menerapkan Listener
    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    //Melepaskan Litener
    @Override
    protected void onStop(){
        super.onStop();
        if (listener != null){
            firebaseAuth.removeAuthStateListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeEmail:
                startActivity(new Intent(MainActivity.this, ChangeEmailActivity.class));
                break;

            case R.id.changePass:
                startActivity(new Intent(MainActivity.this, ChangeEmailActivity.class));
                break;

            case R.id.deleteAccount:
                break;

            case R.id.logout:
                firebaseAuth.signOut();
                break;
        }

    }
}