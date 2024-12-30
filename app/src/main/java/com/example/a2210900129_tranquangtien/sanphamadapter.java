package com.example.a2210900129_tranquangtien;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import model.SanPham;

public class sanphamadapter extends ArrayAdapter<SanPham> {
    private Context context;
    private List<SanPham> sanPhamList;

    public sanphamadapter(Context context, List<SanPham> sanPhamList) {
        super(context, 0, sanPhamList);
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    public sanphamadapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SanPham sanPham = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        }

        TextView tvMaSP = convertView.findViewById(R.id.tvMaSP);
        TextView tvTenSP = convertView.findViewById(R.id.tvTenSP);
        TextView tvThanhTien = convertView.findViewById(R.id.tvThanhTien);

        tvMaSP.setText(sanPham.getMaSP());
        tvTenSP.setText(sanPham.getTenSP());
        tvThanhTien.setText(String.valueOf(sanPham.getThanhTien()));

        return convertView;
    }

}
