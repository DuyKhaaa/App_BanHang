package com.example.do_an.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.do_an.ChitietspActivity;
import com.example.do_an.R;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.adapter.ProductAdapter;
import com.example.do_an.model.Product;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView searchView;
    SessionManager sessionManager;

    public ProFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProFragment.
     */
    // TODO: Rename and change types and number of parameters
    SQLiteDatabase database;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Product> productList;
    public static ProFragment newInstance(String param1, String param2) {
        ProFragment fragment = new ProFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_pro, container, false);
        // Inflate layout cho Fragment Pro
        View view = inflater.inflate(R.layout.fragment_pro, container, false);
        //database = SQLiteDatabase.openDatabase(requireContext().getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        addControls(view);
        addLoad();
        addEvents();
        return view;
    }
    private void addEvents() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String s=newText;
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    @SuppressLint("Range")
    private void addLoad() {
        // Thực hiện truy vấn SQLite để lấy dữ liệu sản phẩm từ bảng tbProducts
        try {
            database = SQLiteDatabase.openDatabase(requireContext().getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = database.rawQuery("SELECT ProductID, NamePro, Status, DecriptionPro, Image, NameCate " +
                    "FROM tbProducts " +
                    "INNER JOIN tbCategory ON tbProducts.Category = tbCategory.IDCate", null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") int productId = cursor.getInt(cursor.getColumnIndex("ProductID"));
                    @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("NamePro"));
                    @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("Status"));
                    @SuppressLint("Range") byte[] imageData = cursor.getBlob(cursor.getColumnIndex("Image"));
                    @SuppressLint("Range") String categoryName = cursor.getString(cursor.getColumnIndex("NameCate"));
                    String decriptionPro = cursor.getString(cursor.getColumnIndex("DecriptionPro"));
                    productList.add(new Product(productId, productName, status, imageData, categoryName, decriptionPro));
                }
                productAdapter.notifyDataSetChanged(); // Cập nhật giao diện
            } else {
                Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            database.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Lỗi khi truy vấn cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
    private void addControls(View view) {
        searchView=view.findViewById(R.id.searchView);
        //toolbar = view.findViewById(R.id.toolbar);
        searchView.clearFocus();
        recyclerView = view.findViewById(R.id.recycler_view_products);
        // Khởi tạo danh sách sản phẩm
        productList = new ArrayList<>();

        // Khởi tạo adapter và thiết lập cho RecyclerView
        productAdapter = new ProductAdapter(getContext(), productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý khi một item được nhấn
                Product product = productList.get(position);
                sessionManager.setSelectedProduct(product); // Lưu sản phẩm được chọn vào SessionManager
                // Thực hiện hành động mong muốn, ví dụ: chuyển tới trang chi tiết sản phẩm
                Intent intent = new Intent(getActivity(), ChitietspActivity.class);
                //intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(productAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}