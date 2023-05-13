package com.soltan.app.Kenawy;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import android.content.Context;

public class addedKenawy extends RecyclerView.Adapter<addedKenawy.ViewHolder>{
    Context context;
    StorageReference storageRef;
    FirebaseFirestore db;
    List<String> list;
    String playlist;
    FragmentManager fragmentManager;
    public addedKenawy(List<String>list, FragmentManager fragmentManager, String playlist){
      this.fragmentManager=fragmentManager;
      this.list=list;
      this.playlist=playlist;
    }


    @NonNull
    @Override
    public addedKenawy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addedKenawy.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt.setText(list.get(position));
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               storageRef= FirebaseStorage.getInstance().getReference();
                storageRef.child("فتاوى"+"/"+playlist).child(list.get(position))
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "تم مسح الملف", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@android.support.annotation.NonNull Exception exception) {
                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
                            }
                        });


//                db = FirebaseFirestore.getInstance();
//                db.collection("Fatwa").document(playlist).collection("0")
//                        .document(list.get(position))
//                        .delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//// Create a reference to the file to delete
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@androidx.annotation.NonNull Exception e) {
//                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();
//                            }
//                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt;
        private Button btn_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
            btn_del=itemView.findViewById(R.id.btn_del);
        }
    }
}
