package com.soltan.app.Videos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NumberVideoAdpter extends RecyclerView.Adapter<NumberVideoAdpter.ViewHolder>{

    Context context;
    String title;
    String sub;
    String hasvideo;
    SubVideoTitleAdpter adpter;
    Map<Integer, String>cat,subtile;
    FirebaseFirestore db;
    List<String>list;
    ArrayList<Pair<Integer,String>> mActualData;
    FragmentManager fragmentManager;
    public NumberVideoAdpter(Map<Integer, String>cat,List<String>list ,Context context, String title, String sub,FragmentManager fragmentManager,String hasvideo
    ){
      this.context=context;
      this.cat=cat;
      this.sub=sub;
      this.title=title;
      this.list=list;
      this.hasvideo=hasvideo;
      this.fragmentManager=fragmentManager;
        mActualData = new ArrayList<>(cat.size());
        cat.forEach((s, s2) -> mActualData.add(new Pair<>(s, s2)));

   }
    @NonNull
    @Override
    public NumberVideoAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_droplist_item, parent, false);
        return new NumberVideoAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberVideoAdpter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.txt.setText( String.format("%s", mActualData.get(position).first));
        holder.txt.setText(list.get(position));
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //Log.d("tag","list size"+list.size());
              //Log.d("tag","list id"+list.get(position));
              //Log.d("tag","hasvideo"+hasvideo);
              if (hasvideo.equals("true")) {
                  db = FirebaseFirestore.getInstance();
                  db.collection("videos").document(title)
                          .collection("0").document(sub)
                          .collection("0")
                          .document(String.valueOf(list.get(position)))
                          .collection("0").get()
                          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                              @Override
                              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                  subtile = new HashMap<>();
                                  if (task.isSuccessful()) {
                                      for (QueryDocumentSnapshot document2 : task.getResult()) {
                                          subtile.put(Integer.valueOf(document2.getId()),(String) document2.get("videoId"));
                //                          Log.d("tag",list.get(position)+"================="+document2.getId()+"============"+document2.get("videoId")+"==>"+hasvideo);
                                      }
                                      Map<Integer, String> treeMap = new TreeMap<Integer, String>(subtile);
                                     // holder.recyclerView.setHasFixedSize(true);
                                      adpter = new SubVideoTitleAdpter(treeMap, context, list.get(position), fragmentManager,hasvideo);
                                      //hasSub="not";
                                    //Log.d("tag","hasSub "+mActualData.get(position).first);
                                      holder.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                      holder.recyclerView.setAdapter(adpter);
                                      holder.recyclerView.setHasFixedSize(true);
                                  }else {
                  //                    Log.d("tag","error"+task.getException().toString()
                   //                   );
                                  }
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                    //               Log.d("tag",e.toString());

                              }
                          });
              }
              else {
                  Intent intent = new Intent(context, PlayYoutubeActivity.class);
                  intent.putExtra("linkId", mActualData.get(position).second.toString());

                  context.startActivity(intent);
              }
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
