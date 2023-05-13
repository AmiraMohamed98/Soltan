package com.soltan.app.Videos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soltan.app.AddVideoData;
import com.soltan.app.Fragments.SectionFragment;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class VideosFragment extends Fragment {
    private VideosAdpter adapter;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    //List<AddVideoData> list;
    List<String>list;
    AddVideoData videoData;
    Map<String,String> cat;
    Fragment fragment = null;

    public VideosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData_1();
        return view;
    }
    void getData_1(){
        //subVideo=true
        db = FirebaseFirestore.getInstance();
        cat=new HashMap<>();
        db.collection("videos")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                      //  list.add(document.getId());
                        cat.put(document.getId(), (String) document.get("subTitle"));
                       //  Log.d("tag", "videos data" +document.getId() + " => " + document.getData());

                    }
                    Map<String, String> treeMap = new TreeMap<String, String>(cat);
                    adapter = new VideosAdpter(treeMap, getContext(),getFragmentManager());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "لا توجد فيديوهات", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void getData(){
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        db.collection("videos").document("إحياء علوم الدين").collection("0").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        // Log.d("tag", document.getId() + " => " + document.getData());

                    }
//                    adapter = new DropListAdapter(list, getContext(),"");
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                } else {
                    //Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    void getDataFromFirebase(){
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        db = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("VideosData");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String value = snapshot.getValue(String.class);
                //Log.d("TAG", "Value is: " + value);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    videoData = dataSnapshot.getValue(AddVideoData.class);

                   // list.add(videoData);
                    //Log.d("tag", "video name"+videoData.getVideo_Name());
                  //  Log.d("tag", "video id"+videoData.getVideo_id());
                    // videoData.getPlayList_id();




                }
                // Log.d("tag", "video is"+list.toString());
                // adapter = new CustomAdapter(list, getApplication());
                //  recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });

    }


}
