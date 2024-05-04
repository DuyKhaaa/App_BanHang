package com.example.do_an.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.do_an.PaymentActivity;
import com.example.do_an.R;
import com.example.do_an.Singleton.GioHangManager;
import com.example.do_an.Singleton.SessionManager;
import com.example.do_an.adapter.GiohangAdapter;
import com.example.do_an.model.Giohang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    private ListView listViewCart;
    private GiohangAdapter giohangAdapter;
    private List<Giohang> giohangList;
    Button btnMuaHang;
    TextView txtTotalSLCart,txtTotalMoneyCart;
    //private Giohang giohang;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Sửa phương thức newInstance để truyền danh sách giỏ hàng
    public static CartFragment newInstance(List<Giohang> giohangList) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putSerializable("giohangList", (Serializable) giohangList);
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
        /*if(getArguments()!=null){
            giohang = (Giohang) getArguments().getSerializable("giohang");
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_cart, container, false);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        //Ánh xạ
        addControls(view);
        // Lấy danh sách giỏ hàng từ đối số
        /*Bundle bundle = getArguments();
        if (bundle != null) {
            List<Giohang> giohangList = (List<Giohang>) bundle.getSerializable("giohangList");
            // Cập nhật danh sách giỏ hàng
            if (giohangList != null) {
                updateGiohangList(giohangList);
            }
        }*/
        // Lấy danh sách giỏ hàng từ SessionManager
        SessionManager sessionManager = new SessionManager(requireContext());
        List<Giohang> giohangList = sessionManager.getGiohangList();

        // Hiển thị danh sách giỏ hàng trên ListView
        updateGiohangList(giohangList);
        addEventsThanhToan();
        updateTotalAmountAndQuantity(giohangList);
        return view;
    }

    private void addEventsThanhToan() {
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu danh sách giỏ hàng vào Session
                SessionManager sessionManager = new SessionManager(requireContext());
                sessionManager.setGiohangList(giohangList);
                // Chuyển sang Activity mới để điền thông tin địa chỉ và xác nhận đặt hàng
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                //intent.putExtra("giohangList", (Serializable) giohangList);
                startActivity(intent);
            }
        });
    }

    private void addControls(View view) {
        listViewCart = view.findViewById(R.id.listViewCart);
        giohangList = new ArrayList<>(); // Khởi tạo giohangList trước khi sử dụng
        giohangAdapter = new GiohangAdapter(getContext(), giohangList,this);
        listViewCart.setAdapter(giohangAdapter);
        /*
        // Khởi tạo danh sách giỏ hàng và adapter
        giohangList = new ArrayList<>();
        giohangAdapter = new GiohangAdapter(getContext(), giohangList)
         */
        btnMuaHang = view.findViewById(R.id.btnMuaHang);
        txtTotalSLCart = view.findViewById(R.id.txtTotalSLCart);
        txtTotalMoneyCart = view.findViewById(R.id.txtTotalMoneyCart);
    }

    public void updateGiohangList(List<Giohang> giohangList) {
        if (giohangList != null) { // Kiểm tra xem giohangList có null không
            this.giohangList.clear();
            this.giohangList.addAll(giohangList);
            giohangAdapter.notifyDataSetChanged();
            updateTotalAmountAndQuantity(giohangList); // Thêm dòng này để cập nhật tổng số tiền và tổng số hing
        }
    }
    public void updateTotalAmountAndQuantity(List<Giohang> giohangList) {
        if (giohangList != null) { // Kiểm tra xem giohangList có null không
            int totalAmount = 0;
            int totalQuantity = 0;
            for (Giohang giohang : giohangList) {
                // Kiểm tra xem mục giỏ hàng có được chọn không trước khi tính toán
                if (giohang.isSelected()) {
                    totalAmount += giohang.getPrice() * giohang.getSoLuong();
                    totalQuantity += giohang.getSoLuong();
                }
            }
            txtTotalMoneyCart.setText(String.valueOf(totalAmount));
            txtTotalSLCart.setText(String.valueOf(totalQuantity));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Kiểm tra nếu danh sách giỏ hàng đã thay đổi, thì cập nhật lại giao diện
        List<Giohang> giohangList = GioHangManager.getInstance().getGiohangList();
        if (giohangList != null && !giohangList.isEmpty()) {
            updateGiohangList(giohangList);
        }
    }
}