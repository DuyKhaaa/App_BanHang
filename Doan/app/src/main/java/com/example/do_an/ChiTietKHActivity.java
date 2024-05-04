package com.example.do_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.do_an.model.KhachHang;

public class ChiTietKHActivity extends AppCompatActivity {
    TextView txtTenKH,txtMaKH, txtPassKH, txtEmailKH, txtPhoneKH;
    Button btnXoaKH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khactivity);
        addControls();
        btnXoaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietKHActivity.this);
                builder.setTitle("Xóa");
                builder.setMessage("Xóa không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int kq = KhachHangActivity.database.delete("tbCustomer", "IDCus=?", new String[]{txtMaKH.getText().toString()});
                        if(kq > 0)
                            finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void addControls() {
        txtMaKH=findViewById(R.id.txtMaKHCN);
        txtTenKH=findViewById(R.id.txtTenKHCN);
        txtPassKH=findViewById(R.id.txtPassKHCN);
        txtEmailKH=findViewById(R.id.txtEmailKHCN);
        txtPhoneKH=findViewById(R.id.txtPhoneKHCN);
        btnXoaKH=findViewById(R.id.btnXoaKH);
        Intent intent=getIntent();
        KhachHang kh= (KhachHang) intent.getSerializableExtra("kh");
        txtMaKH.setText(kh.getIDCus()+"");
        txtTenKH.setText(kh.getName());
        txtPassKH.setText(kh.getPass());
        txtEmailKH.setText(kh.getEmail());
        txtPhoneKH.setText(kh.getPhone()+"");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnLogout){
            /*
            Intent i=new Intent(this, HomeloginActivity.class);
            startActivity(i);
             */
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}