package app.com.example.android.agenttagging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.PropertyAdapter;
import app.com.example.android.agenttagging.model.PropertyModel;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyModel> propertyModelList;

    String[] propertyAddress = new String[]{"22nd Jump Street","23nd Jump Street","24nd Jump Street","22nd Jump Street","23nd Jump Street","24nd Jump Street","22nd Jump Street","23nd Jump Street","24nd Jump Street"};
    String[] propertyHeadline = new String[]{"5th Avenue","6th Avenue","7th Avenue","8th Avenue","9th Avenue","57th Avenue","59th Avenue","52th Avenue","54th Avenue"};
    String[] propertyType = new String[]{"Apartment","Bunglo","Flat","Open space","Shutter","Apartment","HBD","Flat","Bunglo"};
    String[] propertyOwner = new String[]{"Shuvam","Suman","Abin","Pujan","Ashish","Vishal","Shuvam","Abin","Suman"};
    String[] propertyPrice = new String[]{"145000","123666","110000","1444999","345678","99999","876787","564535","123987"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycleviewpost);
        final PropertyAdapter propertyAdapter = new PropertyAdapter(getApplicationContext(), this.propertyModelList);
        recyclerView.setAdapter(propertyAdapter);
        recyclerView.setLayoutManager(layoutManager);


        LinearLayout quick = (LinearLayout) findViewById(R.id.quicksearch);
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Quick.class);
                startActivity(intent);
            }
        });

       /* LinearLayout quick = (LinearLayout) findViewById(R.id.quicksearch);
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.quicksearchfrag, new QuickSearch()).commit();
            }
        });
    */
    }
}
