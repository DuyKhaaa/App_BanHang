package com.example.do_an.Singleton;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.do_an.model.Giohang;
import com.example.do_an.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "YourAppSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LOGGED_IN = "loggedIn";
    private static final String KEY_GIOHANG_LIST = "giohangList";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_CUSTOMER_ID = "customerId";
    private static final String KEY_SELECTED_PRODUCT = "selected_product";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu thông tin đăng nhập vào session
    public void setLoggedIn(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    // Kiểm tra xem người dùng có đăng nhập hay không
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    // Lấy tên người dùng từ session
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    // Xóa thông tin session khi người dùng đăng xuất
    public void logout() {
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_LOGGED_IN);
        editor.apply();
    }
    // Lưu danh sách giỏ hàng vào session
    public void setGiohangList(List<Giohang> giohangList) {
        Gson gson = new Gson();
        String json = gson.toJson(giohangList);
        editor.putString(KEY_GIOHANG_LIST, json);
        editor.apply();
    }

    // Lấy danh sách giỏ hàng từ session
    public List<Giohang> getGiohangList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_GIOHANG_LIST, null);
        Type type = new TypeToken<List<Giohang>>() {}.getType();
        return gson.fromJson(json, type);
    }
    // Phương thức để lưu số điện thoại vào session
    public void setPhoneNumber(String phoneNumber) {
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    // Phương thức để lấy số điện thoại từ session
    public String getPhoneNumber() {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, null);
    }
    public void clearGiohangList() {
        // Xóa danh sách giỏ hàng
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_GIOHANG_LIST);
        editor.apply();
    }

    // Phương thức để lưu mã khách hàng vào session
    public void setCustomerId(int customerId) {
        editor.putInt(KEY_CUSTOMER_ID, customerId);
        editor.apply();
    }

    // Phương thức để lấy mã khách hàng từ session
    public int getCustomerId() {
        return sharedPreferences.getInt(KEY_CUSTOMER_ID, -1);
    }
    // Thêm một phương thức vào SessionManager để lưu một sản phẩm
    public void setSelectedProduct(Product product) {
        Gson gson = new Gson();
        String json = gson.toJson(product);
        editor.putString(KEY_SELECTED_PRODUCT, json);
        editor.apply();
    }
    // Phương thức để lấy sản phẩm được chọn từ session
    public Product getSelectedProduct() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_SELECTED_PRODUCT, null);
        return gson.fromJson(json, Product.class);
    }
}
