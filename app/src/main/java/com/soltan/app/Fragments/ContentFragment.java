package com.soltan.app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.soltan.app.AddVideoData;
import com.soltan.app.Content.CustomAdapter;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.List;

public class ContentFragment extends Fragment {
    private CustomAdapter adapter;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddVideoData videoData;
    List<AddVideoData> list ;
    FirebaseFirestore db;
    List<String> liststr;
    public ContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // recyclerView =  findViewById(R.id.recyclerView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.content_fragment, container, false);
//        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getContext(), AddContentActivity.class);
//                startActivity(intent);
//            }
//        });
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list=new ArrayList<>();
        //getDataFromFirebase();
        getData();
        return view;


        //        RecyclerView recyclerview = (RecyclerView)
//                inflater.inflate(R.layout.recycler_item, container, false);
//        adapter = new CustomAdapter(getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        return recyclerView;recyclerView
//        return recyclerView;

    }

    void getData(){
        db = FirebaseFirestore.getInstance();
        videoData = new AddVideoData();
        db.collection("Content")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //   liststr.add(document.getId());
                                videoData = document.toObject(AddVideoData.class);

                                list.add(videoData);
                                // Log.d("tag", "list is "+videoData.getVideo_name()+"==="+videoData.getAudio_name()+"========"+videoData.getPdf_name());
                            }

                            adapter = new CustomAdapter(list, getContext(),getFragmentManager());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    void getDataFromFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("VideosData");
        databaseReference.keepSynced(true);
        videoData = new AddVideoData();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String value = snapshot.getValue(String.class);
                //Log.d("TAG", "Value is: " + value);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    videoData = dataSnapshot.getValue(AddVideoData.class);

                    list.add(videoData);
                    Log.d("tag","videoData.id"+videoData.getVideo_id());
                    //videoData.getPlayList_id();


                }
                adapter = new CustomAdapter(list, getContext(),getFragmentManager());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });

    }

    void getData_1(){
        db = FirebaseFirestore.getInstance();
        videoData = new AddVideoData();

        db.collection("videos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //liststr.add(document.getId());
                                videoData = (AddVideoData) document.getData();

                                list.add(videoData);
                                Log.d("tag", "list is "+list);

                            }
//                            adapter = new CustomAdapter(list, getContext());
//                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
//        db.collection("videos").whereEqualTo("isVideo","true").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    //liststr = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        //liststr.add(document.getId());
//                        videoData = (AddVideoData) document.getData();
//
//                        list.add(videoData);
//                         Log.d("tag", "list is "+list);
//
//                    }
//                    adapter = new CustomAdapter(list, getContext());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.d("tag", "Error getting documents: ", task.getException());
//                }
//            }
//        });
    }
}