package com.example.do_an;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an.model.SanPham;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class SanPhamActivity extends AppCompatActivity {
    public String DATABASE_NAME="DBMeoMeo";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database = null;
    ListView lvSP;
    Button btnOpen;
    ArrayAdapter<SanPham> adapterSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        proccessCopy();
        addControls();
        addEvents();
        xulyCapnhat();
    }
    private void xulyCapnhat() {
      lvSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              SanPham sp = adapterSanPham.getItem(position);
              Intent intent=new Intent(SanPhamActivity.this,CapnhatSPActivity.class);
              intent.putExtra("sp",sp);
              startActivity(intent);
          }
      });
    }
     @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }
    private void addEvents() {
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SanPhamActivity.this, ThemspActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from tbProducts", null);


        adapterSanPham.clear();
        while (cursor.moveToNext()){
            int ma=cursor.getInt(0);
            String ten=cursor.getString(1);
            String mota=cursor.getString(2);
            int loai=cursor.getInt(3);
            String sta=cursor.getString(4);
            byte[] imageData = cursor.getBlob(5);
            SanPham s=new SanPham(ten,sta,mota,imageData,ma,loai);
            adapterSanPham.add(s);

        }
    }

    private void addControls() {
        btnOpen = findViewById(R.id.btnOpen);
        lvSP = findViewById(R.id.lvSP);
        adapterSanPham = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvSP.setAdapter(adapterSanPham);
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