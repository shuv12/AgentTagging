package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.AgentAdapter;
import app.com.example.android.agenttagging.model.AgentModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class Agent extends AppCompatActivity {

    private boolean mSlideState = false;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<AgentModel> agentModelList;
    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private NavigationView nvDrawer;
    private LinearLayout viewmyprofile;
    private ImageView alwaysHome1, alwaysHome2;

    private static final String GETAGENTURL = "http://abinrimal.com.np/rest/GetAgents";
    private static final String GETAGENTPIC = "http://abinrimal.com.np/rest/images/users/";

    /*String[] agentName = new String[]{"Ari Gold", "Shuvam Agrawal", "Ganesh Kumar", "Suman Jung", "Abin Rimal", "Ashish", "Vishal", "Pujan", "Shuvam", "Ganesh", "Suman", "Abin", "Ashish", "Vishal", "Pujan", "Ari Gold"};
    String[] agentNum = new String[]{"+81-98986557", "+977-8785636228", "+91-8870639465", "+81-001199111", "+81-98986557", "+977-8785636228", "+91-8870639465", "+81-001199111", "+81-98986557", "+977-8785636228", "+91-8870639465", "+81-001199111", "+81-98986557", "+977-8785636228", "+91-8870639465", "+81-001199111"};
    String[] agentPic = new String[]{"http://www.montagio.com.au/display.php?o=1753", "http://www.montagio.com.au/display.php?o=1747"
            , "http://static.socialitelife.com/uploads/2011/09/jeremy-piven-ari-gold-shopping-09302011-06-675x900.jpg"
            , "https://s-media-cache-ak0.pinimg.com/236x/6c/41/3b/6c413bfa0ce3aaa185698b8408397df1.jpg"
            , "http://cdn.styleforum.net/f/f5/350x197px-f54a2ac8_suit4.png"
            , "https://s-media-cache-ak0.pinimg.com/736x/e0/a9/9c/e0a99c0993aa2de40e99df6388fc14ad.jpg"
            , "http://www.montagio.com.au/display.php?o=1748", "http://www.montagio.com.au/display.php?o=1753"
            , "http://www.montagio.com.au/display.php?o=1747"
            , "http://static.socialitelife.com/uploads/2011/09/jeremy-piven-ari-gold-shopping-09302011-06-675x900.jpg"
            , "https://s-media-cache-ak0.pinimg.com/236x/6c/41/3b/6c413bfa0ce3aaa185698b8408397df1.jpg"
            , "http://cdn.styleforum.net/f/f5/350x197px-f54a2ac8_suit4.png"
            , "https://s-media-cache-ak0.pinimg.com/736x/e0/a9/9c/e0a99c0993aa2de40e99df6388fc14ad.jpg"
            , "http://www.montagio.com.au/display.php?o=1748", "http://www.montagio.com.au/display.php?o=1753"
            , "http://www.montagio.com.au/display.php?o=1747"
            , "http://static.socialitelife.com/uploads/2011/09/jeremy-piven-ari-gold-shopping-09302011-06-675x900.jpg"
            , "https://s-media-cache-ak0.pinimg.com/236x/6c/41/3b/6c413bfa0ce3aaa185698b8408397df1.jpg"
            , "http://cdn.styleforum.net/f/f5/350x197px-f54a2ac8_suit4.png"
            , "https://s-media-cache-ak0.pinimg.com/736x/e0/a9/9c/e0a99c0993aa2de40e99df6388fc14ad.jpg"
            , "http://www.montagio.com.au/display.php?o=1748"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        new GetAllAgents().execute();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);

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

        View header = nvDrawer.getHeaderView(0);
        alwaysHome1 = (ImageView) header.findViewById(R.id.alwayshome);
        alwaysHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agent.this, FrontPage.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        TextView textView = (TextView) header.findViewById(R.id.logintext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nvDrawer.getHeaderView(0).setVisibility(View.GONE);
                View headerlayout = nvDrawer.inflateHeaderView(R.layout.drawerview);
                createListing.setVisibility(View.VISIBLE);
                alwaysHome2 = (ImageView) headerlayout.findViewById(R.id.alwayshome);
                alwaysHome2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Agent.this, FrontPage.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
                viewmyprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Agent.this, ViewProfile.class);
                        intent.putExtra("myprofile", true);
                        startActivity(intent);
                    }
                });
                createListing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Agent.this, NewListingPageOne.class);
                        startActivity(intent1);
                    }
                });
            }
        });


       /* this.agentModelList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            AgentModel agentModel = new AgentModel();
            agentModel.setAgentName(this.agentName[i]);
            agentModel.setAgentNumber(this.agentNum[i]);
            agentModel.setAgentPic(this.agentPic[i]);
            this.agentModelList.add(agentModel);
        }*/

        /*layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView = (RecyclerView) findViewById(R.id.recycleagentpost);
        final AgentAdapter agentAdapter = new AgentAdapter(getApplicationContext(), this.agentModelList);
        recyclerView.setAdapter(agentAdapter);
        recyclerView.setLayoutManager(layoutManager);*/


        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agent.this, Notify.class);
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
                            Intent intent = new Intent(Agent.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {
                            Intent intent2 = new Intent(Agent.this, GroupTeam.class);
                            startActivity(intent2);
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(Agent.this, UpcomingEvent.class);
                            startActivity(intent1);
                        }

                        if (id == R.id.setting) {

                        }

                        if (id == R.id.agents) {
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }



    private class GetAllAgents extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Agent.this);
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
                JSONArray mArray = new JSONArray(s);
                agentModelList = new ArrayList<>();
                for (int i = 0; i < mArray.length(); i++) {
                    AgentModel agentModel = new AgentModel();
                    JSONObject object = new JSONObject();
                    object = mArray.getJSONObject(i);
                    String fname = object.optString("first_name");
                    String lname = object.optString("last_name");
                    String phone = object.optString("phone");
                    String userpic = object.optString("profile_photo");
                    String picurl = GETAGENTPIC + userpic;
                    agentModel.setAgentName(fname + " " + lname);
                    agentModel.setAgentNumber(phone);
                    agentModel.setAgentPic(picurl);
                    agentModelList.add(agentModel);

                    layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView = (RecyclerView) findViewById(R.id.recycleagentpost);
                    final AgentAdapter agentAdapter = new AgentAdapter(getApplicationContext(), agentModelList);
                    recyclerView.setAdapter(agentAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(GETAGENTURL);
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


                    //Log.v("Result",result.toString());
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