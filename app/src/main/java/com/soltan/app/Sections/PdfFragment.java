package com.soltan.app.Sections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.soltan.app.Fragments.SectionFragment;
import com.soltan.app.PDF.FolderAndPdf;
import com.soltan.app.PDF.PdfHasLecture;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PdfFragment extends Fragment {

    private DropListAdapter adapter;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<String> list;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    Fragment fragment = null;
    public PdfFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pdf, container, false);
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new SectionFragment();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.simpleFrameLayout, fragment);
                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft3.commit();
            }
        });
        simpleFrameLayout = view.findViewById(R.id.simpleFrameLayout);
        tabLayout = view.findViewById(R.id.simpleTabLayout);
        tabLayout.setVisibility(View.VISIBLE);
// Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("كتب ومؤلفات"); // set the Text for the first Tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("كتب تم شرحها"); // set the Text for the second Tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FolderAndPdf();
                        break;
                    case 1:
                        fragment = new PdfHasLecture();
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
                        fragment = new FolderAndPdf();
                        break;
                    case 1:
                        fragment = new PdfHasLecture();
                        break;
                }
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }


        });
        //        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        getData();
        return view;
    }
    void getDataFromFirebase() {
        db = FirebaseFirestore.getInstance();
        db.collection("videos").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    Toast.makeText(getContext(), " data found in Database", Toast.LENGTH_SHORT).show();
                    Log.d("tag", list.toString());
                    adapter = new DropListAdapter(list, getContext(),"كتب");
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
        //.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()) {
//
//                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                    for (DocumentSnapshot d : list) {
//                        Log.d("tag","videoData is "+d);
//                        videoData= d.toObject(AddVideoData.class);
//                        listVideos.add(videoData);
//                       // Log.d("tag","videoData is "+listVideos);
//                        Toast.makeText(getContext(), " data found in Database", Toast.LENGTH_SHORT).show();
//                    }
//                    adapter = new DropListAdapter(listVideos, getContext());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    void getData(){
        db = FirebaseFirestore.getInstance();
        db.collection("videos").whereEqualTo("isPdf","true").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                       // Log.d("tag", document.getId() + " => " + document.getData());

                    }
                    adapter = new DropListAdapter(list, getContext(),"كتب");
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    //Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}