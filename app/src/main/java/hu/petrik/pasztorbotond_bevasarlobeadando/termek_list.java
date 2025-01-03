package hu.petrik.pasztorbotond_bevasarlobeadando;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class termek_list extends AppCompatActivity {

    private ListView LVtermekek;
    private Button Btvissza;
    private List<Termekek> termekek;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termek_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LVtermekek = findViewById(R.id.LVtermekek);
        Btvissza = findViewById(R.id.Btvissza);

        Btvissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(termek_list.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        termekek = new ArrayList<>();
        loadTermekek();
        adapter = new Adapter(termekek, this);
        LVtermekek.setAdapter(adapter);

        LVtermekek.setOnItemClickListener((parent, view, position, id) -> {
            Termekek selectedTermek = termekek.get(position);
            int termekId = selectedTermek.getId();

            Intent intent = new Intent(termek_list.this, ModositasActivity.class);
            intent.putExtra("termekId", termekId);
            startActivity(intent);
        });

    }

    private void loadTermekek() {
        Log.d("hi", "hierts");
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        apiService.getAllTermek().enqueue(new Callback<List<Termekek>>() {
            @Override
            public void onResponse(Call<List<Termekek>> call, Response<List<Termekek>> response) {
                Log.d("API Response", "Response Data: " + response.body());

                if (response.isSuccessful() && response.body() != null) {
                    termekek.clear();
                    termekek.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Termekek>> call, Throwable t) {

                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(termek_list.this, "Nem sikerült betölteni a termékeket!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}