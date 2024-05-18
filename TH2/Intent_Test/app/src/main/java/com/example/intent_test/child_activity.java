package com.example.intent_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class child_activity extends AppCompatActivity {
    Button btnChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_activity);

        Intent yourintent = getIntent();
        Bundle yourbundle = yourintent.getBundleExtra("mypacket");
        String string = yourbundle.getString("string");
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        btnChild = (Button) findViewById(R.id.btnChild);
        btnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(child_activity.this, MainActivity.class);
//                startActivity(intent1);
                finish();
            }
        });
    }
}