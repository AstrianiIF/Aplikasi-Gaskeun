package com.example.gaskeun;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserProfileActivity extends AppCompatActivity {

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

        // Fungsi Logout
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(UserProfileActivity.this, Login.class);
            startActivity(intent);
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