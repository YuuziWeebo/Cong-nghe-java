package vn.edu.eaut.lab10.repository;

import jakarta.persistence.*;
import vn.edu.eaut.lab10.config.JPAUtil;
import java.util.List;

public class GenericRepository<T> {
    private final Class<T> type;
    public GenericRepository(Class<T> type){this.type=type;}
    public List<T> findAll(){
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        try{return em.createQuery("SELECT x FROM "+type.getSimpleName()+" x ORDER BY x.id DESC",type).getResultList();}
        finally{em.close();}
    }
    public T find(int id){
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        try{return em.find(type,id);}finally{em.close();}
    }
    public void save(T obj){
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager(); EntityTransaction tx=em.getTransaction();
        try{tx.begin();em.persist(obj);tx.commit();}catch(Exception e){if(tx.isActive())tx.rollback();throw e;}finally{em.close();}
    }
    public void update(T obj){
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager(); EntityTransaction tx=em.getTransaction();
        try{tx.begin();em.merge(obj);tx.commit();}catch(Exception e){if(tx.isActive())tx.rollback();throw e;}finally{em.close();}
    }
    public void delete(int id){
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager(); EntityTransaction tx=em.getTransaction();
        try{tx.begin();T obj=em.find(type,id);if(obj!=null)em.remove(obj);tx.commit();}catch(Exception e){if(tx.isActive())tx.rollback();throw e;}finally{em.close();}
    }
}
