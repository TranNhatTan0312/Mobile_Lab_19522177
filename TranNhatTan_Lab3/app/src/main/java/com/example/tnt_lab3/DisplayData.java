package com.example.tnt_lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayData extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    String[]sfname, sphone;
    int[]id;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        dBmain = new DBmain(this);
        findid();
        displaydata();
    }

    private void displaydata() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from info", null);
        if(cursor.getCount() > 0) {
            id = new int[cursor.getCount()];
            sfname = new String[cursor.getCount()];
            sphone= new String[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext()) {
                id[i] = cursor.getInt(0);
                sfname[i] = cursor.getString(1);
                sphone[i] = cursor.getString(2);
                i++;
            }
            CustAdapter custAdapter = new CustAdapter();
            listView.setAdapter(custAdapter);
        }
    }

    private void findid() {
        listView = findViewById(R.id.lv);
    }

    private class CustAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sfname.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txtfname, txtphone;
            CardView cardView;
            convertView = LayoutInflater.from(DisplayData.this).inflate(R.layout.singledata, parent, false);
            txtfname = convertView.findViewById(R.id.txt_fname);
            txtphone = convertView.findViewById(R.id.txt_fphone);
            cardView = convertView.findViewById(R.id.cardview);

            txtfname.setText(sfname[position]);
            txtphone.setText(sphone[position]);

            return null;
        }
    }
}