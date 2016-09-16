package app.com.example.android.agenttagging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

public class NewListingPageOne extends AppCompatActivity {
    private CheckableLinearLayout lhbd,lcondo,llanded,lbanksale;
    private Button toPageTwo;
    private ImageView backBtn1;
    private TextView textnob,taggingLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing_page_one);

        backBtn1 = (ImageView) findViewById(R.id.backbtn1);
        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toPageTwo = (Button) findViewById(R.id.nexttotwo);
        toPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewListingPageOne.this,NewListingPageTwo.class);
                startActivity(intent);
            }
        });


        textnob = (TextView) findViewById(R.id.new_nob);
        textnob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageOne.this)
                        .title(R.string.nob)
                        .items("1","2","3","4","5","6","7","8","9","9+")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String displaytext = "No. of Bedrooms : " + "<b>" + text + "</b>";
                                textnob.setText(Html.fromHtml(displaytext));
                            }
                        })
                        .show();
            }
        });

        taggingLimit = (TextView) findViewById(R.id.new_tl);
        taggingLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageOne.this)
                        .title(R.string.tagginglimit)
                        .items("5","10","15","20","25","30","50","100")
                        .itemsCallback(new MaterialDialog.ListCallback(){
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                String displayt ="Tagging limit : "+ "<b>" + text + "</b>";
                                taggingLimit.setText(Html.fromHtml(displayt));
                            }
                        })
                        .show();
            }
        });

        lbanksale = (CheckableLinearLayout) findViewById(R.id.checkablebanksale);
        lcondo = (CheckableLinearLayout) findViewById(R.id.checkablecondo);
        lhbd = (CheckableLinearLayout) findViewById(R.id.checkablehbd);
        llanded = (CheckableLinearLayout) findViewById(R.id.checkablelanded);


        llanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llanded.isChecked()){
                    lhbd.setChecked(false);
                    lcondo.setChecked(false);
                    lbanksale.setChecked(false);
                }
            }
        });

        lcondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcondo.isChecked()){
                    lhbd.setChecked(false);
                    llanded.setChecked(false);
                    lbanksale.setChecked(false);
                }
            }
        });

        lhbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lhbd.isChecked()){
                    llanded.setChecked(false);
                    lcondo.setChecked(false);
                    lbanksale.setChecked(false);
                }
            }
        });

        lbanksale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lbanksale.isChecked()){
                    lhbd.setChecked(false);
                    lcondo.setChecked(false);
                    llanded.setChecked(false);
                }
            }
        });
    }
}
