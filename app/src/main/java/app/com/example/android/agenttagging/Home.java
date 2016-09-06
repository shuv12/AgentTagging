package app.com.example.android.agenttagging;

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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.PropertyAdapter;
import app.com.example.android.agenttagging.model.PropertyModel;

public class Home extends AppCompatActivity {
    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    //private boolean quickopen = false;
    public LinearLayout quick,quicksearchlayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyModel> propertyModelList;

    String[] propertyAddress = new String[]{"22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street"};
    String[] propertyHeadline = new String[]{"5th Avenue", "6th Avenue", "7th Avenue", "8th Avenue", "9th Avenue", "57th Avenue", "59th Avenue", "52th Avenue", "54th Avenue"};
    String[] propertyType = new String[]{"Apartment", "Bunglo", "Flat", "Open space", "Shutter", "Apartment", "HBD", "Flat", "Bunglo"};
    String[] propertyOwner = new String[]{"Shuvam", "Suman", "Abin", "Pujan", "Ashish", "Vishal", "Shuvam", "Abin", "Suman"};
    String[] propertyPrice = new String[]{"145000", "123666", "110000", "1444999", "345678", "99999", "876787", "564535", "123987"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        SearchView mSearchView = (SearchView) findViewById(R.id.search_bar);
        try
        {
            Field searchField = SearchView.class.getDeclaredField("mSearchButton");
            //searchField.setAccessible(true);
            //ImageView searchBtn = (ImageView)searchField.get(mSearchView);
            //searchBtn.setImageResource(R.drawable.search_icon);
            searchField = SearchView.class.getDeclaredField("mSearchPlate");
            //searchField.setAccessible(true);
            LinearLayout searchPlate = (LinearLayout)searchField.get(mSearchView);
            //((ImageView)searchPlate.getChildAt(0)).setImageResource(R.drawable.searchviewbg);
            searchPlate.setBackgroundResource(R.drawable.searchviewbg);
        }
        catch (NoSuchFieldException e)
        {
            Log.e("error",e.getMessage(),e);
        }
        catch (IllegalAccessException e)
        {
            Log.e("error",e.getMessage(),e);
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

        final NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        View header = nvDrawer.getHeaderView(0);
        TextView textView = (TextView) header.findViewById(R.id.logintext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nvDrawer.getHeaderView(0).setVisibility(View.GONE);
                View headerlayout = nvDrawer.inflateHeaderView(R.layout.drawerview);
            }
        });


        this.propertyModelList = new ArrayList<>();
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
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new WrappingLinearLayoutManager(getApplicationContext()));
        //recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setHasFixedSize(false);



        quick = (LinearLayout) findViewById(R.id.quicksearch);
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // quicksearchlayout.animate().translationY(quicksearchlayout.getHeight());
                quicksearchlayout.setVisibility(View.VISIBLE);
                quick.setVisibility(View.GONE);
                //final LinearLayout searchlay = (LinearLayout) findViewById(R.id.openedquicksearchlayout);
              //  if (quickopen == false) {
                    //quicksearchlayout.animate().translationY(quicksearchlayout.getHeight());
                    //quicksearchlayout.setVisibility(View.VISIBLE);
                   // quickopen = true;
               // } else {
                    //quicksearchlayout.animate().translationY(0);
                   // quicksearchlayout.setVisibility(View.GONE);
                  //  quickopen = false;

               // }

            }
        });


        Button quickcancel = (Button) findViewById(R.id.quickcancel);
        quickcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quicksearchlayout.animate().translationY(0);
                quicksearchlayout.setVisibility(View.GONE);
                quick.setVisibility(View.VISIBLE);
                //quickopen = false;
            }
        });

    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        return true;
                    }
                });
    }
}