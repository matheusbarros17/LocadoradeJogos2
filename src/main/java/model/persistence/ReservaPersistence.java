package model.persistence;

import model.Reserva;
import controller.DatabaseManager;
import model.repository.ReservaRepository;

import javax.persistence.EntityManager;

public class ReservaPersistence implements ReservaRepository {

    private DatabaseManager databaseManager;
    private static ReservaPersistence instance;

    private ReservaPersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static ReservaPersistence getInstance() {
        if (instance == null) {
            instance = new ReservaPersistence();
        }
        return instance;
    }

    @Override
    public void salvar(Reserva reserva) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(reserva, entityManager);

            databaseManager.commitTransaction(entityManager);
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}
