package com.example.do_an.Singleton;

import com.example.do_an.model.Giohang;

import java.util.ArrayList;
import java.util.List;

public class GioHangManager {
    private static GioHangManager instance;
    private List<Giohang> giohangList;

    private GioHangManager() {
        giohangList = new ArrayList<>();
    }

    public static GioHangManager getInstance() {
        if (instance == null) {
            instance = new GioHangManager();
        }
        return instance;
    }

    public List<Giohang> getGiohangList() {
        return giohangList;
    }

    public void addToGioHang(Giohang gioHang) {
        giohangList.add(gioHang);
    }
    // Phương thức xóa sản phẩm khỏi giỏ hàng
    /*
    public void deleteFromGioHang(int position) {
        giohangList.remove(position);
    }*/
    public void deleteFromGioHang(Giohang gioHang) {
        giohangList.remove(gioHang);
    }
    // Thêm phương thức để xóa toàn bộ giỏ hàng
    public void clearGiohangList() {
        giohangList.clear();
    }
}
