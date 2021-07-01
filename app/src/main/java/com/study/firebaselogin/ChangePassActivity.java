package com.study.firebaselogin;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {

    private TextInputEditText myPassword;
    private Button changeButton;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private String getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        //Inisialisasi Widget dan Membuat Objek dari FirebaeUser
        myPassword = findViewById(R.id.changePassword);
        changeButton = findViewById(R.id.change);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        user = FirebaseAuth.getInstance().getCurrentUser();

        //Menyembunyikan / Hide Password
        myPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());


        changeButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getPassword = myPassword.getText().toString().trim();

            //Melakukan Proses Update, dengan memasukan password baru
            user.updatePassword(getPassword).addOnCompleteListener(task -> {
                //Mengecek status keberhasilan saat proses update password
                if (task.isSuccessful()){
                    Toast.makeText(ChangePassActivity.this, "Password Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                } else {
                    Toast.makeText(ChangePassActivity.this, "Terjadi Kesalahan, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
    }
}