package app.com.example.android.agenttagging;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.apmem.tools.layouts.FlowLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class NewListingPageThree extends AppCompatActivity {

    private ImageView backBtn3;
    private ImageButton addPropertyImage;
    private FlowLayout addImageBody;
    private Boolean addingMore;
    private Uri imageUri;
    private InputStream imageStream;
    private Bitmap selectedImage;
    private ImageView image;
    private byte[] imageBytes;
    String encodedImage;
    // LayoutInflater layoutInflater;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String selectedImagePath;
    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_listing_page_three);


        backBtn3 = (ImageView) findViewById(R.id.backbtn3);
        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //addedImageView = (ImageView) findViewById(R.id.added_image);

        addImageBody = (FlowLayout) findViewById(R.id.addimagebody);
        //layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addPropertyImage = (ImageButton) findViewById(R.id.add_property_image);
        addPropertyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(NewListingPageThree.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(NewListingPageThree.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(NewListingPageThree.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    SELECT_PICTURE);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        ActivityCompat.requestPermissions(NewListingPageThree.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                SELECT_PICTURE);
                    }
                } else {

                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SELECT_PICTURE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    try {
                        imageUri = data.getData();
                        imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        image = new ImageView(NewListingPageThree.this);
                        image.setImageBitmap(selectedImage);
                        //FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(240,240);
                        //layoutParams.setMargins(10,10,10,0);
                        image.setPadding(6, 6, 0, 0);
                        addImageBody.addView(image, 240, 240);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}