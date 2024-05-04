package com.example.do_an.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.do_an.Category.BambooActivity;
import com.example.do_an.Category.BookActivity;
import com.example.do_an.Category.BookMarkActivity;
import com.example.do_an.Category.CupsActivity;
import com.example.do_an.Category.HatActivity;
import com.example.do_an.Category.KeyActivity;
import com.example.do_an.Category.MagnetActivity;
import com.example.do_an.Category.PaintActivity;
import com.example.do_an.ChitietspActivity;
import com.example.do_an.MapActivity;
import com.example.do_an.R;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.adapter.ProductAdapter;
import com.example.do_an.model.Customer;
import com.example.do_an.model.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    SessionManager sessionManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtname_hd, txtemail_hd, txt_best;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    ProductAdapter productAdapter;
    List<Product> productList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    ViewFlipper viewFLipper;
    public static HomeFragment newInstance(String param1, String param2) {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sessionManager = new SessionManager(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
         */
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        addControls(view);
        addLoad();
        //recyclerView = view.findViewById(R.id.rcl_best);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        txtname_hd = headerView.findViewById(R.id.name_hd);
        txtemail_hd = headerView.findViewById(R.id.email_hd);
        txt_best = view.findViewById(R.id.txt_best);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(R.id.mn_map==item.getItemId()){
                    Intent i=new Intent(HomeFragment.this.getActivity(), MapActivity.class);
                    startActivity(i);
                }
                else if (R.id.mn_call==item.getItemId()){
                    Intent intent=new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0919041104"));
                    startActivity(intent);
                }
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Xử lý hành động khi người dùng chọn một mục trong drawer
                int id = item.getItemId();
                if (id == R.id.bm) {
                    Intent i = new Intent(getContext(), BookMarkActivity.class);
                    startActivity(i);
                } else if (id == R.id.cups) {
                    Intent i = new Intent(getContext(), CupsActivity.class);
                    startActivity(i);
                } else if (id == R.id.magnet){
                    Intent i = new Intent(getContext(), MagnetActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.key){
                    Intent i = new Intent(getContext(), KeyActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.book){
                    Intent i = new Intent(getContext(), BookActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.bamboo){
                    Intent i = new Intent(getContext(), BambooActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.hat){
                    Intent i = new Intent(getContext(), HatActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.paint){
                    Intent i = new Intent(getContext(), PaintActivity.class);
                    startActivity(i);
                }
                // Đóng drawer sau khi xử lý sự kiện chọn mục
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });
        // Ánh xạ
        AnhXa(view);
        //chạy quảng cáo
        ActionViewFlipper();
        SessionManager sessionManager = new SessionManager(requireContext());
        String username = sessionManager.getUsername();
        Customer customer = getCustomerInfo(username);

        // Hiển thị thông tin khách hàng trên giao diện
        if (customer != null) {
            txtname_hd.setText(customer.getName());
            txtemail_hd.setText(customer.getEmail());
        }
        return view;
    }

    private void addControls(View view) {
        recyclerView = view.findViewById(R.id.rcl_best);
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
    }


    @SuppressLint("Range")
    private void addLoad() {
        try {
            database = SQLiteDatabase.openDatabase(requireContext().getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = database.rawQuery("SELECT ProductID, NamePro, Status, DecriptionPro, Image, NameCate " +
                    "FROM tbProducts " +
                    "INNER JOIN tbCategory ON tbProducts.Category = tbCategory.IDCate LIMIT 4", null);

            if (cursor != null && cursor.getCount() > 0) {
                productList.clear(); // Xóa danh sách sản phẩm cũ
                while (cursor.moveToNext()) {
                    int productId = cursor.getInt(cursor.getColumnIndex("ProductID"));
                    String productName = cursor.getString(cursor.getColumnIndex("NamePro"));
                    String status = cursor.getString(cursor.getColumnIndex("Status"));
                    byte[] imageData = cursor.getBlob(cursor.getColumnIndex("Image"));
                    String categoryName = cursor.getString(cursor.getColumnIndex("NameCate"));
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


    private Customer getCustomerInfo(String username) {
        Customer customer = null;
        if (username != null){
            // Thực hiện truy vấn cơ sở dữ liệu để lấy thông tin của khách hàng
            database = SQLiteDatabase.openDatabase(requireContext().getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
            String query = "SELECT * FROM tbCustomer WHERE name = ?";
            Cursor cursor = database.rawQuery(query, new String[]{username});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int idCus = cursor.getInt(cursor.getColumnIndex("IDCus"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
                @SuppressLint("Range") int phone = cursor.getInt(cursor.getColumnIndex("Phone"));
                customer = new Customer(idCus, name, email, phone);
            }
            cursor.close();
        }

        return customer;
    }
    private void AnhXa(View view){
        viewFLipper = view.findViewById(R.id.viewFLipper);

    }
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR_MR1)
    private void ActionViewFlipper() {
        //Tải ảnh quảng cáo
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://scontent.fsgn2-11.fna.fbcdn.net/v/t39.30808-6/422163109_1724349988087997_626394289159645350_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeG9TxUqVwOUex6Dxay790RvtOQaG0U_QAC05BobRT9AAKJT5RQubCDPN1DRbz0v6GN4i20P6CfLn6AFNH13h6av&_nc_ohc=EmUc5eBkOZ4AX8FZkso&_nc_ht=scontent.fsgn2-11.fna&oh=00_AfBWvtPpIkiKaoEe9-xu5y4Uk_0_JP056KnG30L68oymow&oe=660489F5");
        mangquangcao.add("https://scontent.fsgn2-10.fna.fbcdn.net/v/t39.30808-6/422146596_1724351224754540_7929814574946071317_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHfnszZW-jQdt8T028UeDL6oUhlYuEqkJGhSGVi4SqQkW_KlKR1oNnm_0H7Iw4IQFmDTJAVJW0HDeF66bAPp4ae&_nc_ohc=pqdDAKI9yhYAX-8lxFA&_nc_ht=scontent.fsgn2-10.fna&oh=00_AfA-p51iGFmLSrceaYGg-3obcwET3Niq3LXZbXZxXbvQXA&oe=6604CFD5");
        mangquangcao.add("https://scontent.fsgn2-11.fna.fbcdn.net/v/t39.30808-6/422186057_1724351234754539_4260795208871203534_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEvKloYyAU3LEnxZ6OHUTHL1Tglm2UQzVbVOCWbZRDNVuZ3neiwEK3FHYkWAom43fsCtFAaIT1JgPdhnotTQf97&_nc_ohc=Vj9-OgYb-SgAX9BXCmk&_nc_ht=scontent.fsgn2-11.fna&oh=00_AfD0tIdbTz6GVDNiqQnKf-0IlzVIe5fvTcN844LjuUDtxQ&oe=6605BC97");
        //Thêm ảnh vào ViewFlipper
        for(int i=0;i< mangquangcao.size();i++){
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFLipper.addView(imageView);
        }
        //Thiết lập thời gian và hiệu ứng chuyển trang
        viewFLipper.setFlipInterval(3000);
        viewFLipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFLipper.setInAnimation(slide_in);
        viewFLipper.setOutAnimation(slide_out);
    }


}