package com.soltan.app.PDF;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder>{
    List<String> list;
    FirebaseFirestore db;
    Context context;
    String type;
    StorageReference storageRef;
    AlertDialog.Builder builder;
    public PdfAdapter( List<String> list,Context context,String type){
        this.context=context;
        this.list=list;
        this.type=type;
        builder = new AlertDialog.Builder(context);
    }
    @NonNull
    @Override
    public PdfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.txt.setText(list.get(position).replace(".pdf",""));
      holder.txt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              storageRef= FirebaseStorage.getInstance().getReference();

              //Setting message manually and performing action on button click
              builder.setTitle("ماذا تريد ؟")
                      .setCancelable(false)
                      .setPositiveButton("تنزيل", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                              dialog.cancel();

// Create a reference to the file to delete
                              //storageRef= storageRef.child(type+"/"+list.get(position));
                              storageRef=storageRef.child(type).child(list.get(position));
                              storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                  @Override
                                  public void onSuccess(Uri uri) {
                                      Log.d("tag","pdf url is :"+uri.toString());
                          DownloadManager downloadmanager = (DownloadManager) context.
                                  getSystemService(Context.DOWNLOAD_SERVICE);

                          DownloadManager.Request request = new DownloadManager.Request(uri);

                          request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                         // request.setDestinationInExternalFilesDir(context, type, list.get(position) + "pdf");
                          request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, list.get(position));

                          downloadmanager.enqueue(request);

                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@androidx.annotation.NonNull Exception e) {
                                      Log.d("tag "," file not download " +e.toString());

                                  }
                              });

                      }
                      })
                      .setNegativeButton("عرض", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                              //  Action for 'NO' Button
                              dialog.cancel();
                              // Create a reference to the file to delete
                              //storageRef= storageRef.child(type+"/"+list.get(position));
                              storageRef=storageRef.child(type).child(list.get(position));
                              storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                  @Override
                                  public void onSuccess(Uri uri) {
                                      Log.d("tag","pdf url is :"+uri.toString());
                          Intent intent = new Intent(Intent.ACTION_VIEW);
                          intent.setDataAndType(uri, "application/pdf");
                          context.startActivity(intent);
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@androidx.annotation.NonNull Exception e) {
                                      Log.d("tag "," file not download " +e.toString());

                                  }
                              });

                      }
                      });
              //Creating dialog box
              AlertDialog alert = builder.create();
              //Setting the title manually

              alert.show();
//              File localFile = null;
//              storageRef= FirebaseStorage.getInstance().getReference();
// Create a reference to the file to delete
                //storageRef= storageRef.child(type+"/"+list.get(position));
//              storageRef=storageRef.child(type).child(list.get(position));
//                  storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                      @Override
//                      public void onSuccess(Uri uri) {
//                          Log.d("tag","pdf url is :"+uri.toString());
////                          DownloadManager downloadmanager = (DownloadManager) context.
////                                  getSystemService(Context.DOWNLOAD_SERVICE);
////
////                          DownloadManager.Request request = new DownloadManager.Request(uri);
////
////                          request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
////                         // request.setDestinationInExternalFilesDir(context, type, list.get(position) + "pdf");
////                          request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, list.get(position));
////
////                          downloadmanager.enqueue(request);
//                         // String url = "http://example.com/sample.pdf";
////                          Intent intent = new Intent(Intent.ACTION_VIEW);
////                          intent.setDataAndType(uri, "application/pdf");
////                          context.startActivity(intent);
//                      }
//                  }).addOnFailureListener(new OnFailureListener() {
//                      @Override
//                      public void onFailure(@androidx.annotation.NonNull Exception e) {
//                          Log.d("tag "," file not download " +e.toString());
//
//                      }
//                  });
              }

      });
//        holder.btn_del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                storageRef= FirebaseStorage.getInstance().getReference();
//// Create a reference to the file to delete
//                storageRef= storageRef.child(type+"/"+list.get(position));
//                storageRef.delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(context, "تم مسح الملف", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
////                db = FirebaseFirestore.getInstance();
////                db.collection(type).document(list.get(position))
////                        .delete()
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void aVoid) {
////
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@androidx.annotation.NonNull Exception e) {
////                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
////                            }
////                        });
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
        }
    }
}
