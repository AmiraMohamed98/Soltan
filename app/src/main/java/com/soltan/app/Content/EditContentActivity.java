package com.soltan.app.Content;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.soltan.app.AddVideoData;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditContentActivity extends AppCompatActivity {
    //editContentAdapter adapter;
    CustomAdapter adapter;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddVideoData videoData;
    List<AddVideoData> list ;
    FirebaseFirestore db;
    List<String> liststr;
    Map<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddContentActivity.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<>();
        getData();
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

//                            adapter = new CustomAdapter(list, getApplicationContext());
//                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
//  الإلهام النافع
// - الرسالة القشيرية
// - حاشية الصفتى
// -خطب متنوعة
// - شرح الآبى على العزية
// -شرح كفاية الطالب الربانى على رسالة ابن أبي زيد القيرواني
// متن الأخضري-
// - صفات الصلاة



// - إحياء