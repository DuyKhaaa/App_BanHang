package com.example.do_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.do_an.adapter.DonHangAdapter;
import com.example.do_an.model.DeTail;
import com.example.do_an.model.DonHang;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHDActivity extends AppCompatActivity {
    TextView txtTenHD, txtDiaChiHD, txtNgayHD, txtGiaHD;
    ListView lvCTHD;
    List<DeTail> donHangList;
    DonHangAdapter donHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hdactivity);
        addControls();

    }

    private void addControls() {
        txtTenHD=findViewById(R.id.txtTenHD);
        txtDiaChiHD=findViewById(R.id.txtDiaChiHD);
        txtNgayHD=findViewById(R.id.txtNgayHD);
        txtGiaHD=findViewById(R.id.txtTongTienHD);
        Intent intent=getIntent();
        DonHang dh= (DonHang) intent.getSerializableExtra("hd");
        int idcus=dh.getIdCus();
        String q = "SELECT * FROM tbCustomer WHERE IDCus = ?";
        Cursor cursor = DanhSachDonHangActivity.database.rawQuery(q, new String[]{String.valueOf(idcus)}  );
        cursor.moveToFirst();
        String ten=cursor.getString(1);
        cursor.close();
        txtTenHD.setText(ten);
        txtDiaChiHD.setText(dh.getAddress());
        txtNgayHD.setText(dh.getDate());
        int idOD=dh.getMaDon();
        donHangList = new ArrayList<>();
        int gia1=0;
        Cursor cursor1 = DanhSachDonHangActivity.database.rawQuery("select * from tbOrderDeTail where IDOrder=? ", new String[]{String.valueOf(idOD)});
        while (cursor1.moveToNext()) {
            int mact = cursor1.getInt(0);
            int ma = cursor1.getInt(1);
            int masp = cursor1.getInt(2);
            int maop = cursor1.getInt(3);
            int sl = cursor1.getInt(4);
            int gia = cursor1.getInt(5);
            DeTail k = new DeTail(ma, mact, masp, maop, sl, gia);
            donHangList.add(k);
            gia1=gia1+gia;
        }

        txtGiaHD.setText(gia1+""+"đ");
        lvCTHD = findViewById(R.id.lvCTHD);

        donHangAdapter = new DonHangAdapter(this, donHangList);
        lvCTHD.setAdapter(donHangAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnLogout){
            /*
            Intent i=new Intent(this, HomeloginActivity.class);
            startActivity(i);
             */
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}