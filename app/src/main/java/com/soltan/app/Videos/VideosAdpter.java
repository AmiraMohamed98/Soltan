package com.soltan.app.Videos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class VideosAdpter extends RecyclerView.Adapter<VideosAdpter.ViewHolder>{
    List<String> list;
    List<String> title;
    String hasSub;
    Context context;
    SubTitleVideoAdpter adpter;
    Map<String,String> subtTile;
    FirebaseFirestore db;
    Map<String,String>cat;
    ArrayList<String>sub;
    FragmentManager fragmentManager;
    ArrayList<Pair<String, String>> mActualData;
    Fragment fragment = null;


    public  VideosAdpter(Map<String, String> cat, Context context, FragmentManager fragmentManager){
        this.cat=cat;
        this.context=context;
        this.fragmentManager=fragmentManager;
        mActualData = new ArrayList<>(cat.size());
        cat.forEach((s, s2) -> mActualData.add(new Pair<>(s, s2)));
    }
    @NonNull
    @Override
    public VideosAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_droplist_item, parent, false);
        return new VideosAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdpter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db = FirebaseFirestore.getInstance();
        holder.txt.setText( String.format("%s", mActualData.get(position).first));
        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.d("tag","document "+mActualData.get(position).first +" ==="+mActualData.get(position).second);
                if(mActualData.get(position).second.equals("false")){
                    fragment = new NumberVideoFragment();
                    Bundle args = new Bundle();
                    args.putString("title",mActualData.get(position).first);
                    args.putString("sub","not");
                    args.putString("hasvideo","not");
                    fragment.setArguments(args);
                    FragmentManager fm = fragmentManager;
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.simpleFrameLayout, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }
                else{
                    db.collection("videos/"+mActualData.get(position).first+"/0").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    //sub = new ArrayList<>();
                                    subtTile=new HashMap<>();
                                    for (DocumentSnapshot document2 : queryDocumentSnapshots.getDocuments()) {
                     //                   Log.d("tag","document "+document2.getId() +" ==Data="+document2.getData()+"==subVideo====="+document2.get("subVideo"));
                                       // sub.add(document2.getId());
                                         subtTile.put(document2.getId(), (String) document2.get("subVideo"));
                                        //Log.d("tag","document "+document2.getId() +"==subVideo====="+document2.get("subVideo"));
                      //                   Log.d("tag","document "+sub);
//
                                    }
                                    //Log.d("tag","subtTile "+subtTile);
//                                    Log.d("tag","document "+sub);
                                    //Log.d("tag","hasSub "+hasSub);
                                    holder.recyclerView.setHasFixedSize(true);
                                    Map<String, String> treeMap = new TreeMap<String, String>(subtTile);

                                    adpter = new SubTitleVideoAdpter(sub,treeMap,context,mActualData.get(position).first,fragmentManager);
                                    //hasSub="not";
//                                    Log.d("tag","hasSub "+hasSub);
                                    holder.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                    holder.recyclerView.setAdapter(adpter);
                                }

                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mActualData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
            recyclerView=itemView.findViewById(R.id.recyclerView);
        }
    }
}
