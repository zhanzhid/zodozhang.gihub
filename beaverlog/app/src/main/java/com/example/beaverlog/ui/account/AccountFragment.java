package com.example.beaverlog.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.beaverlog.MainActivity;
import com.example.beaverlog.R;
import com.example.beaverlog.SignUpActivity;
import com.example.beaverlog.UpdateAvatarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;

public class AccountFragment extends Fragment {

    TextView createAccount,emailAddressText, idText, usernameText;
    ImageView avatarImage;
    Button signIn, updateEmailButton, updatePasswordButton, signOutButton;
    EditText signInEmail, signInPassword;
    ValidateInput validateInput;
    String email, password, mEmail, mId, mUsername;
    AlertDialog dialog;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CheckBox remember;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        View root;
        if (mUser != null) {


            root = inflater.inflate(R.layout.fragment_signed_in_account, container, false);
            updateEmailButton = root.findViewById(R.id.updateEmailButton);
            updatePasswordButton = root.findViewById(R.id.updatePasswordButton);
            signOutButton = root.findViewById(R.id.signOutButton);
            emailAddressText = root.findViewById(R.id.emainAddressText);
            idText = root.findViewById(R.id.idText);
            usernameText = root.findViewById(R.id.usernameText);
            avatarImage = root.findViewById(R.id.avatarAccountImageView);

            mEmail = mUser.getEmail();
            mId = mUser.getUid();
            mUsername = mUser.getDisplayName();
            emailAddressText.setText(mEmail);
            idText.setText(mId);
            usernameText.setText((mUsername));
            new downloadImageTask(avatarImage).execute(mUser.getPhotoUrl().toString());
            avatarImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UpdateAvatarActivity.class);
                    startActivity(intent);

                }
            });

            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
            return root;
        } else {
            root = inflater.inflate(R.layout.fragment_account, container, false);
            signIn = root.findViewById(R.id.signInButton);
            createAccount = root.findViewById(R.id.createAccountText);
            signInEmail = root.findViewById(R.id.signInEmail);
            signInPassword = root.findViewById(R.id.signInPassword);
            remember = root.findViewById(R.id.checkBoxRemember);
            validateInput = new ValidateInput(getActivity().getApplicationContext(), signInEmail, signInPassword);

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInAccount();

                }
            });

            remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.isChecked()){
                        SharedPreferences preferences = (SharedPreferences) getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "true");
                        editor.apply();
                    } else if(!buttonView.isChecked()){
                        SharedPreferences preferences = (SharedPreferences) getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "false");
                        editor.apply();
                    }
                }
            });
            createAccount.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivity(intent);
                }
            });
            return root;
        }


    }
/*
    @Override
    public void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(), "Please sign in to continue.", Toast.LENGTH_SHORT).show();
        }
    }
*/
    public void signInAccount() {

        loadingAnimation();

        boolean emailVerified = validateInput.validateEmail();
        boolean passwordVerified = validateInput.validatePassword();

        if(emailVerified && passwordVerified){

            email = signInEmail.getText().toString().trim();
            password = signInPassword.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Sign In failed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
        }else {
            dialog.dismiss();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

}
