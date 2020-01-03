package com.example.listview_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnAdd, btnRead, btnClear;
    private EditText etName, etEmail;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String mail = etEmail.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        switch (v.getId()){
            case R.id.btnAdd:
                values.put(DBHelper.KEY_NAME, name);
                values.put(DBHelper.KEY_MAIL, mail);

                database.insert(DBHelper.TABLE_CONTACTS, null,values);
                break;
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int mailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d("Log", "ID = " +  cursor.getInt(idIndex) +
                                ", name - " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(mailIndex));
                    }while (cursor.moveToNext());
                }else
                    Log.d("mLog", "0 row");
                cursor.close();
                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null,null);
                break;
        }
        dbHelper.close();
    }
}
