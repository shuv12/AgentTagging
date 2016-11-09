package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;
import static app.com.example.android.agenttagging.R.array.splarray;

public class NewListingPageTwo extends AppCompatActivity {

    private Button toPageThree;
    private ImageView backBtn2;
    private TextView furnished, floor, nobaths, splfeat, fnf, oispace, facilities, tenure;
    private EditText topYear,totalUnits,totalFloor,videoUrl;
    Integer[] splitems = new Integer[]{};
    Integer[] fnfitems = new Integer[]{};
    Integer[] oispaceitems = new Integer[]{};
    Integer[] facilitesitems = new Integer[]{};
    List<String> facilitiesArray = new ArrayList<String>();
    List<String> splFeatArray = new ArrayList<String>();
    List<String> fnfArray = new ArrayList<String>();
    List<String> oIArray = new ArrayList<String>();


    public static final String UserPREFERENCES = "UserPrefs" ;
    public static final String ACCESSTOKEN = "accessToken";
    private String userToken;
    private SharedPreferences sharedPreferences;
    private static final String AcessToken = "access_token";
    //String[] facilitiesArray = new String[20];
    //String[] splFeatArray = new String[12];
    //String[] fnfArray = new String[8];
    //String[] oIArray = new String[9];

    String finalJson;

    private static final String CREATEPOSTURL = "http://realthree60.com/dev/apis/Post";

    private String purposeValue,typeValue,titleValue,blockValue,districtValue,streetnameValue,postalcodeValue,unitfromValue,unittoValue
            ,askingpriceValue,bankvaluationValue,tagginglimitValue,bedroomValue,floorAreaValue,floorareaunitValue,descriptionValue
            ,furnishedValue,floorValue,bathroomValue,tenureValue,topYearValue,totalUnitsValue,totalFloorValue,videoUrlValue;

