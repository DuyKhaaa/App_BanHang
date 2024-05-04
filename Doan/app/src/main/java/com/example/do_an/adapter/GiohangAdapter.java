package com.example.do_an.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.do_an.Fragment.CartFragment;
import com.example.do_an.R;
import com.example.do_an.Singleton.GioHangManager;
import com.example.do_an.model.Giohang;

import java.util.List;

public class GiohangAdapter extends BaseAdapter {
    private Context context;
    private List<Giohang> giohangList;
    private CartFragment cartFragment;

    public GiohangAdapter(Context context, List<Giohang> giohangList, CartFragment cartFragment) {
        this.context = context;
        this.giohangList = giohangList;
        this.cartFragment = cartFragment;
    }

    public GiohangAdapter(Context context, List<Giohang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }


    @Override
    public int getCount() {
        if (giohangList != null) {
            return giohangList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return giohangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_cart, null);
            holder = new ViewHolder();
            holder.ivProductImage = view.findViewById(R.id.ivProductImage);
            holder.txtProductNameCart = view.findViewById(R.id.txtProductNameCart);
            holder.txtOptionCart = view.findViewById(R.id.txtOptionCart);
            holder.edtSoLuongCart = view.findViewById(R.id.edtSoLuongCart);
            holder.txtPriceCart = view.findViewById(R.id.txtPriceCart);
            holder.btnUpdateGia = view.findViewById(R.id.btnUpdateGia);
            holder.item_cart_check=view.findViewById(R.id.item_cart_check);
            holder.ic_remove = view.findViewById(R.id.btnRemoveCart);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Lấy thông tin sản phẩm tại vị trí position
        final Giohang giohang = giohangList.get(position);

        // Đổ dữ liệu vào các view
        holder.txtProductNameCart.setText(giohang.getNamePro());
        holder.txtOptionCart.setText(giohang.getOption());
        holder.edtSoLuongCart.setText(String.valueOf(giohang.getSoLuong()));
        holder.txtPriceCart.setText(String.valueOf(giohang.getPrice()));

        holder.idProduct = giohang.getIdProduct(); // Thêm dòng này để thiết lập idProduct
        holder.idOptions = giohang.getIdOptions(); // Thêm dòng này để thiết lập idOptions
        // Kiểm tra xem mảng hình ảnh có null không trước khi sử dụng
        if (giohang.getImage() != null) {
            holder.ivProductImage.setImageBitmap(BitmapFactory.decodeByteArray(giohang.getImage(), 0, giohang.getImage().length));
        } else {
            // Xử lý trường hợp mảng hình ảnh là null (có thể hiển thị hình ảnh mặc định hoặc không hiển thị gì cả)
            holder.ivProductImage.setImageResource(R.drawable.h2); // Sử dụng hình ảnh mặc định hoặc thực hiện xử lý phù hợp
        }
        // Biến cuối cùng (final) cho holder và edtSoLuongCart
        final ViewHolder finalHolder = holder;
        final EditText finalEdtSoLuongCart = holder.edtSoLuongCart;
        // Sự kiện thay đổi số lượng
        holder.edtSoLuongCart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // Kiểm tra nếu người dùng nhập vào là một số hợp lệ
                try {
                    int newQuantity = Integer.parseInt(editable.toString());
                    giohang.setSoLuong(newQuantity);
                    // Tính toán giá mới
                    int newPrice = newQuantity * giohang.getPrice();
                    // Cập nhật giá
                    finalHolder.txtPriceCart.setText(String.valueOf(newPrice));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        // Sự kiện khi nhấn nút Update
        holder.btnUpdateGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy số lượng từ EditText
                int newQuantity = Integer.parseInt(finalEdtSoLuongCart.getText().toString());
                // Tính toán giá mới
                int newPrice = newQuantity * giohang.getPrice();
                // Cập nhật giá
                finalHolder.txtPriceCart.setText(String.valueOf(newPrice));
            }
        });
        // Xử lý sự kiện khi người dùng chọn hoặc bỏ chọn Checkbox
        holder.item_cart_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Đánh dấu mục giỏ hàng là đã chọn hoặc không được chọn
                giohang.setSelected(isChecked);
                // Thông báo cho Fragment về sự thay đổi
                notifyDataSetChanged();
                // Gọi phương thức cập nhật tổng số tiền và số lượng từ CartFragment
                cartFragment.updateTotalAmountAndQuantity(giohangList);
            }
        });
        // Sự kiện khi nhấn nút xóa
        holder.ic_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức xóa mục giỏ hàng
                //deleteItem(position);
                // Gọi phương thức xóa mục giỏ hàng
                GioHangManager.getInstance().deleteFromGioHang(giohang);
                deleteItem(position);
            }
        });
        return view;
    }
    public static class ViewHolder{
        ImageView ivProductImage;
        TextView txtProductNameCart;
        TextView txtOptionCart;
        EditText edtSoLuongCart;
        TextView txtPriceCart;
        ImageButton btnUpdateGia;
        // Thêm trường idProduct và idOptions
        int idProduct;
        int idOptions;
        CheckBox item_cart_check;
        ImageButton ic_remove;
    }
    public void updateGiohang(List<Giohang> giohangList) {
        this.giohangList = giohangList;
        notifyDataSetChanged();
    }
    // Thêm phương thức xóa mục giỏ hàng vào GiohangAdapter
    public void deleteItem(int position) {
        giohangList.remove(position);
        notifyDataSetChanged();
        // Cập nhật tổng số tiền và số lượng từ CartFragment
        cartFragment.updateTotalAmountAndQuantity(giohangList);
    }
}