package com.example.do_an;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.Singleton.GioHangManager;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.adapter.GiohangAdapter;
import com.example.do_an.adapter.OptionsApdater;
import com.example.do_an.adapter.ProductAdapter;
import com.example.do_an.model.Giohang;
import com.example.do_an.model.Options;
import com.example.do_an.model.Product;
import com.nex3z.notificationbadge.NotificationBadge;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLServerSocket;

public class ChitietspActivity extends AppCompatActivity {

    ImageView ivImageProDetail, ivBack, ivCart;
    TextView txtProductNameDetail, txtCategoryNameDetail, txtStatusDetail, txtDecriptionProDetail, txtPriceDetail;
    ListView listViewOptions;
    SessionManager sessionManager;
    SQLiteDatabase database;
    OptionsApdater optionsAdapter;
    List<Options> optionList;
    Button btnAddToCart;
    Options selectedOption;
    private int selectedOptionId;
    private int selectedProductId;
    GiohangAdapter giohangAdapter;
    List<Giohang> giohangList;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        addControls();
        addEvents();
    }

    private void addControls() {
        ivImageProDetail = findViewById(R.id.ivImageProDetail);
        txtProductNameDetail = findViewById(R.id.txtProductNameDetail);
        //txtCategoryNameDetail = findViewById(R.id.txtCategoryNameDetail);
        txtStatusDetail = findViewById(R.id.txtStatusDetail);
        txtDecriptionProDetail = findViewById(R.id.txtDecriptionProDetail);
        txtPriceDetail = findViewById(R.id.txtPriceDetail);
        ivBack = findViewById(R.id.ivBack);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        // Lấy dữ liệu sản phẩm từ SessionManager
        sessionManager = new SessionManager(this);
        Product product = sessionManager.getSelectedProduct();
        // Hiển thị hình ảnh từ mảng byte
        if (product.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            ivImageProDetail.setImageBitmap(bitmap);
        }
        txtProductNameDetail.setText(product.getProductName());
        //txtCategoryNameDetail.setText(product.getCategoryName());
        txtStatusDetail.setText(product.getStatus());
        txtDecriptionProDetail.setText(product.getDecriptionPro());

        listViewOptions = findViewById(R.id.listViewOptions);
        // Load danh sách tùy chọn và giá tương ứng vào listViewOptions
        loadOptions(product.getProductId());
        // Khởi tạo danh sách giỏ hàng và adapter
        giohangList = new ArrayList<>();
        //giohangAdapter = new GiohangAdapter(this, giohangList);
        //product = (Product) intent.getSerializableExtra("product");
        badge = findViewById(R.id.menu_sl);

    }

    private void loadOptions(int productId) {
        optionList = new ArrayList<>();
        try {
            database = SQLiteDatabase.openDatabase(getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = database.rawQuery("SELECT IdOpt, Option, Price FROM tbOptions WHERE ProductID = ?", new String[]{String.valueOf(productId)});

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") int idOpt = cursor.getInt(cursor.getColumnIndex("IdOpt"));
                    @SuppressLint("Range") String optionName = cursor.getString(cursor.getColumnIndex("Option"));
                    @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("Price"));
                    optionList.add(new Options(idOpt, productId, optionName, price));
                }
                optionsAdapter = new OptionsApdater(this, optionList);
                listViewOptions.setAdapter(optionsAdapter);
            } else {
                Toast.makeText(this, "Không có tùy chọn nào", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            //database.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi truy vấn cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    private void addEvents() {
        // Thêm sự kiện click cho từng mục trong listViewOptions
        listViewOptions.setOnItemClickListener((parent, view, position, id) -> {
            selectedOption = optionList.get(position);
            Options option = optionList.get(position);
            // Cập nhật giá trị idOptions và idProduct tương ứng với tùy chọn được chọn
            int idOptions = option.getIdOpt();
            int idProduct = option.getProductID();
            // Lưu giá trị idOptions và idProduct vào biến toàn cục
            this.selectedOptionId = idOptions;
            this.selectedProductId = idProduct;
            // Hiển thị giá tương ứng
            int price = option.getPrice();
            txtPriceDetail.setText(String.valueOf(price));
        });
        //nút back
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                // Chuyển về MainActivity
                Intent intent = new Intent(ChitietspActivity.this, MainActivity.class);
                startActivity(intent);
                 */
                finish();;
            }
        });
        //Thêm sự liện cho nút Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOption != null) {
                    String productName = txtProductNameDetail.getText().toString();
                    String option = selectedOption.getOption();
                    int price = Integer.parseInt(txtPriceDetail.getText().toString());
                    //byte[] image = ((BitmapDrawable) ivImageProDetail.getDrawable()).getBitmap().getNinePatchChunk();
                    // Chuyển hình ảnh thành byte array
                    byte[] image = bitmapToByteArray(((BitmapDrawable) ivImageProDetail.getDrawable()).getBitmap());
                    // Lấy idProduct và idOptions từ option đã chọn
                    int idProduct = selectedProductId; // Thay product.getProductId() bằng phương thức tương ứng
                    int idOptions = selectedOptionId;
                    Giohang gioHang = new Giohang(productName, option, price, 1, image, idProduct, idOptions);

                    //giohangList.add(gioHang);
                    // Thêm vào giỏ hàng
                    GioHangManager.getInstance().addToGioHang(gioHang);
                    // Truyền danh sách giỏ hàng sang MainActivity khi chuyển
                    // Lưu danh sách giỏ hàng vào SessionManager
                    SessionManager sessionManager = new SessionManager(ChitietspActivity.this);
                    sessionManager.setGiohangList(GioHangManager.getInstance().getGiohangList());
                    /*
                    Intent intent = new Intent(ChitietspActivity.this, MainActivity.class);
                    //intent.putExtra("giohangList", (Serializable) giohangList);
                    intent.putExtra("fragmentToLoad", "cart");
                    startActivity(intent);
                    */
                    badge.setNumber(GioHangManager.getInstance().getGiohangList().size());
                    Toast.makeText(ChitietspActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChitietspActivity.this, "Vui lòng chọn tùy chọn", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivCart = findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChitietspActivity.this, MainActivity.class);
                intent.putExtra("fragmentToLoad", "cart");
                startActivity(intent);
            }
        });
    }

    //Thêm phương thức chuyển đổi bitmap thành byte array
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}