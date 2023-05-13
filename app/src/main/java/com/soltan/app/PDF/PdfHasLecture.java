package com.soltan.app.PDF;

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
import com.soltan.app.Sections.PdfFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class PdfHasLecture extends Fragment {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<String> list;
    private PdfAdapter adapter;
    StorageReference storageRef;
    FirebaseStorage storage;
    Fragment fragment = null;
    public PdfHasLecture() {
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
        View view= inflater.inflate(R.layout.fragment_pdf_has_lecture, container, false);
//        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getContext(),AddPdf.class);
//                intent.putExtra("type","كتب تم شرحها");
//                startActivity(intent);
//            }
//        });
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PdfFragment();
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
        storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();
        getData1();

        return view;
    }
    void getData1(){

        storageRef.child("كتب تم شرحها").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        list = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            //String newItem=item.getName().replace(".pdf","");
                            list.add(item.getName());
                        }
                        adapter = new PdfAdapter(list, getContext(),"كتب تم شرحها");
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
    void getData(){
        db = FirebaseFirestore.getInstance();
        db.collection("كتب تم شرحها").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                                // Log.d("tag", "videos data" +document.getId() + " => " + document.getData());

                            }
                            adapter = new PdfAdapter(list, getContext(),"كتب تم شرحها");
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "لا توجد ملفات", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "لا توجد ملفات", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}