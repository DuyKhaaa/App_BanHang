package com.example.do_an.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.google.android.material.textfield.TextInputLayout;

public class DangkyActivity extends AppCompatActivity {

    TextView txtDn;
    Button btnDk;
    TextInputLayout tilPasswordDKy, tilPasswordXacNhan;
    ImageButton btnShowPasswordDKy, btnShowPasswordXacNhan;
    EditText edPasswordDky, edPasswordXacNhan;
    EditText edUserName, edEmail, edPhone;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        database = SQLiteDatabase.openDatabase(getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);

        addControls();
        //Xem mật khẩu
        ShowPass();
        //Xử lý chữ đăng nhập
        txtDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DangkyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //Xử lý nút đăng ký
        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            /*public void onClick(View v) {
                //Intent intent=new Intent(DangkyActivity.this, MainActivity.class);
                //startActivity(intent);
            }*/
            public void onClick(View v) {
                // Kiểm tra dữ liệu đầu vào
                String username = edUserName.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String password = edPasswordDky.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DangkyActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chèn dữ liệu vào cơ sở dữ liệu
                ContentValues values = new ContentValues();
                values.put("IDCus", phone);
                values.put("Name", username);
                values.put("Password", password);
                values.put("Email", email);
                values.put("Phone", phone);

                long result = database.insert("tbCustomer", null, values);
                if (result != -1) {
                    // Chèn thành công, chuyển hướng đến màn hình chính
                    Intent intent = new Intent(DangkyActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Đóng activity hiện tại để ngăn người dùng quay lại màn hình đăng ký
                } else {
                    // Xử lý lỗi khi chèn vào cơ sở dữ liệu
                    Toast.makeText(DangkyActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void addControls() {
        txtDn = findViewById(R.id.txtDn);
        btnDk = findViewById(R.id.btnDk);
        tilPasswordDKy = findViewById(R.id.textInputLayoutPasswordDKy);
        tilPasswordXacNhan = findViewById(R.id.textInputLayoutPasswordXacNhan);
        btnShowPasswordDKy = findViewById(R.id.btnShowPasswordDKy);
        btnShowPasswordXacNhan = findViewById(R.id.btnShowPasswordXacNhan);
        edUserName = findViewById(R.id.edTenDangNhap);
        edEmail = findViewById(R.id.edtEmail);
        edPhone = findViewById(R.id.edtPhone);
        edPasswordDky = findViewById(R.id.edPasswordDKy);
        edPasswordXacNhan = findViewById(R.id.edPasswordXacNhan);
    }
    private void ShowPass(){
        ////thiet lap thuoc tinh endIConMode cho TextInputLayout
        tilPasswordDKy.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        tilPasswordXacNhan.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        //Xử lý sự kiện nút show
        btnShowPasswordDKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edPasswordDky.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    edPasswordDky.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnShowPasswordDKy.setImageResource(R.drawable.ic_showpass);
                }
                else {
                    edPasswordDky.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnShowPasswordDKy.setImageResource(R.drawable.ic_showpass);
                }
                edPasswordDky.setSelection(edPasswordDky.getText().length());
            }
        });
        btnShowPasswordXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edPasswordXacNhan.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    edPasswordXacNhan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnShowPasswordXacNhan.setImageResource(R.drawable.ic_showpass);
                }
                else {
                    edPasswordXacNhan.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnShowPasswordXacNhan.setImageResource(R.drawable.ic_showpass);
                }
                edPasswordXacNhan.setSelection(edPasswordXacNhan.getText().length());
            }
        });
    }
}