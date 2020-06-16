package com.example.beaverlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NewReplyActivity extends AppCompatActivity {
    TextView opTitle, opUsername, opContent;
    ImageView opAvatar;
    String title, username, content, avatarUrl, postId;
    Long replyCount;
    EditText newReplyContent;
    Button submitReply,cancelReply;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reply);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        opTitle = findViewById(R.id.postTitleTextInNewReply);
        opUsername = findViewById(R.id.authorUsernameInNewReply);
        opContent = findViewById(R.id.postContentTextInNewReply);
        submitReply = findViewById(R.id.createNewReplyButton);
        cancelReply = findViewById(R.id.cancelNewReplyButton);
        newReplyContent = findViewById(R.id.newReplyContent);
        opAvatar = findViewById(R.id.thumbnailImageInNewReply);
        getData();
        setData();

        final Intent intent = new Intent(NewReplyActivity.this, PostDetailActivity.class);

        submitReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> reply = new HashMap<>();
                reply.put("content", newReplyContent.getText().toString());
                reply.put("postID", postId);
                reply.put("userId", mUser.getUid());
                reply.put("username", mUser.getDisplayName());
                reply.put("timestamp", FieldValue.serverTimestamp());
                reply.put("avatarUrl", mUser.getPhotoUrl().toString());
                db.collection("reply").add(reply);
                db.collection("post").document(postId).update("latestReplyAt", FieldValue.serverTimestamp());
                db.collection("post").document(postId).update("replyCount", (replyCount+1));
                //opReplyCount.setText((replyCount+1)+" replies");
                newReplyContent.setText("");

                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("username", username);
                intent.putExtra("replyCount", (replyCount+1));
                intent.putExtra("avatarUrl", avatarUrl);
                intent.putExtra("postId", postId);
                startActivity(intent);
                finish();
            }
        });

        cancelReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        //opReplyCount.setText(replyCount+" replies");
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
