package com.example.do_an;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.do_an.Fragment.CartFragment;
import com.example.do_an.Fragment.HomeFragment;
import com.example.do_an.Fragment.ProFragment;
import com.example.do_an.Fragment.ProfileFragment;
import com.example.do_an.Singleton.GioHangManager;
import com.example.do_an.databinding.ActivityMainBinding;
import com.example.do_an.model.Giohang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DrawerLayout drawerLayout;
    ViewFlipper viewFLipper;
    public String DATABASE_NAME="DBMeoMeo";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proccessCopy();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //AnhXa();
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            if (item.getItemId()==R.id.mn_home){
                replaceFragment(new HomeFragment());
            }
            if (item.getItemId()==R.id.mn_pro){
                replaceFragment(new ProFragment());
            }

            if (item.getItemId()==R.id.mn_cart){
                //replaceFragment(new CartFragment());
                //handleCartSelection();
                // Kiểm tra nếu giỏ hàng rỗng thì hiển thị thông báo và chuyển về trang ProFragment
                if (GioHangManager.getInstance().getGiohangList() == null || GioHangManager.getInstance().getGiohangList().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
                    //replaceFragment(new ProFragment());
                } else {
                    // Nếu có sản phẩm trong giỏ hàng thì chuyển đến trang CartFragment
                    handleCartSelection();
                }
            }
            if (item.getItemId()==R.id.mn_profile){
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
        handleIntent(getIntent());

    }
    private void handleCartSelection() {
        if (!(getSupportFragmentManager().findFragmentById(R.id.frameLayout) instanceof CartFragment)) {
            replaceFragment(new CartFragment());
        }
    }
    private void handleIntent(Intent intent) {
        if (intent != null) {
            String fragmentToLoad = intent.getStringExtra("fragmentToLoad");
            if ("cart".equals(fragmentToLoad)) {
                // Kiểm tra nếu giỏ hàng rỗng thì hiển thị thông báo và chuyển về trang ProFragment
                if (GioHangManager.getInstance().getGiohangList() == null || GioHangManager.getInstance().getGiohangList().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
                    //replaceFragment(new ProFragment());
                } else {
                    // Nếu có sản phẩm trong giỏ hàng thì chuyển đến trang CartFragment
                    handleCartSelection();
                    // Chọn item Cart trong bottomNavigationView
                    binding.bottomNavigationView.setSelectedItemId(R.id.mn_cart);
                }
            }
        }
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    public String getDatabasePath(){
        return  getApplicationInfo().dataDir+DB_SUFFIX_PATH+DATABASE_NAME;
    }
    private void proccessCopy() {
        try{
            File file = getDatabasePath(DATABASE_NAME);
            if(!file.exists()) {
                copyDatabaseFromAssest();
                //Toast.makeText(this, "Coppy Database Successful", Toast.LENGTH_SHORT).show();
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
    /*
    //viet ham tao menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnLogout){
            Intent i=new Intent(this, HomeloginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
     */
}