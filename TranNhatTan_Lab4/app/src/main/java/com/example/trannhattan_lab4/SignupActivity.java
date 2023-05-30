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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText username, fullname, password, phone;

    // creating variable for button
    private Button signupBtn;

    // creating a strings for storing
    // our values from edittext fields.
    private String userName, fullName, Password, Phone;
    TextView SignUp;
    // creating a variable
    // for firebasefirestore
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

    @SuppressLint("MissingInflatedId")
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
        SignUp = findViewById(R.id.BacktoLogin);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                if (TextUtils.isEmpty(fullName)) {
                    username.setError("Please enter full name");
                } else if (TextUtils.isEmpty(Phone)) {
                    password.setError("Please enter phone");
                } else if (TextUtils.isEmpty(userName)) {
                    password.setError("Please enter user name");
                }
                else if (TextUtils.isEmpty(Password)) {
                    password.setError("Please enter password");
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(userName, fullName, Phone, Password);

                }
            }
        });
    }

    private void addDataToFirestore(String Name, String Phone, String UserN, String Passw) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbUsers = db.collection("users");

        // adding our data to our courses object class.
        Users user1 = new Users(Name, Phone, UserN, Passw);

        Map<String, Object> user = new HashMap<>();
        user.put("name", user1.getFullName());
        user.put("phone", user1.getPhone());
        user.put("username", user1.getUserName());
       // user.put("password", user1.getPassword());
        user.put("password", Encrypt.HashPasswordMd5(Password));

        // below method is use to add data to Firebase Firestore.
        dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(SignupActivity.this, "Dang ky thanh cong", Toast.LENGTH_LONG).show();
                username.setText("");
                fullname.setText("");
                password.setText("");
                phone.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Log.d("abc",e.getStackTrace().toString());
                Toast.makeText(SignupActivity.this, "Dang ky that bai \n" + e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}