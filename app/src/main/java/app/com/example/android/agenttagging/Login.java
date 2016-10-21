package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
    private TextView forgetPass;
    private ImageView imageView;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public static final String UserPREFERENCES = "UserPrefs" ;
    public static final String ACCESSTOKEN = "accessToken";
    public static final String ISLOGGED = "islogged";
    public static final String LOGGEDUSERNAME = "loggedusername";
    public static final String LOGGEDUSERPIC = "loggeduserpic";



    private static final String LOGINFAILED = "Check username and password!";
    private static final String GRANTYPEVAL = "password";
    private static final String CLIENTIDVAL = "testclient";
    private static final String CLIENTSECRETVAL = "testpass";
    private static final String GRANTYPE = "grant_type";
    private static final String CLIENTID = "client_id";
    private static final String CLIENTSECRET = "client_secret";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String LOGIN_URL = "http://realthree60.com/dev/apis/AgentLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail = (EditText) findViewById(R.id.userinputemail);
        editTextPassword = (EditText) findViewById(R.id.userinputpassword);


        forgetPass = (TextView) findViewById(R.id.forgetpass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Login.this, FrontPage.class);
                startActivity(intent1);
            }
        });

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

        new AsyncLogin().execute(email, password);
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {

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
                Boolean success = object.optBoolean("Success");
                if (success)
                {
                    String userToken = object.optString("access_token");
                    JSONArray userDetail = object.optJSONArray("user-data");
                    JSONObject userObject = userDetail.getJSONObject(0);
                    String userID = userObject.optString("id");
                    String userName = userObject.optString("name");
                    String userEmail = userObject.optString("email");
                    String userImage = userObject.optString("user_image");
                    String userPhone = userObject.optString("phone");
                    SharedPreferences sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ACCESSTOKEN, userToken);
                    editor.putBoolean(ISLOGGED,true);
                    editor.putString(LOGGEDUSERNAME,userName);
                    editor.putString(LOGGEDUSERPIC,userImage);
                    editor.apply();
                    Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    Login.this.finish();
                }
                else {
                    Toast.makeText(Login.this, LOGINFAILED, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
                Toast.makeText(Login.this, LOGINFAILED, Toast.LENGTH_SHORT).show();

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
                        .appendQueryParameter(USERNAME, params[0])
                        .appendQueryParameter(PASSWORD, params[1])
                        .appendQueryParameter(GRANTYPE, GRANTYPEVAL)
                        .appendQueryParameter(CLIENTID,CLIENTIDVAL)
                        .appendQueryParameter(CLIENTSECRET,CLIENTSECRETVAL);
                String query = builder.build().getEncodedQuery();

                Log.v("query", query);
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

                   // Log.v("Login Data",result.toString());
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