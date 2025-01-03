package hu.petrik.pasztorbotond_bevasarlobeadando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModositasActivity extends AppCompatActivity {

    private EditText ETnev, ETar, ETmennyiseg, ETmertekegyseg;
    private Button Btmodositas, Bttorles, Btmegse;
    private int termekId;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modositas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        termekId = getIntent().getIntExtra("termekId", -1);
        loadTermek();
        Btmodositas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modositas();
                Intent intent = new Intent(ModositasActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Bttorles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                torles();

            }
        });

        Btmegse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModositasActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadTermek() {
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        apiService.getTermekById(termekId).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ETnev.setText(response.body().getNev());
                    ETar.setText(String.valueOf(response.body().getEgysegAr()));
                    ETmennyiseg.setText(String.valueOf(response.body().getMennyiseg()));
                    ETmertekegyseg.setText(response.body().getMertekegyseg());
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(ModositasActivity.this, "Nem sikerült betölteni az adatokat!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void modositas() {
        String nev = ETnev.getText().toString();
        String egysegArStr = ETar.getText().toString();
        String mennyisegStr = ETmennyiseg.getText().toString();
        String mertekegyseg = ETmertekegyseg.getText().toString();

        if (nev.isEmpty() || egysegArStr.isEmpty() || mennyisegStr.isEmpty() || mertekegyseg.isEmpty()) {
            Toast.makeText(this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        int egysegAr;
        double mennyiseg;
        try {
            egysegAr = Integer.parseInt(egysegArStr);
            mennyiseg = Double.parseDouble(mennyisegStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Helytelen formátum!", Toast.LENGTH_SHORT).show();
            return;
        }

        Termekek termek = new Termekek(termekId, nev, egysegAr, mennyiseg, mertekegyseg);
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        apiService.updateTermek(termekId, termek).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ModositasActivity.this, "Termék módosítva!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ModositasActivity.this, "Hiba történt a módosítás során!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(ModositasActivity.this, "Hálózati hiba!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void torles() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Biztosan törölni szeretnéd ezt a terméket?")
                .setPositiveButton("Igen", (dialog, id) -> {
                    RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
                    apiService.deleteTermek(termekId).enqueue(new Callback<Termekek>() {
                        @Override
                        public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ModositasActivity.this, "Termék törölve!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ModositasActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ModositasActivity.this, "Hiba történt a törlés során!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Termekek> call, Throwable t) {
                            Toast.makeText(ModositasActivity.this, "Hálózati hiba!", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Nem", null);

        alertDialog = builder.create();

        if (!isFinishing()) { // Csak akkor jelenítsd meg, ha az Activity még aktív
            alertDialog.show();
        }
    }

    void init(){
        ETnev = findViewById(R.id.ETnev);
        ETar = findViewById(R.id.ETar);
        ETmennyiseg = findViewById(R.id.ETmennyiseg);
        ETmertekegyseg = findViewById(R.id.ETmertekegyseg);

        Btmodositas = findViewById(R.id.Btmodositas);
        Bttorles = findViewById(R.id.Bttorles);
        Btmegse = findViewById(R.id.Btmegse);
    }
}