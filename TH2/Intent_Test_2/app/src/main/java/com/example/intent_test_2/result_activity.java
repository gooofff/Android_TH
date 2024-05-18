package com.example.intent_test_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class result_activity extends AppCompatActivity {
    TextView txtResult;
    Button btnBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.result_activity);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnBack = (Button) findViewById(R.id.btnBack);
        Intent yourIntent = getIntent();
        Bundle yourBundle = yourIntent.getBundleExtra("packet");
        int a = yourBundle.getInt("a");
        int b = yourBundle.getInt("b");
        String result = "";
        if (a == 0 && b == 0) {
            result = "Vô số nghiệm";
        }
        else if (a == 0 && b != 0) {
            result = "Vô nghiệm";
        }
        else {
            DecimalFormat formatNumber = new DecimalFormat("0.##");
            result = formatNumber.format(-b * 1.0 / a);
        }
        txtResult.setText(result);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
