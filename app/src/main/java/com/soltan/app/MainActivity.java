package com.soltan.app;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;


import com.soltan.app.ChannelPage.channelsFragment;
import com.soltan.app.Content.CustomAdapter;
import com.soltan.app.Content.EditContentActivity;
import com.soltan.app.Fragments.SectionFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity

{

StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db;
    //private ArrayList<String,String> arrayList = new ArrayList<>();
    List<AddVideoData> list ;
    //String YouTubeVideoEmbedCode = "<html><body><iframe width=\"330\" height=\"330\" src=\"https://www.youtube.com/embed/-fEIzQ5JD84\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
    CustomAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddVideoData videoData;
    RecyclerView recyclerView;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    List<String> listStr;
    Fragment fragment = null;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);{
            firebaseDatabase = FirebaseDatabase.getInstance();
            //databaseReference = firebaseDatabase.getReference("VideosData");
            // databaseReference = firebaseDatabase.getReference("Channels");
            storageReference = storage.getReference();
            videoData = new AddVideoData();
            drawerLayout = findViewById(R.id.drawerLayout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            // to make the Navigation drawer icon always appear on the action bar
            //actionbar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



            recyclerView = findViewById(R.id.recyclerView);
            list=new ArrayList<>();
            // getDataFromFirebase();
            // getAllData();
            // get the reference of FrameLayout and TabLayout
            simpleFrameLayout = findViewById(R.id.simpleFrameLayout);
            tabLayout = findViewById(R.id.simpleTabLayout);
// Create a new Tab named "First"
            TabLayout.Tab firstTab = tabLayout.newTab();
            firstTab.setText("قنوات"); // set the Text for the first Tab
            tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
            TabLayout.Tab secondTab = tabLayout.newTab();
            secondTab.setText("الاقسام"); // set the Text for the second Tab
            tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
            TabLayout.Tab thirdTab = tabLayout.newTab();
            thirdTab.setText("المحتوى"); // set the Text for the first Tab
            tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout


// perform setOnTabSelectedListener event on TabLayout
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly

                    switch (tab.getPosition()) {
                        case 0:
                            fragment = new channelsFragment();
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.simpleFrameLayout, fragment);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.commit();
                            break;
                        case 1:
                            fragment = new SectionFragment();
                            FragmentManager fm2 = getSupportFragmentManager();
                            FragmentTransaction ft2 = fm2.beginTransaction();
                            ft2.replace(R.id.simpleFrameLayout, fragment);
                            ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft2.commit();
                            break;
                        case 2:
                            Intent intent=new Intent(getApplicationContext(), EditContentActivity.class);
                            startActivity(intent);

                            break;
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

