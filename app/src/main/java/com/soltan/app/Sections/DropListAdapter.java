package com.soltan.app.Sections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropListAdapter extends RecyclerView.Adapter<DropListAdapter.ViewHolder> {
    List<String> list;
    Context context;
    String type="video";
    Map<String,String>cat;
    ArrayList<Pair<String, String>> mActualData;
    Fragment fragment = null;
    FragmentManager fragmentManager;

    public DropListAdapter(List<String> list,Context context,String type){
       this.context=context;
       this.list=list;
       this.type=type;

    }

    public DropListAdapter(Map<String, String> cat, Context context) {
        this.context=context;
        this.cat=cat;
        mActualData = new ArrayList<>(cat.size());
        cat.forEach((s, s2) -> mActualData.add(new Pair<>(s, s2)));
    }

    @NonNull
    @Override
    public DropListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_droplist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DropListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.txt.setText(list.get(position));
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(type.equals("video")){
               Intent intent=new Intent(context ,ShowDataTOEdit.class);
               intent.putExtra("playlist",list.get(position));
               context.startActivity(intent);

//              else {
//                  FirebaseStorage storage = FirebaseStorage.getInstance();
//                  StorageReference storageRef = storage.getReference();
//                  storageRef.child(type).child(list.get(position)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                      @Override
//                      public void onSuccess(Uri uri) {
//                          Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                          browserIntent.setData(Uri.parse(uri.toString()));
//                          context.startActivity(browserIntent);
//                      }
//                  });
              }
          }
      });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
        }
    }
}
