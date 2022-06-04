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

public class SemSenha extends AppCompatActivity {
    private TextView senhaAtual, senhaPossivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_senha);

        Intent intent=getIntent();
        String anterior=intent.getStringExtra(MainActivity.EXTRA_RESPONSE);

        senhaAtual = findViewById(R.id.txt_senha_atual);
        senhaAtual.setText(anterior); //currentNumber

        senhaPossivel = findViewById(R.id.txt_npossivel);
        senhaPossivel.setText(anterior); //highestNumber+1

        Button btnSenha = findViewById(R.id.btn_pedir_senha);
        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder().url(MainActivity.api_url+"getTicket").post(MainActivity.requestBody).build();

            MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(SemSenha.this, "ERRO", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    openComSenha(request);
                    ////currentNumber e highestNumber
                }
            });
        });
    }

    public void openComSenha(Request request){
        Intent intent=new Intent(this, ComSenha.class);
        startActivity(intent);

    }
}