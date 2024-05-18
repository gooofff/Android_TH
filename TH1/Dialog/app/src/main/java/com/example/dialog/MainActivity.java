package com.example.dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    EditText edtName, edtCMND, edtInfor;
    CheckBox chkBao, chkSach, chkCoding;
    RadioGroup group;
    Button btnSubmit;
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
        edtCMND = (EditText) findViewById(R.id.edtCMND);
        edtInfor = (EditText) findViewById(R.id.edtInfor);
        chkBao = (CheckBox) findViewById(R.id.chkBao);
        chkSach = (CheckBox) findViewById(R.id.chkSach);
        chkCoding = (CheckBox) findViewById(R.id.chkCoding);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformation();
            }
        });
    }
    public void showInformation() {

        // Kiểm tra tên
        String name = edtName.getText().toString();
        name = name.trim();
        if (name.length() < 3) {
            edtName.requestFocus();
            edtName.selectAll();
            Toast.makeText(getApplicationContext(), "Tên phải >= 3 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra CMND
        String cmnd = edtCMND.getText().toString();
        cmnd = cmnd.trim();
        if (cmnd.length() != 9) {
            edtCMND.requestFocus();
            edtCMND.selectAll();
            Toast.makeText(getApplicationContext(), "CMND phải đúng 9 số", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra bằng cấp
        String bang = "";
        group = findViewById(R.id.idGroup);
        int id = group.getCheckedRadioButtonId();
//        if (id == -1) {
//            Toast.makeText(getApplicationContext(), "Phải chọn bằng cấp", Toast.LENGTH_SHORT).show();
//            return;
//        }
        RadioButton rad = findViewById(id);
        bang = rad.getText() + "";


        // Kiểm tra sở thích
        String sothich = "";
        if (!chkBao.isChecked() && !chkSach.isChecked() && !chkCoding.isChecked()) {
            Toast.makeText(getApplicationContext(), "Phải chọn ít nhất 1 sở thích", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            if (chkBao.isChecked()) {
                sothich += chkBao.getText().toString();
            }
            if (chkSach.isChecked()) {
                sothich += chkSach.getText().toString();
            }
            if (chkCoding.isChecked()) {
                sothich = chkCoding.getText().toString();
            }
        }

        // Bổ sung
        String bosung = edtInfor.getText().toString();

        // Tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Tạo nội dung cho Dialog
        String msg = name + "\n";
        msg += cmnd + "\n";
        msg += bang + "\n";
        msg += sothich + "\n";
        msg += "-------------\n";
        msg += "Thông tin bổ sung:\n";
        msg += bosung + "\n";
        msg += "-------------\n";
        builder.setMessage(msg);
        builder.create().show();
    }

//    @Override
//    public void onBackInvoked() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Question");
//        builder.setMessage("Are you sure you want to exit?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.create().show();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}