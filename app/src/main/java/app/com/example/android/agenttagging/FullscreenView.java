package app.com.example.android.agenttagging;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import app.com.example.android.agenttagging.adapter.FullScreenImageAdapter;

public class FullscreenView extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        images = getIntent().getStringArrayListExtra("Images");

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new FullScreenImageAdapter(FullscreenView.this,images));
    }
}
