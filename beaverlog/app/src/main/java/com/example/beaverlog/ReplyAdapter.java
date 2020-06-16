package com.example.beaverlog;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {

    ArrayList<String>  content, username, avatarUrl, postId;
    Context context;
    public ReplyAdapter(Context ct,  ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3){
        context = ct;
        content = s2;
        avatarUrl = s3;
        username = s1;

    }
    @NonNull
    @Override
    public ReplyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reply_item, parent, false);
        return new ReplyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.MyViewHolder holder, int position) {
        holder.text1.setText(username.get(position));
        holder.text2.setText(content.get(position));
        //holder.image.setImageResource(images);
        new downloadImageTask(holder.image).execute(avatarUrl.get(position));

    }

    @Override
    public int getItemCount() {
        if(content == null)
            return 0;
        return content.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView text1, text2;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.replyAuthorUsername);
            text2 = itemView.findViewById(R.id.replyContentText);
            image = itemView.findViewById(R.id.replyThumbnailImage);
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
