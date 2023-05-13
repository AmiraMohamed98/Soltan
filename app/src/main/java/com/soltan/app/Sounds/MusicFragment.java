package com.soltan.app.Sounds;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.soltan.app.R;


public class MusicFragment extends Fragment {
    TextView txt_max_value,txt_current_value;
    SeekBar seekBar;
    ImageButton btn_play;
    MediaPlayer mediaPlayer ;
    String url;
    Thread updateseekbar;
    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle parameters = getArguments();
        url=parameters.getString("url");

        View view= inflater.inflate(R.layout.fragment_music, container, false);
        txt_current_value=view.findViewById(R.id.txt_current_value);
        txt_max_value=view.findViewById(R.id.txt_max_value);
        seekBar=view.findViewById(R.id.seekBar);
        btn_play=view.findViewById(R.id.btn_play);
                if (mediaPlayer !=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(url));
        mediaPlayer.start();

        updateseekbar = new Thread()
        {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentposition = 0;

                while (currentposition<totalDuration)
                {
                    try {
                        sleep(500);
                        currentposition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentposition);
                    }
                    catch (InterruptedException | IllegalStateException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
//        mediaPlayer.setVolume(0.5f, 0.5f);
//        mediaPlayer.setLooping(false);
//
        String endTime = createTime(mediaPlayer.getDuration());
        txt_max_value.setText(endTime);
        seekBar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                int x = (int) Math.ceil(i / 1000f);
//                if (x != 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                    seekBar.setProgress(0);
//                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });


        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txt_current_value.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                   // btn_play.setBackgroundResource(R.drawable.ic_pause);
                    btn_play.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
                    mediaPlayer.pause();
                }
                else
                {
                    //btn_play.setBackgroundResource(R.drawable.ic_play);
                    btn_play.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_pause));
                    mediaPlayer.start();
                }
            }
        });


        return view;
    }
    public String createTime(int duration)
    {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time+=min+":";

        if (sec<10)
        {
            time+="0";
        }
        time+=sec;

        return  time;
    }
//    if (mediaPlayer.isPlaying()){
//        Log.d("tag ","========if ========== ");
//        holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
//        mediaPlayer.pause();
//        holder.seekBar.setProgress(0);
//    }
//                else {
//        Log.d("tag ","========elseif ========== ");
//        holder.imageButton.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.ic_media_pause));
//        mediaPlayer.setVolume(0.5f, 0.5f);
//        mediaPlayer.setLooping(false);
//        holder.seekBar.setVisibility(View.VISIBLE);
//        holder.seekBar.setMax(mediaPlayer.getDuration());
//        mediaPlayer.start();
//        // new Thread(String.valueOf(this)).start();
//    }
}