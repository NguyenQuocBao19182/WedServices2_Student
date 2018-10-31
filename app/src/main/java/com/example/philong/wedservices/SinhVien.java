package com.example.philong.wedservices;

import java.io.Serializable;

public class SinhVien implements Serializable {
    private int ID;
    private String hoTen;
    private int namSinh;
    private String diaChi;

    public SinhVien(int ID, String hoTen, int namSinh, String diaChi) {
        this.ID = ID;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.diaChi = diaChi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
