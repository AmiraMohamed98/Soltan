package com.soltan.app.Sections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.soltan.app.Utlis.MyConsts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    Context context;
    List<String> list;
    String playlist;
    FirebaseFirestore db;
    public VideoAdapter(List<String> list, Context context,String playlist){
        this.playlist=playlist;
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        YouTubePlayer.OnInitializedListener listener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(list.get(position));
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(context, "failed play", Toast.LENGTH_SHORT).show();
            }
        };
        holder.youTubePlayerView.initialize(MyConsts.PLAYLIST_ID,listener);
           holder.btn_delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   db = FirebaseFirestore.getInstance();
                   db.collection("videos/"+playlist+"/0")
                           .document(list.get(position))
                           .delete()
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Toast.makeText(context, "تم مسح الفيديو", Toast.LENGTH_SHORT).show();
                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(context, "لم يتم مسح الفيديو", Toast.LENGTH_SHORT).show();
                               }
                           });

               }
           });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView youTubePlayerView;
        Button btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_delete=itemView.findViewById(R.id.btn_delete);
            youTubePlayerView=itemView.findViewById(R.id.youtube);

        }
    }
}
