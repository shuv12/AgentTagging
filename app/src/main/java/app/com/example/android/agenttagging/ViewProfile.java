package app.com.example.android.agenttagging;

import android.content.Intent;
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
import android.widget.TextView;

public class ViewProfile extends AppCompatActivity {

    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView nvDrawer;
    private Button createListing,messageme,updateme;
    private TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);


        messageme = (Button) findViewById(R.id.messageme);
        updateme = (Button) findViewById(R.id.updateprofile);
        titleName = (TextView) findViewById(R.id.titlename);

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
        if (extras != null){
            Boolean myprofile = extras.getBoolean("myprofile");
            if (myprofile == true){
                messageme.setVisibility(View.GONE);
                updateme.setVisibility(View.VISIBLE);
                titleName.setText(getString(R.string.mypro));
            }
        }

        createListing = (Button) findViewById(R.id.createlisting);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        View header = nvDrawer.getHeaderView(0);
        TextView textView = (TextView) header.findViewById(R.id.logintext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nvDrawer.getHeaderView(0).setVisibility(View.GONE);
                View headerlayout = nvDrawer.inflateHeaderView(R.layout.drawerview);
                createListing.setVisibility(View.VISIBLE);
            }
        });



        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ProfileFragmentPagerAdapter pagerAdapter = new ProfileFragmentPagerAdapter(getSupportFragmentManager(),
                ViewProfile.this);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.property){
                            Intent intent = new Intent(ViewProfile.this,Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam){

                        }

                        if (id == R.id.upcoming){

                        }

                        if (id == R.id.setting){

                        }

                        if (id == R.id.agents){
                            Intent intent = new Intent(ViewProfile.this,Agent.class);
                            startActivity(intent);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}

