package com.soltan.app.Sounds;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class addedSoundsAdapter extends RecyclerView.Adapter<addedSoundsAdapter.ViewHolder> {
    Context context;
    List<String> list;
    String title, sub;
      FirebaseFirestore db;
  //  StorageReference storageRef;
    //MediaPlayer mediaPlayer ;
    boolean wasPlaying = false;
    DownloadManager.Request request;
    DownloadManager downloadmanager;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    AlertDialog.Builder builder;
    public addedSoundsAdapter(List<String> list, Context context, String title, String sub,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.title = title;
        this.sub = sub;
        builder = new AlertDialog.Builder(context);
        this.fragmentManager=fragmentManager;
//        if (mediaPlayer !=null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
    }

    @NonNull
    @Override
    public addedSoundsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sublist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addedSoundsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt.setText(list.get(position));
       // mediaPlayer = new MediaPlayer();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("ماذا تريد ؟")
                        .setCancelable(false)
                        .setPositiveButton("تنزيل", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(sub.equals("not")) {
                                    db = FirebaseFirestore.getInstance();
                                    db.collection("Audio").document(title)
                                            .collection("0").document(list.get(position)).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                           // Log.d("TAG", "DocumentSnapshot data: " + document.get("url"));
                                                            String s= (String) document.get("url");
                                                            //String[] arrOfStr = s.split("=");
                                                            request = new DownloadManager.Request(Uri.parse(s));
                                                           // Log.d("tag", "addedSoundsAdapter tagbbb " +s +"location"+title+sub+list.get(position));
                                                            downloadmanager = (DownloadManager) context.
                                                                    getSystemService(Context.DOWNLOAD_SERVICE);
                                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                            //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
                                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
                                                            downloadmanager.enqueue(request);
                                                        } else {
                                                           // Log.d("TAG", "No such document");
                                                        }
                                                    } else {
                                                      //  Log.d("TAG", "get failed with ", task.getException());
                                                    }
                                                }
                                            });
//                                    storageRef = FirebaseStorage.getInstance().getReference();
//                                    storageRef = storageRef.child("audio").child(title).child(list.get(position));
//                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            request = new DownloadManager.Request(uri);
//                                            Log.d("tag", "addedSoundsAdapter tagbbb " +uri +"location"+title+sub+list.get(position));
//                                            downloadmanager = (DownloadManager) context.
//                                                    getSystemService(Context.DOWNLOAD_SERVICE);
//                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                                            //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
//                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
//                                            downloadmanager.enqueue(request);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("tag "," file not download " +e.toString());
//
//                                        }
//                                    });
                               }
                                else {
                                    db = FirebaseFirestore.getInstance();
                                    db.collection("Audio").document(title)
                                            .collection("0").document(sub)
                                            .collection("0").document(list.get(position)).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            //Log.d("TAG", "DocumentSnapshot data: " + document.get("url"));
                                                            String s= (String) document.get("url");
                                                            //String[] arrOfStr = s.split("=");
                                                            request = new DownloadManager.Request(Uri.parse(s));
                                                          //  Log.d("tag", "addedSoundsAdapter tagbbb " +s +"location"+title+sub+list.get(position));
                                                            downloadmanager = (DownloadManager) context.
                                                                    getSystemService(Context.DOWNLOAD_SERVICE);
                                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                            //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
                                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
                                                            downloadmanager.enqueue(request);
                                                        } else {
                                                        //    Log.d("TAG", "No such document");
                                                        }
                                                    } else {
                                                      //  Log.d("TAG", "get failed with ", task.getException());
                                                    }
                                                }
                                            });
