package com.example.tirarsenha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ComSenha extends AppCompatActivity {
    private TextView senhaAtual, suaSenha;
    private String guiche="", pinCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_senha);

        Intent intent = getIntent();
        String currentNumber = intent.getStringExtra(MainActivity.EXTRA_VALOR1);
        String highestNumber = intent.getStringExtra(MainActivity.EXTRA_VALOR2);
        pinCode = intent.getStringExtra(MainActivity.EXTRA_VALOR3);

        senhaAtual = findViewById(R.id.txt_senha_atual2);
        senhaAtual.setText(currentNumber);

        suaSenha = findViewById(R.id.txt_sua_senha);
        suaSenha.setText(highestNumber);

        //TODO: APAGAR SOMENTE PARA TESTE
        Button btnSenha = findViewById(R.id.btn_teste);
        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url(MainActivity.api_url + "/")
                    .get()
                    .build();

            MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(ComSenha.this, "ERRO", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                    /*String jsonString=response.body().string();
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(jsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        guiche= obj.getString("highestNumber");
                        codigo= obj.getString("highestNumber");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/



                    openCodigo(guiche, pinCode);
                    //currentNumber e highestNumber
                }
            });
        });
    }

    public void openCodigo(String guiche, String pinCode) {
        Intent intent = new Intent(this, Codigo.class);
        intent.putExtra(MainActivity.EXTRA_VALOR1, guiche);
        intent.putExtra(MainActivity.EXTRA_VALOR2, pinCode);
        startActivity(intent);
    }
}