package com.example.sql_quanlysinhvien;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtId, edtName, edtNum;
    Button btnAdd, btnEdit, btnDel;
    ListView listView;
    ArrayList<String> myList;
    ArrayAdapter<String> adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtId = (EditText) findViewById(R.id.edtId);
        edtName = (EditText) findViewById(R.id.edtName);
        edtNum = (EditText) findViewById(R.id.edtNum);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDel = (Button) findViewById(R.id.btnDel);

        listView = (ListView) findViewById(R.id.listView);
        myList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(adapter);

        db = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblop (malop TEXT primary key, tenlop TEXT, siso INTEGER)";
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Bang da ton tai");
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtId.getText().toString();
                String tenlop = edtName.getText().toString();
                int siso = Integer.parseInt(edtNum.getText().toString());
                ContentValues value = new ContentValues();
                value.put("malop", malop);
                value.put("tenlop", tenlop);
                value.put("siso", siso);
                String msg = "";
                if (db.insert("tblop", null, value) == -1) {
                    msg = "Them khong thanh cong";
                }
                else {
                    msg = "Them thanh cong";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}