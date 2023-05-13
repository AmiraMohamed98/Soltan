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

import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class NumberVideoFragment extends Fragment {
    RecyclerView recyclerView;
    String title,sub,hasvideo;
    FirebaseFirestore db;
    List<String > list,subtitle;
    List<Integer>mlist;
    NumberVideoAdpter adpter;
    Map<Integer, String> cat;
    Map<Integer,String> mcat;
    Fragment fragment = null;
    public NumberVideoFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle parameters = getArguments();
        title=parameters.getString("title");
        sub=parameters.getString("sub");
        hasvideo=parameters.getString("hasvideo");
        Log.d("tag","hasvideo :"+hasvideo);
        View view= inflater.inflate(R.layout.fragment_number_video, container, false);
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new VideosFragment();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.simpleFrameLayout, fragment);
                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft3.commit();
            }
        });
        recyclerView =  view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData();
        return view;
    }
    void getData() {
        if (sub.equals("not")) {
            db = FirebaseFirestore.getInstance();
            db.collection("videos").document(title)
                    .collection("0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list=new ArrayList<>();
                            mlist=new ArrayList<>();
                            cat=new HashMap<>();
                            mcat=new HashMap<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                     //Log.d("tag", "tagNumber" + document.getId() + " => " + document.get("videoId")+"==>"+hasvideo);
                                    cat.put(Integer.valueOf(document.getId()), (String) document.get("videoId"));
                                    list.add(document.getId());
                                }
                                for (String s:list) {
                                    mlist.add(Integer.valueOf(s));
                                }
                                Collections.sort(mlist);
                                list=new ArrayList<>();
                                for (int s:mlist) {
                                    list.add(String.valueOf(s));
                                }
                                Map<Integer, String > treeMap = new TreeMap<Integer, String>(cat);
                              //  Log.d("tag", "mcat " + treeMap);
                                //Log.d("tag", "list " + list);

                                // treeMap.forEach((k, v) -> cat.put(String.valueOf((k)), v));
                                //Map<String, String> cat = treeMap.entrySet().stream().collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), Map.Entry::getValue, (a, b) -> b)); //Working
                                //treeMap.forEach((k, v) -> cat.putIfAbsent(k.toString(), v));

                                //Log.d("tag", "mcat " + cat);

                                adpter = new NumberVideoAdpter(treeMap,list, getContext(),title,sub,getFragmentManager(),hasvideo);
                                recyclerView.setAdapter(adpter);
                                adpter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "لم يتم استدعاء مرئيات", Toast.LENGTH_SHORT).show();
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "لا يوجد مرئيات", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            if (hasvideo == null) {
                db = FirebaseFirestore.getInstance();
                db.collection("videos").document(title)
                        .collection("0").document(sub).collection("0")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                cat=new HashMap<>();
                                mcat=new HashMap<>();
                                list=new ArrayList<>();
                                mlist=new ArrayList<>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                            // Log.d("tag", "tagbbb " + document.getId() + " => " + document.get("num")+"====>"+document.get("videoId")+"==>"+hasvideo);
                                        cat.put(Integer.valueOf(document.getId()),(String) document.get("videoId"));
                                        list.add(document.getId());

                                    }
                                    for (String s:list) {
                                        mlist.add(Integer.valueOf(s));
                                    }
                                    Collections.sort(mlist);
                                    list=new ArrayList<>();
                                    for (int s:mlist) {
                                        list.add(String.valueOf(s));
                                    }
                                    Map<Integer, String> treeMap = new TreeMap<>(cat);
//                                    Log.d("tag", "mcat " + treeMap);
//                                    Log.d("tag", "list " + list);
//                                    Map<Integer, String> treeMap = new TreeMap<Integer, String>(mcat);
//                                    Log.d("tag", "mcat " + treeMap);
//                                    treeMap.forEach((k, v) -> cat.put(String.valueOf((k)), v));

                                    //  cat = treeMap.entrySet().stream().collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), Map.Entry::getValue, (a, b) -> b)); //Working
                                   // Map<String, String> treeMap2 = new TreeMap<String , String>(cat);
//                                    for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
//                                        Integer k = entry.getKey();
//                                        String v = entry.getValue();
//                                        cat.put(k.toString(), v);
//                                    }
                                    //treeMap.forEach((k, v) -> cat.merge(k.toString(), v));
//                                    for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
//                                        cat.merge(String.valueOf(entry.getKey()), entry.getValue(), (oldval, newval) -> oldval);
//                                    }

                                  //  Log.d("tag", "tagbbb " + cat);

                                    adpter = new NumberVideoAdpter(treeMap,list, getContext(),title,sub,getFragmentManager(),"not");
                                    recyclerView.setAdapter(adpter);
                                    adpter.notifyDataSetChanged();

//                                adpter = new addedSoundsAdapter(list, getContext(),title,sub);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "لا يوجد مرئيات", Toast.LENGTH_SHORT).show();
                                    // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "لا يوجد مرئيات", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else {
                subtitle = new ArrayList<>();
                cat=new HashMap<>();
                db = FirebaseFirestore.getInstance();
                db.collection("videos").document(title)
                        .collection("0").document(sub).collection("0")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                list = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d("tag", "tagbbb2 " + document.getId() + " => " + document.getData()+"=======>"+(String) document.get("subVideo")+"==>"+hasvideo);
                                        list.add(document.getId());
                                        cat.put(0, "00");
                                    }
                                    Collections.sort(list);
                                    //Map<String, String> treeMap = new TreeMap<String, String>(cat);
                                    adpter = new NumberVideoAdpter(cat,list, getContext(),title,sub,getFragmentManager(),hasvideo);
                                    recyclerView.setAdapter(adpter);
                                    adpter.notifyDataSetChanged();
//                                adpter = new addedSoundsAdapter(list, getContext(),title,sub);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "لا يوجد مرئيات", Toast.LENGTH_SHORT).show();
                                    // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "لا يوجد مرئيات", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }}

