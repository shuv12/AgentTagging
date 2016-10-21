package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
    private LinearLayout viewmyprofile, toolbarLayout, searchLayout;
    private ImageView alwaysHome1, alwaysHome2, searchIcon, crossIcon;
    private EditText searchText;

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

    private static final String GETAGENTURL = "http://www.realthree60.com/dev/apis/GetAgents";
    private static final String GETAGENTPIC = "http://www.realthree60.com/dev/apis/assets/users/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        new GetAllAgents().execute();


        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;


        searchText = (EditText) findViewById(R.id.searchtext);
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        crossIcon = (ImageView) findViewById(R.id.crossIcon);
        toolbarLayout = (LinearLayout) findViewById(R.id.toolbar_layout);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);



        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                searchText.setText("");
                searchText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);

                searchText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            //performSearch();
                            return true;
                        }
                        return false;
                    }
                });

            }
        });

        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.GONE);
                toolbarLayout.setVisibility(View.VISIBLE);
                searchText.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
            }
        });



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
        else {
            headerlayout.setVisibility(View.GONE);
            header = nvDrawer.getHeaderView(0);
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
                    Intent intent = new Intent(Agent.this, Login.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

        }
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
                            Intent intent3 = new Intent(Agent.this, SettingPage.class);
                            startActivity(intent3);
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
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    JSONArray mArray = mObject.optJSONArray("data");
                    agentModelList = new ArrayList<>();
                    for (int i = 0; i < mArray.length(); i++) {
                        AgentModel agentModel = new AgentModel();
                        JSONObject object = new JSONObject();
                        object = mArray.getJSONObject(i);
                        String fname = object.optString("name");
                        String email = object.optString("email");
                        String phone = object.optString("phone");
                        String userpic = object.optString("user_image");
                        String picurl = GETAGENTPIC + userpic;
                        agentModel.setAgentName(fname);
                        agentModel.setAgentNumber(phone);
                        agentModel.setAgentPic(picurl);
                        agentModelList.add(agentModel);

                        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerView = (RecyclerView) findViewById(R.id.recycleagentpost);
                        final AgentAdapter agentAdapter = new AgentAdapter(getApplicationContext(), agentModelList);
                        recyclerView.setAdapter(agentAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                }
                else {
                    Toast.makeText(Agent.this,"Unable to fetch data",Toast.LENGTH_SHORT).show();
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