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
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.GroupAdapter;
import app.com.example.android.agenttagging.model.GroupModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;
import static app.com.example.android.agenttagging.R.id.agents;

public class GroupTeam extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private NavigationView nvDrawer;
    private boolean mSlideState = false;
    private LinearLayout viewmyprofile,addAgents;
    private ImageView alwaysHome1,alwaysHome2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<GroupModel> groupModelList;
    private LinearLayout drawertoMsg,drawertoNoti;


    private ArrayList<String> agentsName = new ArrayList<String>();
    private ArrayList<String> agentsPhone = new ArrayList<String>();
    private ArrayList<String> agentsImage = new ArrayList<String>();
    private ArrayList<String> topBanner = new ArrayList<String>();
    private ArrayList<String> midBanner = new ArrayList<String>();


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

    private static final String GroupURL = "http://www.realthree60.com/dev/apis/Group";
    private static final String GETPIC = "http://www.realthree60.com/dev/apis/assets/users/";
    private static final String GETBANNER = "http://www.realthree60.com/dev/apis/assets/banners/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_team);

        new GetGroup().execute();


        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutgroup);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);
        addAgents = (LinearLayout) findViewById(R.id.addagents);

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
                    Intent intent = new Intent(GroupTeam.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    finish();
                }
            });

            drawertoMsg = (LinearLayout) headerlayout.findViewById(R.id.drawermessage);
            drawertoMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupTeam.this, MessagePage.class);
                    startActivity(intent);
                    finish();
                }
            });

            drawertoNoti = (LinearLayout) headerlayout.findViewById(R.id.drawernotification);
            drawertoNoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupTeam.this, Notify.class);
                    startActivity(intent);
                    finish();
                }
            });

            viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
            viewmyprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupTeam.this, ViewProfile.class);
                    intent.putExtra("myprofile", true);
                    startActivity(intent);
                    finish();
                }
            });
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(GroupTeam.this, NewListingPageOne.class);
                    startActivity(intent1);
                    finish();
                }
            });
        }
        else {
            //headerlayout.setVisibility(View.GONE);
            header = nvDrawer.getHeaderView(0);
            alwaysHome1 = (ImageView) header.findViewById(R.id.alwayshome);
            alwaysHome1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupTeam.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            TextView textView = (TextView) header.findViewById(R.id.logintext);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupTeam.this, Login.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }

        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupTeam.this, Notify.class);
                startActivity(intent);
                finish();
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
                            Intent intent = new Intent(GroupTeam.this, Home.class);
                            startActivity(intent);
                            finish();
                        }

                        if (id == R.id.groupteam) {

                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(GroupTeam.this, UpcomingEvent.class);
                            startActivity(intent1);
                            finish();
                        }

                        if (id == R.id.setting) {
                            Intent intent3 = new Intent(GroupTeam.this, SettingPage.class);
                            startActivity(intent3);
                            finish();
                        }

                        if (id == agents) {
                            Intent intent2 = new Intent(GroupTeam.this, Agent.class);
                            startActivity(intent2);
                            finish();
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    private class GetGroup extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(GroupTeam.this);
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
                    String dataString = mObject.optString("data");
                    JSONObject object = new JSONObject(dataString);
                    groupModelList = new ArrayList<>();
                    GroupModel groupModel = new GroupModel();

                    JSONArray bannerArray = object.optJSONArray("top-slider");
                    for (int i=0; i< bannerArray.length(); i++){
                        JSONObject bannerobj = (JSONObject)bannerArray.getJSONObject(i);
                        String bannerimage = bannerobj.optString("image");
                        String topBannerUrl = GETBANNER + bannerimage;
                        topBanner.add(topBannerUrl);
                    }




                    JSONArray midbannerArray = object.optJSONArray("mid-slider");
                    for (int i=0; i< midbannerArray.length(); i++){
                        JSONObject midbannerobj = new JSONObject();
                        midbannerobj = midbannerArray.getJSONObject(i);
                        String midbannerimage = midbannerobj.optString("image");
                        String midBannerUrl = GETBANNER + midbannerimage;
                        midBanner.add(midBannerUrl);
                    }

                    JSONArray agentsArray = object.optJSONArray("agents");
                    for (int i=0; i < agentsArray.length(); i++){
                        JSONObject agentObj = new JSONObject();
                        agentObj = agentsArray.getJSONObject(i);
                        String agent_name = agentObj.optString("name");
                        String agent_image = agentObj.optString("user_image");
                        String agent_phone = agentObj.optString("phone");
                        String agentImageUrl = GETPIC + agent_image;

                        agentsName.add(agent_name);
                        agentsPhone.add(agent_phone);
                        agentsImage.add(agentImageUrl);

                    }


                    JSONArray Desarray = object.optJSONArray("company_description");
                    JSONObject desobj = (JSONObject)Desarray.getJSONObject(0);
                    String companyDes = desobj.optString("value");

                    JSONArray RecruitArray = object.optJSONArray("company_recruitment");
                    JSONObject recruobj = (JSONObject)RecruitArray.getJSONObject(0);
                    String companyRecruit = recruobj.optString("value");

                    JSONArray contactArray = object.optJSONArray("contact");
                    JSONObject contactobj = (JSONObject)contactArray.getJSONObject(0);
                    String contactname = contactobj.optString("name");
                    String contactimage = contactobj.optString("user_image");
                    String contactphone = contactobj.optString("phone");
                    String contactImageUrl = GETPIC + contactimage;

                    groupModel.setAgentsImage(agentsImage);
                    groupModel.setAgentsName(agentsName);
                    groupModel.setAgentsPhone(agentsPhone);

                    groupModel.setCompany_des(companyDes);
                    groupModel.setCompany_recruit(companyRecruit);
                    groupModel.setContact_name(contactname);
                    groupModel.setContact_number(contactphone);
                    groupModel.setContact_pic(contactImageUrl);
                    groupModel.setTopBanner(topBanner);
                    Log.v("TopBanner : ",topBanner.toString());
                    groupModel.setMidBanner(midBanner);
                    Log.v("MidBanner : ",midBanner.toString());

                    groupModelList.add(groupModel);

                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView = (RecyclerView) findViewById(R.id.recyclegroupteam);
                    final GroupAdapter groupAdapter = new GroupAdapter(getApplicationContext(), groupModelList);
                    recyclerView.setAdapter(groupAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
                else {
                    Toast.makeText(GroupTeam.this,"Unable to fetch data",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(GroupURL);
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
