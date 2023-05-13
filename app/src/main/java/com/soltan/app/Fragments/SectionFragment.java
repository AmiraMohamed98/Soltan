package com.soltan.app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.soltan.app.R;
import com.soltan.app.Sections.KenawyFragment;
import com.soltan.app.Sections.PdfFragment;
import com.soltan.app.Sections.VarietiesFragment;
import com.soltan.app.Videos.VideosFragment;
import com.soltan.app.Sounds.SoundsFragment;
import com.google.android.material.tabs.TabLayout;

public class SectionFragment extends Fragment {
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    public SectionFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.section_fragment, container, false);
        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = view.findViewById(R.id.simpleFrameLayout);
        tabLayout = view.findViewById(R.id.simpleTabLayout);
        tabLayout.setVisibility(View.VISIBLE);
// Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("صوتيات"); // set the Text for the first Tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("مرئيات"); // set the Text for the second Tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("ملفات"); // set the Text for the first Tab
        tabLayout.addTab(thirdTab);
        TabLayout.Tab fourTab = tabLayout.newTab();
        fourTab.setText("منوعات"); // set the Text for the first Tab
        tabLayout.addTab(fourTab);
        TabLayout.Tab sixTab = tabLayout.newTab();
        sixTab.setText("فتاوى"); // set the Text for the first Tab
        tabLayout.addTab(sixTab);// add  the tab at in the TabLayout


// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new SoundsFragment();
                        break;
                    case 1:
                        fragment = new VideosFragment();
                        break;
                    case 2:
                        fragment = new PdfFragment();
                        break;
                    case 3:
                        fragment = new VarietiesFragment();
                        break;
                    case 4:
                        fragment = new KenawyFragment();
                        break;
                }
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new SoundsFragment();
                        break;
                    case 1:
                        fragment = new VideosFragment();
                        break;
                    case 2:
                        fragment = new PdfFragment();
                        break;
                    case 3:
                        fragment = new VarietiesFragment();
                        break;
                    case 4:
                        fragment = new KenawyFragment();
                        break;
                }
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });

      return view;
    }
}
