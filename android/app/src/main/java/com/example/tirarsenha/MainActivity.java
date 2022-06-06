package com.example.tirarsenha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String api_url = "http://192.168.1.68:8080/";
    public static final String EXTRA_RESPONSE="com.example.applications.tirarsenha.EXTRA_RESPONSE";

    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static RequestBody requestBody = new FormBody.Builder().build();
    // declare attribute for textview
    private TextView teste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teste = findViewById(R.id.tt);

        //Iniciar Pedidos
        Button btnSenha = findViewById(R.id.btn_iniciar);


        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url(api_url+"getState")
                    .header("User-Agent", "OkHttp Headers.java")
                    .addHeader("Accept", "application/json; q=0.5")
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "ERRO", Toast.LENGTH_SHORT).show();
                        teste.setText("Servidor embaixo! Tente mais tarde!");
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        openSemSenha(response);
                        //currentNumber e highestNumber+1

                        ///*
                        final String myResponse=response.body().string();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                teste.setText(myResponse);
                            }
                        });
                        //*/
                    }
                }
            });
        });
    }

    public void openSemSenha(Response response){
        Intent intent=new Intent(this, SemSenha.class);
        intent.putExtra(EXTRA_RESPONSE, response.toString());
        startActivity(intent);
    }
}