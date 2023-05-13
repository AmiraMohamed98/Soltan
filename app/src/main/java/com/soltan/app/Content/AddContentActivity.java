package com.soltan.app.Content;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.soltan.app.MainActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContentActivity extends AppCompatActivity {
    FirebaseFirestore db;
    StorageReference storageRef;
    FirebaseStorage storage;
    RecyclerView recycler_view_video,recycler_view_audio,recycler_view_pdf;
    ItemClickListener itemClickListener1,itemClickListener2,itemClickListener3,itemClickListener;
    addContentAdapter adapter;
    List<String>videosList;
    List<String>audioList;
    List<String>pdfList;
    Map<String ,String>map;
    Map<String ,String>audio;
    Map<String ,String>pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();
        recycler_view_video=findViewById(R.id.recycler_view_video);
        recycler_view_audio=findViewById(R.id.recycler_view_audio);
        recycler_view_pdf=findViewById(R.id.recycler_view_pdf);
        map=new HashMap<>();

        itemClickListener1 = new ItemClickListener() {
            @Override public void onClick(String s,String t)
            {
                if(t.equals("video")){
                    map.put("video_name",s == null ? "not"  : s);
                    //Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();

                }
                 if(t.equals("audio")){
                         map.put("audio_name",s);
                         map.put("audio_url", audio.get(s));

                    // Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();
                }
                if(t.equals("pdf")) {

                            map.put("pdf_name",s);
                            map.put("pdf_url", pdf.get(s));

                   // Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();

                }

            }
        };
        itemClickListener2 = new ItemClickListener() {
            @Override public void onClick(String s,String t)
            {
                //  Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();
                if(t.equals("video")){
                    map.put("video_name",s == null ? "not"  : s);
                    //Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();


                }
                if(t.equals("audio")){


                        map.put("audio_name",s);
                        map.put("audio_url", audio.get(s));



                    // Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();
                }
                if(t.equals("pdf")) {

                        map.put("pdf_name",s);
                        map.put("pdf_url", pdf.get(s));


                   // Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();

                }

            }
        };
        itemClickListener3 = new ItemClickListener() {
            @Override public void onClick(String s,String t)
            {
                //  Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();

                if(t.equals("video")){
                    map.put("video_name",s == null ? "not"  : s);
                   // Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();

                }
                if(t.equals("audio")){


                        map.put("audio_name",s);
                        map.put("audio_url", audio.get(s));



                    // Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();
                }
                if(t.equals("pdf")) {

                        map.put("pdf_name",s);
                        map.put("pdf_url", pdf.get(s));


                    //Toast.makeText(AddContentActivity.this, "texr is"+ s +"====="+t, Toast.LENGTH_SHORT).show();

                }

            }
        };

        recycler_view_video.setHasFixedSize(true);
        recycler_view_video.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view_audio.setHasFixedSize(true);
        recycler_view_audio.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view_pdf.setHasFixedSize(true);
        recycler_view_pdf.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData_1();
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                db.collection("Content").document(map.get("video_name")).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddContentActivity.this, "تم الاضافه", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                              Log.d("tag","failed to add");
                            }
                        });
//                Map<String,String> map=new HashMap<>();
//                map.put("video_Link",video_Link);
//                map.put("audio_link",audio_link);
//                map.put("pdf_link",pdf_link);
//                db = FirebaseFirestore.getInstance();
//                db.collection("Content").document("videos")
//                        .set(map)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(AddContentActivity.this, "تم الاضافه ", Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("tag", "لم يتم الاضافه", e);
//                            }
//                        });
//            }}

            }});

    }
    void getData_1(){
        db = FirebaseFirestore.getInstance();
        db.collection("videos").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        videosList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection("videos").document(document.getId()).collection("0")
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                      //  Log.d("tag", "tagbbb " + document2.getId() + " => " + document2.getData());
                                                        videosList.add(document2.getId());

                                                    }
                                                    adapter = new addContentAdapter(videosList,itemClickListener1,getApplicationContext(),"video");
                                                    recycler_view_video.setAdapter(adapter);
                                                    adapter.notifyDataSetChanged();
                                                }}

                                        });

                            }

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
        audioList = new ArrayList<>();
        audio=new HashMap<>();
        map.put("audio_name","not");
        map.put("audio_url", "not");
        map.put("pdf_name","not");
        map.put("pdf_url", "not");
        storageRef.child("audio").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            //  Log.d("tag","prefix"+prefix.getName());
                            storageRef.child("audio").child(prefix.getName()).listAll()
                                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            for (StorageReference item : listResult.getItems()) {
                                                //Log.d("tag", "item" + item.getName());
                                                audioList.add(item.getName());
                                                item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                      audio.put(item.getName(),uri.toString());
                                                    }
                                                });

                                            }

                                        }
                                    }) ;
                            storageRef.child("audio").child(prefix.getName()).listAll()
                                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            for (StorageReference pref : listResult.getPrefixes()) {
                                                //Log.d("tag", "item" + item.getName());
                                                storageRef.child("audio").child(prefix.getName()).child(pref.getName()).listAll()
                                                        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                                            @Override
                                                            public void onSuccess(ListResult listResult) {
                                                                for (StorageReference item2 : listResult.getItems()) {
                                                                    //Log.d("tag", "item" + item.getName());
                                                                    audioList.add(item2.getName());
                                                                    item2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri) {
                                                                            audio.put(item2.getName(),uri.toString());
                                                                        }
                                                                    });
                                                                }


                                                            }
                                                        });
                                            }

                                        }
                                    });
                        }
                    }
                });
        storageRef.child("فتاوى").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                          //  Log.d("tag","prefix"+prefix.getName());
                            storageRef.child("فتاوى").child(prefix.getName()).listAll()
                                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            for (StorageReference item : listResult.getItems()) {
                                                //Log.d("tag", "item" + item.getName());
                                                audioList.add(item.getName());
                                                item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        audio.put(item.getName(),uri.toString());

                                                    }
                                                });
                                            }

                                            adapter = new addContentAdapter(audioList,itemClickListener2,
                                                    getApplicationContext(),"audio");
                                            recycler_view_audio.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }) ;
                        }
                    }
                });
        pdfList=new ArrayList<>();
        pdf=new HashMap<>();
        storageRef.child("كتب تم شرحها").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            // Log.d("tag","item"+item.getName());
                            pdfList.add(item.getName());
                           item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   pdf.put(item.getName(),uri.toString());

                               }
                           });
                        }

                    }
                });
        storageRef.child("كتب ومؤلفات").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {

                            pdfList.add(item.getName());
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pdf.put(item.getName(),uri.toString());
                                }
                            });

                        }
                        adapter = new addContentAdapter(pdfList,itemClickListener3,getApplicationContext(),"pdf");
                        recycler_view_pdf.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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