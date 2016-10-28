package app.com.example.android.agenttagging;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

public class DisplayUploadingPhoto extends AppCompatActivity {

    private ImageView backBtn3;
    private String userChoosenTask;
    public final static int PICK_PHOTO_CODE = 1046;
    public final static int CAPTURE_PHOTO_CODE = 1047;
    private ImageButton addMore;
    private ArrayList<Uri> mArrayUri;
    private ArrayList<Bitmap> mBitmapsSelected;
    private byte[] imageBytes;
    private ArrayList<String> imagelist = new ArrayList<String>();
    private JSONArray imageArray = new JSONArray();
    private String[] multiImage;
    String newPostID;
    String encodedImage;
    String image;
    private Button donePost;

    public static final String UserPREFERENCES = "UserPrefs" ;
    public static final String ACCESSTOKEN = "accessToken";
    private String userToken;
    private SharedPreferences sharedPreferences;
    private static final String AcessToken = "access_token";

    private FlowLayout addImageBody;
    private ImageView newImage;

    private static final String postImageUrl = "http://realthree60.com/dev/apis/PostGallery/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_uploading_photo);

        selectImage();

        addImageBody = (FlowLayout) findViewById(R.id.addimagebody);


        donePost = (Button) findViewById(R.id.donepost);
        donePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
                userToken = sharedPreferences.getString(ACCESSTOKEN,null);
                new UploadImageinNewPost().execute();
            }
        });

        Bundle extra = getIntent().getExtras();
        newPostID = extra.getString("newpostid");
        /*newImage = new ImageView(this);
        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(DisplayUploadingPhoto.this)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                addImageBody.removeView(newImage);
                            }
                        })
                        .customView(newImage,false)
                        .positiveText("OK")
                        .negativeText("DELETE")
                        .show();
            }
        });*/

        addMore = (ImageButton) findViewById(R.id.add_property_image);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        backBtn3 = (ImageView) findViewById(R.id.backbtn3);
        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(DisplayUploadingPhoto.this);
        builder.setTitle("Upload Property Images!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Utility utility = new Utility();
                boolean result=utility.checkPermission(DisplayUploadingPhoto.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_PHOTO_CODE);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_PHOTO_CODE)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAPTURE_PHOTO_CODE)
                onCaptureImageResult(data);
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data.getClipData() != null) {
            try {
                ClipData mClipData = data.getClipData();
                mArrayUri = new ArrayList<Uri>();
                mBitmapsSelected = new ArrayList<Bitmap>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    //Uri photoUri = uri.getData();
                    // Do something with the photo based on Uri
                    //Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    // !! You may need to resize the image if it's too large
                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 90, uri);
                    Bitmap bitmap = Bitmap.createScaledBitmap(bitmap1,100,100,true);
                    Bitmap bitmap2 = Bitmap.createBitmap(bitmap1);
                    image = getStringImage(bitmap);
                    mBitmapsSelected.add(bitmap);
                    newImage = new ImageView(this);
                    newImage.setImageBitmap(bitmap);
                    newImage.setPadding(6,6,0,0);
                    addImageBody.addView(newImage,240,240);
                   // imageArray.put(image);
                    imagelist.add(image);
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        //byte[] byteArray = bytes.toByteArray();
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //image = getStringImage(thumbnail);
        newImage = new ImageView(this);
        newImage.setImageBitmap(thumbnail);
        newImage.setPadding(6,6,0,0);
        addImageBody.addView(newImage,240,240);
        imagelist.add(getStringImage(thumbnail));
       // imageArray.put(image);
        //imagelist.add(image);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private class UploadImageinNewPost extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(DisplayUploadingPhoto.this);
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
                   // String newPostID = object.optString("post_id");
                    Toast.makeText(DisplayUploadingPhoto.this, " Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(DisplayUploadingPhoto.this, UploadPhoto.class);
                    //intent.putExtra("newpostid",newPostID);
                    //startActivity(intent);
                }
                else {
                    Toast.makeText(DisplayUploadingPhoto.this, "SOMEthing went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("CreatePost", "JSON exception", e);
                Toast.makeText(DisplayUploadingPhoto.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String imageUrl = postImageUrl + newPostID;
                url = new URL(imageUrl);
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
                //conn.setRequestProperty("Content-Type","multipart");


                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);



                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(AcessToken,userToken)
                        .appendQueryParameter("images",imagelist.toString());
                String query = builder.build().getEncodedQuery();

                Log.v("Images 1 : ",imagelist.get(0));
              //  Log.v("Images 2 : ",imagelist.get(1));
                //Log.v("Images 3 : ",imagelist.get(2));
                //Log.v("Images 4 : ",imagelist.get(3));
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
