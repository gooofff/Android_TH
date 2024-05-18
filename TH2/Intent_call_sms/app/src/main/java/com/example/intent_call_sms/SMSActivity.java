package com.example.intent_call_sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SMSActivity extends AppCompatActivity {
    EditText edtSMS;
    ImageButton btnSMS2;
    Button btnBack2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms);

        edtSMS = (EditText) findViewById(R.id.edtSMS);
        btnSMS2 = (ImageButton) findViewById(R.id.btnSMS2);
        btnBack2 = (Button) findViewById(R.id.btnBack2);

        btnSMS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent  = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + edtSMS.getText().toString()));
                if (ActivityCompat.checkSelfPermission(SMSActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SMSActivity.this, new String[] {Manifest.permission.SEND_SMS}, 1);
                    return;
                }
                startActivity(smsIntent);
            }
        });

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
