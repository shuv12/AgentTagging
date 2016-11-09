package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

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
    private CheckBox rememberPassword;
    private ImageView thisBack;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public static final String UserPREFERENCES = "UserPrefs";
    public static final String UserPREFERENCESS = "UserPref";
    public static final String ACCESSTOKEN = "accessToken";
    public static final String ISLOGGED = "islogged";
    public static final String EDITTEXTUSERNAME = "saveusername";
    public static final String EDITTEXTPASSWORD = "savepassword";
    public static final String LOGGEDUSERNAME = "loggedusername";
    public static final String LOGGEDUSERPIC = "loggeduserpic";
    public static final String LOGGEDUSERID = "loggeduserid";

    private SharedPreferences sharedPreferencess;
    private SharedPreferences.Editor editors;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String email;
    private String password;

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


        thisBack = (ImageView) findViewById(R.id.thisback);
        rememberPassword = (CheckBox) findViewById(R.id.rememberpassword);
        editTextEmail = (EditText) findViewById(R.id.userinputemail);
        editTextPassword = (EditText) findViewById(R.id.userinputpassword);
        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        String istoken = sharedPreferences.getString(ACCESSTOKEN,null);
        if (istoken == null){
            sharedPreferencess = getSharedPreferences(UserPREFERENCESS,MODE_PRIVATE);
            editTextEmail.setText(sharedPreferencess.getString(EDITTEXTUSERNAME,null));
            editTextPassword.setText(sharedPreferencess.getString(EDITTEXTPASSWORD,null));
        }


        thisBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        forgetPass = (TextView) findViewById(R.id.forgetpass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(Login.this)
                        .title(R.string.enteremail)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .input(R.string.emailhint, R.string.a, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                new MaterialDialog.Builder(Login.this)
                                        .content(R.string.checkemail)
                                        .positiveText(R.string.ok)
                                        .show();
                                //Intent intent1 = new Intent(Login.this, FrontPage.class);
                                //startActivity(intent1);
                                //finish();
                            }
                        })
                        .show();
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
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

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
                   // String userToken = object.optString("access_token");
                    JSONObject data = object.optJSONObject("data");
                    JSONArray userDetail = data.optJSONArray("user-details");
                    JSONObject userObject = userDetail.getJSONObject(0);
                    String userID = userObject.optString("id");
                    String userName = userObject.optString("name");
                    String userEmail = userObject.optString("email");
                    String userImage = userObject.optString("user_image");
                    String userPhone = userObject.optString("phone");
                    String userToken = data.optString("access_token");
                    //String totalSale = userObject.optString("total_sale");
                    //String totalRent = userObject.optString("total_rent");
                    //String totalProperty = String.valueOf(Integer.parseInt(totalSale) + Integer.parseInt(totalRent));
                    sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(ACCESSTOKEN, userToken);
                    editor.putBoolean(ISLOGGED,true);
                    editor.putString(LOGGEDUSERNAME,userName);
                    editor.putString(LOGGEDUSERPIC,userImage);
                    editor.putString(LOGGEDUSERID,userID);
                    editor.apply();
                    if (rememberPassword.isChecked()){
                        sharedPreferencess = getSharedPreferences(UserPREFERENCESS,MODE_PRIVATE);
                        editors = sharedPreferencess.edit();
                        editors.putString(EDITTEXTUSERNAME, email);
                        editors.putString(EDITTEXTPASSWORD,password);
                        editors.apply();
                    }
                    else {
                        sharedPreferencess = getSharedPreferences(UserPREFERENCESS,MODE_PRIVATE);
                        editors = sharedPreferencess.edit();
                        editors.clear();
                        editors.apply();
                    }
                    //editor.apply();
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