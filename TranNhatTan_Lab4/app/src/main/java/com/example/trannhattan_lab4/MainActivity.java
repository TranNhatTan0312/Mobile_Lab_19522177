package com.example.trannhattan_lab4;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText username, fullname, password, phone;

    // creating variable for button
    private Button signupBtn;

    // creating a strings for storing
    // our values from edittext fields.
    private String userName, fullName, Password, Phone;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        fullname = findViewById(R.id.EditFullname);
        phone = findViewById(R.id.EditPhone);
        username = findViewById(R.id.EditUsername);
        password = findViewById(R.id.EditPassword);
        signupBtn = findViewById(R.id.btn_SignIn);

        // adding on click listener for button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                userName = username.getText().toString();
                fullName = fullname.getText().toString();
                Password = password.getText().toString();
                Phone = phone.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(userName)) {
                    username.setError("Please enter User Name");
                } else if (TextUtils.isEmpty(Password)) {
                    password.setError("Please enter Course Description");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(userName, fullName, Phone, Password);
                }
            }
        });
    }

    private void addDataToFirestore(String courseName, String courseDescription, String courseDuration, String Phone) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbUsers = db.collection("users");

        // adding our data to our courses object class.
        Users user1 = new Users(courseName, courseDescription, Phone, courseDuration);

        // below method is use to add data to Firebase Firestore.
        dbUsers.add(user1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(MainActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(MainActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}