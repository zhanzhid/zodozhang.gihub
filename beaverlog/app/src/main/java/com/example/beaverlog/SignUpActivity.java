package com.example.beaverlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.beaverlog.ui.account.ValidateInput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    EditText signUpEmail, signUpPassword, signUpRepeatPassword, signUpUsername;
    Button signUpButton;
    ImageView backArrow;
    String email, password, username;
    ValidateInput validateInput;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Calendar calendar = Calendar.getInstance();
    //String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpRepeatPassword = findViewById(R.id.repeatPassword);
        signUpUsername = findViewById(R.id.signUpUsername);
        signUpButton = findViewById(R.id.signUpButton);
        backArrow = findViewById(R.id.backArrow);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        //AlerDialog Builder
        builder = new AlertDialog.Builder(this);


        validateInput = new ValidateInput(
                SignUpActivity.this,
                signUpEmail,
                signUpPassword,
                signUpRepeatPassword,
                signUpUsername
        );

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewAccount();
            }
        });
    }

    public void signUpNewAccount() {
        loadingAnimation();

        boolean emailVerified = validateInput.validateEmail();
        boolean passwordVerified = validateInput.validatePassword();
        boolean repeatPasswordVerified = validateInput.validateRepeatPassword();
        boolean usernameVerified = validateInput.validateUsername();


        if(emailVerified && passwordVerified && repeatPasswordVerified && usernameVerified){

            email = signUpEmail.getText().toString().trim();
            password = signUpPassword.getText().toString().trim();
            username = signUpUsername.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                final FirebaseUser user = mAuth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/beaverlog1.appspot.com/o/defaultAvatar%2FOregon-State-W.png?alt=media&token=482c9e15-d480-4220-92a8-ae57dee60ee9"))
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Map<String, Object> mUser = new HashMap<>();
                                                    mUser.put("username", username);
                                                    mUser.put("email", email);
                                                    mUser.put("timestamp", FieldValue.serverTimestamp());
                                                    db.collection("users").document(user.getUid()).set(mUser);
                                                }
                                            }
                                        });


                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();



                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Sign Up failed.",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });

        }else {
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
