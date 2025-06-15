package com.example.gaskeun;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button loginButton;
    TextView registerNow;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi view
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerNow = findViewById(R.id.buatAkun);
        dbHelper = new DatabaseHelper(this);

        // Tombol "Buat Akun"
        registerNow.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, DaftarAkun.class);
            startActivity(intent);
        });

        // Tombol Login
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Cek apakah login sebagai user atau joki
            if (dbHelper.checkUserCredentials(username, password)) {
                Toast.makeText(Login.this, "Login sebagai User!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, UserHomeActivity.class));
                finish();
            } else if (dbHelper.checkJokiCredentials(username, password)) {
                Toast.makeText(Login.this, "Login sebagai Joki!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, JokiHomeActivity.class));
                finish();
            } else {
                Toast.makeText(Login.this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
