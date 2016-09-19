package app.com.example.android.agenttagging;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.EventAdapter;
import app.com.example.android.agenttagging.model.EventModel;

public class UpcomingEvent extends AppCompatActivity {

    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<EventModel> eventModelList;
    private EventAdapter eventAdapter;
    private NavigationView nvDrawer;

    String[] title = new String[]{"Malaysia Property Show", "Nepal Radisson Hotel", "Hyatt Hotel", "Kaja Ghar", "Malaysia Property Show", "Nepal Radisson Hotel", "Hyatt Hotel", "Kaja Ghar", "Malaysia Property Show", "Nepal Radisson Hotel", "Hyatt Hotel", "Kaja Ghar"};
    String[] address = new String[]{"Marriott Hotel Singapore", "Nepal Radisson Hotel", "Hyatt Hotel", "Baneshwor", "Marriott Hotel Singapore", "Nepal Radisson Hotel", "Hyatt Hotel", "Baneshwor", "Marriott Hotel Singapore", "Nepal Radisson Hotel", "Hyatt Hotel", "Baneshwor"};
    String[] timing = new String[]{"11am - 7pm", "10am - 4pm", "10am - 5pm", "11am - 1am", "11am - 7pm", "10am - 4pm", "10am - 5pm", "11am - 1am", "11am - 7pm", "10am - 4pm", "10am - 5pm", "11am - 1am"};
    String[] date = new String[]{"Aug 15", "Sep 11", "Oct 4", "Feb 30", "Aug 15", "Sep 11", "Oct 4", "Feb 30", "Aug 15", "Sep 11", "Oct 4", "Feb 30"};
    String[] headline = new String[]{"Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
            , "Marriott International, Inc. is an American multinational diversified hospitality company"
    };
    String[] description = new String[]{"Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
            , "Marriott was founded by John Willard Marriott in 1927 when he and his wife, Alice Sheets Marriott, opened a root beer stand in Washington, D.C.\\n As a Mormon missionary in the humid summers in Washington D.C, Marriott was convinced that what residents of the city needed was a place to get a cool drink. The Marriotts later expanded their enterprise into a chain of restaurants and hotels."
    };

    String[] image = new String[]{"https://eqsolutionsmedia.files.wordpress.com/2015/08/banner-service.jpg", "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg",
            "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg",
            "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg",
            "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg",
            "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"
            , "http://www.aal-europe.eu/wp-content/uploads/2013/12/events_medium.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_event);

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

        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEvent.this, Notify.class);
                startActivity(intent);
            }
        });


        this.eventModelList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            EventModel eventModel = new EventModel();
            eventModel.setAddress(this.address[i]);
            eventModel.setDate(this.date[i]);
            eventModel.setDescription(this.description[i]);
            eventModel.setHeadline(this.headline[i]);
            eventModel.setTiming(this.timing[i]);
            eventModel.setTitle(this.title[i]);
            eventModel.setImage(this.image[i]);
            this.eventModelList.add(eventModel);
        }

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycleeventpost);
        eventAdapter = new EventAdapter(getApplicationContext(), this.eventModelList);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.property) {
                            Intent intent = new Intent(UpcomingEvent.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {

                        }

                        if (id == R.id.upcoming) {

                        }

                        if (id == R.id.setting) {

                        }

                        if (id == R.id.agents) {
                            Intent intent1 = new Intent(UpcomingEvent.this, Agent.class);
                            startActivity(intent1);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}
