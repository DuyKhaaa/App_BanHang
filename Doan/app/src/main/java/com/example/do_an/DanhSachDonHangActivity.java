package com.example.do_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.do_an.model.DonHang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DanhSachDonHangActivity extends AppCompatActivity {
    public String DATABASE_NAME="DBMeoMeo";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database = null;
    ListView lvDH;

    ArrayAdapter<DonHang> adapterDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_don_hang);
        proccessCopy();
        addControls();
        loadData();
        lvDH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DonHang hd = adapterDonHang.getItem(position);
                Intent intent=new Intent(DanhSachDonHangActivity.this,ChiTietHDActivity.class);
                intent.putExtra("hd",hd);
                startActivity(intent);
            }
        });
    }
    private void addControls() {
        lvDH = findViewById(R.id.lvDH);
        adapterDonHang = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvDH.setAdapter(adapterDonHang);
    }
    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }
    private void loadData() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from tbOrder ", null);
        adapterDonHang.clear();
        while (cursor.moveToNext()){
            int ma=cursor.getInt(0);
            String ngay=cursor.getString(1);
            int maKH=cursor.getInt(2);
            String diachi=cursor.getString(3);
            //int mact=cursor.getInt(5);
            // int masp=cursor.getInt(6);
            //int maop=cursor.getInt(7);
            //int sl=cursor.getInt(8);
            //int gia=cursor.getInt(9);
            DonHang d=new DonHang(ma, ngay, maKH, diachi );
            //DonHang k=new DonHang(ma,ngay,maKH,diachi,mact,masp,maop,sl,gia);
            adapterDonHang.add(d);
        }

    }
    public String getDatabasePath(){
        return  getApplicationInfo().dataDir+DB_SUFFIX_PATH+DATABASE_NAME;
    }
    private void proccessCopy() {
        try{
            File file = getDatabasePath(DATABASE_NAME);
            if(!file.exists()) {
                copyDatabaseFromAssest();
                Toast.makeText(this, "Coppy Database Successful", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(this, "Coppy Database Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyDatabaseFromAssest() {
        try{
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePath();
            File file = new File(getApplicationInfo().dataDir+DB_SUFFIX_PATH);
            if(!file.exists())
            {
                file.mkdir();
            }
            OutputStream outFile = new FileOutputStream(outputFileName);
            byte []buffer = new byte[1024];
            int length;
            while ((length=inputFile.read(buffer))>0) outFile.write(buffer, 0, length);
            outFile.flush();
            outFile.close();
            inputFile.close();
        }
        catch (Exception ex){
            Log.e("Error", ex.toString());
        }
    }
    // viet su kien cho Menu
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