//                                    storageRef = FirebaseStorage.getInstance().getReference();
//                                    //storageRef = storageRef.child("audio").child(title).child(sub).child(list.get(position));
//                                    storageRef = storageRef.child("audio/"+title+"/"+sub+"/"+list.get(position));
//
//                                    Log.d("tag", "addedSoundsAdapter tagbbb " +"location"+list.get(position));
//
//                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            request = new DownloadManager.Request(uri);
//                                            Log.d("tag", "addedSoundsAdapter tagbbb " +uri +"location"+title+sub+list.get(position));
//                                            downloadmanager = (DownloadManager) context.
//                                                    getSystemService(Context.DOWNLOAD_SERVICE);
//                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                                            //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
//                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
//                                            downloadmanager.enqueue(request);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("tag "," file not download " +e.toString());
//
//                                        }
//                                    });

                                }
                            }
                        }).setNegativeButton("إستماع", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(sub.equals("not")) {
                                    db = FirebaseFirestore.getInstance();
                                    db.collection("Audio").document(title)
                                            .collection("0").document(list.get(position)).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                        //    Log.d("TAG", "DocumentSnapshot data: " + document.get("url"));
                                                            String s= (String) document.get("url");
                                                            //String[] arrOfStr = s.split("=");
                                                            Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                                                            intent.setAction(android.content.Intent.ACTION_VIEW);
                                                           // for ( String a : arrOfStr)
                                                                intent.setDataAndType(Uri.parse(s), "audio/*");
                                                            context.startActivity(intent);
                                                        } else {
                                                          //  Log.d("TAG", "No such document");
                                                        }
                                                    } else {
                                                    //    Log.d("TAG", "get failed with ", task.getException());
                                                    }
                                                }
                                            });
//                                    storageRef = FirebaseStorage.getInstance().getReference();
//                                    storageRef = storageRef.child("audio").child(title).child(list.get(position));
//
//                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            Log.d("tag", "addedSoundsAdapter tagbbb " +uri +"location"+list.get(position));
//
//                                            //=MediaPlayer.create(context,uri);
////                                            fragment = new MusicFragment();
////                                            Bundle args = new Bundle();
////                                            args.putString("url",uri.toString());
////                                            fragment.setArguments(args);
////                                            FragmentManager fm3 = fragmentManager;
////                                            FragmentTransaction ft3 = fm3.beginTransaction();
////                                            ft3.replace(R.id.simpleFrameLayout, fragment);
////                                            ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////                                            ft3.commit();
//                                            Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
//                                            intent.setAction(android.content.Intent.ACTION_VIEW);
//                                            intent.setDataAndType(uri, "audio/*");
//                                            context.startActivity(intent);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("tag "," file not download " +e.toString());
//
//                                        }
//                                    });
                                            }
                                else {
                                    db = FirebaseFirestore.getInstance();
                                    db.collection("Audio").document(title)
                                            .collection("0").document(sub)
                                            .collection("0").document(list.get(position)).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            //Log.d("TAG", "DocumentSnapshot data: " + document.get("url"));
                                                            String s= (String) document.get("url");
                                                            //String[] arrOfStr = s.split("=");
                                                            Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                                                            intent.setAction(android.content.Intent.ACTION_VIEW);
                                                            // for ( String a : arrOfStr)
                                                            intent.setDataAndType(Uri.parse(s), "audio/*");
                                                            context.startActivity(intent);
                                                        } else {
                                                         //   Log.d("TAG", "No such document");
                                                        }
                                                    } else {
                                                        //Log.d("TAG", "get failed with ", task.getException());
                                                    }
                                                }
                                            });


//                                    storageRef = FirebaseStorage.getInstance().getReference();
//                                    storageRef = storageRef.child("audio").child(title+"/"+sub).child(list.get(position));
//                                    Log.d("tag", "addedSoundsAdapter tagbbb " +"location"+list.get(position));
//
//                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            Log.d("tag", "addedSoundsAdapter tagbbb " +uri );
//
//                                            //mediaPlayer=MediaPlayer.create(context,uri);
////                                            fragment = new MusicFragment();
////                                            Bundle args = new Bundle();
////                                            args.putString("url",uri.toString());
////                                            fragment.setArguments(args);
////                                            FragmentManager fm3 = fragmentManager;
////                                            FragmentTransaction ft3 = fm3.beginTransaction();
////                                            ft3.replace(R.id.simpleFrameLayout, fragment);
////                                            ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////                                            ft3.commit();
//                                            Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
//                                            intent.setAction(android.content.Intent.ACTION_VIEW);
//                                            intent.setDataAndType(uri, "audio/*");
//                                            context.startActivity(intent);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("tag "," file not download " +e.toString());
//
//
//                                        }
//                                    });

                                }
                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually

                alert.show();


            }
        });
