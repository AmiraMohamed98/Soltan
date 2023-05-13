package com.soltan.app.Kenawy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.soltan.app.R;
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

public class ShowKenawyToEdit extends AppCompatActivity {
    RecyclerView recyclerView;
    StorageReference storageRef;
    FirebaseStorage storage;
    FirebaseFirestore db;
    String playList;
    addedKenawy adapter;
    List<String > list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kenawy_to_edit);
        Bundle parameters = getIntent().getExtras();
        playList=parameters.getString("playlist");
        storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData1();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ShowKenawyToEdit.this, AddKenawy.class);
                intent.putExtra("listName",playList);
                startActivity(intent);

            }
        });
    }
    void getData(){
        db = FirebaseFirestore.getInstance();
        db.collection("Fatwa/"+playList+"/0").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d("tag", "tagbbb "+document.getId() + " => " + document.getData());
                                list.add(document.getId());
                            }
//                            adapter = new addedKenawy(list,getFragmentManager(),playList);
//                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("tag", "لم يتم استدعاء الملفات  ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "لا يوجد ملفات", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void getData1(){

        storageRef.child("فتاوى"+"/"+playList).listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        list = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            String newitem=item.getName().replace(".MP3","");
                            list.add(newitem);
                        }
//                        adapter = new addedKenawy(list,getApplicationContext(),playList);
//                        recyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "لا يوجد ملفات", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}