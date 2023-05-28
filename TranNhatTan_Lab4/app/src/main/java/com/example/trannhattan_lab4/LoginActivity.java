package com.example.trannhattan_lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String userName, Password ;
    // creating variable for button
    private Button signupBtn;
    private EditText editUsername, editPassword;
    Button buttonlogin;
    TextView Register;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        editUsername = findViewById(R.id.LoginEditUsername);

        editPassword = findViewById(R.id.LoginEditPassword);

        buttonlogin = findViewById(R.id.btn_Login);
        Register = findViewById(R.id.Register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                userName = editUsername.getText().toString();
                Password = editPassword.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(userName)) {
                    editUsername.setError("Please enter User Name");
                } else if (TextUtils.isEmpty(Password)) {
                    editPassword.setError("Please enter Course Description");
                } else {
                    // calling method to add data to Firebase Firestore.
                    signIn(userName,  Password);

                }
            }
        });
    }
    private void signIn(String email, String password) {


    }
}