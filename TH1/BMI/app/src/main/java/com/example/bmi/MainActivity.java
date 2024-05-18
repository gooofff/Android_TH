package com.example.bmi;

import static java.lang.Math.pow;

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
    EditText edtName, edtHeight, edtWeight, edtBMI, edtResult;
    Button btnCal;
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
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtBMI = (EditText) findViewById(R.id.edtBMI);
        edtResult = (EditText) findViewById(R.id.edtResult);
        btnCal = (Button) findViewById(R.id.btnCal);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat format_number = new DecimalFormat("#.00");
                String S_H = edtHeight.getText() + "";
                String S_W = edtWeight.getText() + "";
                if (S_H.equals("") || S_W.equals("")) {
                    Toast.makeText(getApplicationContext(), "Mời nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Double H = Double.parseDouble(S_H);
                    Double W = Double.parseDouble(S_W);
                    if (H == 0 || W == 0) {
                        Toast.makeText(getApplicationContext(), "Thông tin không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Double BMI = W / (pow(H,2));
                        edtBMI.setText("" + format_number.format(BMI));
                        if (BMI < 18) {
                            edtResult.setText("Bạn gầy");
                        }
                        else if (BMI <= 24.9) {
                            edtResult.setText("Bạn bình thường");
                        }
                        else if (BMI <= 29.9) {
                            edtResult.setText("Bạn béo phì độ I");
                        }
                        else if (BMI <= 34.9) {
                            edtResult.setText("Bạn béo phì độ II");
                        }
                        else {
                            edtResult.setText("Bạn béo phì độ III");
                        }
                    }
                }
            }
        });

    }
}