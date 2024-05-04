package com.example.do_an.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.do_an.HomeloginActivity;
import com.example.do_an.R;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.model.Customer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters\
    TextView txtTenDN, txtEmail, txtSDT;
    Button btnLogout;
    SQLiteDatabase database;
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);*/
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Ánh xạ
        AnhXa(view);
        // Lấy tên đăng nhập của khách hàng từ Intent khi chuyển từ LoginActivity
        //String username = getActivity().getIntent().getStringExtra("name");
        // Lấy tên người dùng từ session trong ProfileFragment
        SessionManager sessionManager = new SessionManager(requireContext());
        String username = sessionManager.getUsername();
        // Lấy số điện thoại từ session
        String phoneNumber = sessionManager.getPhoneNumber();
        if (phoneNumber != null) {
            // Hiển thị số điện thoại trên giao diện
            txtSDT.setText(phoneNumber);
        }

        // Thực hiện truy vấn cơ sở dữ liệu để lấy thông tin của khách hàng
        Customer customer = getCustomerInfo(username);

        // Hiển thị thông tin khách hàng trên giao diện
        if (customer != null) {
            txtTenDN.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtSDT.setText(String.valueOf(customer.getPhone()));
        }
        //trả về
        return view;
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


    private void AnhXa(View view) {
        txtTenDN = view.findViewById(R.id.txtTenDN);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtSDT = view.findViewById(R.id.txtSDT);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funLogout();
            }
        });
    }

    public void funLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Close");
        builder.setMessage("Xác nhận đăng xuất ?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), HomeloginActivity.class);
                startActivity(intent);
            }

        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}