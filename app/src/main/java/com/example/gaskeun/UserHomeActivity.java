package com.example.gaskeun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView navCalendar = findViewById(R.id.navCalendar);
        ImageView navProfile = findViewById(R.id.navProfile);
        ImageView chatIcon = findViewById(R.id.chatIcon);

        // Fungsi Tombol Chat
        chatIcon.setOnClickListener(v -> {
            String phoneNumber = "6285158602224"; // Ganti nomor
            String url = "https://wa.me/" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.setPackage("com.whatsapp");
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "WhatsApp tidak terpasang.", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigasi Bar
        navCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomeActivity.this, UserPesanActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomeActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });

    }
}