//        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 downloadmanager = (DownloadManager) context.
//                        getSystemService(Context.DOWNLOAD_SERVICE);
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
//                downloadmanager.enqueue(request);
//
////                if(sub.equals("not")) {
////                    storageRef = storageRef.child("audio").child(title).child(list.get(position));
////                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                        @Override
////                        public void onSuccess(Uri uri) {
////                             downloadmanager = (DownloadManager) context.
////                                    getSystemService(Context.DOWNLOAD_SERVICE);
////
////                             request = new DownloadManager.Request(uri);
////
////                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
////                           // request.setDestinationInExternalFilesDir(context, title, title + "mp3");
////                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position) );
////                            downloadmanager.enqueue(request);
////
//////                            BroadcastReceiver receiver = new BroadcastReceiver() {
//////                                @Override
//////                                public void onReceive(Context context, Intent intent) {
//////                                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//////                                    // check if the download completed successfully
//////                                    if (id != -1 && id == downloadId) {
//////                                        // download completed, do something here
//////                                    }
//////                                }
//////                            };
//////                            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//////                            registerReceiver(receiver, filter);
////                        }
////                    }).addOnFailureListener(new OnFailureListener() {
////                        @Override
////                        public void onFailure(@NonNull Exception e) {
////                            Log.d("tag "," file not download " +e.toString());
////
////                        }
////                    });
////                }
////                else {
////                    storageRef = storageRef.child("audio").child(title).child(sub).child(list.get(position));
////
////                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                        @Override
////                        public void onSuccess(Uri uri) {
////                            DownloadManager downloadmanager = (DownloadManager) context.
////                                    getSystemService(Context.DOWNLOAD_SERVICE);
////
////                            DownloadManager.Request request = new DownloadManager.Request(uri);
////
////                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
////                            //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
////                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
////
////                            downloadmanager.enqueue(request);
////                        }
////                    }).addOnFailureListener(new OnFailureListener() {
////                        @Override
////                        public void onFailure(@NonNull Exception e) {
////                            Log.d("tag "," file not download " +e.toString());
////
////                        }
////                    });
////
////                }
//            }
//        });
//        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
////                holder.txtvalue.setVisibility(View.VISIBLE);
////                                holder.seekBar.setVisibility(View.VISIBLE);
//                int x = (int) Math.ceil(i / 1000f);
//                holder.txtvalue.setText(String.valueOf(x));
////                if (x != 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
////                    mediaPlayer.pause();
////                    mediaPlayer.release();
////                    mediaPlayer = null;
////                    holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
////                    holder.seekBar.setProgress(0);
////                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
////                holder.txtvalue.setVisibility(View.VISIBLE);
//                holder.seekBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//                holder.seekBar.setProgress(0);
////                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
////                    mediaPlayer.seekTo(seekBar.getProgress());
////                    mediaPlayer.stop();
////                    mediaPlayer.release();
////                    mediaPlayer = null;
////
////                }
//            }
//        });

