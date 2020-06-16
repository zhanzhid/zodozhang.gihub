package com.example.beaverlog.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beaverlog.MainActivity;
import com.example.beaverlog.PostDetailActivity;
import com.example.beaverlog.R;
import com.example.beaverlog.UpdateAvatarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    //String title[], content[];
    ArrayList<String> title, content, username, avatarUrl, postId;
    ArrayList<Long> replyCount;
    ArrayList<Boolean> liked;
    ImageView likeButton;
    String userId;
    //int images;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PostAdapter(String id, Context ct,  ArrayList<String> s1,  ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4, ArrayList<Long> s5, ArrayList<String> s6, ArrayList<Boolean> s7){
        userId = id;
        context = ct;
        title = s1;
        content = s2;
        avatarUrl = s3;
        username = s4;
        replyCount = s5;
        postId = s6;
        liked = s7;
        //images = img;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.text1.setText(title.get(position));
        holder.text2.setText(content.get(position));
        holder.text3.setText(username.get(position));
        holder.text4.setText(replyCount.get(position)+" replies");
        //holder.image.setImageResource(images);
        new downloadImageTask(holder.image).execute(avatarUrl.get(position));

        if (liked.get(position)) {
            holder.like.setColorFilter(Color.argb(255, 255, 0, 0));
        }

        holder.postItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("title", title.get(position));
                intent.putExtra("content", content.get(position));
                intent.putExtra("username", username.get(position));
                intent.putExtra("replyCount", replyCount.get(position));
                intent.putExtra("avatarUrl", avatarUrl.get(position));
                intent.putExtra("postId", postId.get(position));
                context.startActivity(intent);

            }
        });

        if(userId != null) {
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(liked.get(position)){
                        holder.like.setColorFilter(Color.argb(255, 169, 169, 169));
                        liked.set(position, false);
                        db.collection("users").document(userId).collection("likedPost").document(postId.get(position)).delete();
                        Toast.makeText(context, "Removed from liked posts.", Toast.LENGTH_SHORT).show();

                    } else {
                        holder.like.setColorFilter(Color.argb(255, 255, 0, 0));
                        liked.set(position, true);
                        Map<String, Object> likedPost = new HashMap<>();
                        likedPost.put("title", title.get(position));
                        likedPost.put("content", content.get(position));
                        likedPost.put("username", username.get(position));
                        likedPost.put("likedAt", FieldValue.serverTimestamp());
                        likedPost.put("replyCount", replyCount.get(position));
                        likedPost.put("avatarUrl", avatarUrl.get(position));
                        db.collection("users").document(userId).collection("likedPost").document(postId.get(position)).set(likedPost);

                        Toast.makeText(context, "Added to liked posts.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else {
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Log in to like posts.", Toast.LENGTH_SHORT).show();


                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView text1, text2, text3, text4;
        ImageView image, like;
        ConstraintLayout postItemLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.postTitleText);
            text2 = itemView.findViewById(R.id.postContentText);
            text3 = itemView.findViewById(R.id.authorUsername);
            text4 = itemView.findViewById(R.id.replyCount);
            image = itemView.findViewById(R.id.thumbnailImage);
            postItemLayout = itemView.findViewById(R.id.postItem);
            like = itemView.findViewById(R.id.likeButton);
        }
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
