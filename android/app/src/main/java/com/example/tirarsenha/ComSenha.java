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

public class ComSenha extends AppCompatActivity {

    private TextView senhaAtual, suaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_senha);

        Intent intent = getIntent();
        String anterior = intent.getStringExtra(MainActivity.EXTRA_RESPONSE);

        senhaAtual = findViewById(R.id.txt_senha_atual2);
        senhaAtual.setText(anterior+"0"); //currentNumber

        suaSenha = findViewById(R.id.txt_sua_senha);
        suaSenha.setText(anterior+"0"); //highestNumber

        Button btnSenha = findViewById(R.id.btn_teste);
        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder().url(MainActivity.api_url + "listen").get().build();

            MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(ComSenha.this, "ERRO", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    openCodigo(request);
                    ////currentNumber e highestNumber
                }
            });
        });
    }

    public void openCodigo(Request request) {
        Intent intent = new Intent(this, Codigo.class);
        startActivity(intent);
    }
}