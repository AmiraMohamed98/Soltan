package com.soltan.app.Videos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.soltan.app.R;
import com.soltan.app.Utlis.MyConsts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PlayVideosFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    String title,sub;

    List<String > list;


    public PlayVideosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Bundle parameters = getArguments();
//        title=parameters.getString("title");
//        sub=parameters.getString("sub");
//        Log.d("tag","title"+title+"sub"+sub);
        View view= inflater.inflate(R.layout.fragment_play_videos, container, false);
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        getData_1();
        YouTubePlayerView ytPlayer = (YouTubePlayerView)view.findViewById(R.id.youtube);
        ytPlayer.initialize(MyConsts.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
                {
                    youTubePlayer.loadVideo("HzeK7g8cD0Y");
                    youTubePlayer.play();
                }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getContext(), "Video player Failed", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
    void getData_1() {
        if (sub.equals("not")) {
            db = FirebaseFirestore.getInstance();
            db.collection("videos").document(title)
                    .collection("0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Log.d("tag", "tagbbb " + document.getId() + " => " + document.getData());
                                    list.add(document.getId());
                                }
//                                adapter = new addedSoundsAdapter(list, getContext(),title,sub);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "لم يتم استدعاء صوتيات", Toast.LENGTH_SHORT).show();
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "لا يوجد صوتيات", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            db = FirebaseFirestore.getInstance();
            db.collection("videos").document(title)
                    .collection("0").document(sub).collection("0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            list = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("tag", "tagbbb " + document.getId() + " => " + document.getData());
                                    list.add(document.getId());
                                }

//                                adapter = new addedSoundsAdapter(list, getContext(),title,sub);
//                                recyclerView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "لا يوجد صوتيات", Toast.LENGTH_SHORT).show();
                                // Log.d("tag", "لم يتم استدعاء الفيديوهات  ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "لا يوجد صوتيات", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}