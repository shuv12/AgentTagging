package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.PropertyAdapter;
import app.com.example.android.agenttagging.model.PropertyModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class SearchProperty extends AppCompatActivity {

    private ImageView backBtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyModel> propertyModelList;
    private PropertyAdapter propertyAdapter;
    private String getUrl;
    private static final String GETPROPERTYPIC = "http://www.realthree60.com/dev/apis/assets/posts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_property);

        getUrl = getIntent().getExtras().getString("URL");

        new PerfromQuickSearch().execute();

        backBtn = (ImageView) findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class PerfromQuickSearch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(SearchProperty.this);
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
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    JSONArray mArray = mObject.optJSONArray("data");
                    propertyModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++) {
                        PropertyModel propertyModel = new PropertyModel();
                        JSONObject object = new JSONObject();
                        object = mArray.getJSONObject(i);
                        String propertyID = object.optString("id");
                        String purpose = object.optString("purpose");
                        String type = object.optString("type");
                        String fname = object.optString("first_name");
                        String img = object.optString("featured_img");
                        String title = object.optString("title");
                        String streetName = object.optString("street_name");
                        String askingPrice = object.optString("asking_price");
                        String floorArea = object.optString("floor_area");
                        // String priceperunit = String.valueOf(Integer.parseInt(askingPrice) / Integer.parseInt(floorArea));
                        //String faUnit = object.optString("floor_area_unit");
                        String pro_img_url = GETPROPERTYPIC + img;

                        propertyModel.setPropertyID(propertyID);
                        propertyModel.setPropertyAddress(streetName);
                        propertyModel.setPropertyHeadline(title);
                        // propertyModel.setPropertyOwner(fname);
                        propertyModel.setPropertyPurpose(purpose);
                        propertyModel.setPropertyPrice(askingPrice);
                        propertyModel.setPropertyPic(pro_img_url);
                        propertyModel.setPropertyType(type);
                        propertyModel.setPropertyArea(floorArea);
                        //propertyModel.setPropertyAreaUnit(faUnit);
                        //propertyModel.setPropertyPricePerUnit(priceperunit);
                        propertyModelList.add(propertyModel);


                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView = (RecyclerView) findViewById(R.id.recyclesearchviewpost);
                        propertyAdapter = new PropertyAdapter(getApplicationContext(), propertyModelList);
                        recyclerView.setAdapter(propertyAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                } else {
                    Toast.makeText(SearchProperty.this, "No result found", Toast.LENGTH_SHORT).show();
                    finish();
                }

            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(getUrl);

                Log.v("query", url.toString());
            } catch (Exception e) {
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


                    Log.v("Result", result.toString());
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
