package app.com.example.android.agenttagging;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.PropertyAdapter;
import app.com.example.android.agenttagging.model.PropertyModel;

import static android.content.Context.MODE_PRIVATE;
import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyFragAll extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private String agentDetailID;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyModel> propertyModelList;
    private String userAccessToken;
    private static final String AcessToken = "access_token";
    private static final String UserPREFERENCES = "UserPrefs" ;
    private static final String ACCESSTOKEN = "accessToken";
    private static final String GETMYDETAILSURL = "http://realthree60.com/dev/apis/Agent";
    private static final String GETPROPERTYPIC = "http://www.realthree60.com/dev/apis/assets/posts/";
    private PropertyAdapter propertyAdapter;

    private static final String GETAGENTSDETAILSURL = " http://realthree60.com/dev/apis/Agent/";


    public static PropertyFragAll newInstance(int page) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PropertyFragAll fragment = new PropertyFragAll();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_frag_all, container, false);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        userAccessToken = sharedPreferences.getString(ACCESSTOKEN,null);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclepropertyall);

        Boolean myProfile = getActivity().getIntent().getExtras().getBoolean("myprofile",false);

        if (myProfile){
            new GetMyDetails().execute();
        }
        else {
            agentDetailID = getActivity().getIntent().getExtras().getString("agentdetailID");
            new GetAgentsDetails().execute();
        }
        /*if (userAccessToken == null){
            Toast.makeText(getContext(),"Please Login First",Toast.LENGTH_SHORT).show();
        }
        else {
            new GetMyDetails().execute();
        }*/

        return view;
    }

    private class GetMyDetails extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(getContext());
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
                    JSONArray mArray = mObject.optJSONArray("all-listing");
                    propertyModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++) {
                        PropertyModel propertyModel = new PropertyModel();
                        JSONObject object = new JSONObject();
                        object = mArray.getJSONObject(i);
                        String propertyID = object.optString("id");
                        String purpose = object.optString("purpose");
                        String type = object.optString("type");
                        String img = object.optString("featured_img");
                        String title = object.optString("title");
                        String streetName = object.optString("street_name");
                        String askingPrice = object.optString("asking_price");
                        String floorArea = object.optString("floor_area");
                        String faUnit = object.optString("floor_area_unit");
                        String pro_img_url = GETPROPERTYPIC + img;

                        propertyModel.setPropertyID(propertyID);
                        propertyModel.setPropertyAddress(streetName);
                        propertyModel.setPropertyHeadline(title);
                        propertyModel.setPropertyPurpose(purpose);
                        propertyModel.setPropertyPrice(askingPrice);
                        propertyModel.setPropertyPic(pro_img_url);
                        propertyModel.setPropertyType(type);
                        propertyModel.setPropertyArea(floorArea);
                        propertyModel.setPropertyAreaUnit(faUnit);
                        propertyModelList.add(propertyModel);


                        propertyAdapter = new PropertyAdapter(getContext(), propertyModelList);
                        recyclerView.setAdapter(propertyAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Error in fetching data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("PropertyListing", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(GETMYDETAILSURL);
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
                        .appendQueryParameter(AcessToken, userAccessToken);
                String query = builder.build().getEncodedQuery();

               // Log.v("query", query);
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

                     //Log.v("Login Data",result.toString());
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


    private class GetAgentsDetails extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(getContext());
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
                    JSONArray mArray = mObject.optJSONArray("all-listing");
                    propertyModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++) {
                        PropertyModel propertyModel = new PropertyModel();
                        JSONObject object = new JSONObject();
                        object = mArray.getJSONObject(i);
                        String propertyID = object.optString("id");
                        String purpose = object.optString("purpose");
                        String type = object.optString("type");
                        String img = object.optString("featured_img");
                        String title = object.optString("title");
                        String streetName = object.optString("street_name");
                        String askingPrice = object.optString("asking_price");
                        String floorArea = object.optString("floor_area");
                       // String priceperunit = String.valueOf(Integer.parseInt(askingPrice) / Integer.parseInt(floorArea));
                        String faUnit = object.optString("floor_area_unit");
                        String pro_img_url = GETPROPERTYPIC + img;

                        propertyModel.setPropertyID(propertyID);
                        propertyModel.setPropertyAddress(streetName);
                        propertyModel.setPropertyHeadline(title);
                        propertyModel.setPropertyPurpose(purpose);
                        propertyModel.setPropertyPrice(askingPrice);
                        propertyModel.setPropertyPic(pro_img_url);
                        propertyModel.setPropertyType(type);
                        propertyModel.setPropertyArea(floorArea);
                        propertyModel.setPropertyAreaUnit(faUnit);
                       // propertyModel.setPropertyPricePerUnit(priceperunit);
                        propertyModelList.add(propertyModel);


                        propertyAdapter = new PropertyAdapter(getContext(), propertyModelList);
                        recyclerView.setAdapter(propertyAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Error in fetching data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("PropertyListing", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String agentDetailUrl = GETAGENTSDETAILSURL + agentDetailID;
                url = new URL(agentDetailUrl);
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
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
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

                    //Log.v("Login Data",result.toString());
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
