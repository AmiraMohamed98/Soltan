package com.soltan.app.Videos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;

import java.util.ArrayList;
import java.util.Map;

public class SubVideoTitleAdpter extends RecyclerView.Adapter<SubVideoTitleAdpter.ViewHolder>{
    Map<Integer, String> sub;
    Context context;
    String title;
    FragmentManager fragmentManager;
    String hasvideo;
    ArrayList<Pair<Integer, String>> mActualData;
    public SubVideoTitleAdpter(Map<Integer, String> sub, Context context, String title, FragmentManager fragmentManager,String hasvideo) {
      this.context=context;
      this.fragmentManager=fragmentManager;
      this.title=title;
      this.sub=sub;
      this.hasvideo=hasvideo;
        mActualData = new ArrayList<>(sub.size());
        sub.forEach((s, s2) -> mActualData.add(new Pair<>(s, s2)));
    }

    @NonNull
    @Override
    public SubVideoTitleAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_item, parent, false);
        return new SubVideoTitleAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubVideoTitleAdpter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt.setText( String.format("%s", mActualData.get(position).first));
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context, PlayYoutubeActivity.class);
              intent.putExtra("linkId", mActualData.get(position).second);
              context.startActivity(intent);

          }
      });
    }

    @Override
    public int getItemCount() {
        return mActualData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);

        }
    }
}
