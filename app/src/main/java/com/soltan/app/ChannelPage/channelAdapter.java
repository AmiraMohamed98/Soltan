package com.soltan.app.ChannelPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class channelAdapter extends RecyclerView.Adapter<channelAdapter.ViewHolder> {

    List<channelData> list ;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public channelAdapter(List<channelData> list ,Context context){
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_item, parent, false);

        return new channelAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

     holder.txt.setText(list.get(position).getChannelName());

        Picasso
                .get()
                .load(list.get(position).getImageUrl())
                .into(holder.img);
//        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent=new Intent(context,EditChannelActivity.class);
//               intent.putExtra("Name",list.get(position).getChannelName());
//                intent.putExtra("link",list.get(position).getChannelUrl());
//                intent.putExtra("image",list.get(position).getImageUrl());
//                context.startActivity(intent);
//
//            }
//        });
//        holder.btn_del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseDatabase = FirebaseDatabase.getInstance();
//                databaseReference = firebaseDatabase.getReference("Channels");
//                databaseReference.child(list.get(position).getChannelName()).removeValue()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//            }
//        });
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getChannelUrl())));
         }
     });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt;
        private final CircularImageView img;
       // private final Button btn_edit,btn_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
            img=itemView.findViewById(R.id.img);
//            btn_del=itemView.findViewById(R.id.btn_del);
//            btn_edit=itemView.findViewById(R.id.btn_edit);
        }
    }
}
