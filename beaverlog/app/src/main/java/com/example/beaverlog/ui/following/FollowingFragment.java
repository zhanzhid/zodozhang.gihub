package com.example.beaverlog.ui.following;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beaverlog.R;
import com.example.beaverlog.ui.home.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FollowingFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    View root;
    RecyclerView recyclerView;
    //String postTitle[], postContent[];
    ArrayList<String> postTitle = new ArrayList<String>();
    ArrayList<String> postId = new ArrayList<String>();
    ArrayList<String> postContent = new ArrayList<String>();
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<Long> replyCount  = new ArrayList<Long>();
    int thumbnail = R.drawable.ic_android_black_24dp;
    ArrayList<String> avatarUrls  = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            root = inflater.inflate(R.layout.fragment_following, container, false);
            recyclerView = root.findViewById(R.id.recyclerViewLikedPost);
            db.collection("users").document(mUser.getUid()).collection("likedPost").orderBy("likedAt", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    postTitle.add(document.getString("title"));
                                    postContent.add(document.getString("content"));
                                    username.add(document.getString("username"));
                                    replyCount.add(document.getLong("replyCount"));
                                    avatarUrls.add(document.getString("avatarUrl"));
                                    postId.add(document.getId());

                                }
                            } else {
                                Toast.makeText(getActivity(), "Failed to read posts", Toast.LENGTH_SHORT).show();
                            }
                            LikedPostAdapter likedPostAdapter = new LikedPostAdapter(getActivity(), postTitle, postContent, avatarUrls, username, replyCount, postId);
                            recyclerView.setAdapter(likedPostAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    });


        } else {
            Toast.makeText(getActivity(), "Must log in to see liked posts.", Toast.LENGTH_SHORT).show();

        }

        return root;
    }
}
