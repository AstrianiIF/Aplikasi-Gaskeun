package com.example.gaskeun;

import android.content.Intent;
import android.content.SharedPreferences;
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

        // Inisialisasi view dan database
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerNow = findViewById(R.id.buatAkun);
        dbHelper = new DatabaseHelper(this);

        // Cek session login
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String savedUsername = prefs.getString("username", null);
        String savedRole = prefs.getString("role", null);

        if (savedUsername != null && savedRole != null) {
            if (savedRole.equals("user")) {
                startActivity(new Intent(Login.this, UserHomeActivity.class));
            } else if (savedRole.equals("joki")) {
                startActivity(new Intent(Login.this, JokiHomeActivity.class));
            }
            finish(); // Tidak kembali ke login saat back
        }

        // Tombol "Buat Akun"
        registerNow.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, DaftarAkun.class);
            startActivity(intent);
        });

        // Tombol Login
        loginButton.setOnClickListener(v -> {
            String inputUsername = usernameInput.getText().toString().trim();
            String inputPassword = passwordInput.getText().toString().trim();

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(Login.this, "Isi Username dan Password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkUserCredentials(inputUsername, inputPassword)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", inputUsername);
                editor.putString("role", "user");
                editor.apply();

                Toast.makeText(Login.this, "Login sebagai User!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, UserHomeActivity.class));
                finish();

            } else if (dbHelper.checkJokiCredentials(inputUsername, inputPassword)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", inputUsername);
                editor.putString("role", "joki");
                editor.apply();

                Toast.makeText(Login.this, "Login sebagai Joki!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, JokiHomeActivity.class));
                finish();

            } else {
                Toast.makeText(Login.this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
