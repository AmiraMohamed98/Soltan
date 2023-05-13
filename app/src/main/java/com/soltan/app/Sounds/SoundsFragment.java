package com.soltan.app.Sounds;

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

import com.soltan.app.Fragments.SectionFragment;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SoundsFragment extends Fragment {
    private SoundDropList adapter;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<String> list;
    ArrayList<String>title;
    ArrayList<String>sub;
    Map<String,String>cat;
    Map<String,ArrayList<String>>subtTile;
    Fragment fragment = null;
    Map<String, String> result;
    public SoundsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sounds, container, false);
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
        //list=new ArrayList<>();
       // getData();
        getData_1();
        return view;
    }

    void getData_1(){
        db = FirebaseFirestore.getInstance();
        cat=new HashMap<>();
        db.collection("Audio")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cat.put(document.getId(), (String) document.get("subTitle"));
                         //result= sortMap(cat);
                    }
                   // Log.d("tag","SoundsFragment document is "+cat);
                    Map<String, String> treeMap = new TreeMap<String, String>(cat);
                    adapter = new SoundDropList(treeMap, getContext(),getFragmentManager());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    public static Map sortMap(Map map) {
        List <Entry<String, String>> capitalList = new LinkedList<>(map.entrySet());

        // call the sort() method of Collections
        Collections.sort(capitalList, (l1, l2) -> l1.getValue().compareTo(l2.getValue()));

        // create a new map
        Map<String, String> result = new HashMap<>();

        // get entry from list to the map
        for (Map.Entry<String, String> entry : capitalList) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    void getData(){
        db = FirebaseFirestore.getInstance();
        subtTile=new HashMap<>();
       // sub=new ArrayList<>();
            db.collection("Audio").whereEqualTo("subTitle","true").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
                            db.collection("Audio/"+document.getId()+"/0").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    sub=new ArrayList<>();
                                    for (DocumentSnapshot document2 : queryDocumentSnapshots.getDocuments()) {
                                      //  Log.d("tag","document "+document.getId() +" ==="+document2.getId());
                                          sub.add(document2.getId());

                                      // Log.d("tag","document "+sub);
                                    }
                                    subtTile.put(document.getId(), sub);

                                    sub= new ArrayList<>();
                                    //Log.d("tag","document "+subtTile);
                                }
                            });
                        }

                        //Log.d("tag","Audio"+ subtTile.toString());
//                        adapter = new SoundDropList(list, getContext(),subtTile);
//                          recyclerView.setAdapter(adapter);
//                          adapter.notifyDataSetChanged();


                    } else {
                        // Log.d("tag", "Error getting documents: ", task.getException());
                    }
                }
            });
        db.collection("Audio").whereEqualTo("subTitle","false").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        title=new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                title.add(document.getId());
                                list.add(document.getId());
                               // Log.d("tag","document is"+title);
                            }

                            //Log.d("tag","Audio"+ subtTile.toString());
                            adapter = new SoundDropList(list, getContext(),subtTile,title);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        } else {
                            // Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
        }
    }
