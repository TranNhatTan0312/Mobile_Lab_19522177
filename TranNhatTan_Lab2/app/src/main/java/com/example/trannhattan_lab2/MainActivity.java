package com.example.trannhattan_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextSalary;
    Button buttonCalculate;
    ScrollView scrollViewResult;

    List<PersonalSalary> personalSalaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.edittext_username);
        editTextSalary = findViewById(R.id.edittext_salary);
        buttonCalculate = findViewById(R.id.button_calculate);
        scrollViewResult = findViewById(R.id.scrollview_result);
        personalSalaryList = new ArrayList<>();

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                int salary = Integer.parseInt(editTextSalary.getText().toString());

                PersonalSalary personalSalary = new PersonalSalary(username, salary);
                personalSalary.calculateSalary();
                personalSalaryList.add(personalSalary);
                showResult();
            }
        });
    }
    private void showResult() {
        LinearLayout linearLayoutResult = findViewById(R.id.linearlayout_result);
        linearLayoutResult.removeAllViews();

        for (PersonalSalary personalSalary : personalSalaryList) {
            TextView textView = new TextView(this);
            DecimalFormat decimalFormat = new DecimalFormat("#");
            String netSalaryStr = decimalFormat.format(personalSalary.getNetSalary());
            textView.setText("Full name: " + personalSalary.getUsername() + ", Net salary: " + netSalaryStr);
            linearLayoutResult.addView(textView);
        }
    }
}
