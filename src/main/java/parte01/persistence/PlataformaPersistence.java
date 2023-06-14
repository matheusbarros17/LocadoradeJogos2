package parte01.persistence;

import parte01.model.Plataforma;
import parte01.model.repository.DatabaseManager;
import parte01.model.repository.PlataformaRepository;

import javax.persistence.EntityManager;

public class PlataformaPersistence implements PlataformaRepository {

    private DatabaseManager databaseManager;
    private static PlataformaPersistence instance;

    private PlataformaPersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static PlataformaPersistence getInstance() {
        if (instance == null) {
            instance = new PlataformaPersistence();
        }
        return instance;
    }

    @Override
    public void salvar(Plataforma plataforma) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(plataforma, entityManager);

            databaseManager.commitTransaction(entityManager);
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}
