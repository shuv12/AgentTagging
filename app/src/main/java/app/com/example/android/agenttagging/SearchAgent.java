package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.AgentAdapter;
import app.com.example.android.agenttagging.model.AgentModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;




public class SearchAgent extends AppCompatActivity {

    private String agentSearchString;
    private static final String searchValue = "searchAgent";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<AgentModel> agentModelList;
    private AgentAdapter agentAdapter;
    private ImageView backBtn;
    private static final String GETAGENTPIC = "http://www.realthree60.com/dev/apis/assets/users/";
    private static final String SEARCHAGENTURL = "http://realthree60.com/dev/apis/searchAgent?name=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_agent);

        agentSearchString = getIntent().getExtras().getString(searchValue);
        new PerfromAgentSearch().execute();

        backBtn = (ImageView) findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





    private class PerfromAgentSearch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(SearchAgent.this);
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
                //JSONObject jsonObject = new JSONObject(s);
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    JSONArray mArray = mObject.optJSONArray("data");
                    agentModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++) {
                        AgentModel agentModel = new AgentModel();
                        //JSONObject object = new JSONObject();
                        JSONObject object = mArray.getJSONObject(i);
                        String id = object.optString("id");
                        String fname = object.optString("name");
                        //String email = object.optString("email");
                        String phone = object.optString("phone");
                        String userpic = object.optString("user_image");
                        String picurl = GETAGENTPIC + userpic;
                        agentModel.setAgentID(id);
                        agentModel.setAgentName(fname);
                        agentModel.setAgentNumber(phone);
                        agentModel.setAgentPic(picurl);
                        agentModelList.add(agentModel);

                        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerView = (RecyclerView) findViewById(R.id.recycleagentpost);
                        agentAdapter = new AgentAdapter(getApplicationContext(), agentModelList);
                        recyclerView.setAdapter(agentAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                }
                else {
                    Toast.makeText(SearchAgent.this,"No result found",Toast.LENGTH_SHORT).show();
                    finish();
                }

            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String encodedUrl = null;
                try {
                    encodedUrl = URLEncoder.encode(agentSearchString, "UTF-8");
                } catch (UnsupportedEncodingException ignored) {
                    // Can be safely ignored because UTF-8 is always supported
                }
                String searchUrl = SEARCHAGENTURL + encodedUrl;
                Log.v("searchUrl",searchUrl);
                url = new URL(searchUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
            } catch (IOException e1) {
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


                    Log.v("Result",result.toString());
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
