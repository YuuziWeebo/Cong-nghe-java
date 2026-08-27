package vn.edu.eaut.lab9.model;
import jakarta.persistence.*;import java.util.*;
@Entity @Table(name="lop_hoc") public class LopHoc{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @Column(nullable=false,unique=true,length=50) private String tenLop;
 @OneToMany(mappedBy="lopHoc") private List<SinhVien> sinhViens=new ArrayList<>();
 public LopHoc(){} public LopHoc(String tenLop){this.tenLop=tenLop;} public Integer getId(){return id;} public String getTenLop(){return tenLop;} public void setTenLop(String v){tenLop=v;} public List<SinhVien> getSinhViens(){return sinhViens;}
}
