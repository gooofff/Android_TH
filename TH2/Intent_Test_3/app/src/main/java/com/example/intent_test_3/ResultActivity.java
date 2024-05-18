package com.example.intent_test_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    EditText edtA2, edtB2;
    Button btnSum, btnDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.result_activity);

        edtA2 = (EditText) findViewById(R.id.edtA2);
        edtB2 = (EditText) findViewById(R.id.edtB2);
        btnSum = (Button) findViewById(R.id.btnSum);
        btnDiff = (Button) findViewById(R.id.btnDiff);
        Intent myIntent = getIntent();
        int a = myIntent.getIntExtra("a", 0);
        int b = myIntent.getIntExtra("b", 0);
        edtA2.setText(a + "");
        edtB2.setText(b + "");
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = a + b;
                myIntent.putExtra("result", sum);
                setResult(33, myIntent);
                finish();
            }
        });
        btnDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diff = a - b;
                myIntent.putExtra("result", diff);
                setResult(34, myIntent);
                finish();
            }
        });
    }
}
