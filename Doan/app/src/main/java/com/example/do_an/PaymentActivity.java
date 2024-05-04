package com.example.do_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.Singleton.GioHangManager;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.model.Giohang;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    EditText edtDiaChi,edtTenPayment, edtSDTPayment;
    TextView txtTotalMoney,txtTotalSLPayment;
    Button btnOrderPayment;
    SQLiteDatabase database;
    List<Giohang> giohangList;

    // Trong PaymentActivity.java
// Thay vì lấy mã khách hàng từ session, bạn sẽ lấy nó từ SessionManager
    int customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        addControls();
        // Trong phương thức onCreate của PaymentActivity
        SessionManager sessionManager = new SessionManager(this);
        giohangList = sessionManager.getGiohangList();
        // Hiển thị thông tin giỏ hàng trên giao diện
        updateGiohangListOnUI(giohangList);
        database = SQLiteDatabase.openDatabase(getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        addEventsDatHang();
        // Di chuyển việc khởi tạo SessionManager vào đây
        customerId = sessionManager.getCustomerId();
    }

    private void addEventsDatHang() {
        btnOrderPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ giao diện
                String ten = edtTenPayment.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String sdt = edtSDTPayment.getText().toString();
                String date = getCurrentDate(); // Lấy ngày hiện tại
                List<Giohang> giohangList = new SessionManager(PaymentActivity.this).getGiohangList();

                // Kiểm tra nếu giỏ hàng không rỗng và thông tin người dùng không trống
                if (!giohangList.isEmpty() && !ten.isEmpty() && !diaChi.isEmpty() && !sdt.isEmpty()) {
                    // Lưu thông tin đơn hàng vào cơ sở dữ liệu
                    int maDonHang = saveDonHang(date, ten, diaChi);

                    // Lưu thông tin chi tiết đơn hàng vào cơ sở dữ liệu
                    saveChiTietDonHang(maDonHang, giohangList);

                    // Hiển thị thông báo đặt hàng thành công
                    Toast.makeText(PaymentActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

                    // Xóa giỏ hàng sau khi đã đặt hàng thành công
                    // Gọi phương thức xóa mục giỏ hàng
                    GioHangManager.getInstance().clearGiohangList();
                    Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng PaymentActivity sau khi đặt hàng thành công
                } else {
                    Toast.makeText(PaymentActivity.this, "Vui lòng điền đầy đủ thông tin và chọn sản phẩm để đặt hàng.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Phương thức lưu thông tin đơn hàng vào cơ sở dữ liệu
    private int saveDonHang(String date, String ten, String diaChi) {
        ContentValues values = new ContentValues();
        values.put("Date", date);
        values.put("IDCus", customerId); // Thay thế 1 bằng mã khách hàng thực tế (nếu có)
        values.put("Address", diaChi);

        long insertedId = database.insert("tbOrder", null, values);
        return (int) insertedId;
    }

    // Phương thức lưu thông tin chi tiết đơn hàng vào cơ sở dữ liệu
    private void saveChiTietDonHang(int maDonHang, List<Giohang> giohangList) {
        for (Giohang giohang : giohangList) {
            if(giohang.isSelected()) {
                ContentValues values = new ContentValues();
                values.put("IDOrder", maDonHang);
                values.put("IDProduct", giohang.getIdProduct()); // Thay thế giohang.getIdProduct() bằng phương thức lấy ID sản phẩm từ đối tượng Giohang
                values.put("IDOptions", giohang.getIdOptions()); // Thay thế giohang.getIdOptions() bằng phương thức lấy ID tùy chọn từ đối tượng Giohang
                values.put("Quantity", giohang.getSoLuong());
                values.put("TotalPrice", giohang.getSoLuong() * giohang.getPrice());

                database.insert("tbOrderDetail", null, values);
            }
        }
    }

    // Phương thức lấy ngày hiện tại
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    private void updateGiohangListOnUI(List<Giohang> giohangList) {
        int totalMoney = calculateTotalMoney(giohangList);
        int totalQuantity = calculateTotalQuantity(giohangList);

        // Hiển thị tổng tiền và tổng số hàng trên giao diện
        txtTotalMoney.setText(String.valueOf(totalMoney));
        txtTotalSLPayment.setText(String.valueOf(totalQuantity));

        // Hiển thị thông tin người dùng (nếu cần)
        SessionManager sessionManager = new SessionManager(this);
        String username = sessionManager.getUsername();
        String phoneNumber = sessionManager.getPhoneNumber(); // Để lấy được số điện thoại, bạn cần lưu nó trong SessionManager khi người dùng đăng nhập
        edtTenPayment.setText(username); // Hiển thị tên người dùng
        edtSDTPayment.setText(phoneNumber); // Hiển thị số điện thoại
    }

    // Phương thức tính tổng tiền từ danh sách giỏ hàng
    private int calculateTotalMoney(List<Giohang> giohangList) {
        int totalMoney = 0;
        for (Giohang giohang : giohangList) {
            //totalMoney += giohang.getPrice() * giohang.getSoLuong();
            if (giohang.isSelected()) {
                totalMoney += giohang.getPrice() * giohang.getSoLuong();
            }
        }
        return totalMoney;
    }

    // Phương thức tính tổng số hàng từ danh sách giỏ hàng
    private int calculateTotalQuantity(List<Giohang> giohangList) {
        int totalQuantity = 0;
        for (Giohang giohang : giohangList) {
            //totalQuantity += giohang.getSoLuong();
            if (giohang.isSelected()) {
                totalQuantity += giohang.getSoLuong();
            }
        }
        return totalQuantity;
    }


    private void addControls() {
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtTenPayment = findViewById(R.id.edtTenPayment);
        edtSDTPayment = findViewById(R.id.edtSDTPayment);
        txtTotalMoney = findViewById(R.id.txtTotalMoney);
        btnOrderPayment = findViewById(R.id.btnOrderPayment);
        txtTotalSLPayment = findViewById(R.id.txtTotalSLPayment);

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