package com.evelyne.labs.myapplication.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evelyne.labs.myapplication.R;
import com.evelyne.labs.myapplication.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button  chooseImage, Upload;
    private EditText company,capacity,plate,price;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView showUpload;

    private Uri imageUri;
    private StorageReference mStorage;
    private DatabaseReference mReference;
    private StorageTask mUploadTask;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_upload,null);

        showUpload = (TextView) root.findViewById(R.id.showUploads);
        company = (EditText) root.findViewById(R.id.description);
        capacity = (EditText) root.findViewById(R.id.cap);
        plate = (EditText) root.findViewById(R.id.noplate);
        price = (EditText) root.findViewById(R.id.prices);
        Upload = (Button) root.findViewById(R.id.upload);
        chooseImage = (Button) root.findViewById(R.id.chooseFile);
        imageView = (ImageView) root.findViewById(R.id.imageview);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBarUpload);

        mStorage = FirebaseStorage.getInstance().getReference("uploads");
        mReference = FirebaseDatabase.getInstance().getReference("uploads");

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(),"Upload in Progress", Toast.LENGTH_LONG).show();

                }else {
                    OpenImageChooser();
                }
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadfile();
                progressBar.setVisibility(View.VISIBLE);

            }
        });

        return root;
    }
    private void OpenImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST || resultCode == RESULT_OK
         || data !=null || data.getData() != null){
            imageUri = data.getData();

            Picasso.with(chooseImage.getContext()).load(imageUri).into(imageView);
        }
    }
    private String getFileExt(Uri uri){

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void Uploadfile(){
        if(imageUri !=null){
           StorageReference filereference = mStorage.child(System.currentTimeMillis()
           +"."+getFileExt(imageUri));

           mUploadTask=filereference.putFile(imageUri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                           Toast.makeText(getContext(),"Upload Successful", Toast.LENGTH_LONG).show();
                           Upload upload = new Upload(company.getText().toString().trim()
                           ,taskSnapshot.getUploadSessionUri().toString());
                           Upload uploada = new Upload(capacity.getText().toString().trim()
                                   ,taskSnapshot.getUploadSessionUri().toString());
                           Upload uploadb = new Upload(plate.getText().toString().trim()
                                   ,taskSnapshot.getUploadSessionUri().toString());
                           Upload uploadc = new Upload(price.getText().toString().trim()
                                   ,taskSnapshot.getUploadSessionUri().toString());
                           String uploadId = mReference.push().getKey();
                           mReference.child(uploadId).setValue(upload);
                           mReference.child(uploadId).setValue(uploada);
                           mReference.child(uploadId).setValue(uploadb);
                           mReference.child(uploadId).setValue(uploadc);
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                           //gives the progress
                           double progress=(100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                           //progressBar.getProgress((int)progress);
                       }
                   });
        }else{
            Toast.makeText(getContext(),"No File Selected", Toast.LENGTH_LONG).show();
        }
    }
}