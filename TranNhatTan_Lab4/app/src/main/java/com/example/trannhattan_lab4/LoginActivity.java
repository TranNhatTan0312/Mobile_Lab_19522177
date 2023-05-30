package com.example.trannhattan_lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = FirebaseFirestore.getInstance();
        sharedPreferences  = getSharedPreferences("users", Context.MODE_PRIVATE);
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
                String userName = editUsername.getText().toString();
                String Password = editPassword.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(userName)) {
                    editUsername.setError("Please enter User Name");
                }
                if(Validate.ValidateUsername(userName) == false){
                    Toast.makeText(getApplicationContext(),"Username phải là chữ và phải trên 6 ký tự",Toast.LENGTH_LONG).show();
                    return;
                }
                if(Validate.ValidatePassword(Password) == false){
                    Toast.makeText(getApplicationContext(),"Password phải trên 6 ký tự",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    editPassword.setError("Please enter password");
                }
                db.collection("users")
                        .whereEqualTo("username",userName)
                        .whereEqualTo("password", Encrypt.HashPasswordMd5(Password))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(!task.getResult().isEmpty()){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", userName);
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                }
                               else{
                                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại",Toast.LENGTH_LONG).show();

                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Đăng nhập thất bại",Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
    }
}