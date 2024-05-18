package com.example.intent_filer_web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent myIntent = getIntent();
        Uri data = myIntent.getData();

        try {
            WebView webView1 = findViewById(R.id.webView1);
            webView1.loadUrl(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}