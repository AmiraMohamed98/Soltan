package com.soltan.app.ChannelPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soltan.app.MainActivity;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddChannelActivity extends AppCompatActivity {
    EditText edit_channel_link, edit_channel_name, edit_Image_link;
    Button btn_add;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        edit_channel_link = findViewById(R.id.edit_channel_link);
        edit_channel_name = findViewById(R.id.edit_channel_name);
        edit_Image_link = findViewById(R.id.edit_Image_link);
        btn_add = findViewById(R.id.btn_add);
       findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String channel_link = String.valueOf(edit_channel_link.getText());
                String channel_name = String.valueOf(edit_channel_name.getText());
                String Image_link = String.valueOf(edit_Image_link.getText());
               // Log.d("tag","data added"+channel_name);
                Map<String,String> map=new HashMap<>();
                map.put("channelName",channel_name);
                map.put("channelUrl",channel_link);
                map.put("imageUrl",Image_link);
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference();
                myRef.child("Channels").child(channel_name).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Log.d("tag","data added"+channel_name +map);
                        Toast.makeText(AddChannelActivity.this, "تم إضافه القناه", Toast.LENGTH_SHORT).show();
                        edit_channel_link.setText("");
                        edit_channel_name.setText("");
                        edit_Image_link.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddChannelActivity.this, " لم يتم إضافه القناه "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}