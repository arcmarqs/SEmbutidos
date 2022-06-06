package com.example.tirarsenha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SemSenha extends AppCompatActivity {
    private TextView senhaAtual, senhaPossivel;
    private String hNumber="", pinCode="",cNumber="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_senha);

        //Receber os valores enviados da página anterior
        Intent intent=getIntent();
        String ACN =intent.getStringExtra(MainActivity.EXTRA_VALOR1);
        String AHN=intent.getStringExtra(MainActivity.EXTRA_VALOR2);

        //Inserção dos valores nos campos correspondentes
        senhaAtual = findViewById(R.id.txt_senha_atual);
        senhaAtual.setText(ACN); //currentNumber

        senhaPossivel = findViewById(R.id.txt_npossivel);
        senhaPossivel.setText(AHN); //highestNumber


        //Pressionar o Botão para obter uma senha
        final Button[] btnSenha = {findViewById(R.id.btn_pedir_senha)};
        btnSenha[0].setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url(MainActivity.api_url+"getTicket")
                    .build();

            MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(SemSenha.this, "Servidor embaixo! Tente mais tarde!", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        String jsonString=response.body().string();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            hNumber= obj.getString("highestNumber");
                            pinCode= obj.getString("pinCode");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Segunda Request - Obter o Número Atual
                        Request request = new Request.Builder()
                                .url(MainActivity.api_url+"getCurrentNumber")
                                .build();
                        MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                           @Override
                           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                               String jsonString=response.body().string();
                               JSONObject obj = null;
                               try {
                                   obj = new JSONObject(jsonString);
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   cNumber= obj.getString("currentNumber");

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                               openComSenha(cNumber, hNumber, pinCode);
                           }

                           @Override
                           public void onFailure(@NotNull Call call, @NotNull IOException e) {
                               runOnUiThread(() -> {
                                   Toast.makeText(SemSenha.this, "Servidor embaixo! Tente mais tarde!", Toast.LENGTH_SHORT).show();
                               });
                           }
                        });
                    }
                }
            });
        });
    }

    public void openComSenha(String currentNumber, String highestNumber,String pinCode){
        Intent intent=new Intent(this, ComSenha.class);
        intent.putExtra(MainActivity.EXTRA_VALOR1, currentNumber);
        intent.putExtra(MainActivity.EXTRA_VALOR2, highestNumber);
        intent.putExtra(MainActivity.EXTRA_VALOR3, pinCode);

        startActivity(intent);
    }
}