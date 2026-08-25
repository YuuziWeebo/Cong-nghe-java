package vn.edu.eaut.lab9.repository;
import jakarta.persistence.*;import vn.edu.eaut.lab9.config.JPAUtil;import vn.edu.eaut.lab9.model.*;import java.util.*;
public class SinhVienRepository extends BaseRepository<SinhVien,Integer>{protected Class<SinhVien> type(){return SinhVien.class;}
 public List<SinhVien> search(String kw,int page){EntityManager e=JPAUtil.getEntityManagerFactory().createEntityManager();try{return e.createQuery("select s from SinhVien s left join fetch s.lopHoc where lower(s.hoTen) like :kw or lower(s.maSinhVien) like :kw or lower(coalesce(s.lopHoc.tenLop,'')) like :kw order by s.id desc",SinhVien.class).setParameter("kw","%"+kw.toLowerCase()+"%").setFirstResult(page*5).setMaxResults(5).getResultList();}finally{e.close();}}
 public long count(String kw){EntityManager e=JPAUtil.getEntityManagerFactory().createEntityManager();try{return e.createQuery("select count(s) from SinhVien s left join s.lopHoc where lower(s.hoTen) like :kw or lower(s.maSinhVien) like :kw or lower(coalesce(s.lopHoc.tenLop,'')) like :kw",Long.class).setParameter("kw","%"+kw.toLowerCase()+"%").getSingleResult();}finally{e.close();}}
}
