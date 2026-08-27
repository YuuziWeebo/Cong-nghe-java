package vn.edu.eaut.lab10.repository;

import jakarta.persistence.*;
import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.User;
import java.util.List;

public class UserRepository {
    public User findByEmail(String email) {
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email=:email",User.class)
                    .setParameter("email",email).getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }
    public List<User> findAll() {
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        try { return em.createQuery("SELECT u FROM User u ORDER BY u.id DESC",User.class).getResultList(); }
        finally { em.close(); }
    }
    public void save(User u) {
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx=em.getTransaction();
        try { tx.begin(); em.persist(u); tx.commit(); } catch(Exception e){if(tx.isActive())tx.rollback();throw e;} finally{em.close();}
    }
    public void update(User u) {
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx=em.getTransaction();
        try { tx.begin(); em.merge(u); tx.commit(); } catch(Exception e){if(tx.isActive())tx.rollback();throw e;} finally{em.close();}
    }
    public User find(int id) {
        EntityManager em=JPAUtil.getEntityManagerFactory().createEntityManager();
        try{return em.find(User.class,id);}finally{em.close();}
    }
}
