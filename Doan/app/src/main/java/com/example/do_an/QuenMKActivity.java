package com.example.do_an;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuenMKActivity extends AppCompatActivity {

EditText edtNewmk, edtPrenewmk,txtEmailmk;
Button btnSavenewmk;
Intent intent;
SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mkactivity);
        addControls();
        btnSavenewmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = SQLiteDatabase.openDatabase(getDatabasePath("DBMeoMeo").getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
                String email = txtEmailmk.getText().toString();
                String pass = edtNewmk.getText().toString();
                String repass= edtPrenewmk.getText().toString();
                String q = "SELECT COUNT(*) FROM tbCustomer WHERE Email = ?";
                Cursor cursor = database.rawQuery(q, new String[]{email});
                cursor.moveToFirst();
                int count = cursor.getInt(0);

                cursor.close();

                if (count > 0) {
                    if (pass.isEmpty()|| repass.isEmpty()){
                        Toast.makeText(QuenMKActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    if(pass.equals(repass)){
                        ContentValues values=new ContentValues();
                        values.put("Password",pass);
                        long kq= database.update("tbCustomer",values,"Email=?",new String[]{email});
                        if(kq==1){
                            Toast.makeText(QuenMKActivity.this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(QuenMKActivity.this,"Đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(QuenMKActivity.this, "Email không ồn tại", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void addControls() {
        txtEmailmk=findViewById(R.id.txtemailmk);
        edtNewmk=findViewById(R.id.edtnewmk);
        edtPrenewmk=findViewById(R.id.edtprenewmk);
        btnSavenewmk=findViewById(R.id.btnsavenewmk);
    }
}