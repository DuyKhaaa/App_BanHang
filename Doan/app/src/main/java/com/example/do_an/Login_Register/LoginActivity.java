package com.example.do_an.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.AdminActivity;
import com.example.do_an.MainActivity;
import com.example.do_an.QuenMKActivity;
import com.example.do_an.R;
import com.example.do_an.Singleton.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilPassword;
    TextView txtDk , txtFomk;
    Button btnDn;
    ImageButton btnShowPassword;
    EditText edPassword,edUserName;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        database = SQLiteDatabase.openDatabase(getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        //ẩn/hiện mật khẩu trong EditText
        //thiet lap thuoc tinh endIConMode cho TextInputLayout
        tilPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        //Xử lý sự kiện nút show
        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edPassword.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnShowPassword.setImageResource(R.drawable.ic_showpass);
                }
                else {
                    edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnShowPassword.setImageResource(R.drawable.ic_showpass);
                }
                edPassword.setSelection(edPassword.getText().length());
            }
        });
        txtDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, DangkyActivity.class);
                startActivity(intent);
            }
        });
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icERR=getResources().getDrawable(R.drawable.ic_error);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth()+15,icERR.getIntrinsicHeight());
                String name=edUserName.getText().toString().trim();
                String pass=edPassword.getText().toString().trim();
                if(name.isEmpty()){
                    edUserName.setCompoundDrawables(null,null,icERR,null);
                    edUserName.setError("Vui lòng nhập tên đăng nhập");
                    return;
                }
                if(pass.isEmpty()){
                    edPassword.setCompoundDrawables(null,null,icERR,null);
                    edPassword.setError("Vui lòng nhập mật khẩu");
                    return;
                }
                /*if(!name.isEmpty()&&!pass.isEmpty()){
                    edUserName.setCompoundDrawables(null,null,null,null);
                    edPassword.setCompoundDrawables(null,null,null,null);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }*/
                // Kiểm tra trong cơ sở dữ liệu
                //boolean isAdmin = checkAdmin(name, pass);
                //boolean isCustomer = checkCustomer(name, pass);
                /*
                if (!name.isEmpty() && !pass.isEmpty()) {
                    edUserName.setCompoundDrawables(null, null, null, null);
                    edPassword.setCompoundDrawables(null, null, null, null);
                    boolean isAdmin = checkAdmin(name, pass);
                    boolean isCustomer = checkCustomer(name, pass);
                    if (isAdmin) {
                        // Nếu là Admin, chuyển hướng đến màn hình đăng ký
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }else if (isCustomer) {
                        // Nếu là Customer, chuyển hướng đến màn hình Customer
                        // Khởi tạo SessionManager trong LoginActivity
                        int customerId = getCustomerId(name); // Lấy mã khách hàng
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        // Lưu thông tin người dùng vào session khi đăng nhập thành công
                        sessionManager.setLoggedIn(name);
                        sessionManager.setCustomerId(customerId);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //intent.putExtra("name",edUserName.getText().toString());
                        startActivity(intent);
                    }else {
                        // Nếu không tìm thấy hoặc không phải Admin/Customer
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }*/
                // Kiểm tra trong cơ sở dữ liệu trong một luồng phụ
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isAdmin = checkAdmin(name, pass);
                        boolean isCustomer = checkCustomer(name, pass);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!name.isEmpty() && !pass.isEmpty()) {
                                    edUserName.setCompoundDrawables(null, null, null, null);
                                    edPassword.setCompoundDrawables(null, null, null, null);
                                    if (isAdmin) {
                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                    } else if (isCustomer) {
                                        int customerId = getCustomerId(name);
                                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                                        sessionManager.setLoggedIn(name);
                                        sessionManager.setCustomerId(customerId);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        txtFomk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, QuenMKActivity.class);
                startActivity(intent);
            }
        });

    }
    private void addControl(){
        tilPassword = findViewById(R.id.textInputLayoutPasswordDKy);
        btnShowPassword = findViewById(R.id.btnShowPasswordDKy);
        btnDn=findViewById(R.id.btnDn);
        txtDk=findViewById(R.id.txtDK);
        edPassword = findViewById(R.id.edPasswordDKy);
        edUserName = findViewById(R.id.edTenDangNhap);
        txtFomk = findViewById(R.id.txtForget);
    }
    private boolean checkAdmin(String username, String password) {
        // Thực hiện truy vấn trong cơ sở dữ liệu để kiểm tra xem username và password có phải là của Admin không
        // Dùng rawQuery hoặc sử dụng các phương thức khác của SQLiteDatabase để thực hiện truy vấn
        // Trả về true nếu tìm thấy một bản ghi tương ứng, ngược lại trả về false
        // Ví dụ:
        if(username != null && password != null){
            String query = "SELECT * FROM tbAdmin WHERE username = ? AND password = ?";
            Cursor cursor = database.rawQuery(query, new String[]{username, password});
            return cursor.getCount() > 0;
        }
        else {
            return false;
        }
    }

    private boolean checkCustomer(String username, String password) {
        // Tương tự, thực hiện kiểm tra xem username và password có phải là của một Customer không
        // Trả về true nếu tìm thấy một bản ghi tương ứng, ngược lại trả về false
        // Ví dụ:
        if(username != null && password != null){
            String query = "SELECT * FROM tbCustomer WHERE name = ? AND password = ?";
            Cursor cursor = database.rawQuery(query, new String[]{username, password});
            return cursor.getCount() > 0;
        }
        else {
            return false;
        }
    }
    @SuppressLint("Range")
    private int getCustomerId(String username) {
        int customerId = -1;
        if (username != null) {
            // Thực hiện truy vấn cơ sở dữ liệu để lấy mã khách hàng
            database = SQLiteDatabase.openDatabase(getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            String query = "SELECT IDCus FROM tbCustomer WHERE name = ?";
            Cursor cursor = database.rawQuery(query, new String[]{username});
            if (cursor.moveToFirst()) {
                customerId = cursor.getInt(cursor.getColumnIndex("IDCus"));
            }
            cursor.close();
        }
        return customerId;
    }

}