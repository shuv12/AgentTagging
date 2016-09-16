package app.com.example.android.agenttagging;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.AgentAdapter;
import app.com.example.android.agenttagging.model.AgentModel;

public class Agent extends AppCompatActivity {

    private boolean mSlideState = false;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<AgentModel> agentModelList;
    private DrawerLayout mDrawerLayout;
    private Button createListing;
    private NavigationView nvDrawer;
    private Button viewAgentProfile;

    String[] agentName = new String[]{"Ari Gold","Shuvam Agrawal","Ganesh Kumar","Suman Jung","Abin Rimal","Ashish","Vishal","Pujan","Shuvam","Ganesh","Suman","Abin","Ashish","Vishal","Pujan","Ari Gold"};
    String[] agentNum = new String[]{"+81-98986557","+977-8785636228","+91-8870639465","+81-001199111","+81-98986557","+977-8785636228","+91-8870639465","+81-001199111","+81-98986557","+977-8785636228","+91-8870639465","+81-001199111","+81-98986557","+977-8785636228","+91-8870639465","+81-001199111"};
    String[] agentPic = new String[]{"http://www.montagio.com.au/display.php?o=1753","http://www.montagio.com.au/display.php?o=1747"
            ,"http://static.socialitelife.com/uploads/2011/09/jeremy-piven-ari-gold-shopping-09302011-06-675x900.jpg"
            ,"https://s-media-cache-ak0.pinimg.com/236x/6c/41/3b/6c413bfa0ce3aaa185698b8408397df1.jpg"
            ,"http://cdn.styleforum.net/f/f5/350x197px-f54a2ac8_suit4.png"
            ,"https://s-media-cache-ak0.pinimg.com/736x/e0/a9/9c/e0a99c0993aa2de40e99df6388fc14ad.jpg"
            ,"http://www.montagio.com.au/display.php?o=1748","http://www.montagio.com.au/display.php?o=1753"
            ,"http://www.montagio.com.au/display.php?o=1747"
            ,"http://static.socialitelife.com/uploads/2011/09/jeremy-piven-ari-gold-shopping-09302011-06-675x900.jpg"
            ,"https://s-media-cache-ak0.pinimg.com/236x/6c/41/3b/6c413bfa0ce3aaa185698b8408397df1.jpg"
            ,"http://cdn.styleforum.net/f/f5/350x197px-f54a2ac8_suit4.png"
            ,"https://s-media-cache-ak0.pinimg.com/736x/e0/a9/9c/e0a99c0993aa2de40e99df6388fc14ad.jpg"
            ,"http://www.montagio.com.au/display.php?o=1748","http://www.montagio.com.au/display.php?o=1753"
            ,"http://www.montagio.com.au/display.php?o=1747"
            ,"http://static.socialitelife.com/uploads/2011/09/jeremy-piven-ari-gold-shopping-09302011-06-675x900.jpg"
            ,"https://s-media-cache-ak0.pinimg.com/236x/6c/41/3b/6c413bfa0ce3aaa185698b8408397df1.jpg"
            ,"http://cdn.styleforum.net/f/f5/350x197px-f54a2ac8_suit4.png"
            ,"https://s-media-cache-ak0.pinimg.com/736x/e0/a9/9c/e0a99c0993aa2de40e99df6388fc14ad.jpg"
            ,"http://www.montagio.com.au/display.php?o=1748"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);


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



        this.agentModelList = new ArrayList<>();
        for (int i=0; i<16; i++){
            AgentModel agentModel = new AgentModel();
            agentModel.setAgentName(this.agentName[i]);
            agentModel.setAgentNumber(this.agentNum[i]);
            agentModel.setAgentPic(this.agentPic[i]);
            this.agentModelList.add(agentModel);
        }

        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView = (RecyclerView) findViewById(R.id.recycleagentpost);
        final AgentAdapter agentAdapter = new AgentAdapter(getApplicationContext(), this.agentModelList);
        recyclerView.setAdapter(agentAdapter);
        recyclerView.setLayoutManager(layoutManager);


        /*viewAgentProfile = (Button) recyclerView.findViewById(R.id.viewagentprofile);
        viewAgentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agent.this,ViewProfile.class);
                startActivity(intent);
            }
        });*/

        ImageView notibtn = (ImageView) findViewById(R.id.notificationbtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agent.this, Notify.class);
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

                        if (id == R.id.property){
                            Intent intent = new Intent(Agent.this,Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam){

                        }

                        if (id == R.id.upcoming){

                        }

                        if (id == R.id.setting){

                        }

                        if (id == R.id.agents){
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}
