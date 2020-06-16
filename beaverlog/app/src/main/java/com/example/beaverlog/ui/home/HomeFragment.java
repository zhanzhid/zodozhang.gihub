package com.example.beaverlog.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.beaverlog.MainActivity;
import com.example.beaverlog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query.Direction;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout homeRefreshLayout;
    //String postTitle[], postContent[];
    ArrayList<String> postTitle = new ArrayList<String>();
    ArrayList<String> postId = new ArrayList<String>();
    ArrayList<String> postContent = new ArrayList<String>();
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> likedPost = new ArrayList<String>();
    ArrayList<Long> replyCount  = new ArrayList<Long>();
    ArrayList<Boolean> liked = new ArrayList<Boolean>();
    int thumbnail = R.drawable.ic_android_black_24dp;
    ArrayList<String> avatarUrls  = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    DocumentReference likedRef;
    AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView = root.findViewById(R.id.recyclerViewPost);
        homeRefreshLayout = root.findViewById(R.id.homeRefershLayout);

        if(mUser != null){
            loadingAnimation();
            db.collection("users").document(mUser.getUid()).collection("likedPost")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    likedPost.add(document.getId());
                                }

                            } else {
                                Toast.makeText(getActivity(), "Failed to read liked", Toast.LENGTH_SHORT).show();
                            }

                            db.collection("post").orderBy("latestReplyAt", Direction.DESCENDING)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                dialog.dismiss();
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    liked.add(likedPost.contains(document.getId()));
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

                                            PostAdapter postAdapter = new PostAdapter(mAuth.getUid(), getActivity(), postTitle, postContent, avatarUrls, username, replyCount, postId, liked);
                                            recyclerView.setAdapter(postAdapter);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        }
                                    });
                        }
                    });
        } else {
            loadingAnimation();
            db.collection("post").orderBy("latestReplyAt", Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    liked.add(likedPost.contains(document.getId()));
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

                            PostAdapter postAdapter = new PostAdapter(mAuth.getUid(), getActivity(), postTitle, postContent, avatarUrls, username, replyCount, postId, liked);
                            recyclerView.setAdapter(postAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    });
        }

        homeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                homeRefreshLayout.setRefreshing(false);
            }
        });



        return root;
    }
    public void loadingAnimation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
}
