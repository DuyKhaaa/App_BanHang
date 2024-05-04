package com.example.do_an;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an.model.SanPham;

public class CapnhatSPActivity extends AppCompatActivity {
EditText edtMaCN,edtTenCN, edtMotaCN, edtLoaiCN, edtStaCN;
ImageView imSPCN;
Button btnCN,btnXoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhatsp);
        addControls();
       xuLyCapNhat();
       xuLyDelete();
    }

    private void xuLyDelete() {
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AlertDialog.Builder builder = new AlertDialog.Builder(CapnhatSPActivity.this);
              builder.setTitle("Xóa");
              builder.setMessage("Xóa không?");
              builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                 int kq = SanPhamActivity.database.delete("tbProducts", "ProductID=?", new String[]{edtMaCN.getText().toString()});
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

    private void xuLyCapNhat() {
       btnCN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ContentValues values=new ContentValues();
               values.put("NamePro",edtTenCN.getText().toString());
               values.put("DecriptionPro",edtMotaCN.getText().toString());
               values.put("Category",edtLoaiCN.getText().toString());
               values.put("Status",edtStaCN.getText().toString());
               int kq=SanPhamActivity.database.update("tbProducts",values,"ProductID=?",new String[]{edtMaCN.getText().toString()});
               if(kq>0)
                   finish();
               else
                   Toast.makeText(CapnhatSPActivity.this,"fail",Toast.LENGTH_SHORT);

           }
       });
    }


    private void addControls() {
        edtMaCN=findViewById(R.id.edtMaSPCN);
        edtTenCN=findViewById(R.id.edtTenSPCN);
        edtMotaCN=findViewById(R.id.edtMoTaSPCN);
        edtLoaiCN=findViewById(R.id.edtLoaiSPCN);
        edtStaCN=findViewById(R.id.edtTrangThaiSPCN);
        imSPCN=findViewById(R.id.imSP);
        btnCN=findViewById(R.id.btnCN);
        btnXoa=findViewById(R.id.btnXoa);
        Intent intent=getIntent();
        SanPham sp= (SanPham) intent.getSerializableExtra("sp");
        if (sp.getHinh() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(sp.getHinh(), 0, sp.getHinh().length);
            imSPCN.setImageBitmap(bitmap);
        }
        edtMaCN.setText(sp.getIDPro().toString());
        edtTenCN.setText(sp.getNamePro().toString());
        edtMotaCN.setText(sp.getMota().toString());
        edtLoaiCN.setText(sp.getLoai().toString());
       edtStaCN.setText(sp.getStatus().toString());
       edtMaCN.setEnabled(false);
    }

    // viet su kien cho Menu
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