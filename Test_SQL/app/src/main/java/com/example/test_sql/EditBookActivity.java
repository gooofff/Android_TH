package com.example.test_sql;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {
    private EditText edtTitleEdit, edtAuthorEdit, edtTagsEdit;
    private Button btnUpdateBook, btnDeleteBook;
    private DatabaseHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtTitleEdit = findViewById(R.id.edtTitleEdit);
        edtAuthorEdit = findViewById(R.id.edtAuthorEdit);
        edtTagsEdit = findViewById(R.id.edtTagsEdit);
        btnUpdateBook = findViewById(R.id.btnUpdateBook);
        btnDeleteBook = findViewById(R.id.btn_DeleleBook);
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("BOOK_ID", -1);

        loadBookInfo();

        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitleEdit.getText().toString();
                String author = edtAuthorEdit.getText().toString();
                String tags = edtTagsEdit.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TITLE, title);
                values.put(DatabaseHelper.COLUMN_AUTHOR, author);
                values.put(DatabaseHelper.COLUMN_TAGS, tags);

                db.update(DatabaseHelper.TABLE_BOOKS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
                db.close();
                Intent myIntent = new Intent(EditBookActivity.this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                finish();
            }
        });

        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditBookActivity.this).setTitle("Delete Book").setMessage("Are you sure you want to delete this book?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = edtTitleEdit.getText().toString();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(DatabaseHelper.TABLE_BOOKS, DatabaseHelper.COLUMN_TITLE + " = ?", new String[]{title});
                        db.close();
                        Intent myIntent = new Intent(EditBookActivity.this, MainActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(myIntent);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
            }
        });
    }

    private void loadBookInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS, null, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)}, null, null, null);

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
            String tags = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TAGS));

            edtTitleEdit.setText(title);
            edtAuthorEdit.setText(author);
            edtTagsEdit.setText(tags);
        }

        cursor.close();
        db.close();
    }
}
