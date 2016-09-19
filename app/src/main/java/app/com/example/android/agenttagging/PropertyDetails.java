package app.com.example.android.agenttagging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PropertyDetails extends AppCompatActivity {
    private Button Show_more,Show_less;
    private ImageView backbtn;
    private LinearLayout show_more_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        Show_more = (Button) findViewById(R.id.show_more_button);
        Show_less = (Button) findViewById(R.id.show_less);
        show_more_layout = (LinearLayout) findViewById(R.id.show_more);
        backbtn = (ImageView) findViewById(R.id.debackbtn);

        Show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show_more.setVisibility(View.GONE);
                show_more_layout.setVisibility(View.VISIBLE);
            }
        });

        Show_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show_more.setVisibility(View.VISIBLE);
                show_more_layout.setVisibility(View.GONE);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyDetails.this.finish();
            }
        });
    }
}
