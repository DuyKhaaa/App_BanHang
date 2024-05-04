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

import com.example.do_an.model.KhachHang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class KhachHangActivity extends AppCompatActivity {
    public String DATABASE_NAME="DBMeoMeo";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database = null;
    ListView lvKH;
    ArrayAdapter<KhachHang> adapterKhachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        proccessCopy();
        addControls();
        loadData();
        xulyXemCT();
    }
    private void xulyXemCT() {
        lvKH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhachHang kh = adapterKhachHang.getItem(position);
                Intent intent=new Intent(KhachHangActivity.this,ChiTietKHActivity.class);
                intent.putExtra("kh",kh);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }
    private void loadData() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from tbCustomer", null);
        adapterKhachHang.clear();
        while (cursor.moveToNext()){
            int ma=cursor.getInt(0);
            String ten=cursor.getString(1);
            String pass=cursor.getString(2);
            String email=cursor.getString(3);
            int phone=cursor.getInt(4);
            KhachHang k=new KhachHang(ma,ten,pass,email,phone);
            adapterKhachHang.add(k);
        }
    }

    private void addControls() {
        lvKH = findViewById(R.id.lvKH);
        adapterKhachHang = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvKH.setAdapter(adapterKhachHang);
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