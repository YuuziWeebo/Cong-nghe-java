package vn.edu.eaut.lab9.model;
import jakarta.persistence.*;import java.util.*;
@Entity @Table(name="mon_hoc") public class MonHoc{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id; @Column(nullable=false,unique=true,length=20) private String maMon; @Column(nullable=false,length=100) private String tenMon; private Integer soTinChi;
 @OneToMany(mappedBy="monHoc") private List<Diem> dsDiem=new ArrayList<>();
 public MonHoc(){} public MonHoc(String ma,String ten,Integer tc){maMon=ma;tenMon=ten;soTinChi=tc;} public Integer getId(){return id;} public String getMaMon(){return maMon;} public void setMaMon(String v){maMon=v;} public String getTenMon(){return tenMon;} public void setTenMon(String v){tenMon=v;} public Integer getSoTinChi(){return soTinChi;} public void setSoTinChi(Integer v){soTinChi=v;} public List<Diem> getDsDiem(){return dsDiem;}
}
