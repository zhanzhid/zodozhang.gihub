package com.example.beaverlog.ui.following;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beaverlog.PostDetailActivity;
import com.example.beaverlog.R;
import com.example.beaverlog.ui.home.PostAdapter;

import java.io.InputStream;
import java.util.ArrayList;

public class LikedPostAdapter extends RecyclerView.Adapter<LikedPostAdapter.MyViewHolder> {

    ArrayList<String> title, content, username, avatarUrl, postId;
    ArrayList<Long> replyCount;
    //int images;
    Context context;
    public LikedPostAdapter(Context ct,  ArrayList<String> s1,  ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4, ArrayList<Long> s5, ArrayList<String> s6){
        context = ct;
        title = s1;
        content = s2;
        avatarUrl = s3;
        username = s4;
        replyCount = s5;
        postId = s6;
        //images = img;

    }
    @NonNull
    @Override
    public LikedPostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.following_item, parent, false);
        return new LikedPostAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedPostAdapter.MyViewHolder holder, final int position) {
        holder.text1.setText(title.get(position));
        holder.text2.setText(content.get(position));
        holder.text3.setText(username.get(position));
        holder.text4.setText(replyCount.get(position)+" replies");
        //holder.image.setImageResource(images);
        new LikedPostAdapter.downloadImageTask(holder.image).execute(avatarUrl.get(position));

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
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView text1, text2, text3, text4;
        ImageView image;
        ConstraintLayout postItemLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.likedPostTitleText);
            text2 = itemView.findViewById(R.id.likedPostContentText);
            text3 = itemView.findViewById(R.id.likedAuthorUsername);
            text4 = itemView.findViewById(R.id.likedReplyCount);
            image = itemView.findViewById(R.id.likedThumbnailImage);
            postItemLayout = itemView.findViewById(R.id.followingItem);
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
