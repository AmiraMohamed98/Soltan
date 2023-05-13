package com.soltan.app.Kenawy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.soltan.app.Sections.KenawyFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class playAndDownloadkenawy extends Fragment {
    RecyclerView recyclerView;
    StorageReference storageRef;
    String title,sub;
    playKenawyAdapter adapter;
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
        title=parameters.getString("playlist");
        //Log.d("tag","title"+title+"sub"+sub);
        View view= inflater.inflate(R.layout.fragment_play_and_download_sound, container, false);
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new KenawyFragment();
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

    void getData_1(){
        storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("فتاوى"+"/"+title).listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        list = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                           // String newitem=item.getName().replace(".MP3","");
                            list.add(item.getName());
                        }
                        adapter = new playKenawyAdapter(list,getFragmentManager(),title,getContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "لا يوجد ملفات", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}