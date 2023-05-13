package com.soltan.app.Sounds;

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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SoundDropList extends RecyclerView.Adapter<SoundDropList.ViewHolder> {
    List<String> list;
    List<String> title;
    Context context;
    Map<String, ArrayList<String>> subtTile;
    SubTitleAdpter adpter;
    FirebaseFirestore db;
    Map<String,String>cat;
    List<String>sub,msub;
    FragmentManager fragmentManager;
    ArrayList<Pair<String, String>> mActualData;
    Fragment fragment = null;

    public SoundDropList(Map<String, String> cat, Context context, FragmentManager fragmentManager){
        this.cat=cat;
        this.context=context;
        this.fragmentManager=fragmentManager;
        mActualData = new ArrayList<>(cat.size());
        cat.forEach((s, s2) -> mActualData.add(new Pair<>(s, s2)));
       // cat.entrySet().stream().sorted(Map.Entry.comparingByKey());

    }
    public SoundDropList(List<String> list, Context context, Map<String,
            ArrayList<String>> subtTile,List<String> title){
     this.list=list;
     this.context=context;
     this.subtTile=subtTile;
     this.title=title;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_droplist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db = FirebaseFirestore.getInstance();
      holder.txt.setText( String.format("%s", mActualData.get(position).first));
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if(mActualData.get(position).second.equals("false")){
                fragment = new playAndDownloadSound();
                Bundle args = new Bundle();
                args.putString("title",mActualData.get(position).first);
                args.putString("sub","not");
                fragment.setArguments(args);
                FragmentManager fm = fragmentManager;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();


                //==========Control panel===========

//                Intent intent2=new Intent(context,ShowSoundToEdit.class);
//                      intent2.putExtra("title",mActualData.get(position).first);
//                      intent2.putExtra("sub","not");
//                      context.startActivity(intent2);
            }
            else{
                //               Log.d("tag","true");

                db.collection("Audio/"+mActualData.get(position).first+"/0").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                sub= new ArrayList<>();
                                for (DocumentSnapshot document2 : queryDocumentSnapshots.getDocuments()) {
                                    //  Log.d("tag","SoundDropList  "+document2.getId());
                                    sub.add(document2.getId());
                                    // Log.d("tag","document "+sub);
                                }
                                //Log.d("tag","document "+subtTile);
                                holder.recyclerView.setHasFixedSize(true);
                               //  Log.d("tag","document "+sub);
                                Collections.sort(sub);
                                //Log.d("tag","document2 "+sub);
                                adpter = new SubTitleAdpter(sub,context,mActualData.get(position).first,fragmentManager);
                                holder.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                holder.recyclerView.setAdapter(adpter);
                            }

                        });
            }



            }

//              for (String element : title) {
//                  if (element == list.get(position)) {
//                      Intent intent2=new Intent(context,ShowSoundToEdit.class);
//                      intent2.putExtra("title",list.get(position));
//                      intent2.putExtra("sub","not");
//                      context.startActivity(intent2);
//                  }
//              }
//                  Set set = subtTile.entrySet();//Converting to Set so that we can traverse
//                  Iterator itr = set.iterator();
//                  while (itr.hasNext()) {
//                      //Converting to Map.Entry so that we can get key and value separately
//                      Map.Entry entry = (Map.Entry) itr.next();
//                      if (list.get(position).equals(entry.getKey())) {
//                          Log.d("tag", entry.getKey() + " " + entry.getValue());
//                          holder.recyclerView.setHasFixedSize(true);
//                          adpter = new SubTitleAdpter((ArrayList<String>) entry.getValue(), context, list.get(position));
//                          holder.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                          holder.recyclerView.setAdapter(adpter);
//                      }
//                  }
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


