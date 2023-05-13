package com.soltan.app.Sounds;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowSoundToEdit extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    String title,sub;
    addedSoundsAdapter adapter;
    List<String > list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sound_to_edit);
        Bundle parameters = getIntent().getExtras();
        title=parameters.getString("title");
        sub=parameters.getString("sub");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData_1();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ShowSoundToEdit.this, AddSoud.class);
                intent.putExtra("title",title);
                intent.putExtra("sub",sub);
                startActivity(intent);

            }
        });
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
                                    Log.d("tag", "tagbbb " + document.getId() + " => " + document.getData());
                                    list.add(document.getId());
                                }
//                                 adapter = new addedSoundsAdapter(list, getApplicationContext(),title,sub);
//
//                                  recyclerView.setAdapter(adapter);
//                                 adapter.notifyDataSetChanged();
                            } else {
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "لا يوجد فيديوهات", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            db = FirebaseFirestore.getInstance();
            db.collection("Audio").document(title)
                    .collection("0").document(sub).collection("0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("tag", "tagbbb " + document.getId() + " => " + document.getData());
                                    list.add(document.getId());
                                }

//                                 adapter = new addedSoundsAdapter(list, getApplicationContext(),title,sub);
//                                  recyclerView.setAdapter(adapter);
//                                 adapter.notifyDataSetChanged();
                            } else {
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
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
}