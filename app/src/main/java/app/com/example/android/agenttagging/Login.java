package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button loginButton;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LOGIN_URL = "http://abinrimal.com.np/rest/userlogin.php";


    //private LinearLayout findlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail = (EditText) findViewById(R.id.userinputemail);
        editTextPassword = (EditText) findViewById(R.id.userinputpassword);


        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeLogin(v);
            }
        });
    }

    public void invokeLogin(View view) {
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        new AsyncLogin().execute(email,password);
    }

    private class AsyncLogin extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(Login.this);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                Object status_message = object.get("status_message");
                Object report = object.get("data");
                String status = status_message.toString();
                String report_msg = report.toString();
                if (status.equals("true")){
                    Toast.makeText(Login.this,"Welcome",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    //intent.putExtra(EMAIL,email);
                    startActivity(intent);
                    Login.this.finish();
                }
                else {
                    Toast.makeText(Login.this,report_msg,Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                Log.e("Agent","JSON exception",e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(LOGIN_URL);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");


                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                Log.v("query",query);
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }
    }
}