package model.repository;

import javax.persistence.*;

public class DatabaseManager {

    private static DatabaseManager instance;
    private EntityManagerFactory entityManagerFactory;

    private DatabaseManager() {
        // Configurar a inicialização do EntityManagerFactory aqui
        entityManagerFactory = Persistence.createEntityManagerFactory("BancoPU");
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void persist(Object object) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(object);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}