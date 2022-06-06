package com.example.tirarsenha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Codigo extends AppCompatActivity {
    private TextView guiche, codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);
        Intent intent = getIntent();
        String nguiche = intent.getStringExtra(MainActivity.EXTRA_VALOR1);
        String pinCode = intent.getStringExtra(MainActivity.EXTRA_VALOR2);

        guiche = findViewById(R.id.txt_nguiche);
        guiche.setText(nguiche); //Numero de Guiche

        codigo = findViewById(R.id.txt_codigo);
        codigo.setText(pinCode); //Código

        Button btnSenha = findViewById(R.id.btn_te);
        btnSenha.setOnClickListener(v -> {
            /*Request request = new Request.Builder().url(MainActivity.api_url + "checkOut").get().build();

            MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(Codigo.this, "ERRO", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    openAtendido();
                }
            });*/
            //TODO: APAGAR ISTO

            openAtendido();
        });
    }

    public void openAtendido() {
        Intent intent = new Intent(this, Atendido.class);
        startActivity(intent);
    }
}