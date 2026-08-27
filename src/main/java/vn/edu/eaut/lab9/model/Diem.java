package vn.edu.eaut.lab9.model;
import jakarta.persistence.*;
@Entity @Table(name="diem") public class Diem{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @ManyToOne(optional=false) @JoinColumn(name="sinh_vien_id") private SinhVien sinhVien;
 @ManyToOne(optional=false) @JoinColumn(name="mon_hoc_id") private MonHoc monHoc;
 private Double diemChuyenCan,diemGiuaKy,diemCuoiKy;
 public Diem(){} public Diem(SinhVien sv,MonHoc mh,double cc,double gk,double ck){sinhVien=sv;monHoc=mh;diemChuyenCan=cc;diemGiuaKy=gk;diemCuoiKy=ck;}
 public Integer getId(){return id;} public SinhVien getSinhVien(){return sinhVien;} public void setSinhVien(SinhVien v){sinhVien=v;} public MonHoc getMonHoc(){return monHoc;} public void setMonHoc(MonHoc v){monHoc=v;} public Double getDiemChuyenCan(){return diemChuyenCan;} public void setDiemChuyenCan(Double v){diemChuyenCan=v;} public Double getDiemGiuaKy(){return diemGiuaKy;} public void setDiemGiuaKy(Double v){diemGiuaKy=v;} public Double getDiemCuoiKy(){return diemCuoiKy;} public void setDiemCuoiKy(Double v){diemCuoiKy=v;} public double getTongKet(){return Math.round((diemChuyenCan*.1+diemGiuaKy*.3+diemCuoiKy*.6)*100)/100.0;} public String getXepLoai(){double d=getTongKet();return d>=8.5?"A":d>=7?"B":d>=5.5?"C":d>=4?"D":"F";}
}
