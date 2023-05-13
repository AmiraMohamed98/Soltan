package com.soltan.app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.soltan.app.Sections.DropListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class MainActivity2 extends AppCompatActivity
//        implements GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener
        {
    Button playBtn, pauseBtn;
    MediaPlayer mediaPlayer;
    private DropListAdapter adapter;
    FirebaseFirestore db;
    StorageReference storageRef;
    FirebaseStorage storage;
    List<String> audioList;
//    private Context context;
    //    private static String url = "https://newcapitalconsultants.com/response.json";
//
//    private static final String type = "vehicleType";
//    private static final String color = "vehicleColor";
////    private static final String fuel = "fuel";
//
//    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
//
//    ListView lv;
//    ArrayList<String> VideoTitle = new ArrayList<>();
//    ArrayList<String> VideoId = new ArrayList<>();
//    List<AddVideoData> list;
//    //CustomAdapter adapter;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;
//    AddVideoData videoData;
//    RecyclerView recyclerView;
//    String mSelectedFileDriveId = "1QS-D8eJR1jY90VP2rfoexPUT_IbWptZd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // HashMap<String, String[][]> map=new HashMap<>();

        //arrayList=new ArrayList<>();
        // arrayList.add("","","");
//        map.put("https://yt3.ggpht.com/ytc/AKedOLTP_IBYOCtlqkvJqpG4tZGSmguBZERtgwygJ3WqEw=s88-c-k-c0x00ffffff-no-rj", "https://www.youtube.com/channel/UCH1_lZBvUlVGNqzhcm8rZyw"});
//        map.put("https://yt3.ggpht.com/ytc/AKedOLTzuf87AuwjFvjIiHDqtMd7RsZ6XEY_VF9E66q-=s88-c-k-c0x00ffffff-no-rj","https://www.youtube.com/c/Ahmedsaidaldahh");
//        map.put("https://yt3.ggpht.com/ytc/AKedOLRctGDp1Va4xZs2cOlue_l1kmCJGq4EeLce5ipy=s88-c-k-c0x00ffffff-no-rj","https://www.youtube.com/user/AzharTvSite");
//        map.put("https://yt3.ggpht.com/ytc/AKedOLSeOFWHqPYLZmBt14EmFupJOYPDNpSgkjeNvtR9=s88-c-k-c0x00ffffff-no-rj","https://www.youtube.com/channel/UCmEAgtc64Pz0mJPBuXnL53A");
//        addDataToFirebase(map);

        //downloadfromFirebase();
//        addDataToFirebase(
//         "001 شرح شيخ الإسلام زكريا الأنصاري على الرسالة القشيرية - المقدمة (1) "
//       ,"LXOmpmLJEao","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase( " لة القشيرية 052- أ.د/ محمد سيد سلطان - تابع ترجمة الإمام الجنيد  "
//                ,"QepqGRwkQ4k","PLsM_RxqسلطانIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","QvTXAE-ufoA","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","Fiw2pbpDk5g","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","3THHUfYCHwM","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","4qRRI8HTA0U","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","MYBCLY8A5vA","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","pZ-eE7nst50","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","3HJ7LqBuJQ0","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");
//        addDataToFirebase("","sXDylnKNqEs","PLsM_RxqIvRtpKX6KwvdH5R5bvKhVHRo-6","not_found","not_found");



//        playBtn = findViewById(R.id.idBtnPlay);
//        pauseBtn = findViewById(R.id.idBtnPause);



//        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//        browserIntent.setData(Uri.parse("https://firebasestorage.googleapis.com/v0/b/videotube-352521.appspot.com/o/فتاوى%2Fفتاوى%20واحكام%20معاصره%2Fفتاوى%20معاصرة0104.WAV?alt=media&token=933b926a-e645-4b83-9c92-eb61d21f2945"));
//        startActivity(browserIntent);

        // setting on click listener for our play and pause buttons.
        // getFilesFromStorage();
//        playBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // calling method to play audio.
//                //playAudio();
//                getFilesFromStorage();
//            }
//        });
//
//        pauseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // checking the media player
//                // if the audio is playing or not.
//                if (mediaPlayer.isPlaying()) {
//                    // pausing the media player if media player
//                    // is playing we are calling below line to
//                    // stop our media player.
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                    mediaPlayer.release();
//
//                    // below line is to display a message
//                    // when media player is paused.
//                    Toast.makeText(MainActivity2.this, "Audio has been paused", Toast.LENGTH_SHORT).show();
//                } else {
//                    // this method is called when media
//                    // player is not playing.
//                    Toast.makeText(MainActivity2.this, "Audio has not played", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void getFilesFromStorage() {

        db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

//    storageRef.child("كتب").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//        @Override
//        public void onSuccess(ListResult listResult) {
//            for (StorageReference prefix : listResult.getPrefixes()) {
//                Log.d("tag", "prefix is" + prefix);
//            }
//
//            for (StorageReference item : listResult.getItems()) {
//                // All the items under listRef.
//                Log.d("tag", "item is" + item.getName());
//                Map<String, String> user = new HashMap<>();
//                user.put("isPdf", "true");
//                user.put("pdfName",item.getName());
//                db.collection("videos").document(item.getName()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("tag", "pdf is" + item.getName());
//                    }
//                });
//
//
////
////                mediaPlayer = new MediaPlayer();
////                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                try {
////                    mediaPlayer.setDataSource("");
////
////                    mediaPlayer.prepare();
////                    mediaPlayer.start();
////                    Log.d("tag","sound started");
////
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    Log.d("tag","sound is"+e.getMessage());
////
////                }
////                Toast.makeText(getApplicationContext(), "Audio started playing..", Toast.LENGTH_SHORT).show();
////            }
////
//            }
//        }
//
//    });


    }

    void getData() {
        db = FirebaseFirestore.getInstance();
        // subtTile=new HashMap<>();
//        db.collection("Audio").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    list = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        list.add(document.getId());
//                        Log.d("tag", "Audio is "+document.getId() + " => " + document.getData());
//
//                    }
//                    adapter = new SoundDropList(list, getContext());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.d("tag", "Error getting documents: ", task.getException());
//                }
//            }
//        });
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
       // audioList = new ArrayList<>();
        storageRef.child("audio").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            //  Log.d("tag","prefix"+prefix.getName());
                            storageRef.child("audio").child(prefix.getName()).listAll()
                                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            for (StorageReference item : listResult.getItems()) {
                                                //Log.d("tag", "item" + item.getName());
                                                Log.d("tag", "item is" + item.getName() );

                                              //  audioList.add(item.getName());

//                                                storageRef.child("audio/"+item.getName())
//                                                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                            @Override
//                                                            public void onSuccess(Uri uri) {
//                                                                Log.d("tag", "item is" + item.getName() + "uri is " + uri.toString());
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull Exception e) {
//                                                                Log.d("tag", "failed to get"+e.getMessage());
//
//                                                            }
//                                                        });

                                            }

                                        }
                                    });
