package vn.edu.eaut.lab10.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPAUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("lab10PU");

    private JPAUtil() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }

    public static void shutdown() {
        if (EMF.isOpen()) EMF.close();
    }
}
