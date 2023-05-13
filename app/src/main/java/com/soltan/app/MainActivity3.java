package com.soltan.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;

import com.soltan.app.ChannelPage.channelsFragment;
import com.soltan.app.Fragments.ContentFragment;
import com.soltan.app.Fragments.SectionFragment;
import com.soltan.app.Sidebar.InfoFragment;
import com.soltan.app.Sidebar.TableFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.soltan.app.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    public AppBarConfiguration mAppBarConfiguration;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public ActivityMain3Binding binding;
    TabLayout tabLayout;
    Fragment fragment = null;
    FrameLayout simpleFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain2.toolbar);
        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.facebook, R.id.svcc, R.id.table_page, R.id.telegram, R.id.whatsapp)
                .setOpenableLayout(drawer)
                .build();
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                int itemId = item.getItemId();
                Log.d("tag", "id is :" + itemId);
                switch (itemId) {
                    case R.id.svcc:
                        binding.drawerLayout.openDrawer(GravityCompat.START);
                        fragment = new InfoFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.simpleFrameLayout, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.table_page:
                        binding.drawerLayout.openDrawer(GravityCompat.START);
                        fragment = new TableFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.simpleFrameLayout, fragment); // replace a Fragment with Frame Layout
                        transaction.commit(); // commit the changes
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.facebook:
                        binding.drawerLayout.openDrawer(GravityCompat.START);
                        Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Drmsayedsultan/"));
                        startActivity(intent);
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.telegram:
                        binding.drawerLayout.openDrawer(GravityCompat.START);
                        Intent intent2 =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Drmsayedsultan2"));
                        startActivity(intent2);
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.whatsapp:
                        binding.drawerLayout.openDrawer(GravityCompat.START);
                        Intent intent3 =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/LRZYdkQHXn4J0GlHG0JAfx"));
                        startActivity(intent3);
                        binding.drawerLayout.closeDrawers();
                        break;
                    default:
                        return true;
                }


                return false;


            }
        });
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
        tabLayout.addTab(thirdTab);
        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly

                switch (tab.getPosition()) {
                    case 0:
                        fragment = new channelsFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction().setReorderingAllowed(true);
                        ft.replace(R.id.simpleFrameLayout, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();
                        break;
                    case 1:
                        fragment = new SectionFragment();
                        FragmentManager fm2 = getSupportFragmentManager();
                        FragmentTransaction ft2 = fm2.beginTransaction().setReorderingAllowed(true);
                        ft2.replace(R.id.simpleFrameLayout, fragment);
                        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft2.commit();
                        break;
                    case 2:
                        fragment = new ContentFragment();
                        FragmentManager fm3 = getSupportFragmentManager();
                        FragmentTransaction ft3 = fm3.beginTransaction().setReorderingAllowed(true);
                        ft3.replace(R.id.simpleFrameLayout, fragment);
                        ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft3.commit();
                        break;
//                        Intent intent = new Intent(getApplicationContext(), EditContentActivity.class);
//                        startActivity(intent);
//
//                        break;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
////        switch (item.getItemId())
////        {
////            case R.id.svcc:
////                binding.drawerLayout.openDrawer(GravityCompat.START);
////                Toast.makeText(MainActivity3.this, "svcc",Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.table_page:
////                binding.drawerLayout.openDrawer(GravityCompat.START);
////                Toast.makeText(MainActivity3.this, "table_page",Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.facebook:
////                binding.drawerLayout.openDrawer(GravityCompat.START);
////                Toast.makeText(MainActivity3.this, "facebook",Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.telegram:
////                binding.drawerLayout.openDrawer(GravityCompat.START);
////                Toast.makeText(MainActivity3.this, "telegram",Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.whatsapp:
////                binding.drawerLayout.openDrawer(GravityCompat.START);
////                Toast.makeText(MainActivity3.this, "whatsapp",Toast.LENGTH_SHORT).show();
////                break;
////            default:
////                return true;
////        }
//

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity3, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}