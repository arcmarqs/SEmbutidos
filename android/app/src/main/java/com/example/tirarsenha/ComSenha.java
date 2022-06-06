package com.example.tirarsenha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ComSenha extends AppCompatActivity {
    private TextView senhaAtual, suaSenha;
    private String guiche="0", pinCode="";

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

        Button btnSenha = findViewById(R.id.btn_teste);
        btnSenha.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url(MainActivity.api_url + "/getState")
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
                    String jsonString = response.body().string();
                    JSONObject obj = null;
                    JSONArray arrayGuiche = null;
                    try {
                        obj = new JSONObject(jsonString);
                        arrayGuiche = obj.getJSONArray("guicheArray");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                            JSONObject obj1 = arrayGuiche.getJSONObject(1);
                            if (obj1.getString("ticketNumber")==highestNumber){
                                guiche = obj1.getString("guichetId");
                            }else{
                                obj1 = arrayGuiche.getJSONObject(2);
                                if (obj1.getString("ticketNumber")==highestNumber) {
                                    guiche = obj1.getString("guichetId");
                                }
                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   openCodigo(guiche, pinCode);

                }
            });
        });
    }
    public void openCodigo(String Nguiche, String pinCode) {
        Intent intent = new Intent(this, Codigo.class);
        intent.putExtra(MainActivity.EXTRA_VALOR1, Nguiche);
        intent.putExtra(MainActivity.EXTRA_VALOR2, pinCode);
        startActivity(intent);
    }
}