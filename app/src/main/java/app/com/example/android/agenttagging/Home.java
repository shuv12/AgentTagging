package app.com.example.android.agenttagging;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

    private boolean quickopen = false;
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
        //FrameLayout recycleFrame = (FrameLayout) findViewById(R.id.recycleframe);



        //final Fragment QuickSearchFragment = getSupportFragmentManager().findFragmentById(R.id.fragent_quick_search);

        /*recycleFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quickopen == true){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    QuickSearch obj = (QuickSearch)getSupportFragmentManager().findFragmentById(R.id.quicksearch_placeholder);
                    ft.hide(obj);
                    ft.commit();
                    quickopen = false;
                }
            }
        });*/



        final LinearLayout quick = (LinearLayout) findViewById(R.id.quicksearch);
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (quickopen == false){
                    ft.replace(R.id.quicksearch_placeholder, new QuickSearch(),"tag");
                    ft.commit();
                    quickopen = true;
                }
                else{
                    QuickSearch obj = (QuickSearch)getSupportFragmentManager().findFragmentById(R.id.quicksearch_placeholder);
                    ft.hide(obj);
                    ft.commit();
                    quickopen = false;
                }

            }
        });

    }

    /*public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }*/
}
