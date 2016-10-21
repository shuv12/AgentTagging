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
import android.widget.Toast;

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
    private ImageView backbtn, taggedbtn;
    private CircleImageView viewTaggedAgents;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyDetailModel> propertyDetailModelList;
    private String propertyID;


    private static final String GETPROPERTYDETAILURL = "http://realthree60.com/dev/apis/GetPost/";
    private static final String GETPROPERTYDETAILPIC = "http://www.realthree60.com/dev/apis/assets/posts/";
    private static final String GETPROPERTYDETAILUSERPIC = "http://www.realthree60.com/dev/apis/assets/users/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyID = getIntent().getStringExtra("PropertyID");

        new GetDetailProperty().execute();

        backbtn = (ImageView) findViewById(R.id.debackbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyDetails.this.finish();
            }
        });

        taggedbtn = (ImageView) findViewById(R.id.profile_show_tagged);
        taggedbtn.setOnClickListener(new View.OnClickListener() {
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
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    propertyDetailModelList = new ArrayList<>();
                    PropertyDetailModel propertyDetailModel = new PropertyDetailModel();
                    JSONArray topSliderArray = mObject.optJSONArray("top-slider");
                    JSONObject topSlider = (JSONObject)topSliderArray.getJSONObject(0);
                    String tsgString = topSlider.optString("gallery");
                    JSONArray tsgArray = new JSONArray(tsgString);
                    String topSlider1 = tsgArray.getString(0);
                    String topSlider2 = tsgArray.getString(1);


                    JSONArray userDetailsArray = mObject.optJSONArray("user_details");
                    JSONObject userDetailsObject = (JSONObject)userDetailsArray.getJSONObject(0);
                    String userId = userDetailsObject.optString("id");
                    String userImage = userDetailsObject.optString("user_image");
                    String userPhone = userDetailsObject.optString("phone");

                    JSONArray postHeaderArray = mObject.optJSONArray("post-header");
                    JSONObject postHeaderObject = (JSONObject)postHeaderArray.getJSONObject(0);
                    String title = postHeaderObject.optString("title");
                    String streetName = postHeaderObject.optString("street_name");
                    String askingPrice = postHeaderObject.optString("asking_price");
                    String bedroom = postHeaderObject.optString("bedroom");
                    String bathroom = postHeaderObject.optString("bathroom");
                    String fA = postHeaderObject.optString("floor_area");
                    String floorArea = fA + " sf";
                    String floorAreaUnit = postHeaderObject.optString("floor_area_unit");
                    String landArea = postHeaderObject.optString("land_area");
                    String landAreaUnit = postHeaderObject.optString("land_area_unit");

                    String priceperunit = String.valueOf(Integer.parseInt(askingPrice)/Integer.parseInt(fA));


                    JSONArray proDeArray = mObject.optJSONArray("property-details");
                    JSONObject proDeObject = (JSONObject)proDeArray.getJSONObject(0);
                    String createdAt = proDeObject.optString("created_at");
                    String purpose = proDeObject.optString("purpose");
                    String unitFrom = proDeObject.optString("unit_from");
                    String unitTo = proDeObject.optString("unit_to");
                    String district = proDeObject.optString("district");
                    String block = proDeObject.optString("block");
                    String postalCode = proDeObject.optString("postal_code");
                    String tenure = proDeObject.optString("tenure");
                    String totalUnits = proDeObject.optString("total_units");
                    String totalFloors = proDeObject.optString("total_floors");
                    String topYear = proDeObject.optString("top_year");

                    String unitNO = unitFrom + " to " + unitTo;


                    JSONArray uDArray = mObject.optJSONArray("unit-detail");
                    JSONObject uDObject = (JSONObject)uDArray.getJSONObject(0);
                    String furnished = uDObject.optString("furnished");
                    String floor = uDObject.optString("floor");

                    String splf = uDObject.optString("special_features");
                    JSONArray splfJson = new JSONArray(splf);
                    String[] sF = new String[splfJson.length()];
                    for(int a=0;a<splfJson.length();a++)
                        sF[a]=splfJson.getString(a);
                    String specialFeatures = Arrays.toString(sF);
                    specialFeatures = specialFeatures.replaceAll("\\[", "").replaceAll("\\]","");

                    String oi = uDObject.optString("outdoor_indoor");
                    JSONArray oiJson = new JSONArray(oi);
                    String[] oI = new String[oiJson.length()];
                    for(int c=0;c<oiJson.length();c++)
                        oI[c]=oiJson.getString(c);
                    String outdoorIndoor = Arrays.toString(oI);
                    outdoorIndoor = outdoorIndoor.replaceAll("\\[", "").replaceAll("\\]","");

                    String ff = uDObject.optString("fixtures_fittings");
                    JSONArray ffJson = new JSONArray(ff);
                    String[] fF = new String[ffJson.length()];
                    for (int b=0; b<ffJson.length();b++)
                        fF[b]=ffJson.getString(b);
                    String fixturesFittings = Arrays.toString(fF);
                    fixturesFittings = fixturesFittings.replaceAll("\\[", "").replaceAll("\\]","");


                    JSONArray DesArray = mObject.optJSONArray("description");
                    JSONObject DesObject = (JSONObject)DesArray.getJSONObject(0);
                    String description = DesObject.optString("description");

                    JSONArray facArray = mObject.optJSONArray("facilities");
                    JSONObject facObject = (JSONObject)facArray.getJSONObject(0);
                    String facString = facObject.optString("facilities");
                    JSONArray facJson = new JSONArray(facString);
                    String[] fac = new String[facJson.length()];
                    for (int d=0;d<facJson.length();d++)
                        fac[d] = facJson.getString(d);
                    String facilities = Arrays.toString(fac);
                    facilities = facilities.replaceAll("\\[", "").replaceAll("\\]","");


                    JSONArray videoArray = mObject.optJSONArray("property-video");
                    JSONObject videoObject = (JSONObject)videoArray.getJSONObject(0);
                    String videoUrl = videoObject.optString("video_url");

                    String pro_img_url = GETPROPERTYDETAILPIC + topSlider1;
                    String pro_user_pic_url = GETPROPERTYDETAILUSERPIC + userImage;


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
                    propertyDetailModel.setAgentnumber(userPhone);


                    propertyDetailModelList.add(propertyDetailModel);


                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView = (RecyclerView) findViewById(R.id.property_detail_recycler_view);
                    final PropertyDetailAdapter propertyDetailAdapter = new PropertyDetailAdapter(getApplicationContext(), propertyDetailModelList);
                    recyclerView.setAdapter(propertyDetailAdapter);
                    recyclerView.setLayoutManager(layoutManager);


                }
                else {
                    Toast.makeText(PropertyDetails.this,"Unable to fetch data",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Log.e("PropertyDetail", "JSON exception", e);
            } catch (Exception e){
                Log.e("PropertyDetail", "Some kind of exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String urlString = GETPROPERTYDETAILURL + propertyID;
                url = new URL(urlString);
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
