package app.com.example.android.agenttagging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class UploadPhoto extends AppCompatActivity {

    private ImageView backBtn3;
    private LinearLayout tapUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        tapUpload = (LinearLayout) findViewById(R.id.clickedtoupload);
        tapUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadPhoto.this,DisplayUploadingPhoto.class);
                startActivity(intent);
            }
        });

        backBtn3 = (ImageView) findViewById(R.id.backbtn);
        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
