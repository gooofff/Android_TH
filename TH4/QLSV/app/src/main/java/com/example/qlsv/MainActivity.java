package com.example.qlsv;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtName, edtEmail;
    private Button btnAdd, btnUpdate, btnDel, btnClear;
    private ListView listView;
    private List<Student> studentList;
    private List<String> list;
    private FirebaseDatabaseHelper databaseHelper;
    ArrayAdapter<String> adapter;
    private String StudentID = "";


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

        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnClear = (Button) findViewById(R.id.btnClear);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        studentList = new ArrayList<>();
        databaseHelper = new FirebaseDatabaseHelper();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
                edtEmail.setText("");
                loadStudents();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                String[] parts = selectedItem.split(" - ");
                if (parts.length == 3) {
                    StudentID = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    edtName.setText(name);
                    edtEmail.setText((email));
                    adapter.clear();
                }
                loadStudents();
            }
        });

        loadStudents();
    }

    private void addStudent() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        if (name.equals("") || email.equals("")) {
            Toast.makeText(this, "Please enter a name and email", Toast.LENGTH_SHORT).show();
        }
        else {
            String id = databaseHelper.getReference().push().getKey();
            Student student = new Student(id, name, email);
            StudentID = "";
            databaseHelper.addStudent(student);
        }
    }
    private void updateStudent() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        Student student = new Student(StudentID, name, email);
        databaseHelper.updateStudent(StudentID, student);
        loadStudents();
    }
    private void deleteStudent() {
        if (StudentID.isEmpty()) {
            Toast.makeText(this, "Please select a student to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseHelper.deleteStudent(StudentID);
            StudentID = "";
            edtName.setText("");
            edtEmail.setText("");
            loadStudents();
        }
    }
    private void loadStudents() {
        databaseHelper.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                }

                for (Student student : studentList) {
                    String temp = student.getId() + " - " + student.getName() + " - " + student.getEmail();
                    list.add(temp);
                }

                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}