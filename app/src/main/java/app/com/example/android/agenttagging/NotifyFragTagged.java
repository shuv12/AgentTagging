package app.com.example.android.agenttagging;


import android.app.ProgressDialog;
import android.content.Intent;
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

import app.com.example.android.agenttagging.adapter.NotifyTaggedAdapter;
import app.com.example.android.agenttagging.model.NotificationsModel;

import static android.content.Context.MODE_PRIVATE;
import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragTagged extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotificationsModel> notificationsModelList;
    String forrent = "for rent";
    String forsale = "for sale";
    String pending = "Pending";
    String approved = "Approved";
    String denied = "Denied";
    private SharedPreferences sharedPreferences;
    private Boolean isLogged;
    private String accessToken;
    private String action;
    private NotifyTaggedAdapter notifyTaggedAdapter;

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;


    public static final String ACCESSTOKENPARA = "access_token";
    public static final String NOTITAGGEDURL = "http://realthree60.com/dev/apis/TaggedNotification";
    public static final String ACCESSTOKEN = "accessToken";
    public static final String ISLOGGED = "islogged";
    public static final String UserPREFERENCES = "UserPrefs" ;
    private static final String GETPROPERTYPIC = "http://www.realthree60.com/dev/apis/assets/posts/";

    public static NotifyFragTagged newInstance(int page) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NotifyFragTagged fragment = new NotifyFragTagged();
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
        View view = inflater.inflate(R.layout.fragment_notify_frag_tagged, container, false);

        sharedPreferences = getContext().getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        accessToken = sharedPreferences.getString(ACCESSTOKEN,null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclenotiposttagged);

        if (isLogged){
            new GETTAGGEDNOTI().execute();
        }
        else {
            Toast.makeText(getContext(),"Please Login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(),Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        /*layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclenotiposttagged);
        notifyTaggedAdapter = new NotifyTaggedAdapter(getContext(), notificationsModelList);
        recyclerView.setAdapter(notifyTaggedAdapter);
        recyclerView.setLayoutManager(layoutManager);*/

        return view;
    }


    private class GETTAGGEDNOTI extends AsyncTask<String, String, String> {

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
                    JSONArray mArray = mObject.optJSONArray("data");
                    notificationsModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++) {
                        NotificationsModel notificationsModel = new NotificationsModel();
                        JSONObject object = new JSONObject();
                        object = mArray.getJSONObject(i);
                        String ID = object.optString("id");
                        String postID = object.optString("post_id");
                        String purpose = object.optString("purpose");
                        action = object.optString("action");
                        String img = object.optString("featured_img");
                        String notificationText = object.optString("notification_text");
                        String pro_img_url = GETPROPERTYPIC + img;


                        notificationsModel.setNotiforType(purpose);
                        notificationsModel.setNotiID(ID);
                        notificationsModel.setNotiPic(pro_img_url);
                        notificationsModel.setNotiproid(postID);
                        notificationsModel.setNotiStatus(action);
                        notificationsModel.setNotiText(notificationText);
                        notificationsModelList.add(notificationsModel);


                        layoutManager = new LinearLayoutManager(getContext());
                        //recyclerView = (RecyclerView) view.findViewById(R.id.recyclenotiposttagged);
                        notifyTaggedAdapter = new NotifyTaggedAdapter(getContext(), notificationsModelList);
                        recyclerView.setAdapter(notifyTaggedAdapter);
                        recyclerView.setLayoutManager(layoutManager);

                    }
                } else {
                    Toast.makeText(getContext(), "Error in fetching data", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("PropertyListing", "JSON exception", e);
            }

        }

            @Override
            protected String doInBackground(String... params) {
                try {
                    url = new URL(NOTITAGGEDURL);
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
                            .appendQueryParameter(ACCESSTOKENPARA, accessToken);
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
