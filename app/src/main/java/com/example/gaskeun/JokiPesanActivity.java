package com.example.gaskeun;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public class JokiPesanActivity extends AppCompatActivity {

    LinearLayout containerOrders;
    DatabaseHelper dbHelper;

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

        containerOrders = findViewById(R.id.containerOrders);
        dbHelper = new DatabaseHelper(this);

        ImageView navHome = findViewById(R.id.navHome);
        ImageView navProfile = findViewById(R.id.navProfile);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(JokiPesanActivity.this, JokiHomeActivity.class));
        });

        navProfile.setOnClickListener(v -> {
            startActivity(new Intent(JokiPesanActivity.this, JokiProfileActivity.class));
        });

        tampilkanDataTransaksi();
    }

    private void tampilkanDataTransaksi() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM transaksi", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username_game"));
                String password = cursor.getString(cursor.getColumnIndex("password_game"));
                String game = cursor.getString(cursor.getColumnIndex("game"));
                int hari = cursor.getInt(cursor.getColumnIndex("jumlah_hari"));
                int harga = cursor.getInt(cursor.getColumnIndex("harga"));
                String status = cursor.getString(cursor.getColumnIndex("status"));

                View cardView = LayoutInflater.from(this).inflate(R.layout.item_transaksi_card, null);

                TextView edtUsername = cardView.findViewById(R.id.edtUsername);
                TextView edtPassword = cardView.findViewById(R.id.edtPassword);
                TextView gameSpinner = cardView.findViewById(R.id.gameSpinner);
                TextView daysInput = cardView.findViewById(R.id.daysInput);
                TextView estimasiHargaText = cardView.findViewById(R.id.estimasiHargaText);
                TextView txtStatus = cardView.findViewById(R.id.txtStatus);
                Button btnAcc = cardView.findViewById(R.id.btnAcc);

                edtUsername.setText("Username : " + username);
                edtPassword.setText("Password : " + password);
                gameSpinner.setText("Game : " + game);
                daysInput.setText("Durasi Joki : " + hari + " Hari");
                estimasiHargaText.setText("Estimasi Harga : Rp " + harga);
                txtStatus.setText("Status : " + status);

                if ("disetujui".equalsIgnoreCase(status)) {
                    btnAcc.setEnabled(false);
                    btnAcc.setText("Sudah ACC");
                }

                btnAcc.setOnClickListener(v -> {
                    SQLiteDatabase dbUpdate = dbHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("status", "disetujui");
                    dbUpdate.update("transaksi", cv, "id=?", new String[]{String.valueOf(id)});
                    txtStatus.setText("Status : disetujui");
                    btnAcc.setEnabled(false);
                    btnAcc.setText("Sudah ACC");
                });

                containerOrders.addView(cardView);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
