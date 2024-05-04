package com.example.do_an.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.do_an.ChitietspActivity;
import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.adapter.ProductAdapter;
import com.example.do_an.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Product> hatProducts;
    SQLiteDatabase database;
    ImageView ivback;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hat);
        addControls();
        loadHatProducts();
        sessionManager = new SessionManager(this);
        addEvents();
    }
    private void loadHatProducts() {
        try {
            hatProducts = new ArrayList<>();
            database = SQLiteDatabase.openDatabase(this.getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = database.rawQuery("SELECT ProductID, NamePro, Status, DecriptionPro, Image, NameCate " +
                    "FROM tbProducts " +
                    "INNER JOIN tbCategory ON tbProducts.Category = tbCategory.IDCate " +
                    "WHERE tbCategory.NameCate = 'Nón'", null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") int productId = cursor.getInt(cursor.getColumnIndex("ProductID"));
                    @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("NamePro"));
                    @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("Status"));
                    @SuppressLint("Range") byte[] imageData = cursor.getBlob(cursor.getColumnIndex("Image"));
                    @SuppressLint("Range") String categoryName = cursor.getString(cursor.getColumnIndex("NameCate"));
                    @SuppressLint("Range") String decriptionPro = cursor.getString(cursor.getColumnIndex("DecriptionPro"));
                    hatProducts.add(new Product(productId, productName, status, imageData, categoryName, decriptionPro));
                }
                cursor.close();
            } else {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
            database.close();

            // Khởi tạo adapter và thiết lập cho RecyclerView
            productAdapter = new ProductAdapter(this, hatProducts, new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Xử lý khi một item được nhấn
                    Product product = hatProducts.get(position);
                    sessionManager.setSelectedProduct(product); // Lưu sản phẩm được chọn vào SessionManager
                    // Thực hiện hành động mong muốn, ví dụ: chuyển tới trang chi tiết sản phẩm
                    Intent intent = new Intent(HatActivity.this, ChitietspActivity.class);
                    //intent.putExtra("product", product);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(productAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi truy vấn cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        recyclerView = findViewById(R.id.recycler_view_hat);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ivback = findViewById(R.id.ivback2);
    }
    private void addEvents() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Chuyển về MainActivity
                /*
                Intent intent = new Intent(HatActivity.this, MainActivity.class);
                startActivity(intent);*/
                finish();;
            }
        });
    }
}





