package com.example.do_an;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
Button btnADSP, btnADKH, btnADHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        btnADKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminActivity.this,KhachHangActivity.class);
                startActivity(i);
            }
        });
        btnADHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(AdminActivity.this, DanhSachDonHangActivity.class);
                startActivity(i1);
            }
        });
    }

    private void addControls() {
        btnADSP = findViewById(R.id.btnADSP);
        btnADSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, SanPhamActivity.class);
                startActivity(intent);
            }
        });
        btnADKH=findViewById(R.id.btnADKH);
        btnADHD=findViewById(R.id.btnADDH);
    }

    public void funLogoutAdmin(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        //builder.setTitle("Close");
        builder.setMessage("Xác nhận đăng xuất ?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}