//        holder.imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragment = new MusicFragment();
//                FragmentManager fm3 = fragmentManager;
//                FragmentTransaction ft3 = fm3.beginTransaction();
//                ft3.replace(R.id.simpleFrameLayout, fragment);
//                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft3.commit();
//
//
////                if (mediaPlayer.isPlaying()){
////                    Log.d("tag ","========if ========== ");
////                    holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
////                    mediaPlayer.pause();
////                    holder.seekBar.setProgress(0);
////                }
////                else {
////                    Log.d("tag ","========elseif ========== ");
////                    holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_pause));
////                    mediaPlayer.setVolume(0.5f, 0.5f);
////                    mediaPlayer.setLooping(false);
////                    holder.seekBar.setVisibility(View.VISIBLE);
////                    holder.seekBar.setMax(mediaPlayer.getDuration());
////                    mediaPlayer.start();
////                   // new Thread(String.valueOf(this)).start();
////                }
//
//
//
//
//
//
//
//
//
//
//                //               try {
////                    if (!wasPlaying) {
////                        mediaPlayer.pause();
////                        mediaPlayer.release();
////                        mediaPlayer = null;
////                        holder.seekBar.setProgress(0);
////                        wasPlaying = false;
////                        holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
////                    }
////                    if (!wasPlaying) {
//
////                        if (mediaPlayer == null) {
////                            mediaPlayer = new MediaPlayer();
////                        }
//                    //else {
//                       // mediaPlayer = new MediaPlayer();
////                        if (sub.equals("not")) {
////                            storageRef = storageRef.child("audio").child(title).child(list.get(position));
////                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                                        @Override
////                                        public void onSuccess(Uri uri) {
////                                            Log.d("tag", "uri is :" + uri.toString());
////                                            if(mediaPlayer.isPlaying()){
////                                                holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
////                                                mediaPlayer.pause();
////                                            }else {
////
////
////                                                holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_pause));
////                                                // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                                                // mediaPlayer.setDataSource(uri.toString());
////                                                mediaPlayer=MediaPlayer.create(context,uri);
////                                                // mediaPlayer.prepare();
////                                                mediaPlayer.setVolume(0.5f, 0.5f);
////                                                mediaPlayer.setLooping(false);
////                                                holder.seekBar.setMax(mediaPlayer.getDuration());
////                                                mediaPlayer.start();
////                                                wasPlaying = true;
////                                                new Thread(String.valueOf(this)).start();
////                                            }
////                                        }
////                                    })
////                                    .addOnFailureListener(new OnFailureListener() {
////                                        @Override
////                                        public void onFailure(@NonNull Exception e) {
////                                            Log.d("tag", "uri is :" + e);
////                                        }
////                                    });
////                        }
////                        else {
////                            storageRef = storageRef.child("audio").child(title).child(sub).child(list.get(position));
////                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                                        @Override
////                                        public void onSuccess(Uri uri) {
////                                            Log.d("tag", "uri is :" + uri.toString());
////                                            if (mediaPlayer.isPlaying()) {
////                                                holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
////                                                mediaPlayer.pause();
////                                            } else {
////                                                holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_pause));
////                                                //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                                                //mediaPlayer.setDataSource(uri.toString());
////                                                mediaPlayer = MediaPlayer.create(context, uri);
////                                                //mediaPlayer.prepare();
////                                                mediaPlayer.setVolume(0.5f, 0.5f);
////                                                mediaPlayer.setLooping(false);
////                                                holder.seekBar.setMax(mediaPlayer.getDuration());
////                                                mediaPlayer.start();
////                                                wasPlaying = true;
////                                                new Thread(String.valueOf(this)).start();
////                                            }
////                                        }
////                                    })
////                                    .addOnFailureListener(new OnFailureListener() {
////                                        @Override
////                                        public void onFailure(@NonNull Exception e) {
////                                            Log.d("tag", "uri is :" + e);
////                                        }
////                                    });
////
////                        }
//
//
//                    //}
//
//                   // wasPlaying = false;
////                } catch (Exception e) {
////                    e.printStackTrace();
////
////                }
//
//
//// ========================control panel======================
////                if (sub.equals("not")) {
////
////                     db.collection("Audio").document(title)
////                             .collection("0").document(list.get(position))
////                             .delete()
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void aVoid) {
////                                storageRef= FirebaseStorage.getInstance().getReference();
////                                storageRef = storageRef.child("audio").child(title).child(list.get(position));
////                                storageRef.delete()
////                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                            @Override
////                                            public void onSuccess(Void aVoid) {
////                                                Toast.makeText(context, "تم مسح الملف", Toast.LENGTH_SHORT).show();
////                                            }
////                                        })
////                                        .addOnFailureListener(new OnFailureListener() {
////                                            @Override
////                                            public void onFailure(@android.support.annotation.NonNull Exception exception) {
////                                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
////                                            }
////                                        });
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@androidx.annotation.NonNull Exception e) {
////                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
////                            }
////                        });
////                } else {
////
////                     db = FirebaseFirestore.getInstance();
////                     db.collection("Audio").document(title)
////                             .collection("0").document(sub).collection("0")
////                             .document(list.get(position)).delete()
////                             .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                 @Override
////                                 public void onSuccess(Void aVoid) {
////                                     storageRef= FirebaseStorage.getInstance().getReference();
////// Create a reference to the file to delete
////                                     storageRef = storageRef.child("audio").child(title).child(sub).child(list.get(position));
////                                     storageRef.delete()
////                                             .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                                 @Override
////                                                 public void onSuccess(Void aVoid) {
////                                                     Toast.makeText(context, "تم مسح الملف", Toast.LENGTH_SHORT).show();
////                                                 }
////                                             })
////                                             .addOnFailureListener(new OnFailureListener() {
////                                                 @Override
////                                                 public void onFailure(@android.support.annotation.NonNull Exception exception) {
////                                                     Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
////                                                 }
////                                             });
////                                 }
////                             })
////                             .addOnFailureListener(new OnFailureListener() {
////                                 @Override
////                                 public void onFailure(@androidx.annotation.NonNull Exception e) {
////                                     Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
////                                 }
////                             });
////                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);

        }
    }
}
