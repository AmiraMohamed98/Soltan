package com.soltan.app.Kenawy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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

import java.util.List;

public class KenawyListAdpter extends RecyclerView.Adapter<KenawyListAdpter.ViewHolder>{
    List<String> list;
    Context context;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    public KenawyListAdpter(List<String> list,FragmentManager fragmentManager){
       this.fragmentManager=fragmentManager;
        this.list=list;
    }
    @NonNull
    @Override
    public KenawyListAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.droplist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KenawyListAdpter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
         holder.txt.setText(list.get(position));
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 fragment = new playAndDownloadkenawy();
                 Bundle args = new Bundle();
                 args.putString("playlist",list.get(position));
                 fragment.setArguments(args);
                 FragmentManager fm = fragmentManager;
                 FragmentTransaction ft = fm.beginTransaction();
                 ft.replace(R.id.simpleFrameLayout, fragment);
                 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                 ft.commit();


//                 Intent intent=new Intent(context , ShowKenawyToEdit.class);
//                 intent.putExtra("playlist",list.get(position));
//                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
        }
    }
}
