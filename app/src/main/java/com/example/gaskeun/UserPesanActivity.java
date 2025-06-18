package com.example.gaskeun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserPesanActivity extends AppCompatActivity {

    private EditText edtUsernameGame, edtPasswordGame, edtHari;
    private Spinner gameSpinner;
    private TextView estimasiHargaText;
    private final int hargaPerHari = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_pesan);

        DatabaseHelper db = new DatabaseHelper(this);

        edtUsernameGame = findViewById(R.id.edtUsername);
        edtPasswordGame = findViewById(R.id.edtPassword);
        edtHari = findViewById(R.id.daysInput);
        gameSpinner = findViewById(R.id.gameSpinner);
        estimasiHargaText = findViewById(R.id.estimasiHargaText);
        Button btnBayar = findViewById(R.id.btnBayar);

        ImageView navHome = findViewById(R.id.navHome);
        ImageView navProfile = findViewById(R.id.navProfile);

        // Navigasi
        navHome.setOnClickListener(v -> startActivity(new Intent(this, UserHomeActivity.class)));
        navProfile.setOnClickListener(v -> startActivity(new Intent(this, UserProfileActivity.class)));

        // Estimasi harga otomatis saat jumlah hari diketik
        edtHari.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int hari = Integer.parseInt(s.toString());
                    int totalHarga = hari * hargaPerHari;
                    estimasiHargaText.setText("Estimasi Harga : Rp. " + String.format("%,d", totalHarga).replace(',', '.'));
                } catch (NumberFormatException e) {
                    estimasiHargaText.setText("Estimasi Harga : Rp. 0");
                }
            }
        });

        btnBayar.setOnClickListener(v -> {
            String usernameGame = edtUsernameGame.getText().toString().trim();
            String passwordGame = edtPasswordGame.getText().toString().trim();
            String gameDipilih = gameSpinner.getSelectedItem().toString().trim();
            String hariInput = edtHari.getText().toString().trim();

            if (usernameGame.isEmpty() || passwordGame.isEmpty() || hariInput.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua isian!", Toast.LENGTH_SHORT).show();
                return;
            }

            int hari = Integer.parseInt(hariInput);
            int harga = hari * hargaPerHari;

            // Ambil username dari session
            SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
            String usernameCustomer = prefs.getString("username", "unknown");

            boolean sukses = db.buatTransaksiTanpaPenjoki(
                    usernameCustomer,
                    usernameGame,
                    passwordGame,
                    hari,
                    gameDipilih,
                    harga
            );

            if (sukses) {
                Toast.makeText(this, "Pesanan berhasil dikirim!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserHomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Gagal menyimpan pesanan!", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
