package com.example.gaskeun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DaftarAkun extends AppCompatActivity {

    EditText usernameInput, passwordInput, emailInput, phoneInput;
    Button daftarButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar_akun);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi elemen form
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        emailInput = findViewById(R.id.email);
        phoneInput = findViewById(R.id.phone);
        daftarButton = findViewById(R.id.daftarButton);

        dbHelper = new DatabaseHelper(this);

        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();

                boolean success = dbHelper.registerUser(username, password, email, phone);

                if (success) {
                    Toast.makeText(DaftarAkun.this, "Berhasil daftar!", Toast.LENGTH_SHORT).show();

                    // Kosongkan form
                    usernameInput.setText("");
                    passwordInput.setText("");
                    emailInput.setText("");
                    phoneInput.setText("");

                    // Pindah ke LoginActivity
                    Intent intent = new Intent(DaftarAkun.this, Login.class);
                    startActivity(intent);
                    finish(); // agar tidak bisa kembali ke halaman daftar
                } else {
                    Toast.makeText(DaftarAkun.this, "Gagal daftar. Coba lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
