package com.soltan.app.Content;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soltan.app.AddVideoData;
import com.soltan.app.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<AddVideoData> list;
    Context context;
    Fragment fragment = null;
    FragmentManager fragmentManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final WebView webView;
         //YouTubePlayerView youtube;
         Button btn_read;
         Button btn_leasn;
      //  Button btn_view;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            webView =  view.findViewById(R.id.webView);
            //youtube=view.findViewById(R.id.youtube);
            btn_read =  view.findViewById(R.id.btn_read);
            btn_leasn =  view.findViewById(R.id.btn_leasn);
           // btn_view =  view.findViewById(R.id.btn_view);
        }


    }


    public CustomAdapter(List<AddVideoData> list,
                         Context context,FragmentManager fragmentManager) {
        this.list=list;
        this.context=context;
        this.fragmentManager=fragmentManager;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        String YouTubeVideoEmbedCode = "<html><body><iframe  src=\"https://www.youtube.com/embed/"+list.get(position).getVideo_name()+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";
        String videoLink="https://www.youtube.com/watch?v="+list.get(position).getVideo_name();
       // Log.d("tag","YouTubeVideoEmbedCode"+YouTubeVideoEmbedCode);
//        if(!list.get(position).getAudio_id().contains("Not_Found"))  {
//            viewHolder.btn_leasn.setVisibility(View.VISIBLE);
//        }
//        if(!list.get(position).getPdf_id().contains("Not_Found"))  {
//            viewHolder.btn_read.setVisibility(View.VISIBLE);
//        }

        // Get element from your dataset at this position and replace the
        // contents of the view with th
//        viewHolder.youtube.initialize(MyConsts.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo(list.get(position).getVideo_id());
//                youTubePlayer.play();
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText(context, "Video player Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        viewHolder.btn_view.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Log.d("tag",videoLink);
//
//            }
//        });
        viewHolder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getPdf_url()=="") {
                    Toast.makeText(context, "لايوجد كتاب", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(list.get(position).getPdf_url()), "application/pdf");
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.btn_leasn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getAudio_url() == "") {
                    Toast.makeText(context, "لايوجد صوت", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(list.get(position).getAudio_url()), "audio/*");
                    context.startActivity(intent);
                }
            }
        });
        WebSettings webSettings = viewHolder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        viewHolder.webView.loadData(YouTubeVideoEmbedCode, "text/html", "utf-8");
        Log.d("tag","YouTubeVideoEmbedCode"+YouTubeVideoEmbedCode);

        viewHolder.webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag",videoLink);

            }
        });
        viewHolder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
//                new YouTubeExtractor(view.getContext()) {
//                    @Override
//                    public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
//                        if (ytFiles != null) {
//                            int itag = 22;
//                            String downloadUrl = ytFiles.get(itag).getUrl();
//                            DownloadManager downloadmanager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
//                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
//                            request.setTitle("My File");
//                            request.setDescription("Downloading");
//                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                            request.setVisibleInDownloadsUi(false);
//                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"downloads");
//                            downloadmanager.enqueue(request);
//                        }
//                    }
//                }.extract(videoLink,true,true);



//                DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
//
//                DownloadManager.Request request= new DownloadManager.Request (Uri.parse(videoLink));
//
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,".mp4");
//                request.allowScanningByMediaScanner();
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
//                downloadManager.enqueue(request);


