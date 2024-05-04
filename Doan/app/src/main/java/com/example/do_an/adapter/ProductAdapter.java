package com.example.do_an.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.do_an.R;
import com.example.do_an.model.Options;
import com.example.do_an.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    private Context context;
    private List<Product> productList;
    private List<Product> productListSearch=new ArrayList<>();
    private OnItemClickListener mListener;
    private List<Options> optionsList;
    // Khai báo interface cho ItemClickListener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    // Thiết lập phương thức để thiết lập mListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public ProductAdapter(Context context, List<Product> productList, OnItemClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.mListener = listener;
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint==null||constraint.length()==0) {
                    return (FilterResults) productList;
                    //filterResults.values = productList;
                    // filterResults.count = productList.size();
                } else {
                    String s = constraint.toString().toLowerCase();
                    productList.removeIf(product -> !product.getProductName().toLowerCase().contains(s));
                    filterResults.values = productListSearch;
                    filterResults.count = productListSearch.size();
                }
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtStatus.setText(product.getStatus());
        // Load hình ảnh bằng Glide hoặc thư viện tương tự
        Glide.with(context).load(product.getImage()).into(holder.imgProduct);

        // Gán sự kiện click vào itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
        // Truy vấn cơ sở dữ liệu để lấy giá đầu tiên cho ProductID tương ứng
        SQLiteDatabase database = SQLiteDatabase.openDatabase(context.getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        // Lấy database từ nguồn dữ liệu của bạn
        if (database != null) {
            int productId = product.getProductId();
            String query = "SELECT Price FROM tbOptions WHERE ProductID = ? ORDER BY IdOpt ASC LIMIT 1";
            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(productId)});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("Price"));
                holder.txtPriceProduct.setText(String.valueOf(price));
            } else {
                holder.txtPriceProduct.setText("N/A"); // Hoặc giá trị mặc định khác nếu không tìm thấy giá
            }
            cursor.close();
        } else {
            holder.txtPriceProduct.setText("N/A"); // Hoặc giá trị mặc định khác nếu không thể truy cập cơ sở dữ liệu
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView txtProductName, txtStatus, txtPriceProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);
        }
    }
}
