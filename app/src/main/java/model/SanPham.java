package model;

public class SanPham {
    private String maSP;
    private String tenSP;
    private int soLuong;

    public double getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    private double donGia;

    public SanPham(String maSP, String tenSP, int soLuong, double donGia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public double getThanhTien() {
        double thanhTien = soLuong * donGia;
        if (soLuong > 10) {
            thanhTien *= 0.9; // Giảm 10%
        }
        return thanhTien;
    }
}
