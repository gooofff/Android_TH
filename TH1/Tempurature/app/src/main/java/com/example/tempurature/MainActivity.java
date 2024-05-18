package com.example.tempurature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText edtCel, edtFah;
    Button btnCel, btnFah,  btnClear;

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

        edtCel = (EditText) findViewById(R.id.edtCel);
        edtFah = (EditText) findViewById(R.id.edtFah);
        btnCel = (Button) findViewById(R.id.btnCel);
        btnFah = (Button) findViewById(R.id.btnFah);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnFah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat Temp = new DecimalFormat("#.00");
                String TempFah = edtFah.getText() + "";
                if (TempFah.equals("")) {
                    Toast.makeText(getApplicationContext(), "Wrong Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    Double F = Double.parseDouble(TempFah);
                    edtCel.setText("" + Temp.format((F - 32) / 1.8));
                }
            }
        });

        btnCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat Temp = new DecimalFormat("#.00");
                String TempCel = edtCel.getText() + "";
                if (TempCel.equals("")) {
                    Toast.makeText(getApplicationContext(), "Wrong Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    Double C = Double.parseDouble(TempCel);
                    edtFah.setText("" + Temp.format(C * 1.8 + 32));
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtCel.setText("");
                edtFah.setText("");
            }
        });
    }
}