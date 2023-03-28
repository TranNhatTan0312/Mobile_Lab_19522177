package com.example.trannhattan_lab1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //khai báo
    String colors[] = {"", "", ""};
    ArrayAdapter<String> myAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, colors)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position, convertView, parent);
                if (position == 0){
                    view.setBackgroundColor(0xFFF44336);
                }
                if(position == 1){
                    view.setBackgroundColor(0xFFFFEB3B);
                }
                if(position == 2){
                    view.setBackgroundColor(0xFF00BCD4);
                }
                return view;
            }
        };

        listView.setAdapter(myAdapter);

    }
}