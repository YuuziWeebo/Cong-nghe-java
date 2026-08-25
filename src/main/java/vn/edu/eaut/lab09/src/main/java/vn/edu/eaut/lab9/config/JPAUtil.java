package vn.edu.eaut.lab9.config;
import jakarta.persistence.*;
public final class JPAUtil {
 private static final EntityManagerFactory emf=Persistence.createEntityManagerFactory("lab09PU");
 private JPAUtil(){}
 public static EntityManagerFactory getEntityManagerFactory(){return emf;}
 public static void close(){if(emf.isOpen()) emf.close();}
}
