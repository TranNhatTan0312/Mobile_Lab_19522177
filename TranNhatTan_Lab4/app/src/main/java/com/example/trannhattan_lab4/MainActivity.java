package com.example.trannhattan_lab4;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.os.Bundle;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

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

    private String encryptPassword(String password) {
        try {
            // Tạo một đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Chuyển đổi chuỗi mật khẩu thành mảng byte
            byte[] passwordBytes = password.getBytes();

            // Mã hóa mảng byte của mật khẩu
            byte[] encryptedBytes = md.digest(passwordBytes);

            // Chuyển đổi mảng byte thành chuỗi hex
            StringBuilder sb = new StringBuilder();
            for (byte b : encryptedBytes) {
                sb.append(String.format("%02x", b));
            }

            // Trả về chuỗi mã hóa
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Nếu xảy ra lỗi, trả về null hoặc giá trị mặc định
        return null;
    }

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

        Map<String, Object> user = new HashMap<>();
        user.put("name", user1.getFullName());
        user.put("phone", user1.getPhone());
        user.put("username", user1.getUserName());
        user.put("password", user1.getPassword());
        String encryptedPassword = encryptPassword(String.valueOf(password));

        // below method is use to add data to Firebase Firestore.
        dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(MainActivity.this, "Dang ky thanh cong", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Log.d("abc",e.getStackTrace().toString());
                Toast.makeText(MainActivity.this, "Fail to add course \n" + e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}