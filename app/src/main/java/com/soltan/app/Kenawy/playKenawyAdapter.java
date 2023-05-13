package com.soltan.app.Kenawy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.soltan.app.Sounds.MusicFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class playKenawyAdapter extends RecyclerView.Adapter<playKenawyAdapter.ViewHolder> {
    Context context;
    List<String> list;
    String title, sub;
    //  FirebaseFirestore db;
    StorageReference storageRef;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean wasPlaying = false;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    AlertDialog.Builder builder;
    DownloadManager.Request request;
    DownloadManager downloadmanager;

    public playKenawyAdapter(List<String> list, FragmentManager fragmentManager, String title,Context context) {
        this.list = list;
       this.fragmentManager=fragmentManager;
        this.title = title;
        this.context=context;
        storageRef= FirebaseStorage.getInstance().getReference();
        builder = new AlertDialog.Builder(context);
    }

    @NonNull
    @Override
    public playKenawyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sublist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull playKenawyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageRef = FirebaseStorage.getInstance().getReference();
                builder.setTitle("ماذا تريد ؟")
                        .setCancelable(false)
                        .setPositiveButton("تنزيل", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                    storageRef.child("فتاوى"+"/"+title).child(list.get(position));                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            request = new DownloadManager.Request(uri);
                                            downloadmanager = (DownloadManager) context.
                                                    getSystemService(Context.DOWNLOAD_SERVICE);
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            //request.setDestinationInExternalFilesDir(context, list.get(position), list.get(position) + "mp3");
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, list.get(position));
                                            downloadmanager.enqueue(request);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("tag "," file not download " +e.toString());

                                        }
                                    });
                                }


                        }).setNegativeButton("إستماع", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                storageRef.child("فتاوى"+"/"+title).child(list.get(position));
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //=MediaPlayer.create(context,uri);
                                            fragment = new MusicFragment();
                                            Bundle args = new Bundle();
                                            args.putString("url",uri.toString());
                                            fragment.setArguments(args);
                                            FragmentManager fm3 = fragmentManager;
                                            FragmentTransaction ft3 = fm3.beginTransaction();
                                            ft3.replace(R.id.simpleFrameLayout, fragment);
                                            ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                            ft3.commit();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("tag "," file not download " +e.toString());

                                        }
                                    });
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
//                File localFile = null;
//                storageRef.child("فتاوى"+"/"+title).child(list.get(position));
//                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            DownloadManager downloadmanager = (DownloadManager) context.
//                                    getSystemService(Context.DOWNLOAD_SERVICE);
//
//                            DownloadManager.Request request = new DownloadManager.Request(uri);
//
//                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                            request.setDestinationInExternalFilesDir(context, title, title + "mp3");
//
//                            downloadmanager.enqueue(request);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("tag "," file not download " +e.toString());
//
//                        }
//                    });
//            }
//        });
//        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                holder.txtvalue.setVisibility(View.VISIBLE);
//                holder.seekBar.setVisibility(View.VISIBLE);
//                int x = (int) Math.ceil(i / 1000f);
//                holder.txtvalue.setText(String.valueOf(x));
//                if (x != 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                    holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
//                    holder.seekBar.setProgress(0);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                holder.txtvalue.setVisibility(View.VISIBLE);
//                holder.seekBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    mediaPlayer.seekTo(seekBar.getProgress());
//                }
//            }
//        });
//        holder.imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer = null;
//                        holder.seekBar.setProgress(0);
//                        wasPlaying = true;
//                        holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
//                    }
//                    if (!wasPlaying) {
//
//                        if (mediaPlayer == null) {
//                            mediaPlayer = new MediaPlayer();
//                        }
//
//                            storageRef.child("فتاوى"+"/"+title).child(list.get(position));
//                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    Log.d("tag", "uri is :" + uri.toString());
//                                    try {
//                                        holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_pause));
//                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                                        mediaPlayer.setDataSource(uri.toString());
//                                        mediaPlayer.prepare();
//                                        mediaPlayer.setVolume(0.5f, 0.5f);
//                                        mediaPlayer.setLooping(false);
//                                        holder.seekBar.setMax(mediaPlayer.getDuration());
//                                        mediaPlayer.start();
//                                        new Thread(String.valueOf(this)).start();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("tag", "uri is :" + e);
//                                }
//                            });
//                    }
//
//                    wasPlaying = false;
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
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
        TextView txt, txtvalue;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);

        }
    }
}
