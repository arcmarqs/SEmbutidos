package com.example.tirarsenha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public static final String api_url = "http://192.168.1.68:8000/";
    public static final String EXTRA_VALOR1="com.example.applications.tirarsenha.EXTRA_VALOR1";
    public static final String EXTRA_VALOR2="com.example.applications.tirarsenha.EXTRA_VALOR2";
    public static final String EXTRA_VALOR3="com.example.applications.tirarsenha.EXTRA_VALOR3";

    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static RequestBody requestBody = new FormBody.Builder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pressionar o Botão para inicar os pedidos
        Button btnSenha = findViewById(R.id.btn_iniciar);

        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url(api_url+"getState")
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "ERRO", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        String currentNumber="0";
                        String highestNumber="0";

                        //Tradução ResponseBody para os valores
                        String jsonString = response.body().string();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            currentNumber = obj.getString("currentNumber");
                            highestNumber= ""+(Integer.parseInt(obj.getString("highestNumber"))+1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       openSemSenha(currentNumber, highestNumber);
                    }
                }
            });
        });
    }

    //Mudar de janela para tirar senha
    public void openSemSenha(String currentNumber, String highestNumber){
        Intent intent=new Intent(this, SemSenha.class);
        intent.putExtra(EXTRA_VALOR1, currentNumber);
        intent.putExtra(EXTRA_VALOR2, highestNumber);
        startActivity(intent);
    }
}