//                            storageRef.child("audio").child(prefix.getName()).listAll()
//                                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                                        @Override
//                                        public void onSuccess(ListResult listResult) {
//                                            for (StorageReference pref : listResult.getPrefixes()) {
//                                                //Log.d("tag", "item" + item.getName());
//                                                storageRef.child("audio").child(prefix.getName()).child(pref.getName()).listAll()
//                                                        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                                                            @Override
//                                                            public void onSuccess(ListResult listResult) {
//                                                                for (StorageReference item2 : listResult.getItems()) {
//                                                                    //Log.d("tag", "item" + item.getName());
//                                                                    audioList.add(item2.getName());
//                                                                }
//
//
//                                                            }
//                                                        });
//                                            }
//
//                                        }
//                                    });
                        }
                    }
                });
    }

    private void playAudio() {


        String audioUrl = "https://firebasestorage.googleapis.com/v0/b/videotube-352521.appspot.com/o/audio%2Fتاج%20العروس%20الحاوي%20لتهذيب%20النفوس%201.MP3?alt=media&token=2781898f-d91a-4594-9d55-3e90a6d2b6f5";

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioUrl);

            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.d("tag", "sound started");

        } catch (IOException e) {
            e.printStackTrace();
            https:
//drive.google.com/drive/folders/17E9juTqvqQdonjBY9NdRGELpFMdMKxZM?usp=sharing
            Log.d("tag", "sound is" + e.getMessage());

        }
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
}// new ProgressTask(MainActivity.this).execute();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();

// videoData = new AddVideoData();
//addDataToFirebase("ما حكم أكل لحم الزرافة؟ الأستاذ الدكتور محمد سيد سلطان","iPg46RAZoco","PLsM_RxqIvRtoj8YP5FerRLRW76MZWIGWM","not_found","not_found");
// addDataToFirebase(" ما حكم أكل الأرنب؟ أ.د | محمد سيد سلطان","g_MeFctfSnw","PLsM_RxqIvRtoj8YP5FerRLRW76MZWIGWM","not_found","not_found");
// recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        list = new ArrayList<>();
//
//        try {
//            // get JSONObject from JSON file
//            JSONObject obj = new JSONObject(loadJSONFromAsset());
//            // fetch JSONArray named users
//            JSONArray userArray = obj.getJSONArray("items");
//            // implement for loop for getting users list data
//            for (int i = 0; i < userArray.length(); i++) {
//                // create a JSONObject for fetching single user data
//                JSONObject userDetail = userArray.getJSONObject(i);
//                // fetch email and name and store it in arraylist
//                VideoTitle.add(userDetail.getString("title"));
//                VideoId.add(userDetail.getString("videoId"));
//                Log.d("tag","videoTitle"+VideoTitle);
//                Log.d("tag","VideoId"+VideoId);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //  call the constructor of CustomAdapter to send the reference and data to Adapter
//        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,VideoId,VideoTitle);
//        recyclerView.setAdapter(customAdapter);


