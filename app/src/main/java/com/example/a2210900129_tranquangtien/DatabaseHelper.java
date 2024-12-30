package com.example.a2210900129_tranquangtien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.SanPham;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLSanPham.db";
    private static final String TABLE_NAME = "SanPham";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Gọi constructor của SQLiteOpenHelper
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng nếu chưa tồn tại
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "maSP TEXT PRIMARY KEY, " +
                "tenSP TEXT, " +
                "soLuong INTEGER, " +
                "donGia REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ và tạo lại bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Thêm sản phẩm
    public void addProduct(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("maSP", sanPham.getMaSP());
        values.put("tenSP", sanPham.getTenSP());
        values.put("soLuong", sanPham.getSoLuong());
        values.put("donGia", sanPham.getDonGia());
        db.insert(TABLE_NAME, null, values);
        db.close(); // Đóng cơ sở dữ liệu
    }

    public void updateProduct(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("tenSP", sanPham.getTenSP());
        values.put("soLuong", sanPham.getSoLuong());
        values.put("donGia", sanPham.getDonGia());
        db.update(TABLE_NAME, values, "maSP = ?", new String[]{sanPham.getMaSP()});
        db.close(); // Đóng cơ sở dữ liệu
    }

    public Cursor getProductById(String maSP) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE maSP = ?", new String[]{maSP});
    }

    // Xóa sản phẩm
    public void deleteProduct(String maSP) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu
        db.delete(TABLE_NAME, "maSP = ?", new String[]{maSP});
        db.close(); // Đóng cơ sở dữ liệu
    }
}