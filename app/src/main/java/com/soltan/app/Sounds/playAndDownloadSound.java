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
import java.util.List;


public class playAndDownloadSound extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    String title,sub;
    addedSoundsAdapter adapter;
    List<String > list;
    Fragment fragment = null;

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
        //Log.d("tag","title"+title+"sub"+sub);
        View view= inflater.inflate(R.layout.fragment_play_and_download_sound, container, false);
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new SoundsFragment();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.simpleFrameLayout, fragment);
                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft3.commit();
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData_1();
        return view;
    }

    void getData_1() {
        if (sub.equals("not")) {
            db = FirebaseFirestore.getInstance();
            db.collection("Audio").document(title)
                    .collection("0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("tag", "playAndDownloadSound tagbbb " + " => " + document.getData());
                                    list.add(document.getId());

                                }
                                Collections.sort(list);
                                adapter = new addedSoundsAdapter(list, getContext(),title,"not",getFragmentManager());
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "لم يتم استدعاء صوتيات", Toast.LENGTH_SHORT).show();
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "لا يوجد صوتيات", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            db = FirebaseFirestore.getInstance();
            db.collection("Audio").document(title)
                    .collection("0").document(sub)
                    .collection("0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("tag", "playAndDownloadSound tagbbb "  + " => " + document.getData());
                                    Log.d("tag","=================");
                                    list.add(document.getId());

                                }
                                Collections.sort(list);
                                adapter = new addedSoundsAdapter(list, getContext(),title,sub,getFragmentManager());
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "لا يوجد صوتيات", Toast.LENGTH_SHORT).show();
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "لا يوجد صوتيات", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}