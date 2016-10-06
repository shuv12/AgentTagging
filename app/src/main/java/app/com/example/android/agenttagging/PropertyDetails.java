package app.com.example.android.agenttagging;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.example.android.agenttagging.adapter.PropertyDetailAdapter;
import app.com.example.android.agenttagging.model.PropertyDetailModel;
import de.hdodenhof.circleimageview.CircleImageView;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class PropertyDetails extends AppCompatActivity {
    private ImageView backbtn;
    private CircleImageView viewTaggedAgents;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyDetailModel> propertyDetailModelList;

    private static final String GETPROPERTYDETAILURL = "http://abinrimal.com.np/rest/GetPost/1";
    private static final String GETPROPERTYDETAILPIC = "http://abinrimal.com.np/rest/images/posts/";
    private static final String GETPROPERTYDETAILUSERPIC = "http://abinrimal.com.np/rest/images/users/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        new GetDetailProperty().execute();

        backbtn = (ImageView) findViewById(R.id.debackbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyDetails.this.finish();
            }
        });

        viewTaggedAgents = (CircleImageView) findViewById(R.id.profile_image_show_tagged);
        viewTaggedAgents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(PropertyDetails.this)
                        .title(R.string.titleagenttagged)
                        .items(R.array.tagged_agents)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            }
                        })
                        .show();
            }
        });
    }


    private class GetDetailProperty extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(PropertyDetails.this);
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
                JSONArray mArray = new JSONArray(s);
                propertyDetailModelList = new ArrayList<>();
                for (int i = 0; i < mArray.length(); i++) {
                    PropertyDetailModel propertyDetailModel = new PropertyDetailModel();
                    JSONObject object = new JSONObject();
                    object = mArray.getJSONObject(i);
                    String propertyDetailID = object.optString("id");
                    String user_id = object.optString("user_id");
                    String purpose = object.optString("purpose");
                    String type = object.optString("type");
                    String title = object.optString("title");
                    String district = object.optString("district");
                    String streetName = object.optString("street_name");
                    String postalCode = object.optString("postal_code");
                    String unitFrom = object.optString("unit_from");
                    String unitTo = object.optString("unit_to");
                    String block = object.optString("block");
                    String bathroom = object.optString("bathroom");
                    String askingPrice = object.optString("asking_price");
                    String bankValuation = object.optString("bank_valuation");
                    String taggingLimit = object.optString("tagging_limit");
                    String bedroom = object.optString("bedroom");
                    String fA = object.optString("floor_area");
                    String floorArea = fA + " sf";
                    String floorAreaUnit = object.optString("floor_area_unit");
                    String description = object.optString("description");
                    String furnished = object.optString("furnished");
                    String floor = object.optString("floor");

                    String splf = object.optString("special_features");
                    JSONArray splfJson = new JSONArray(splf);
                    String[] sF = new String[splfJson.length()];
                    for(int a=0;a<splfJson.length();a++)
                        sF[a]=splfJson.getString(a);
                    String specialFeatures = Arrays.toString(sF);
                    specialFeatures = specialFeatures.replaceAll("\\[", "").replaceAll("\\]","");

                    String ff = object.optString("fixtures_fittings");
                    JSONArray ffJson = new JSONArray(ff);
                    String[] fF = new String[ffJson.length()];
                    for (int b=0; b<ffJson.length();b++)
                        fF[b]=ffJson.getString(b);
                    String fixturesFittings = Arrays.toString(fF);
                    fixturesFittings = fixturesFittings.replaceAll("\\[", "").replaceAll("\\]","");

                    String oi = object.optString("outdoor_indoor");
                    JSONArray oiJson = new JSONArray(oi);
                    String[] oI = new String[oiJson.length()];
                    for(int c=0;c<oiJson.length();c++)
                        oI[c]=oiJson.getString(c);
                    String outdoorIndoor = Arrays.toString(oI);
                    outdoorIndoor = outdoorIndoor.replaceAll("\\[", "").replaceAll("\\]","");

                    String facc = object.optString("facilities");
                    JSONArray facJson = new JSONArray(facc);
                    String[] fac = new String[facJson.length()];
                    for (int d=0;d<facJson.length();d++)
                        fac[d] = facJson.getString(d);
                    String facilities = Arrays.toString(fac);
                    facilities = facilities.replaceAll("\\[", "").replaceAll("\\]","");

                    String tenure = object.optString("tenure");
                    String topYear = object.optString("top_year");
                    String totalUnits = object.optString("total_units");
                    String totalFloors = object.optString("total_floors");
                    String videoUrl = object.optString("video_url");
                    String gallery = object.optString("gallery");
                    String featuredImg = object.optString("featured_img");
                    String report = object.optString("report");
                    String totalView = object.optString("total_view");
                    String searchMeta = object.optString("search_meta");
                    String status = object.optString("status");
                    String createdAt = object.optString("created_at");
                    String updatedAt = object.optString("updated_at");
                    String phone = object.optString("phone");
                    String profilePhoto = object.optString("profile_photo");

                    String priceperunit = String.valueOf(Integer.parseInt(askingPrice)/Integer.parseInt(fA));
                    String unitNO = unitFrom + " to " + unitTo;
                    String pro_img_url = GETPROPERTYDETAILPIC + featuredImg;
                    String pro_user_pic_url = GETPROPERTYDETAILUSERPIC + profilePhoto;

                    propertyDetailModel.setPdff(fixturesFittings);
                    propertyDetailModel.setPdfloor(floor);
                    propertyDetailModel.setPdfurnished(furnished);
                    propertyDetailModel.setPdoispace(outdoorIndoor);
                    propertyDetailModel.setPdspl(specialFeatures);
                    propertyDetailModel.setPdtopyear(topYear);
                    propertyDetailModel.setPdtotalfloors(totalFloors);
                    propertyDetailModel.setPdtotalnounits(totalUnits);
                    propertyDetailModel.setPropertyDetailAddress(streetName);
                    propertyDetailModel.setPropertyDetailAreaUnit(floorAreaUnit);
                    propertyDetailModel.setPropertyDetailBlockno(block);
                    propertyDetailModel.setPropertyDetailDistrict(district);
                    propertyDetailModel.setPropertyDetailDescription(description);
                    propertyDetailModel.setPropertyDetailAskingPrice(askingPrice);
                   // propertyDetailModel.setPropertyDetailFacilities();
                    propertyDetailModel.setPropertyDetailNoofBedrooms(bedroom);
                    propertyDetailModel.setPropertyDetailtitle(title);
                    propertyDetailModel.setPropertyDetailPriceperUnit(priceperunit);
                    propertyDetailModel.setPropertyDetailListedDate(createdAt);
                    propertyDetailModel.setPropertyDetailPostcode(postalCode);
                    propertyDetailModel.setPropertyDetailPurpose(purpose);
                    propertyDetailModel.setPropertyNoofBathrooms(bathroom);
                    propertyDetailModel.setPropertyDetailArea(floorArea);
                    propertyDetailModel.setPropertyDetailUnitNo(unitNO);
                    propertyDetailModel.setPropertyDetailTenure(tenure);
                    propertyDetailModel.setPropertyDetailPic(pro_img_url);
                    propertyDetailModel.setPropertyUserPic(pro_user_pic_url);
                    propertyDetailModel.setPropertyDetailFacilities(facilities);
                    propertyDetailModel.setAgentnumber(phone);


                    propertyDetailModelList.add(propertyDetailModel);


                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView = (RecyclerView) findViewById(R.id.property_detail_recycler_view);
                    final PropertyDetailAdapter propertyDetailAdapter = new PropertyDetailAdapter(getApplicationContext(), propertyDetailModelList);
                    recyclerView.setAdapter(propertyDetailAdapter);
                    recyclerView.setLayoutManager(layoutManager);

                }
            } catch (JSONException e) {
                Log.e("Agent", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(GETPROPERTYDETAILURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
            } catch (IOException e1) {
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

                    //Log.v("Result",result.toString());
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
