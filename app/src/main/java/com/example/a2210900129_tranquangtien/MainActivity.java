package com.example.a2210900129_tranquangtien;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import model.SanPham;

public class MainActivity extends AppCompatActivity {
    String dbName = "QLSanpham.db";
    String dbPath = "/databases/";
    SQLiteDatabase db = null;
    ListView lvcontact;


    sanphamadapter adapter;
    ListView listViewSanPham;
    ArrayList<SanPham> sanPhamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        copyDataFromAsset();

        listViewSanPham = findViewById(R.id.listViewSanPham);
        sanPhamList = new ArrayList<>();
        adapter = new sanphamadapter(this, sanPhamList);
        listViewSanPham.setAdapter(adapter);

        hienthicontact();
        addview();
        
    }

    private void addview() {
        lvcontact = findViewById(R.id.listViewSanPham);
        adapter= new sanphamadapter(MainActivity.this, android.R.layout.simple_list_item_1);
        lvcontact.setAdapter(adapter);
    }

    private void hienthicontact() {
        db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham", null);
        while (cursor.moveToNext()) {
            String MaSp = cursor.getString(0);
            String TenSp = cursor.getString(1);
            int SoLuong = cursor.getInt(2);
            double DonGia = cursor.getDouble(3);
            sanPhamList.add(new SanPham(MaSp, TenSp, SoLuong, DonGia));
        }
        cursor.close();
        adapter.notifyDataSetChanged();

    }
    private void copyDataFromAsset() {
        try {
            InputStream myInput = getAssets().open(dbName);
            String outFileName = getApplicationInfo().dataDir + dbPath + dbName;
            File f = new File(getApplicationInfo().dataDir + dbPath);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            Log.e("LOI", ex.toString());
        }
    }
}