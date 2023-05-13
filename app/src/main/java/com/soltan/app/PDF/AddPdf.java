package com.soltan.app.PDF;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.soltan.app.MainActivity;
import com.soltan.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPdf extends AppCompatActivity {
    EditText edit_pdf_name;
    ActivityResultLauncher<Intent> resultLauncher;
    StorageReference storageRef;
    FirebaseStorage storage;
    FirebaseFirestore db;
    Bundle parameters;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pdf);
        parameters= getIntent().getExtras();
        type=parameters.getString("type");
        edit_pdf_name = findViewById(R.id.edit_pdf_name);
        storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();
        db = FirebaseFirestore.getInstance();
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result)
                    {
                        // Initialize result data
                        Intent data = result.getData();
                        // check condition
                        if (data != null) {
                            // When data is not equal to empty
                            // Get PDf uri
                            Uri sUri = data.getData();
                            // set Uri on text view
                            Log.d("tag","tag is"+ String.valueOf(Html.fromHtml(
                                    "<big><b>PDF Uri</b></big><br>"
                                            + sUri+"")));
                              uploadPdf(sUri);
                            // Get PDF path
                            String sPath = sUri.getPath();
                            // Set path on text view
                            Log.d("tag","tag is"+ String.valueOf(Html.fromHtml(
                                    "<big><b>PDF Path</b></big><br>"
                                            + sPath)));
//                            2022-07-20 07:01:16.620 18555-18555/com.example.controlpanel D/tag: tag isPDF Uri
//                            content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2F1-Basic_Concepts_part1.pdf
//                            2022-07-20 07:01:16.624 18555-18555/com.example.controlpanel D/tag: tag isPDF Path
//                                    /document/raw:/storage/emulated/0/Download/1-Basic_Concepts_part1.pdf
//
                        }
                    }
                });

        // Set click listener on button
        findViewById(R.id.btn_add_file).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v)
                    {
                        // check condition
                        if (ActivityCompat.checkSelfPermission(
                                AddPdf.this,
                                Manifest.permission
                                        .READ_EXTERNAL_STORAGE)
                                != PackageManager
                                .PERMISSION_GRANTED) {
                            // When permission is not granted
                            // Result permission
                            ActivityCompat.requestPermissions(
                                    AddPdf.this,
                                    new String[] {
                                            Manifest.permission
                                                    .READ_EXTERNAL_STORAGE },
                                    1);
                        }
                        else {
                            // When permission is granted
                            // Create method
                            selectPDF();
                        }
                    }
                });
    }

    private void uploadPdf(Uri sUri) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        String name=edit_pdf_name.getText().toString();
        progressDialog.setTitle("جارى التحميل");
        progressDialog.show();

        storageRef = storageRef.child(type).child(name);
        storageRef.putFile(sUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddPdf.this, "تم إضافه الكتاب", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
//                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
//                          while (!uri.isComplete());
//                          Uri url=uri.getResult();
//
//                Map<String,String>map=new HashMap<>();
//                map.put("Name",edit_pdf_name.getText().toString());
//                map.put("url",url.toString());
//                db.collection(type).document(name).set(map)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@androidx.annotation.NonNull Exception e) {
//                                Toast.makeText(AddPdf.this, "لم يتم إضافه الكتاب", Toast.LENGTH_SHORT).show();
//                                progressDialog.dismiss();
//                            }
//                        });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@androidx.annotation.NonNull UploadTask.TaskSnapshot snapshot) {
              double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
              progressDialog.setMessage("تم الرفع "+(int)progress+" %");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception e) {

            }
        });

    }

    private void selectPDF()
    {
        // Initialize intent
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        // check condition
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF();
        }
        else {
            // When permission is denied
            // Display toast
            Toast.makeText(getApplicationContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }
}