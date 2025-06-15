package com.example.gaskeun;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class JokiPesanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_joki_pesan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView navHome = findViewById(R.id.navHome);
        ImageView navProfile = findViewById(R.id.navProfile);

        // Navigasi Bar
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(JokiPesanActivity.this, JokiHomeActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(JokiPesanActivity.this, JokiProfileActivity.class);
            startActivity(intent);
        });
    }

}