package com.soltan.app.Videos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubTitleVideoAdpter extends RecyclerView.Adapter<SubTitleVideoAdpter.ViewHolder> {
    String title;
    Context context;
    List<String> list;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FirebaseFirestore db;
    ArrayList<Pair<String, String>> mActualData;
    Map<String, String> cat;
   public SubTitleVideoAdpter(List<String> list, Context context, String title, FragmentManager fragmentManager){
     this.list=list;
     this.context=context;
     this.title=title;
     this.fragmentManager=fragmentManager;
   }
    public SubTitleVideoAdpter(ArrayList<String> list, Map<String, String> cat, Context context, String title, FragmentManager fragmentManager){
        this.list=list;
        this.context=context;
        this.title=title;
        this.fragmentManager=fragmentManager;
        this.cat=cat;
        mActualData = new ArrayList<>(cat.size());
        cat.forEach((s, s2) -> mActualData.add(new Pair<>(s, s2)));
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sublist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt.setText( String.format("%s", mActualData.get(position).first));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            fragment = new NumberVideoFragment();
            Bundle args = new Bundle();
            args.putString("title",title);
            args.putString("sub",mActualData.get(position).first);
            args.putString("hasvideo",mActualData.get(position).second);
           // Log.d("tag","hasvideo :"+mActualData.get(position).first+"======>"+mActualData.get(position).second);
            fragment.setArguments(args);
            FragmentManager fm = fragmentManager;
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.simpleFrameLayout, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
//           Intent intent=new Intent(context,ShowSoundToEdit.class);
//            intent.putExtra("title",title);
//            intent.putExtra("sub",list.get(position));
//            context.startActivity(intent);
        }
    });

    }

    @Override
    public int getItemCount() {
       return mActualData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
        }
    }
}
