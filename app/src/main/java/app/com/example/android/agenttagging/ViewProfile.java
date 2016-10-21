package app.com.example.android.agenttagging;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class ViewProfile extends AppCompatActivity {

    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView nvDrawer;
    private Button createListing, messageme, updateme;
    private TextView titleName,profileName,profileNumber,editProfileName, editProfileNumber;
    private EditText searchText;
    private ImageView searchIcon,crossIcon,callAgent;
    private LinearLayout viewmyprofile,searchLayout,toolbarLayout;
    private ImageView alwaysHome1,alwaysHome2,editProfileImage, profilePic;
    private String userToken;
    private SharedPreferences sharedPreferences;


    private View header, headerlayout;
    private ImageView userImageview;
    private TextView userTextview;
    private Boolean isLogged;
    private String loggedUserName;
    private String loggedUserPic;


    private static final String GETLOGGEDUSERPICURL = "http://www.realthree60.com/dev/apis/assets/users/";
    public static final String ISLOGGED = "islogged";
    public static final String LOGGEDUSERNAME = "loggedusername";
    public static final String LOGGEDUSERPIC = "loggeduserpic";
    public static final String UserPREFERENCES = "UserPrefs" ;
    public static final String ACCESSTOKEN = "accessToken";
    public static final String TOTALRENT = "totalrent";
    public static final String TOTALSALE = "totalsale";
    public static final String TOTALPROPERTY = "totalproperty";
    private static final String AcessToken = "access_token";
    private static final String GETMYDETAILSURL = "http://realthree60.com/dev/apis/Agent";
    private static final String GETPROFILEPICURL = "http://www.realthree60.com/dev/apis/assets/users/";
    private String userName, userPhone, pro_img_url, totalSale, totalRent, totalProperty;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);

        profilePic = (ImageView) findViewById(R.id.profilepic);
        profileName = (TextView) findViewById(R.id.profilename);
        profileNumber = (TextView) findViewById(R.id.profilenumber);

        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        userToken = sharedPreferences.getString(ACCESSTOKEN,null);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;

        if (userToken == null){
            Toast.makeText(ViewProfile.this,"Please Login First",Toast.LENGTH_SHORT).show();
        }
        else {
            new GetMyDetails().execute();
        }


        callAgent = (ImageView) findViewById(R.id.callthisagent);
        searchText = (EditText) findViewById(R.id.searchtext);
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        crossIcon = (ImageView) findViewById(R.id.crossIcon);
        toolbarLayout = (LinearLayout) findViewById(R.id.toolbar_layout);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        messageme = (Button) findViewById(R.id.messageme);
        updateme = (Button) findViewById(R.id.updateprofile);
        titleName = (TextView) findViewById(R.id.titlename);

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


        drawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSlideState) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else
                    mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Boolean myprofile = extras.getBoolean("myprofile");
            if (myprofile == true) {
                messageme.setVisibility(View.GONE);
                updateme.setVisibility(View.VISIBLE);
                searchIcon.setVisibility(View.VISIBLE);
                callAgent.setVisibility(View.GONE);

                updateme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wrapInScrollView = true;
                        final MaterialDialog dialog = new MaterialDialog.Builder(ViewProfile.this)
                                .customView(R.layout.profileupdateboxlayout, wrapInScrollView)
                                .show();
                        View view = dialog.getCustomView();
                        editProfileImage = (ImageView) view.findViewById(R.id.editprofilepic);
                        editProfileName = (TextView) view.findViewById(R.id.editprofilename);
                        editProfileNumber = (TextView) view.findViewById(R.id.editprofileno);

                        editProfileName.setText(profileName.getText());
                        editProfileNumber.setText(profileNumber.getText());
                        editProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        editProfileImage.setImageDrawable(profilePic.getDrawable());

                        editProfileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    if (ContextCompat.checkSelfPermission(ViewProfile.this,
                                            Manifest.permission.READ_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(ViewProfile.this,
                                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        } else {
                                            ActivityCompat.requestPermissions(ViewProfile.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    SELECT_PICTURE);
                                        }
                                    } else {
                                        ActivityCompat.requestPermissions(ViewProfile.this,
                                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                SELECT_PICTURE);
                                    }
                                } else {
                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                    photoPickerIntent.setType("image/*");
                                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                                }
                            }
                        });
                        Button saveButton = (Button) view.findViewById(R.id.saveupdate);
                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                titleName.setText(getString(R.string.mypro));
            }
        }

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
                    Intent intent = new Intent(ViewProfile.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
            viewmyprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewProfile.this, ViewProfile.class);
                    intent.putExtra("myprofile", true);
                    startActivity(intent);
                }
            });
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(ViewProfile.this, NewListingPageOne.class);
                    startActivity(intent1);
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
                    Intent intent = new Intent(ViewProfile.this, FrontPage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            TextView textView = (TextView) header.findViewById(R.id.logintext);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewProfile.this, Login.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ProfileFragmentPagerAdapter pagerAdapter = new ProfileFragmentPagerAdapter(getSupportFragmentManager(),
                ViewProfile.this);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SELECT_PICTURE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
            } else {
            }
            return;
        }
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        editProfileImage.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.property) {
                            Intent intent = new Intent(ViewProfile.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {
                            Intent intent2 = new Intent(ViewProfile.this, GroupTeam.class);
                            startActivity(intent2);
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(ViewProfile.this, UpcomingEvent.class);
                            startActivity(intent1);
                        }

                        if (id == R.id.setting) {
                            Intent intent3 = new Intent(ViewProfile.this, SettingPage.class);
                            startActivity(intent3);
                        }

                        if (id == R.id.agents) {
                            Intent intent = new Intent(ViewProfile.this, Agent.class);
                            startActivity(intent);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    private class GetMyDetails extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(ViewProfile.this);
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
                    JSONArray mArray = mObject.optJSONArray("user-detail");
                    JSONObject object = new JSONObject();
                    object = mArray.getJSONObject(0);
                    String userID = object.optString("id");
                    userName = object.optString("name");
                    String userEmail = object.optString("email");
                    String userImage = object.optString("user_image");
                    userPhone = object.optString("phone");
                    totalSale = object.optString("total_sale");
                    totalRent = object.optString("total_rent");
                    int totalP = Integer.parseInt(totalRent) + Integer.parseInt(totalSale);
                    totalProperty = Integer.toString(totalP);
                    pro_img_url = GETPROFILEPICURL + userImage;

                    profileName.setText(userName);
                    profileNumber.setText(userPhone);
                    Picasso.with(ViewProfile.this).load(pro_img_url).fit().into(profilePic);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(TOTALPROPERTY,totalProperty);
                    editor.putString(TOTALRENT,totalRent);
                    editor.putString(TOTALSALE,totalSale);
                    editor.apply();

                }
                else {
                    Toast.makeText(ViewProfile.this,"Error in fetching data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("ViewProfile", "JSON exception", e);
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
                        .appendQueryParameter(AcessToken, userToken);
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

                    //Log.v("ViewProfileData",result.toString());
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

