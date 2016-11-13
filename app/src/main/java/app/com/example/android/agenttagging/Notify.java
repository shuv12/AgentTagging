package app.com.example.android.agenttagging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Notify extends AppCompatActivity {

    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView nvDrawer;
    private Button createListing;
    private LinearLayout viewmyprofile;
    private ImageView alwaysHome1,alwaysHome2;
    private LinearLayout drawertoMsg,drawertoNoti;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        isLogged = sharedPreferences.getBoolean(ISLOGGED,false);
        loggedUserName = sharedPreferences.getString(LOGGEDUSERNAME,null);
        loggedUserPic = sharedPreferences.getString(LOGGEDUSERPIC,null);
        String userImageUrl = GETLOGGEDUSERPICURL + loggedUserPic;

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
                    Intent intent = new Intent(Notify.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            drawertoMsg = (LinearLayout) headerlayout.findViewById(R.id.drawermessage);
            drawertoMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Notify.this, MessagePage.class);
                    startActivity(intent);
                    finish();
                }
            });

            drawertoNoti = (LinearLayout) headerlayout.findViewById(R.id.drawernotification);
            drawertoNoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Notify.this, Notify.class);
                    startActivity(intent);
                    finish();
                }
            });

            viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
            viewmyprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Notify.this, ViewProfile.class);
                    intent.putExtra("myprofile", true);
                    startActivity(intent);
                    finish();
                }
            });
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Notify.this, NewListingPageOne.class);
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
                    Intent intent = new Intent(Notify.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            TextView textView = (TextView) header.findViewById(R.id.logintext);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Notify.this, Login.class);
                    startActivity(intent);
                    finish();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });
        }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                Notify.this));

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.property) {
                            Intent intent = new Intent(Notify.this, Home.class);
                            startActivity(intent);
                            finish();
                        }

                        if (id == R.id.groupteam) {
                            Intent intent2 = new Intent(Notify.this, GroupTeam.class);
                            startActivity(intent2);
                            finish();
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(Notify.this, UpcomingEvent.class);
                            startActivity(intent1);
                            finish();
                        }

                        if (id == R.id.setting) {
                            Intent intent3 = new Intent(Notify.this, SettingPage.class);
                            startActivity(intent3);
                            finish();
                        }

                        if (id == R.id.agents) {
                            Intent intent = new Intent(Notify.this, Agent.class);
                            startActivity(intent);
                            finish();
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}
