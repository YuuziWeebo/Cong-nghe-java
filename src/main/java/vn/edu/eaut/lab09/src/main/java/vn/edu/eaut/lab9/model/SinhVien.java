package vn.edu.eaut.lab9.model;
import jakarta.persistence.*;import java.time.LocalDate;import java.util.*;
@Entity @Table(name="sinh_vien") public class SinhVien{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @Column(name="ma_sinh_vien",nullable=false,unique=true,length=20) private String maSinhVien;
 @Column(name="ho_ten",nullable=false,length=100) private String hoTen;
 @Column(length=100) private String email;
 @ManyToOne @JoinColumn(name="lop_hoc_id") private LopHoc lopHoc;
 @Column(name="ngay_sinh") private LocalDate ngaySinh;
 public SinhVien(){} public SinhVien(String ma,String ten,String email,LopHoc lop,LocalDate ns){maSinhVien=ma;hoTen=ten;this.email=email;lopHoc=lop;ngaySinh=ns;}
 public Integer getId(){return id;} public String getMaSinhVien(){return maSinhVien;} public void setMaSinhVien(String v){maSinhVien=v;} public String getHoTen(){return hoTen;} public void setHoTen(String v){hoTen=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public LopHoc getLopHoc(){return lopHoc;} public void setLopHoc(LopHoc v){lopHoc=v;} public LocalDate getNgaySinh(){return ngaySinh;} public void setNgaySinh(LocalDate v){ngaySinh=v;}
}