    private static final String PURPOSE = "purpose";
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private static final String BLOCK = "block";
    private static final String DISTRICT = "district";
    private static final String STREETNAME = "street_name";
    private static final String POSTALCODE = "postal_code";
    private static final String UNITFROM = "unit_from";
    private static final String UNITTO = "unit_to";
    private static final String ASKINGPRICE = "asking_price";
    private static final String BANKVALUATION = "bank_valuation";
    private static final String TAGGINGLIMIT = "tagging_limit";
    private static final String BEDROOM = "bedroom";
    private static final String FLOORAREA = "floor_area";
    private static final String FLOORAREAUNIT = "floor_area_unit";
    private static final String DESCRIPTION = "description";
    private static final String FURNISHED = "furnished";
    private static final String FLOOR = "floor";
    private static final String BATHROOM = "bathroom";
    private static final String SPECIALFEATURES = "special_features";
    private static final String FIXTURESFITTINGS = "fixtures_fittings";
    private static final String OUTDOORINDOOR = "outdoor_indoor";
    private static final String FACILITIES = "facilities";
    private static final String TENURE = "tenure";
    private static final String TOPYEAR = "top_year";
    private static final String TOTALUNITS = "total_units";
    private static final String TOTALFLOORS = "total_floors";
    private static final String VIDEOURL = "video_url";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing_page_two);

        toPageThree = (Button) findViewById(R.id.nexttothree);
        toPageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(NewListingPageTwo.this, UploadPhoto.class);
                //startActivity(intent);
                sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
                userToken = sharedPreferences.getString(ACCESSTOKEN,null);
                Bundle extra = getIntent().getExtras();
                purposeValue = extra.getString("purpose");
                typeValue = extra.getString("type");
                titleValue = extra.getString("title");
                blockValue = extra.getString("block");
                districtValue = extra.getString("district");
                streetnameValue = extra.getString("street_name");
                postalcodeValue = extra.getString("postal_code");
                unitfromValue = extra.getString("unit_from");
                unittoValue = extra.getString("unit_to");
                askingpriceValue = extra.getString("asking_price");
                bankvaluationValue = extra.getString("bank_valuation");
                tagginglimitValue = extra.getString("tagging_limit");
                bedroomValue = extra.getString("bedroom");
                floorAreaValue = extra.getString("floor_area");
                floorareaunitValue = extra.getString("floor_area_unit");
                descriptionValue = extra.getString("description");
                topYearValue = topYear.getText().toString();
                totalFloorValue = totalFloor.getText().toString();
                totalUnitsValue = totalUnits.getText().toString();
                videoUrlValue = videoUrl.getText().toString();
                new SendNewCreatePost().execute();
            }
        });


        topYear = (EditText) findViewById(R.id.topyr);
        totalFloor = (EditText) findViewById(R.id.tof);
        totalUnits = (EditText) findViewById(R.id.tou);
        videoUrl = (EditText) findViewById(R.id.videourl);



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
                                String disfur = getResources().getString(R.string.furnished) + " : " +"<b>" + text + "</b>";
                                furnished.setText(Html.fromHtml(disfur));
                                furnishedValue = text.toString();
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
                                String dirflor = getResources().getString(R.string.floor) + " : " + "<b>" + text + "</b>";
                                floor.setText(Html.fromHtml(dirflor));
                                floorValue = text.toString();
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
                                String dirflor = getResources().getString(R.string.noofbathroom) + " : "+ "<b>" + text + "</b>";
                                nobaths.setText(Html.fromHtml(dirflor));
                                bathroomValue = text.toString();
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
                        .items(splarray)
                        .itemsCallbackMultiChoice(splitems,new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                splitems = which;
                                String dirflor = getResources().getString(R.string.splf) + " : " + "<b>" + which.length + "</b>";
                                splfeat.setText(Html.fromHtml(dirflor));

                                for (int i=0;i<text.length;i++){
                                    splFeatArray.add(text[i].toString());
                                    //splFeatArray[i] = text[i].toString();
                                }

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
                                String dirflor = getResources().getString(R.string.fnf) + " : " + "<b>" + which.length + "</b>";
                                fnf.setText(Html.fromHtml(dirflor));

                                for (int i=0;i<text.length;i++){
                                    fnfArray.add(text[i].toString());
                                    //fnfArray[i] = text[i].toString();
                                }

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
                                String dirflor = getResources().getString(R.string.pro_iospace) + " : " + "<b>" + which.length + "</b>";
                                oispace.setText(Html.fromHtml(dirflor));

                                for (int i=0;i<text.length;i++){
                                    oIArray.add(text[i].toString());
                                    //oIArray[i] = text[i].toString();
                                }

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
                                String dirflor = getResources().getString(R.string.facilities) + " : " + "<b>" + which.length + "</b>";
                                facilities.setText(Html.fromHtml(dirflor));

                                for (int i=0;i<text.length;i++){
                                    facilitiesArray.add(text[i].toString());
                                   //facilitiesArray[i] = text[i].toString();
                                }

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
                                String dirflor = getResources().getString(R.string.tenure) + " : " + "<b>" + text + "</b>";
                                tenure.setText(Html.fromHtml(dirflor));
                                tenureValue = text.toString();
                            }
                        })
                        .show();
            }
        });

    }

    private class SendNewCreatePost extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(NewListingPageTwo.this);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                Boolean success = object.optBoolean("Success");
                if (success)
                {
                    JSONObject jsonObject = object.optJSONObject("data");
                    String newPostID = jsonObject.optString("post_id");
                    Toast.makeText(NewListingPageTwo.this, "Post successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewListingPageTwo.this, UploadPhoto.class);
                    intent.putExtra("newpostid",newPostID);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(NewListingPageTwo.this, "SOMEthing went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("CreatePost", "JSON exception", e);
                Toast.makeText(NewListingPageTwo.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(CREATEPOSTURL);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");


                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);


                JSONArray array1 = new JSONArray();
                for (int i = 0 ; i < splFeatArray.size() ; i++){
                    array1.put(splFeatArray.get(i));
                }
                //jsonObject.accumulate(SPECIALFEATURES,array1);
                JSONArray array2 = new JSONArray();
                for (int i = 0 ; i < fnfArray.size() ; i++){
                    array2.put(fnfArray.get(i));
                }
               // jsonObject.accumulate(FIXTURESFITTINGS,array2);
                JSONArray array3 = new JSONArray();
                for (int i = 0 ; i < oIArray.size() ; i++){
                    array3.put(oIArray.get(i));
                }
                //jsonObject.accumulate(OUTDOORINDOOR,array3);
                JSONArray array4 = new JSONArray();
                for (int i = 0 ; i < facilitiesArray.size() ; i++){
                    array4.put(facilitiesArray.get(i));
                }
                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(AcessToken,userToken)
                        .appendQueryParameter(PURPOSE, purposeValue)
                        .appendQueryParameter(TYPE,typeValue)
                        .appendQueryParameter(TITLE,titleValue)
                        .appendQueryParameter(BLOCK,blockValue)
                        .appendQueryParameter(DISTRICT,districtValue)
                        .appendQueryParameter(STREETNAME,streetnameValue)
                        .appendQueryParameter(POSTALCODE,postalcodeValue)
                        .appendQueryParameter(UNITFROM,unitfromValue)
                        .appendQueryParameter(UNITTO,unittoValue)
                        .appendQueryParameter(ASKINGPRICE,askingpriceValue)
                        .appendQueryParameter(BANKVALUATION,bankvaluationValue)
                        .appendQueryParameter(TAGGINGLIMIT,tagginglimitValue)
                        .appendQueryParameter(BEDROOM,bedroomValue)
                        .appendQueryParameter(FLOORAREA,floorAreaValue)
                        .appendQueryParameter(FLOORAREAUNIT,floorareaunitValue)
                        .appendQueryParameter(DESCRIPTION,descriptionValue)
                        .appendQueryParameter(FURNISHED,furnishedValue)
                        .appendQueryParameter(FLOOR,floorValue)
                        .appendQueryParameter(BATHROOM,bathroomValue)
                        .appendQueryParameter(SPECIALFEATURES,array1.toString())
                        .appendQueryParameter(FIXTURESFITTINGS,array2.toString())
                        .appendQueryParameter(OUTDOORINDOOR,array3.toString())
                        .appendQueryParameter(FACILITIES,array4.toString())
                        .appendQueryParameter(TENURE,tenureValue)
                        .appendQueryParameter(TOPYEAR,topYearValue)
                        .appendQueryParameter(TOTALUNITS,totalUnitsValue)
                        .appendQueryParameter(TOTALFLOORS,totalFloorValue)
                        .appendQueryParameter(VIDEOURL,videoUrlValue);

                String query = builder.build().getEncodedQuery();

                Log.v("Json send : ", query);
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                     Log.v("postedresult",result.toString());
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }
    }

}
