package app.com.example.android.agenttagging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class NewListingPageOne extends AppCompatActivity {
    private CheckableLinearLayout lhbd, lcondo, llanded, lbanksale;
    private Button toPageTwo;
    private ImageView backBtn1;
    private TextView textnob, taggingLimit, subTypeHDB, subTypeCondo, subTypeLaned;
    private RadioGroup purpose;
    private RadioButton isForRent,isForSale;
    private EditText newName,newDistrict,newBlockNo,newStreetName,newPostCode,leftUnit,rightUnit,newAskingPrice,BankValue,floorArea,landArea,addDescription;
    private String type = null;
    private String tglimit,nobed,pur,purr;
    private Spinner areaUnit;
    private String text;
    private LinearLayout onlyLanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing_page_one);


        subTypeCondo = (TextView) findViewById(R.id.subtypecondo);
        subTypeHDB = (TextView) findViewById(R.id.subtypehdb);
        subTypeLaned = (TextView) findViewById(R.id.subtypelanded);

        newName = (EditText) findViewById(R.id.new_name);
        newDistrict = (EditText) findViewById(R.id.new_district);
        newBlockNo = (EditText) findViewById(R.id.new_bno);
        newStreetName = (EditText) findViewById(R.id.new_sn);
        newPostCode = (EditText) findViewById(R.id.new_pc);
        leftUnit = (EditText) findViewById(R.id.left_unit);
        rightUnit = (EditText) findViewById(R.id.right_unit);
        newAskingPrice = (EditText) findViewById(R.id.new_ap);
        BankValue = (EditText) findViewById(R.id.new_obv);
        floorArea = (EditText) findViewById(R.id.new_fa);
        landArea = (EditText) findViewById(R.id.new_la);
        addDescription = (EditText) findViewById(R.id.new_addes);
        areaUnit = (Spinner) findViewById(R.id.floorareaunit);
        text = areaUnit.getSelectedItem().toString();
        onlyLanded = (LinearLayout) findViewById(R.id.onlyinland);



        backBtn1 = (ImageView) findViewById(R.id.backbtn1);
        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        isForRent = (RadioButton) findViewById(R.id.thisisforrent);
        isForSale = (RadioButton) findViewById(R.id.thisisforsale);
        //isForSale.setId(1);
        //isForRent.setId(rent_ID);

        toPageTwo = (Button) findViewById(R.id.nexttotwo);
        toPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == null){
                    Toast.makeText(NewListingPageOne.this,"Property Type is required",Toast.LENGTH_SHORT).show();
                }
                else if (newName.getText() == null){
                    Toast.makeText(NewListingPageOne.this,"Property Title is required",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(NewListingPageOne.this, NewListingPageTwo.class);
                    intent.putExtra("purpose",purr);
                    intent.putExtra("type",type);
                    intent.putExtra("title",newName.getText().toString());
                    intent.putExtra("block",newBlockNo.getText().toString());
                    intent.putExtra("district",newDistrict.getText().toString());
                    intent.putExtra("street_name",newStreetName.getText().toString());
                    intent.putExtra("postal_code",newPostCode.getText().toString());
                    intent.putExtra("unit_from",leftUnit.getText().toString());
                    intent.putExtra("unit_to",rightUnit.getText().toString());
                    intent.putExtra("asking_price",newAskingPrice.getText().toString());
                    intent.putExtra("bank_valuation",BankValue.getText().toString());
                    intent.putExtra("tagging_limit",tglimit);
                    intent.putExtra("floor_area",floorArea.getText().toString());
                    intent.putExtra("floor_area_unit", text);
                    intent.putExtra("bedroom",nobed);
                    intent.putExtra("description",addDescription.getText().toString());

                    startActivity(intent);
                }

            }
        });

        purpose = (RadioGroup) findViewById(R.id.purpose);
        purpose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = purpose.getCheckedRadioButtonId();
                View radioButton = purpose.findViewById(selectedId);
                int idx = purpose.indexOfChild(radioButton);
                Log.i("ID",String.valueOf(selectedId));
                RadioButton r = (RadioButton) purpose.getChildAt(idx);
                pur = r.getText().toString();
                Log.i("Value",pur);
                if (pur == "For Sale"){
                    purr = "Sale";
                }
                else purr = "Rent";
            }
        });


        textnob = (TextView) findViewById(R.id.new_nob);
        textnob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NewListingPageOne.this)
                        .title(R.string.nob)
                        .items("1", "2", "3", "4", "5", "6", "7", "8", "9", "9+")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String displaytext = "No. of Bedrooms : " + "<b>" + text + "</b>";
                                textnob.setText(Html.fromHtml(displaytext));
                                nobed = text.toString();
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
                        .items("5", "10", "15", "20", "25", "30", "50", "100")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                String displayt = "Tagging limit : " + "<b>" + text + "</b>";
                                taggingLimit.setText(Html.fromHtml(displayt));
                                tglimit = text.toString();
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
                if (llanded.isChecked()) {
                    lhbd.setChecked(false);
                    lcondo.setChecked(false);
                    lbanksale.setChecked(false);
                    type = "Landed";

                    onlyLanded.setVisibility(View.VISIBLE);
                    subTypeHDB.setVisibility(View.GONE);
                    subTypeLaned.setVisibility(View.VISIBLE);
                    subTypeCondo.setVisibility(View.GONE);

                    subTypeLaned.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(NewListingPageOne.this)
                                    .title(R.string.lst)
                                    .items(R.array.landedsubtypearray)
                                    .itemsCallback(new MaterialDialog.ListCallback() {
                                        @Override
                                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                            String subLanded = getResources().getString(R.string.lst) + " : " +"<b>" + text + "</b>";
                                            subTypeLaned.setText(Html.fromHtml(subLanded));
                                            //  furnishedValue = text.toString();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            }
        });

        lcondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcondo.isChecked()) {
                    lhbd.setChecked(false);
                    llanded.setChecked(false);
                    lbanksale.setChecked(false);
                    type = "Condo";


                    onlyLanded.setVisibility(View.GONE);
                    subTypeHDB.setVisibility(View.GONE);
                    subTypeLaned.setVisibility(View.GONE);
                    subTypeCondo.setVisibility(View.VISIBLE);

                    subTypeCondo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(NewListingPageOne.this)
                                    .title(R.string.cst)
                                    .items(R.array.condosubtypearray)
                                    .itemsCallback(new MaterialDialog.ListCallback() {
                                        @Override
                                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                            String subCondo = getResources().getString(R.string.cst) + " : " +"<b>" + text + "</b>";
                                            subTypeCondo.setText(Html.fromHtml(subCondo));
                                            //  furnishedValue = text.toString();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            }
        });

        lhbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lhbd.isChecked()) {
                    llanded.setChecked(false);
                    lcondo.setChecked(false);
                    lbanksale.setChecked(false);
                    type = "HDB";

                    onlyLanded.setVisibility(View.GONE);
                    subTypeHDB.setVisibility(View.VISIBLE);
                    subTypeLaned.setVisibility(View.GONE);
                    subTypeCondo.setVisibility(View.GONE);

                    subTypeHDB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(NewListingPageOne.this)
                                    .title(R.string.hdbst)
                                    .items(R.array.hdbsubtypearray)
                                    .itemsCallback(new MaterialDialog.ListCallback() {
                                        @Override
                                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                            String subHBD = getResources().getString(R.string.hdbst) + " : " +"<b>" + text + "</b>";
                                            subTypeHDB.setText(Html.fromHtml(subHBD));
                                          //  furnishedValue = text.toString();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            }
        });

        lbanksale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lbanksale.isChecked()) {
                    lhbd.setChecked(false);
                    lcondo.setChecked(false);
                    llanded.setChecked(false);
                    type = "Bank Sale";

                    onlyLanded.setVisibility(View.GONE);
                    subTypeHDB.setVisibility(View.GONE);
                    subTypeLaned.setVisibility(View.GONE);
                    subTypeCondo.setVisibility(View.GONE);
                }
            }
        });
    }
}