//getDataFromFirebase();

//        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "https://drive.google.com/file/d/1QS-D8eJR1jY90VP2rfoexPUT_IbWptZd/view?usp=sharing"; // your URL here
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioAttributes(
//                        new AudioAttributes.Builder()
//                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                                .setUsage(AudioAttributes.USAGE_MEDIA)
//                                .build()
//                );
//                try {
//                    mediaPlayer.setDataSource(url);
//                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                    mediaPlayer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


//    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
//        private ProgressDialog dialog;
//
//        public ProgressTask(ListActivity activity) {
//
//            Log.i("1", "Called");
//            context = activity;
//            dialog = new ProgressDialog(context);
//        }
//
//        private ListActivity context;
//
//        protected void onPreExecute() {
//            this.dialog.setMessage("Progress start");
//            this.dialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//            ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.recycler_item, new String[] { type, color }, new int[] { R.id.vehicleType, R.id.vehicleColor, R.id.fuel });
//            setListAdapter(adapter);
//            lv = getListView();
//
//        }
//
//        protected Boolean doInBackground(final String... args) {
//
//            JSONParser jParser = new JSONParser();
//            JSONArray json = jParser.getJSONFromUrl(url);
//
//            for (int i = 0; i < json.length(); i++) {
//
//                try {
//
//                    JSONObject c = json.getJSONObject(i);
//                    String vtype = c.getString("title");
//
//                    String vcolor = c.getString("videoId");
//
//                    HashMap<String, String> map = new HashMap<String, String>();
//
//                    map.put("title", vtype);
//                    map.put("videoId", vcolor);
//
//
//                    jsonlist.add(map);
//                } catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//
//        }
//
//    }

//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("video.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//    }


//    private void addDataToFirebase(String VideoName, String Video_id, String PlayList_id,
//                                   String Audio_id, String Pdf_id)  {
//
//        videoData.setVideoName(VideoName);
//        videoData.setVideo_id(Video_id);
//        videoData.setAudio_id(Audio_id);
//        videoData.setPlayList_id(PlayList_id);
//        videoData.setPdf_id(Pdf_id);
//
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.child(videoData.getPlayList_id()).setValue(videoData);
//                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
//                Log.d("tag","data added");
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//                Log.d("tag","Fail to add data " + error);
//            }
//        });
//
//    }
//

//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult result) {
//        // Called whenever the API client fails to connect.
//        // Do something...
//    }
//
//    @Override
//    //https://drive.google.com/file/d/1QS-D8eJR1jY90VP2rfoexPUT_IbWptZd/view?usp=sharing
//    public void onConnected(@Nullable Bundle bundle) {
//        // If there is a selected file, open its contents.
//
//        if (mSelectedFileDriveId != null) {
//            open();
//            return;
//        }
//
//        // Let the user pick a file...
//        IntentSender intentSender = Drive.DriveApi
//                .newOpenFileActivityBuilder()
//                .setMimeType(new String[]{"video/mp4", "image/jpeg", "text/plain"})
//                .build(mGoogleApiClient);
//        try {
//            startIntentSenderForResult(intentSender, RC_OPENER, null, 0, 0, 0);
//        } catch (IntentSender.SendIntentException e) {
//            Log.e("TAG", "Unable to send intent", e);
//        }
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//    }

//    private void open() {
//
//        DriveFile.DownloadProgressListener listener = new DriveFile.DownloadProgressListener() {
//            @Override
//            public void onProgress(long bytesDownloaded, long bytesExpected) {
//                // Update progress dialog with the latest progress.
//                int progress = (int) (bytesDownloaded * 100 / bytesExpected);
//                Log.d("TAG", String.format("Loading progress: %d percent", progress));
//
//            }
//        };
//        DriveFile driveFile = mSelectedFileDriveId.asDriveFile();
//        driveFile.open(mGoogleApiClient, DriveFile.MODE_READ_ONLY, listener)
//                .setResultCallback(driveContentsCallback);
//        mSelectedFileDriveId = null;
//    }
//
//    private final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
//            new ResultCallback<DriveApi.DriveContentsResult>() {
//                @Override
//                public void onResult(@NonNull DriveApi.DriveContentsResult result) {
//                    if (!result.getStatus().isSuccess()) {
//                        Log.w("TAG", "Error while opening the file contents");
//                        return;
//                    }
//                    Log.i("TAG", "File contents opened");
//
//                    // Read from the input stream an print to LOGCAT
//                    DriveContents driveContents = result.getDriveContents();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(driveContents.getInputStream()));
//                    StringBuilder builder = new StringBuilder();
//                    String line;
//                    try {
//                        while ((line = reader.readLine()) != null) {
//                            builder.append(line);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    String contentsAsString = builder.toString();
//                    Log.i("TAG", contentsAsString);
//
//                    // Close file contents
//                    driveContents.discard(mGoogleApiClient);
//                }
//            };
//}