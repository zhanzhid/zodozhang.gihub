package com.example.beaverlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beaverlog.ui.home.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView startReplyButton, opTitle, opUsername, opContent, opReplyCount;
    ImageView opAvatar;
    String title, username, content, avatarUrl, postId;
    Long replyCount;
    EditText newReplyContent;
    Button submitReply,cancelReply;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> replyContent = new ArrayList<String>();
    ArrayList<String> replyAuthor = new ArrayList<String>();
    ArrayList<String> replyThumbnail = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppCompatActivity) this).getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        recyclerView = findViewById(R.id.recyclerViewReplyInDetail);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        opTitle = findViewById(R.id.postTitleTextInDetail);
        opUsername = findViewById(R.id.authorUsernameInDetail);
        opContent = findViewById(R.id.postContentTextInDetail);
        opReplyCount = findViewById(R.id.replyCountInDetail);
        startReplyButton = findViewById(R.id.startReplyButtonInDetail);
        opAvatar = findViewById(R.id.thumbnailImageInDetail);


        getData();
        setData();

        db.collection("reply").orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(PostDetailActivity.this, postId, Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("postID").contains(postId)){
                                    replyContent.add(document.getString("content"));
                                    replyAuthor.add(document.getString("username"));
                                    replyThumbnail.add(document.getString("avatarUrl"));
                                }

                            }
                        } else {
                            Toast.makeText(PostDetailActivity.this, "Failed to read posts", Toast.LENGTH_SHORT).show();
                        }
                        ReplyAdapter replyAdapter = new ReplyAdapter(PostDetailActivity.this, replyAuthor, replyContent, replyThumbnail);
                        recyclerView.setAdapter(replyAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this));
                    }
                });

        if (mUser != null) {
            startReplyButton.setVisibility(View.VISIBLE);
            startReplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostDetailActivity.this, NewReplyActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("username", username);
                    intent.putExtra("replyCount", replyCount);
                    intent.putExtra("avatarUrl", avatarUrl);
                    intent.putExtra("postId", postId);
                    startActivity(intent);
                    finish();
                }
            });

        } else {
            startReplyButton.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Please log in to post reply.", Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {
        if(getIntent().hasExtra("title") &&
                getIntent().hasExtra("content") &&
                getIntent().hasExtra("username") &&
                getIntent().hasExtra("replyCount") &&
                getIntent().hasExtra("avatarUrl") &&
                getIntent().hasExtra("postId")) {
            title = getIntent().getStringExtra("title");
            username = getIntent().getStringExtra("username");
            content = getIntent().getStringExtra("content");
            replyCount = getIntent().getLongExtra("replyCount", 0);
            avatarUrl = getIntent().getStringExtra("avatarUrl");
            postId = getIntent().getStringExtra("postId");


        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {
        opTitle.setText(title);
        opUsername.setText(username);
        opContent.setText(content);
        opReplyCount.setText(replyCount+" replies");
        new downloadImageTask(opAvatar).execute(avatarUrl);
        //opAvatar.setImageResource(Integer.parseInt(avatarUrl));
    }

    private class downloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

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
        }
    }
}
