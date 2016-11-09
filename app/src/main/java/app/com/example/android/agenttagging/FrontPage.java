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
import android.support.v7.widget.SearchView;
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

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class FrontPage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private NavigationView nvDrawer;
    private boolean mSlideState = false;
    private LinearLayout viewmyprofile;
    private SearchView searchView;
    private ImageView alwaysHome1,alwaysHome2;
    private LinearLayout frontHBD,frontCondo,frontLanded,frontBankSale;
   // private LinearLayout bannerBk;
    private ImageView bannerDisplay;

    private View header, headerlayout;
    private ImageView userImageview;
    private TextView userTextview;
    private SharedPreferences sharedPreferences;
    private Boolean isLogged;
    private String loggedUserName;
    private String loggedUserPic;
    private LinearLayout drawertoNoti,drawertoMsg;
    private ArrayList<String> hpBanners = new ArrayList<String>();


    private static final String GETBANNERURL = "http://www.realthree60.com/dev/apis/assets/banners/";
    private static final String HOMEPAGEBANNER = "http://realthree60.com/dev/apis/HomeBanner";
    private static final int delay = 5000;
    private int imageCount = 0;
    private android.os.Handler handler = new android.os.Handler();


    private static final String GETLOGGEDUSERPICURL = "http://www.realthree60.com/dev/apis/assets/users/";
    public static final String ISLOGGED = "islogged";
    public static final String LOGGEDUSERNAME = "loggedusername";
    public static final String LOGGEDUSERPIC = "loggeduserpic";
    public static final String UserPREFERENCES = "UserPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutfront);
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


        //bannerBk = (LinearLayout) findViewById(R.id.banner);
        bannerDisplay = (ImageView) findViewById(R.id.banner_display);

        new GetBanner().execute();
       /* AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(ContextCompat.getDrawable(this,R.drawable.banner_bk), 5000);
        animation.addFrame(ContextCompat.getDrawable(this,R.drawable.banner_1), 5000);
        animation.addFrame(ContextCompat.getDrawable(this,R.drawable.agent_banner), 5000);
        animation.setOneShot(false);

        LinearLayout imageAnim =  (LinearLayout) findViewById(R.id.banner);
        imageAnim.setBackgroundDrawable(animation);

        // start the animation!
        animation.start();*/

        frontBankSale = (LinearLayout) findViewById(R.id.front_banksale);
        frontCondo = (LinearLayout) findViewById(R.id.front_condo);
        frontHBD = (LinearLayout) findViewById(R.id.front_hbd);
        frontLanded = (LinearLayout) findViewById(R.id.front_landed);

        frontHBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                intent.putExtra("type","HDB");
                startActivity(intent);
                finish();
            }
        });

        frontCondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                intent.putExtra("type","condo");
                startActivity(intent);
                finish();
            }
        });

        frontLanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                intent.putExtra("type","Landed");
                startActivity(intent);
                finish();
            }
        });

        frontBankSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                intent.putExtra("type","BankSale");
                startActivity(intent);
                finish();
            }
        });




        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setQueryHint("Search by agent, property, location etc");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
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
                    Intent intent = new Intent(FrontPage.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });



            drawertoMsg = (LinearLayout) headerlayout.findViewById(R.id.drawermessage);
            drawertoMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FrontPage.this, MessagePage.class);
                    startActivity(intent);
                    finish();
                }
            });

            drawertoNoti = (LinearLayout) headerlayout.findViewById(R.id.drawernotification);
            drawertoNoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FrontPage.this, Notify.class);
                    startActivity(intent);
                    finish();
                }
            });




            viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
            viewmyprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FrontPage.this, ViewProfile.class);
                    intent.putExtra("myprofile", true);
                    startActivity(intent);
                    finish();
                }
            });
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(FrontPage.this, NewListingPageOne.class);
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
                    Intent intent = new Intent(FrontPage.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            TextView textView = (TextView) header.findViewById(R.id.logintext);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FrontPage.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

        }

        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, Notify.class);
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
                            Intent intent = new Intent(FrontPage.this, Home.class);
                            startActivity(intent);
                            finish();
                        }

                        if (id == R.id.groupteam) {
                            Intent intent3 = new Intent(FrontPage.this, GroupTeam.class);
                            startActivity(intent3);
                            finish();
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(FrontPage.this, UpcomingEvent.class);
                            startActivity(intent1);
                            finish();
                        }

                        if (id == R.id.setting) {
                            Intent intent4 = new Intent(FrontPage.this, SettingPage.class);
                            startActivity(intent4);
                            finish();
                        }

                        if (id == R.id.agents) {
                            Intent intent2 = new Intent(FrontPage.this, Agent.class);
                            startActivity(intent2);
                            finish();
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }




    private class GetBanner extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(FrontPage.this);
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
                    JSONObject data = mObject.optJSONObject("data");
                    JSONArray sliderImage = data.optJSONArray("slider-image");
                    for (int i = 0; i < sliderImage.length(); i++) {
                        //AgentModel agentModel = new AgentModel();
                        JSONObject object = new JSONObject();
                        object = sliderImage.getJSONObject(i);
                        String im = object.optString("image");
                        String image = GETBANNERURL + im;

                        hpBanners.add(image);
                    }
                    //Log.v("hpBanners : ", hpBanners.toString());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (imageCount == hpBanners.size()-1){
                                imageCount = 0;
                            }
                            else imageCount++;

                            Picasso.with(FrontPage.this).load(hpBanners.get(imageCount)).fit().into(bannerDisplay);
                          /*  Picasso.with(FrontPage.this).load(hpBanners.get(imageCount)).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    bannerBk.setBackground(new BitmapDrawable(getResources(),bitmap));
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    Log.d("TAG","FAILED");
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    Log.d("TAGG","Prepare Load");
                                }
                            });*/

                            handler.postDelayed(this,delay);
                        }
                    },delay);
                }
                else {
                    Toast.makeText(FrontPage.this,"Unable to get banners",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(HOMEPAGEBANNER);
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


                    //Log.v("Homepage Banners : ",result.toString());
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

