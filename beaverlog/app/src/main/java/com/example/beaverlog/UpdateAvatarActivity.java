package com.example.beaverlog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.beaverlog.ui.account.AccountFragment;
import com.example.beaverlog.ui.home.PostAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.UUID;

public class UpdateAvatarActivity extends AppCompatActivity {
    Uri imageUri;
    ImageView currentAvatar, backArrow;
    Button selectPhotoButton, updateAvatarButton;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final int PICK_IMAGE = 100;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference postThumbnailRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppCompatActivity) this).getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);

        builder = new AlertDialog.Builder(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        backArrow = findViewById(R.id.backArrow3);
        currentAvatar = findViewById(R.id.currentAvatar);
        selectPhotoButton = findViewById(R.id.selectPhotoButton);
        updateAvatarButton = findViewById(R.id.confirmUpdateEmailButton);
        updateAvatarButton.setVisibility(View.GONE);

        new downloadImageTask(currentAvatar).execute(mUser.getPhotoUrl().toString());

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnimation();
                StorageReference storageRef = storage.getReference();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                final StorageReference avatarRef = storageRef.child("images/"+imageUri.getLastPathSegment());
                UploadTask uploadTask = avatarRef.putFile(imageUri);


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return avatarRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            final Uri downloadUri = task.getResult();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(downloadUri.toString()))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                db.collection("post")
                                                        .whereEqualTo("userId", mUser.getUid())
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                        postThumbnailRef = db.collection("post").document(document.getId());
                                                                        postThumbnailRef.update("avatarUrl", downloadUri.toString())
                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {
                                                                                        db.collection("reply")
                                                                                                .whereEqualTo("userId", mUser.getUid())
                                                                                                .get()
                                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                                                postThumbnailRef = db.collection("reply").document(document.getId());
                                                                                                                postThumbnailRef.update("avatarUrl", downloadUri.toString())
                                                                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onSuccess(Void aVoid) {



                                                                                                                                //Toast.makeText(UpdateAvatarActivity.this, "Successfully updated." ,Toast.LENGTH_SHORT).show();
                                                                                                                                dialog.dismiss();
                                                                                                                                Intent intent = new Intent(UpdateAvatarActivity.this, MainActivity.class);
                                                                                                                                startActivity(intent);
                                                                                                                                finish();
                                                                                                                            }
                                                                                                                        })
                                                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                                                            @Override
                                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                                Toast.makeText(UpdateAvatarActivity.this, "error!!!." ,Toast.LENGTH_SHORT).show();
                                                                                                                            }
                                                                                                                        });
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                });

                                                                                    }
                                                                                })
                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        Toast.makeText(UpdateAvatarActivity.this, "error!!!." ,Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            }
                                                        });






                                            }
                                        }
                                    });
                        } else {
                            // Handle failures
                            Toast.makeText(UpdateAvatarActivity.this, "Upload failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            currentAvatar.setImageURI(imageUri);
            updateAvatarButton.setVisibility(View.VISIBLE);
        }
    }

    private class downloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        @Override
        protected void onPreExecute() {

            loadingAnimation();
        }
        public downloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            dialog.dismiss();
        }
    }
    public void loadingAnimation() {
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

}
