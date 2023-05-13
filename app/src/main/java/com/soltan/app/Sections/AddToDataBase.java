package com.soltan.app.Sections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.soltan.app.MainActivity;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddToDataBase extends AppCompatActivity {
    TextView txt;
    EditText edit_name,edit_link;
    Button btn_add;
    FirebaseFirestore db;
    ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_data_base);
        Bundle parameters = getIntent().getExtras();
        String list_name=parameters.getString("listName");
        txt=findViewById(R.id.txt);
        edit_name=findViewById(R.id.edit_name);
        edit_link=findViewById(R.id.edit_link);
        btn_add=findViewById(R.id.btn_add);
        txt.setText(txt.getText().toString()+list_name);

       // Log.d("tag","data hhh"+nname);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddToDataBase.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  String nname=edit_name.getText().toString();
                String nlink=edit_link.getText().toString();
                Map<String,String>map=new HashMap<>();
                map.put("video_id",nlink);
//                map.put("video_name",nname);
                db = FirebaseFirestore.getInstance();
                db.collection("videos/"+list_name+"/0").document(nlink)
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddToDataBase.this, " تم الاضافه ", Toast.LENGTH_SHORT).show();
                                edit_link.setText("");
                                edit_name.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddToDataBase.this, "لم يتم الاضافه ", Toast.LENGTH_SHORT).show();
                                Toast.makeText(AddToDataBase.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });
    }

}