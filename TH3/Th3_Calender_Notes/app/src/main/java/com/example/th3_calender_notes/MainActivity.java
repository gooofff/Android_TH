package com.example.th3_calender_notes;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView txtTime;
    EditText edtWork, edtHour, edtMinute;
    Button btnAdd;
    ListView lv;
    ArrayList<String> arrWork;
    ArrayAdapter<String> arrAdapter;
    SharedPreferences sharedPreferences;
    Gson gson;

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

        txtTime = (TextView) findViewById(R.id.txtTime);
        edtWork = (EditText) findViewById(R.id.edtWork);
        edtHour = (EditText) findViewById(R.id.edtHour);
        edtMinute = (EditText) findViewById(R.id.edtMinute);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lv = (ListView) findViewById(R.id.lv);
        sharedPreferences  = getSharedPreferences("SavedValues", MODE_PRIVATE);
        gson = new Gson();

        loadData();
        arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrWork);
        lv.setAdapter(arrAdapter);
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
        txtTime.setText("Hôm Nay: " + simpleDateFormat.format(currentDate));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtWork.getText().toString().equals("") || edtHour.getText().toString().equals("") || edtMinute.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Infor missing");
                    builder.setMessage("Please enter all ìnformation");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                else {
                    String str = "+ " + edtWork.getText().toString() + " - " + edtHour.getText().toString() + ":" + edtMinute.getText().toString();
                    arrWork.add(str);
                    arrAdapter.notifyDataSetChanged();
                    edtWork.setText("");
                    edtHour.setText("");
                    edtMinute.setText("");
                }
            }
        });
    }
    protected void OnPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(arrWork);
        editor.putString("arrWork", json);
        editor.apply();
    }

    private void loadData() {
        String json = sharedPreferences.getString("arrWork", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        arrWork = json.fromJson(json, type);

        if (arrWork == null) {
            arrWork = new ArrayList<>();
        }
    }
}