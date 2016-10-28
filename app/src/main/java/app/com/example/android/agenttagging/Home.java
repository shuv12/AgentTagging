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
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.PropertyAdapter;
import app.com.example.android.agenttagging.model.PropertyModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class Home extends AppCompatActivity {
    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    public LinearLayout quick, quicksearchlayout, viewmyprofile;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyModel> propertyModelList;
    private NavigationView nvDrawer;
    private Button createListing;
    private ImageView alwaysHome1,alwaysHome2,userImageview;
    private TextView userTextview;
    private View header, headerlayout;
    private SearchView mSearchView;
    public LinearLayout searchPlate;
    public Field searchField;
    private RecyclerView.OnItemTouchListener disabler;
    private String listingType;
    private SharedPreferences sharedPreferences;
    private Boolean isLogged;
    private String loggedUserName;
    private String loggedUserPic;

    private static final String GETLOGGEDUSERPICURL = "http://www.realthree60.com/dev/apis/assets/users/";
    public static final String ISLOGGED = "islogged";
    public static final String LOGGEDUSERNAME = "loggedusername";
    public static final String LOGGEDUSERPIC = "loggeduserpic";
    public static final String UserPREFERENCES = "UserPrefs" ;


    private static final String GETPROPERTYURL = "http://realthree60.com/dev/apis/GetPosts";
    private static final String GETPROPERTYTYPEURL = "http://realthree60.com/dev/apis/GetPostsType";
    private static final String GETPROPERTYPIC = "http://www.realthree60.com/dev/apis/assets/posts/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listingType = getIntent().getStringExtra("type");

        new GetAllProperty().execute();

        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;


        mSearchView = (SearchView) findViewById(R.id.search_bar);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });
        try {
            //searchField = SearchView.class.getDeclaredField("mSearchButton");
            searchField = SearchView.class.getDeclaredField("mSearchPlate");
            searchPlate = (LinearLayout) searchField.get(mSearchView);
            searchPlate.setBackgroundResource(R.drawable.searchviewbg);
        } catch (NoSuchFieldException e) {
            Log.e("error", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e("error", e.getMessage(), e);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);

        recyclerView = (RecyclerView) findViewById(R.id.recycleviewpost);

        quicksearchlayout = (LinearLayout) findViewById(R.id.openedquicksearchlayout);

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

       /* header = nvDrawer.getHeaderView(0);
        alwaysHome1 = (ImageView) header.findViewById(R.id.alwayshome);
        alwaysHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FrontPage.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });*/


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
                    Intent intent = new Intent(Home.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
            viewmyprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, ViewProfile.class);
                    intent.putExtra("myprofile", true);
                    startActivity(intent);
                }
            });
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Home.this, NewListingPageOne.class);
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
                    Intent intent = new Intent(Home.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            TextView textView = (TextView) header.findViewById(R.id.logintext);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, Login.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

        }

        /*TextView textView = (TextView) header.findViewById(R.id.logintext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nvDrawer.getHeaderView(0).setVisibility(View.GONE);
                headerlayout = nvDrawer.inflateHeaderView(R.layout.drawerview);
                createListing.setVisibility(View.VISIBLE);
                alwaysHome2 = (ImageView) headerlayout.findViewById(R.id.alwayshome);
                alwaysHome2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, FrontPage.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
                viewmyprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, ViewProfile.class);
                        intent.putExtra("myprofile", true);
                        startActivity(intent);
                    }
                });
                createListing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Home.this, NewListingPageOne.class);
                        startActivity(intent1);
                    }
                });
            }
        });*/


        disabler = new RecyclerViewDisabler();


        quick = (LinearLayout) findViewById(R.id.quicksearch);
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quicksearchlayout.setVisibility(View.VISIBLE);
                quick.setVisibility(View.GONE);
                recyclerView.addOnItemTouchListener(disabler);
            }
        });


        Button quickcancel = (Button) findViewById(R.id.quickcancel);
        quickcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quicksearchlayout.setVisibility(View.GONE);
                quick.setVisibility(View.VISIBLE);
                recyclerView.removeOnItemTouchListener(disabler);
            }
        });


        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Notify.class);
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
                            Intent intent = new Intent(Home.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {
                            Intent intent2 = new Intent(Home.this, GroupTeam.class);
                            startActivity(intent2);
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(Home.this, UpcomingEvent.class);
                            startActivity(intent1);
                        }

                        if (id == R.id.setting) {
                            Intent intent3 = new Intent(Home.this, SettingPage.class);
                            startActivity(intent3);
                        }

                        if (id == R.id.agents) {
                            Intent intent = new Intent(Home.this, Agent.class);
                            startActivity(intent);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }


    private class GetAllProperty extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Home.this);
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
                        recyclerView = (RecyclerView) findViewById(R.id.recycleviewpost);
                        final PropertyAdapter propertyAdapter = new PropertyAdapter(getApplicationContext(), propertyModelList);
                        recyclerView.setAdapter(propertyAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                }
                else {
                    Toast.makeText(Home.this,"Error in fetching data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("PropertyListing", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                if (listingType == null){
                    url = new URL(GETPROPERTYURL);
                }
                else {
                    String propertyUrlWType = GETPROPERTYTYPEURL + "/" + listingType;
                    //Log.v("PropertyUrl",propertyUrlWType);
                    url = new URL(propertyUrlWType);
                }

                //Url:  http://realthree60.com/dev/apis/GetPostsType/{condo/HDB/Bank Sale/Landed}
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