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

public class NewListingPageTwo extends AppCompatActivity {

    private Button toPageThree;
    private ImageView backBtn2;
    private TextView furnished, floor, nobaths, splfeat, fnf, oispace, facilities, tenure;
    Integer[] splitems = new Integer[]{};
    Integer[] fnfitems = new Integer[]{};
    Integer[] oispaceitems = new Integer[]{};
    Integer[] facilitesitems = new Integer[]{};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing_page_two);

        toPageThree = (Button) findViewById(R.id.nexttothree);
        toPageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewListingPageTwo.this, UploadPhoto.class);
                startActivity(intent);
            }
        });

        backBtn2 = (ImageView) findViewById(R.id.backbtn2);
        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        furnished = (TextView) findViewById(R.id.furnished);
        floor = (TextView) findViewById(R.id.floor);
        nobaths = (TextView) findViewById(R.id.noofbathroom);
        splfeat = (TextView) findViewById(R.id.splf);
        fnf = (TextView) findViewById(R.id.fnf);
        oispace = (TextView) findViewById(R.id.outins);
        facilities = (TextView) findViewById(R.id.facilities);
        tenure = (TextView) findViewById(R.id.tenure);

        furnished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.furnished)
                        .items(R.array.furnishedarray)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String disfur = "<b>" + text + "</b>";
                                furnished.setText(Html.fromHtml(disfur));
                            }
                        })
                        .show();
            }
        });

        floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.floor)
                        .items(R.array.floorarray)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String dirflor = "<b>" + text + "</b>";
                                floor.setText(Html.fromHtml(dirflor));
                            }
                        })
                        .show();
            }
        });


        nobaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.noofbathroom)
                        .items("1", "2", "3", "4", "5", "6", "7", "8", "9", "9+")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String dirflor = "<b>" + text + "</b>";
                                nobaths.setText(Html.fromHtml(dirflor));
                            }
                        })
                        .show();
            }
        });


        splfeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.splf)
                        .items(R.array.splarray)
                        .itemsCallbackMultiChoice(splitems,new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                splitems = which;
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });


        fnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.fnf)
                        .items(R.array.fnfarray)
                        .itemsCallbackMultiChoice(fnfitems, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                fnfitems = which;
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });


        oispace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.pro_iospace)
                        .items(R.array.oispacearray)
                        .itemsCallbackMultiChoice(oispaceitems, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                oispaceitems = which;
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });


        facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.facilities)
                        .items(R.array.facilitiesarray)
                        .itemsCallbackMultiChoice(facilitesitems, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                facilitesitems = which;
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });

        tenure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageTwo.this)
                        .title(R.string.tenure)
                        .items(R.array.tenurearray)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String dirflor = "<b>" + text + "</b>";
                                tenure.setText(Html.fromHtml(dirflor));
                            }
                        })
                        .show();
            }
        });

    }
}
