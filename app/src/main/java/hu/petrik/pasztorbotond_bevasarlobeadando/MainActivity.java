package hu.petrik.pasztorbotond_bevasarlobeadando;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText Tnev, Tar, Tmennyiseg, Tmertekegyseg;
    private Button Bthozzaadas, Btlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        Bthozzaadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hozzaadas();
            }
        });
        Btlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, termek_list.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void hozzaadas(){
        String nev = Tnev.getText().toString();
        String arin = Tar.getText().toString();
        String mennyisegin = Tmennyiseg.getText().toString();
        String mertekegyseg = Tmertekegyseg.getText().toString();

        if (nev.isEmpty() || arin.isEmpty() || mennyisegin.isEmpty() || mertekegyseg.isEmpty()) {
            Toast.makeText(this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        int ar;
        double mennyiseg;
        try {
            ar = Integer.parseInt(arin);
            mennyiseg = Double.parseDouble(mennyisegin);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Helytelen formátum!", Toast.LENGTH_SHORT).show();
            return;
        }

        Termekek termek = new Termekek(0, nev, ar, mennyiseg, mertekegyseg);
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        apiService.createTermek(termek).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                Toast.makeText(MainActivity.this, "Termék hozzáadva!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hiba történt!", Toast.LENGTH_SHORT).show();
            }
        });;
    }

    void init(){
        Tnev = findViewById(R.id.Tnev);
        Tar = findViewById(R.id.Tar);
        Tmennyiseg = findViewById(R.id.Tmennyiseg);
        Tmertekegyseg = findViewById(R.id.Tmertekegyseg);
        Bthozzaadas = findViewById(R.id.Bthozzaadas);
        Btlista = findViewById(R.id.Btlista);
    }
}