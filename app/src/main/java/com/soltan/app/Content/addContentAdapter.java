package com.soltan.app.Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;

import java.util.List;

public class addContentAdapter extends RecyclerView.Adapter<addContentAdapter.ViewHolder> {
    List<String> list;
    Context context;
    String type;
    int selectedPosition = -1;
    ItemClickListener itemClickListener;
    public addContentAdapter(List<String> list, ItemClickListener itemClickListener,
                             Context context,String type) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.type=type;

    }

    @NonNull
    @Override
    public addContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.added_content_item, parent, false);
        return new ViewHolder(view);
    }
    private RadioButton lastCheckedRB = null;

    @Override
    public void onBindViewHolder(@NonNull addContentAdapter.ViewHolder holder, int position) {
        holder.radioButton.setText(list.get(position));
        holder.radioButton.setChecked(position == selectedPosition);
//        String s=path.get("audio_path");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.radioButton.setChecked(true);
                itemClickListener.onClick(
                        holder.radioButton.getText().toString(),type);

            }
        });
//        View.OnClickListener rbClick2 = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RadioButton checked_rb = (RadioButton) v;
//                if(lastCheckedRB != null){
//                    lastCheckedRB.setChecked(false);
////                    selectedPosition
////                            = holder.getAdapterPosition();
//                    // Call listener
//                    itemClickListener.onClick(holder.radioButton.getText().toString(),type);
//                }
//                lastCheckedRB = checked_rb;
//            }
//        };
       // holder.radioButton.setOnClickListener(rbClick2);

       // boolean ischecked = position == checkedposition ? true : false;






//        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    // When checked
//                    // update selected position
//                    selectedPosition
//                            = holder.getAdapterPosition();
//                    // Call listener
//                    itemClickListener.onClick(
//                            holder.radioButton.getText()
//                                    .toString(),type);
//                }
//                else {
//                    selectedPosition=-1;
//                }
//            }
//        });

    }

    @Override
    public long getItemId(int position) {
        // pass position
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // pass position
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radio_button);
            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getAdapterPosition();
                   itemClickListener.onClick(radioButton.getText().toString(),type);

                    notifyItemRangeChanged(0, list.size());
                }
            };

            itemView.setOnClickListener(l);
            radioButton.setOnClickListener(l);
        }
    }

//    static class GFG {
//        public static void main(String[] args) {
//            System.out.println("GFG!");
//        }
//    }
}
