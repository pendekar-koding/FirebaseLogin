package com.study.firebaselogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    private TextInputEditText myEmail;
    private Button changeButton;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private String getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        //Inisialisasi Widget dan Membuat Objek dari FirebaeUser
        myEmail = findViewById(R.id.changeEmail);
        changeButton = findViewById(R.id.change);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        user = FirebaseAuth.getInstance().getCurrentUser();

        changeButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getEmail = myEmail.getText().toString().trim();

            //Melakukan Proses Update, dengan memasukan email baru
            user.updateEmail(getEmail).addOnCompleteListener(task -> {
                //Mengecek status keberhasilan saat proses update Password
                if (task.isSuccessful()){
                    Toast.makeText(ChangeEmailActivity.this, "Email Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                } else {
                    Toast.makeText(ChangeEmailActivity.this, "Terjadi Kesalahan, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });

        });
    }
}