package com.soltan.app.Sections;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class ShowDataTOEdit extends YouTubeBaseActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    String playList;
    VideoAdapter adapter;
    List<String >list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_toedit);
        Bundle parameters = getIntent().getExtras();
        playList=parameters.getString("playlist");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData_1();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ShowDataTOEdit.this,AddToDataBase.class);
                intent.putExtra("listName",playList);
                startActivity(intent);

            }
        });
    }
    void getData_1(){
        db = FirebaseFirestore.getInstance();
        db.collection("videos/"+playList+"/0").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("tag", "tagbbb "+document.getId() + " => " + document.getData());
                                list.add(document.getId());
                            }
                            adapter = new VideoAdapter(list, getApplicationContext(),playList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "لا يوجد فيديوهات", Toast.LENGTH_SHORT).show();
            }
        });
    }
}