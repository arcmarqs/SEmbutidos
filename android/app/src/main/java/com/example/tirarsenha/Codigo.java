package com.example.tirarsenha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class Codigo extends AppCompatActivity {
    private TextView guiche, codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);
        Intent intent = getIntent();
        String anterior = intent.getStringExtra(MainActivity.EXTRA_RESPONSE);

        guiche = findViewById(R.id.txt_nguiche);
        guiche.setText(anterior+"0"); //currentNumber

        codigo = findViewById(R.id.txt_codigo);
        codigo.setText(anterior+"0"); //highestNumber

        Button btnSenha = findViewById(R.id.btn_teste2);
        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder().url(MainActivity.api_url + "checkOut").get().build();

            MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(Codigo.this, "ERRO", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    openAtendido(request);
                }
            });
        });
    }

    public void openAtendido(Request request) {
        Intent intent = new Intent(this, Atendido.class);
        startActivity(intent);
    }
}