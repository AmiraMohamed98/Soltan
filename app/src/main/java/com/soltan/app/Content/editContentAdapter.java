package com.soltan.app.Content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.AddVideoData;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class editContentAdapter extends RecyclerView.Adapter<editContentAdapter.ViewHolder>{
    List<AddVideoData> list;
    Context context;
    FirebaseFirestore db;
    public editContentAdapter(List<AddVideoData> list,
                         Context context) {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public editContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_edit_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull editContentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
          holder.txt_video.setText("video link: "+list.get(position).getVideo_name());
        holder.txt_audio.setText("اسم الصوت:"+list.get(position).getAudio_name());
        holder.txt_pdf.setText("اسم الملف: "+list.get(position).getPdf_name());
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                db.collection("Content").document(list.get(position).getVideo_name())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, " يتم المسح ", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "لم يتم مسح الملف", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_video;
        private final TextView txt_audio;
        private final TextView txt_pdf;
        private final Button btn_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_video=itemView.findViewById(R.id.txt_video);
            txt_audio=itemView.findViewById(R.id.txt_audio);
            txt_pdf=itemView.findViewById(R.id.txt_pdf);
            btn_del=itemView.findViewById(R.id.btn_del);

        }
    }
}
