package pt.fcup.tirarsenha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class GetTicketRequestTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {
        String my_url = params[0];
        String my_data = params[1];
        try {
            URL url = new URL(my_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // setting the  Request Method Type
            httpURLConnection.setRequestMethod("POST");
            // adding the headers for request
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            try {
                //to tell the connection object that we will be wrting some data on the server and then will fetch the output result
                httpURLConnection.setDoOutput(true);
                // this is used for just in case we don't know about the data size associated with our request
                httpURLConnection.setChunkedStreamingMode(0);

                // to write tha data in our request
                /*
                OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(my_data);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                */

                // to log the response code of your request
                Log.d("INFO", "MyHttpRequestTask doInBackground : " + httpURLConnection.getResponseCode());
                // to log the response message from your server after you have tried the request.
                Log.d("INFO", "MyHttpRequestTask doInBackground : " + httpURLConnection.getResponseMessage());


                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();


                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tirarSenhaButton = findViewById(R.id.btnTirarSenha);

        tirarSenhaButton.setOnClickListener(v -> {
            try {
                String url = " http://192.168.1.68:8080/getTicket";
                                
                //String result = new GetTicketRequestTask().execute(url, "None").get();

                //final TextView numeroAtribuidoTextView = findViewById(R.id.numeroAtribuido);
                //numeroAtribuidoTextView.setText(result);

                final TextView helpTextView = findViewById(R.id.helperText);
                helpTextView.setText(url);

                //helpTextView.setText("O seu número é:");

            } catch (Exception e) {
                System.out.println(e);
            } 
        });
    }
}