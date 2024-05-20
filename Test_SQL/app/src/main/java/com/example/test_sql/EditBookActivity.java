//package com.example.test_sql;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class EditBookActivity extends AppCompatActivity {
//    private EditText editTextTitle, editTextAuthor, editTextTags;
//    private Button buttonUpdateBook;
//    private DatabaseHelper dbHelper;
//    private int bookId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_book);
//
//        editTextTitle = findViewById(R.id.editTextTitle);
//        editTextAuthor = findViewById(R.id.editTextAuthor);
//        editTextTags = findViewById(R.id.editTextTags);
//        buttonUpdateBook = findViewById(R.id.buttonUpdateBook);
//        dbHelper = new DatabaseHelper(this);
//
//        Intent intent = getIntent();
//        bookId = intent.getIntExtra("BOOK_ID", -1);
//
//        loadBookInfo();
//
//        buttonUpdateBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = editTextTitle.getText().toString();
//                String author = editTextAuthor.getText().toString();
//                String tags = editTextTags.getText().toString();
//
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(DatabaseHelper.COLUMN_TITLE, title);
//                values.put(DatabaseHelper.COLUMN_AUTHOR, author);
//                values.put(DatabaseHelper.COLUMN_TAGS, tags);
//
//                db.update(DatabaseHelper.TABLE_BOOKS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
//                db.close();
//                finish();
//            }
//        });
//    }
//}
