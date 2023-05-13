package com.soltan.app.Sections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soltan.app.Fragments.SectionFragment;
import com.soltan.app.Kenawy.KenawyListAdpter;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class VarietiesFragment extends Fragment {
    private KenawyListAdpter adapter;
    RecyclerView recyclerView;
    List<String> list;
    StorageReference storageRef;
    FirebaseStorage storage;
    Fragment fragment = null;
    public VarietiesFragment() {
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
        View view= inflater.inflate(R.layout.fragment_varieties, container, false);
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
        storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();
//        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getData1();
        return view;
    }
    void getData1(){

        storageRef.child("منوعات").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        list = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            list.add(item.getName());
                        }
                        adapter = new KenawyListAdpter(list, getFragmentManager());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "لا توجد قوائم", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}