package com.example.a2210900129_tranquangtien;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.SanPham;

public class DetailActivity extends AppCompatActivity {
    private EditText edtMaSP, edtTenSP, edtSoLuong, edtDonGia;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private String maSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        edtMaSP = findViewById(R.id.edtMaSP);
        edtTenSP = findViewById(R.id.edtTenSP);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtDonGia = findViewById(R.id.edtDonGia);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        // Nhận dữ liệu từ Intent
        maSP = getIntent().getStringExtra("maSP");
        if (maSP != null) {
            loadProductDetails(maSP); // Nạp thông tin sản phẩm
        }

        btnSave.setOnClickListener(v -> {
            saveProduct(); // Lưu sản phẩm
        });
    }

    private void loadProductDetails(String maSP) {
        Cursor cursor = dbHelper.getProductById(maSP);
        if (cursor.moveToFirst()) {
            int maSPIndex = cursor.getColumnIndex("maSP");
            int tenSPIndex = cursor.getColumnIndex("tenSP");
            int soLuongIndex = cursor.getColumnIndex("soLuong");
            int donGiaIndex = cursor.getColumnIndex("donGia");

            // Kiểm tra chỉ số cột
            if (maSPIndex != -1) {
                edtMaSP.setText(cursor.getString(maSPIndex));
            }
            if (tenSPIndex != -1) {
                edtTenSP.setText(cursor.getString(tenSPIndex));
            }
            if (soLuongIndex != -1) {
                edtSoLuong.setText(cursor.getString(soLuongIndex));
            }
            if (donGiaIndex != -1) {
                edtDonGia.setText(cursor.getString(donGiaIndex));
            }
        } else {
            Toast.makeText(this, "Sản phẩm không tồn tại!", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void saveProduct() {
        String maSP = edtMaSP.getText().toString();
        String tenSP = edtTenSP.getText().toString();
        int soLuong = Integer.parseInt(edtSoLuong.getText().toString());
        double donGia = Double.parseDouble(edtDonGia.getText().toString());

        SanPham sanPham = new SanPham(maSP, tenSP, soLuong, donGia);

        if (this.maSP == null) {
            // Thêm sản phẩm mới
            dbHelper.addProduct(sanPham);
            Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật sản phẩm
            dbHelper.updateProduct(sanPham);
            Toast.makeText(this, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        }

        finish(); // Quay về màn hình chính
    }
}