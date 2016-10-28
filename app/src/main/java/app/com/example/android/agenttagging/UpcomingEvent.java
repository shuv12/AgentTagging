package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.com.example.android.agenttagging.adapter.EventAdapter;
import app.com.example.android.agenttagging.model.EventModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class UpcomingEvent extends AppCompatActivity {

    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<EventModel> eventModelList;
    private EventAdapter eventAdapter;
    private NavigationView nvDrawer;
    private LinearLayout viewmyprofile;
    private ImageView alwaysHome1,alwaysHome2;


    private View header, headerlayout;
    private ImageView userImageview;
    private TextView userTextview;
    private SharedPreferences sharedPreferences;
    private Boolean isLogged;
    private String loggedUserName;
    private String loggedUserPic;

    private static final String GETLOGGEDUSERPICURL = "http://www.realthree60.com/dev/apis/assets/users/";
    public static final String ISLOGGED = "islogged";
    public static final String LOGGEDUSERNAME = "loggedusername";
    public static final String LOGGEDUSERPIC = "loggeduserpic";
    public static final String UserPREFERENCES = "UserPrefs" ;


    private static final String GETUPCOMINGEVENTURL = "http://www.realthree60.com/dev/apis/events";
    private static final String EVENTPICSURL = "http://www.realthree60.com/dev/apis/assets/events/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_event);

        new GetUpcomingEvent().execute();

        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);

        recyclerView = (RecyclerView) findViewById(R.id.recycleeventpost);

        drawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSlideState) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else
                    mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        createListing = (Button) findViewById(R.id.createlisting);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        if (isLogged){
            nvDrawer.getHeaderView(0).setVisibility(View.GONE);
            headerlayout = nvDrawer.inflateHeaderView(R.layout.drawerview);

            userTextview = (TextView) headerlayout.findViewById(R.id.loggedusername);
            userImageview = (ImageView) headerlayout.findViewById(R.id.loggeduserpic);

            userTextview.setText(loggedUserName);
            Picasso.with(this).load(userImageUrl).fit().into(userImageview);

            createListing.setVisibility(View.VISIBLE);
            alwaysHome2 = (ImageView) headerlayout.findViewById(R.id.alwayshome);
            alwaysHome2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UpcomingEvent.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
            viewmyprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UpcomingEvent.this, ViewProfile.class);
                    intent.putExtra("myprofile", true);
                    startActivity(intent);
                }
            });
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(UpcomingEvent.this, NewListingPageOne.class);
                    startActivity(intent1);
                }
            });
        }
        else {
           // headerlayout.setVisibility(View.GONE);
            header = nvDrawer.getHeaderView(0);
            alwaysHome1 = (ImageView) header.findViewById(R.id.alwayshome);
            alwaysHome1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UpcomingEvent.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            TextView textView = (TextView) header.findViewById(R.id.logintext);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UpcomingEvent.this, Login.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }
        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEvent.this, Notify.class);
                startActivity(intent);
            }
        });

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.property) {
                            Intent intent = new Intent(UpcomingEvent.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {
                            Intent intent2 = new Intent(UpcomingEvent.this, GroupTeam.class);
                            startActivity(intent2);
                        }

                        if (id == R.id.upcoming) {

                        }

                        if (id == R.id.setting) {
                            Intent intent3 = new Intent(UpcomingEvent.this, SettingPage.class);
                            startActivity(intent3);
                        }

                        if (id == R.id.agents) {
                            Intent intent1 = new Intent(UpcomingEvent.this, Agent.class);
                            startActivity(intent1);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }



    private class GetUpcomingEvent extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(UpcomingEvent.this);
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
                    eventModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++)
                    {
                        EventModel eventModel = new EventModel();
                        JSONObject object = new JSONObject();
                        object = mArray.getJSONObject(i);
                        String title = object.optString("title");
                        String image = object.optString("image");
                        String location = object.optString("location");

                        String startDateTime = object.optString("start_datetime");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date startdate = sdf.parse(startDateTime);
                        String startsplit = startdate.toString();
                        String[] startstrings = startsplit.split("\\s+");
                        String startEventDate = startstrings[1] + " " + startstrings[2];
                        String startTime = startstrings[3];
                        String eventStartTime = startTime.substring(0,5);

                        String endDateTime = object.optString("end_datetime");
                        Date enddate = sdf.parse(endDateTime);
                        String endsplit = enddate.toString();
                        String[] endstrings = endsplit.split("\\s+");
                        String endEventDate = endstrings[1] + " " + endstrings[2];
                        String endTime = endstrings[3];
                        String eventEndTime = endTime.substring(0,5);

                        String eventTime = eventStartTime + " - " + eventEndTime;
                        String eventDate = startEventDate + " - " + endEventDate;
                        String detailhtml = object.optString("details");
                        String details = Html.fromHtml(detailhtml).toString();

                        String imageUrl = EVENTPICSURL + image;
                        String headline = details.substring(0, Math.min(details.length(),100)) + "..." ;

                        eventModel.setAddress(location);
                        eventModel.setDescription(details);
                        eventModel.setImage(imageUrl);
                        eventModel.setTitle(title);
                        eventModel.setTiming(eventTime);
                        eventModel.setDate(eventDate);
                        eventModel.setHeadline(headline);
                        eventModelList.add(eventModel);


                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView = (RecyclerView) findViewById(R.id.recycleeventpost);
                        eventAdapter = new EventAdapter(getApplicationContext(), eventModelList);
                        recyclerView.setAdapter(eventAdapter);
                        recyclerView.setLayoutManager(layoutManager);

                    }
                }
                else {
                    Toast.makeText(UpcomingEvent.this,"Error in fetching data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("PropertyListing", "JSON exception", e);
            } catch (ParseException e)
            {
                Log.e("Upcoming Event", "Parse exeception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(GETUPCOMINGEVENTURL);
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
                        //StringBuilder result = result.toString().substring(0, result.toString().length()-1);
                    }


                    //Log.v("Result",result.toString());
                    // Pass data to onPostExecute method
                    return (result.toString());
                    //return (result.toString().substring(0,result.toString().length()-1));

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
