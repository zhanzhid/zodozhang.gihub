package com.example.beaverlog.ui.newpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.beaverlog.MainActivity;
import com.example.beaverlog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewpostFragment extends Fragment {
    EditText newPostTitle, newPostContent;
    Button createNewPostButton;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Calendar calendar = Calendar.getInstance();
    //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            root = inflater.inflate(R.layout.fragment_newpost, container, false);

            newPostTitle = root.findViewById(R.id.newPostTitle);
            newPostContent = root.findViewById(R.id.newPostContent);
            createNewPostButton = root.findViewById(R.id.createNewPostButton);

            createNewPostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> post = new HashMap<>();
                    post.put("title", newPostTitle.getText().toString());
                    post.put("content", newPostContent.getText().toString());
                    //post.put("createdAt", currentDate);
                    post.put("userId", mUser.getUid());
                    post.put("username", mUser.getDisplayName());
                    post.put("timestamp", FieldValue.serverTimestamp());
                    post.put("latestReplyAt", FieldValue.serverTimestamp());
                    post.put("replyCount", 0);
                    post.put("avatarUrl", mUser.getPhotoUrl().toString());
                    db.collection("post").add(post);
                    newPostTitle.setText("");
                    newPostContent.setText("");
                    Toast.makeText(getActivity(), "Successfully created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(getActivity(), "Must log in to create new post.", Toast.LENGTH_SHORT).show();
        }

        return root;

    }
}
