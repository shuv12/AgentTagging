package app.com.example.android.agenttagging;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ViewProfile extends AppCompatActivity {

    private boolean mSlideState = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView nvDrawer;
    private Button createListing, messageme, updateme;
    private TextView titleName;
    private EditText searchText;
    private ImageView searchIcon,crossIcon;
    private LinearLayout viewmyprofile,searchLayout,toolbarLayout;
    private ImageView alwaysHome1,alwaysHome2,profileImage;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ImageView drawerMenu = (ImageView) findViewById(R.id.drawermenu);


        searchText = (EditText) findViewById(R.id.searchtext);
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        crossIcon = (ImageView) findViewById(R.id.crossIcon);
        toolbarLayout = (LinearLayout) findViewById(R.id.toolbar_layout);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        messageme = (Button) findViewById(R.id.messageme);
        updateme = (Button) findViewById(R.id.updateprofile);
        titleName = (TextView) findViewById(R.id.titlename);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                searchText.setText("");
                searchText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);

                searchText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            //performSearch();
                            return true;
                        }
                        return false;
                    }
                });

            }
        });

        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.GONE);
                toolbarLayout.setVisibility(View.VISIBLE);
                searchText.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
            }
        });


        drawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSlideState) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else
                    mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Boolean myprofile = extras.getBoolean("myprofile");
            if (myprofile == true) {
                messageme.setVisibility(View.GONE);
                updateme.setVisibility(View.VISIBLE);
                updateme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wrapInScrollView = true;
                        final MaterialDialog dialog = new MaterialDialog.Builder(ViewProfile.this)
                                .customView(R.layout.profileupdateboxlayout, wrapInScrollView)
                                .show();
                        View view = dialog.getCustomView();
                        profileImage = (ImageView) view.findViewById(R.id.editprofilepic);
                        profileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    if (ContextCompat.checkSelfPermission(ViewProfile.this,
                                            Manifest.permission.READ_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(ViewProfile.this,
                                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        } else {
                                            ActivityCompat.requestPermissions(ViewProfile.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    SELECT_PICTURE);
                                        }
                                    } else {
                                        ActivityCompat.requestPermissions(ViewProfile.this,
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
                        Button saveButton = (Button) view.findViewById(R.id.saveupdate);
                        saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                titleName.setText(getString(R.string.mypro));
            }
        }

        createListing = (Button) findViewById(R.id.createlisting);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        View header = nvDrawer.getHeaderView(0);
        alwaysHome1 = (ImageView) header.findViewById(R.id.alwayshome);
        alwaysHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, FrontPage.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        TextView textView = (TextView) header.findViewById(R.id.logintext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nvDrawer.getHeaderView(0).setVisibility(View.GONE);
                View headerlayout = nvDrawer.inflateHeaderView(R.layout.drawerview);
                createListing.setVisibility(View.VISIBLE);
                alwaysHome2 = (ImageView) headerlayout.findViewById(R.id.alwayshome);
                alwaysHome2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewProfile.this, FrontPage.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                viewmyprofile = (LinearLayout) headerlayout.findViewById(R.id.viewmyprofile);
                viewmyprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewProfile.this, ViewProfile.class);
                        intent.putExtra("myprofile", true);
                        startActivity(intent);
                    }
                });
                createListing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(ViewProfile.this, NewListingPageOne.class);
                        startActivity(intent1);
                    }
                });
            }
        });


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ProfileFragmentPagerAdapter pagerAdapter = new ProfileFragmentPagerAdapter(getSupportFragmentManager(),
                ViewProfile.this);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SELECT_PICTURE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
            } else {
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
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profileImage.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.property) {
                            Intent intent = new Intent(ViewProfile.this, Home.class);
                            startActivity(intent);
                        }

                        if (id == R.id.groupteam) {
                            Intent intent2 = new Intent(ViewProfile.this, GroupTeam.class);
                            startActivity(intent2);
                        }

                        if (id == R.id.upcoming) {
                            Intent intent1 = new Intent(ViewProfile.this, UpcomingEvent.class);
                            startActivity(intent1);
                        }

                        if (id == R.id.setting) {

                        }

                        if (id == R.id.agents) {
                            Intent intent = new Intent(ViewProfile.this, Agent.class);
                            startActivity(intent);
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}

