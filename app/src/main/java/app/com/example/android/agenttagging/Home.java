package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
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
    private ImageView alwaysHome1,alwaysHome2;
    private View header, headerlayout;
    private SearchView mSearchView;
    private LinearLayout searchPlate;

    private static final String GETPROPERTYURL = "http://abinrimal.com.np/rest/GetPosts";
    private static final String GETPROPERTYPIC = "http://abinrimal.com.np/rest/images/posts/";



    /*String[] propertyAddress = new String[]{"22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street"};
    String[] propertyHeadline = new String[]{"5th Avenue", "6th Avenue", "7th Avenue", "8th Avenue", "9th Avenue", "57th Avenue", "59th Avenue", "52th Avenue", "54th Avenue"};
    String[] propertyType = new String[]{"Apartment", "Bunglo", "Flat", "Open space", "Shutter", "Apartment", "HBD", "Flat", "Bunglo"};
    String[] propertyOwner = new String[]{"Shuvam", "Suman", "Abin", "Pujan", "Ashish", "Vishal", "Shuvam", "Abin", "Suman"};
    String[] propertyPrice = new String[]{"145000", "123666", "110000", "1444999", "345678", "99999", "876787", "564535", "123987"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new GetAllProperty().execute();

        mSearchView = (SearchView) findViewById(R.id.search_bar);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });
        try {
            Field searchField = SearchView.class.getDeclaredField("mSearchButton");
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


        /*@Override
        public void onDrawerSlide(View drawerView, float offset) {
            View container = findViewById(R.id.container);
            container.setTranslationX(offset * drawerView.getWidth());
        }*/

        createListing = (Button) findViewById(R.id.createlisting);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

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
        });


        /*this.propertyModelList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            PropertyModel propertyModel = new PropertyModel();
            propertyModel.setPropertyAddress(this.propertyAddress[i]);
            propertyModel.setPropertyHeadline(this.propertyHeadline[i]);
            propertyModel.setPropertyOwner(this.propertyOwner[i]);
            propertyModel.setPropertyPrice(this.propertyPrice[i]);
            propertyModel.setPropertyType(this.propertyType[i]);
            this.propertyModelList.add(propertyModel);
        }

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycleviewpost);
        final PropertyAdapter propertyAdapter = new PropertyAdapter(getApplicationContext(), this.propertyModelList);
        recyclerView.setAdapter(propertyAdapter);
        recyclerView.setLayoutManager(layoutManager);*/
        final RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();


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
                //JSONObject ss = new JSONObject(s.substring(s.indexOf("{"), s.lastIndexOf("}") + 1));
                //JSONObject ss = new JSONObject(s);
                JSONArray mArray = new JSONArray(s);
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
                    String priceperunit = String.valueOf(Integer.parseInt(askingPrice)/Integer.parseInt(floorArea));
                    String faUnit = object.optString("floor_area_unit");
                    String pro_img_url = GETPROPERTYPIC + img;
                    propertyModel.setPropertyAddress(streetName);
                    propertyModel.setPropertyHeadline(title);
                   // propertyModel.setPropertyOwner(fname);
                    propertyModel.setPropertyPurpose(purpose);
                    propertyModel.setPropertyPrice(askingPrice);
                    propertyModel.setPropertyPic(pro_img_url);
                    propertyModel.setPropertyType(type);
                    propertyModel.setPropertyArea(floorArea);
                    propertyModel.setPropertyAreaUnit(faUnit);
                    propertyModel.setPropertyPricePerUnit(priceperunit);
                    propertyModelList.add(propertyModel);


                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView = (RecyclerView) findViewById(R.id.recycleviewpost);
                    final PropertyAdapter propertyAdapter = new PropertyAdapter(getApplicationContext(), propertyModelList);
                    recyclerView.setAdapter(propertyAdapter);
                    recyclerView.setLayoutManager(layoutManager);

                }
            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(GETPROPERTYURL);
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