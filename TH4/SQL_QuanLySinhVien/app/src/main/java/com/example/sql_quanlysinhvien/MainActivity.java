package com.example.sql_quanlysinhvien;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText edtId, edtName, edtNum;
    Button btnAdd, btnEdit, btnDel, btnClear;
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
        btnClear = (Button) findViewById(R.id.btnClear);

        listView = (ListView) findViewById(R.id.listView);
        myList = new ArrayList<>();


        db = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblop (malop TEXT primary key, tenlop TEXT, siso INTEGER)";
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Bang da ton tai");
        }

        show();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtId.getText().toString();
                String tenlop = edtName.getText().toString();
                String siso1 = edtNum.getText().toString();
                if (malop.equals("") || tenlop.equals("") || siso1.equals("")) {
                    Toast.makeText(MainActivity.this, "Moi nhap thong tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    int siso = Integer.parseInt(siso1);
                    ContentValues value = new ContentValues();
                    value.put("malop", malop);
                    value.put("tenlop", tenlop);
                    value.put("siso", siso);
                    String msg = "";
                    if (db.insert("tblop", null, value) == -1) {
                        msg = "Them khong thanh cong";
                    } else {
                        msg = "Them thanh cong";
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    recreate();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtId.getText().toString();
                int n = db.delete("tblop", "malop = ?", new String[] {malop});
                String msg = "";
                if (n == 0) {
                    msg = "Xoa khong thanh cong";
                }
                else {
                    msg = n + " ban ghi duoc xoa";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                recreate();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtId.getText().toString();
                String tenlop = edtName.getText().toString();
                String siso1 = edtNum.getText().toString();
                if (malop.equals("") || tenlop.equals("") || siso1.equals("")) {
                    Toast.makeText(MainActivity.this, "Moi nhap thong tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor c = db.query("tblop", null, "malop = ?", new String[] {malop}, null, null, null);
                    if (c.moveToFirst()) {
                        String tenlop2 = c.getString(1);
                        String siso2 = c.getString(2);
                        if (tenlop.equals(tenlop2) && siso1.equals(siso2)) {
                            Toast.makeText(MainActivity.this, "Thong tin trung lap", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            int siso = Integer.parseInt(siso1);
                            ContentValues value = new ContentValues();
                            value.put("tenlop", tenlop);
                            value.put("siso", siso);
                            int n = db.update("tblop", value, "malop = ?", new String[]{malop});
                            String msg = "";
                            if (n == 0) {
                                msg = "Sua khong thanh cong";
                            } else {
                                msg = n + " ban ghi duoc sua thanh cong";
                            }
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            recreate();
                        }
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                String[] parts = selectedItem.split(" - ");
                if (parts.length == 3) {
                    String itemId = parts[0];
                    String itemName = parts[1];
                    String itemNumber = parts[2];
                    edtId.setText(itemId);
                    edtName.setText(itemName);
                    edtNum.setText((itemNumber));
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtId.setText("");
                edtName.setText("");
                edtNum.setText("");
                adapter.notifyDataSetChanged();
                recreate();
            }
        });

        edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void filterList(String text) {
        if (text.equals("")) {
            adapter.clear();
            show();
            adapter.notifyDataSetChanged();
        }
        else {
            List<String> filteredList = new ArrayList<>();
            for (String item : myList) {
                if (item.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            adapter.clear();
            adapter.addAll(filteredList);
            adapter.notifyDataSetChanged();
        }
    }
    private void show() {
        Cursor c = db.query("tblop", null, null, null, null, null, null);
        c.moveToNext();
        String data = "";
        while (!c.isAfterLast()) {
            data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2);
            c.moveToNext();
            myList.add(data);
        }
        c.close();
    }
}