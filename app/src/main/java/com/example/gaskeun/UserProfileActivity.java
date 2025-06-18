package com.example.gaskeun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserProfileActivity extends AppCompatActivity {

    TextView txtUsername, txtEmail, txtPhone;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView navHome = findViewById(R.id.navHome);
        ImageView navCalendar = findViewById(R.id.navCalendar);
        Button logoutButton = findViewById(R.id.logout_button);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        dbHelper = new DatabaseHelper(this);

        // Ambil username dari session
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String username = prefs.getString("username", null);

        if (username != null){
            // Ambil data user dari database
            Cursor cursor = dbHelper.getUserData(username);
            if (cursor != null && cursor.moveToFirst()){
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                txtUsername.setText(username);
                txtEmail.setText(email);
                txtPhone.setText(phone);

                cursor.close();
            }
        }

        // Fungsi Logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hapus session
                SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear(); // hapus semua data sesi
                editor.apply();

                // Kembali ke LoginActivity
                Intent intent = new Intent(UserProfileActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear semua activity
                startActivity(intent);
                finish();
            }
        });

        // Navigasi Bar
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserHomeActivity.class);
            startActivity(intent);
        });

        navCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserPesanActivity.class);
            startActivity(intent);
        });
    }
}