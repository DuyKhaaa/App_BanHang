package com.example.do_an;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.UriCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ThemspActivity extends AppCompatActivity {
    EditText edtMaSP, edtTenSP, edtMoTaSP, edtTrangThai, edtGiaSP;
    ImageView imHinhSP;
    ImageButton imbHinhSP;
    Button btnLuu;
    int  PICK_IMAGE_REQUEST_CODE=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsp);
        addcontrols();
        imbHinhSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở thư viện ảnh
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("ProductID", edtMaSP.getText().toString());
                values.put("NamePro", edtTenSP.getText().toString());
                values.put("DecriptionPro", edtMoTaSP.getText().toString());
                values.put("Category", edtGiaSP.getText().toString());
                values.put("Status", edtTrangThai.getText().toString());

                // Kiểm tra xem ảnh đã được chọn hay chưa
                if (imHinhSP.getDrawable() != null) {
                    // Chuyển đổi ảnh sang mảng byte
                    Bitmap bitmap = ((BitmapDrawable) imHinhSP.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    values.put("Image", imageBytes);
                }

                long result = SanPhamActivity.database.insert("tbProducts", null, values);
                if (result > 0) {
                    finish();
                } else {
                    Toast.makeText(ThemspActivity.this, "Lỗi khi lưu sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData(); // Lấy URI của hình ảnh đã chọn
                try {
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    imHinhSP.setImageBitmap(bitmap); // Đặt hình ảnh vào ImageView
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Lỗi khi tải hình ảnh!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void addcontrols() {
        edtMaSP=findViewById(R.id.edtMaSP);
        edtTenSP=findViewById(R.id.edtTenSP);
        edtMoTaSP=findViewById(R.id.edtMoTaSP);
        edtTrangThai=findViewById(R.id.edtTrangThaiSP);
        edtGiaSP=findViewById(R.id.edtGiaSP);
        imHinhSP=findViewById(R.id.imHinhSP);
        imbHinhSP=findViewById(R.id.imbHinhSP);
        btnLuu=findViewById(R.id.btnLuu);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}