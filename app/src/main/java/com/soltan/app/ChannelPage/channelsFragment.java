package com.soltan.app.ChannelPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class channelsFragment extends Fragment {
    channelAdapter channelAdapter;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    channelData channelData;
    List<channelData> list ;
    public channelsFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.channels_fragment, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list=new ArrayList<>();
        getDataFromFirebase();

        return view;
    }

    void getDataFromFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Channels");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String value = snapshot.getValue(String.class);
                //Log.d("TAG", "Value is: " + value);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    channelData = dataSnapshot.getValue(channelData.class);
                   // Log.d("tag","channelData is "+channelData);
                    list.add(channelData);
                    //Toast.makeText(getContext(), "channelData get success", Toast.LENGTH_SHORT).show();
                }
                channelAdapter = new channelAdapter(list, getContext());
                recyclerView.setAdapter(channelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.w("tag", "Failed to read value.", error.toException());
                Toast.makeText(getContext(),  "لم يتم اضافه القناه"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
