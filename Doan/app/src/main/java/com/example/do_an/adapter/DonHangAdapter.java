package com.example.do_an.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.do_an.DanhSachDonHangActivity;
import com.example.do_an.R;
import com.example.do_an.model.DeTail;
import com.example.do_an.model.DonHang;

import java.io.File;
import java.util.List;

public class DonHangAdapter extends BaseAdapter {
    private Context context;
    private List<DeTail> donHangList;
    public DonHangAdapter(Context context, List<DeTail> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }
    @Override
    public int getCount() {
        return donHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return donHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        DonHangAdapter.ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_donhang, null);
            holder = new DonHangAdapter.ViewHolder();
            holder.ivProductImage1 = view.findViewById(R.id.ivProductImage1);
            holder.txtProductName1 = view.findViewById(R.id.txtProductName1);
            holder.txtOption1 = view.findViewById(R.id.txtOption1);
            holder.txtSoLuongSP = view.findViewById(R.id.txtSoLuongSP);
            holder.txtPrice1 = view.findViewById(R.id.txtPrice1);

            view.setTag(holder);
        } else {
            holder = (DonHangAdapter.ViewHolder) view.getTag();
        }
        final DeTail donHang = donHangList.get(position);
        int masp=donHang.getMaSP();
        String q = "SELECT * FROM tbProducts WHERE ProductID = ?";
        Cursor cursor = DanhSachDonHangActivity.database.rawQuery(q, new String[]{String.valueOf(masp)}  );
        cursor.moveToFirst();
        String ten=cursor.getString(1);
        byte[] hinh=cursor.getBlob(5);
        cursor.close();
        // Đổ dữ liệu vào các view
        holder.txtProductName1.setText(ten);
        int maop=donHang.getMaOptions();
        String q1 = "SELECT * FROM tbOptions WHERE IdOpt = ?";
        Cursor cursor1 = DanhSachDonHangActivity.database.rawQuery(q1, new String[]{String.valueOf(maop)}  );
        cursor1.moveToFirst();
        String op=cursor1.getString(2);
        cursor1.close();
        holder.txtOption1.setText(op);
        holder.txtSoLuongSP.setText(donHang.getSoLuong()+"");
        int productId = donHang.getMaSP();
        String query = "SELECT Price FROM tbOptions WHERE ProductID = ? ORDER BY IdOpt ASC LIMIT 1";
        Cursor cursor2 = DanhSachDonHangActivity.database.rawQuery(query, new String[]{String.valueOf(productId)});
        if (cursor2.moveToFirst()) {
            @SuppressLint("Range") int price = cursor2.getInt(cursor2.getColumnIndex("Price"));
            holder.txtPrice1.setText(String.valueOf(price));
        } else {
            holder.txtPrice1.setText("N/A"); // Hoặc giá trị mặc định khác nếu không tìm thấy giá
        }
        cursor2.close();
        if (hinh != null) {
            holder.ivProductImage1.setImageBitmap(BitmapFactory.decodeByteArray(hinh, 0, hinh.length));
        } else {
            // Xử lý trường hợp mảng hình ảnh là null (có thể hiển thị hình ảnh mặc định hoặc không hiển thị gì cả)
            holder.ivProductImage1.setImageResource(R.drawable.h2); // Sử dụng hình ảnh mặc định hoặc thực hiện xử lý phù hợp
        }
        return view;
    }



    public static class ViewHolder{
        ImageView ivProductImage1;
        TextView txtProductName1;
        TextView txtOption1;
        TextView txtSoLuongSP;
        TextView txtPrice1;
    }
}