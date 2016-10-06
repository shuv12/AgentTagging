package app.com.example.android.agenttagging;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FrontPage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private NavigationView nvDrawer;
    private boolean mSlideState = false;
    private LinearLayout viewmyprofile;
    private SearchView searchView;
    private ImageView alwaysHome1,alwaysHome2;
    private LinearLayout frontHBD,frontCondo,frontLanded,frontBankSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

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


        frontBankSale = (LinearLayout) findViewById(R.id.front_banksale);
        frontCondo = (LinearLayout) findViewById(R.id.front_condo);
        frontHBD = (LinearLayout) findViewById(R.id.front_hbd);
        frontLanded = (LinearLayout) findViewById(R.id.front_landed);

        frontHBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                startActivity(intent);
            }
        });

        frontCondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                startActivity(intent);
            }
        });

        frontLanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                startActivity(intent);
            }
        });

        frontBankSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,Home.class);
                startActivity(intent);
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

        View header = nvDrawer.getHeaderView(0);
        alwaysHome1 = (ImageView) header.findViewById(R.id.alwayshome);
        alwaysHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
                viewmyprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FrontPage.this, ViewProfile.class);
                        intent.putExtra("myprofile", true);
                        startActivity(intent);
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
        });

        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, Notify.class);
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
                            Intent intent = new Intent(FrontPage.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {
                            Intent intent3 = new Intent(FrontPage.this, GroupTeam.class);
                            startActivity(intent3);
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(FrontPage.this, UpcomingEvent.class);
                            startActivity(intent1);
                        }

                        if (id == R.id.setting) {

                        }

                        if (id == R.id.agents) {
                            Intent intent2 = new Intent(FrontPage.this, Agent.class);
                            startActivity(intent2);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}

