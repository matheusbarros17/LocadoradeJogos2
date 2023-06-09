package persistence;

import model.Cliente;
import model.repository.DatabaseManager;

import javax.persistence.EntityManager;

public class ClientePersistence implements model.repository.ClienteRepository {

    private DatabaseManager databaseManager;
    private static ClientePersistence instance;

    private ClientePersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static ClientePersistence getInstance() {
        if (instance == null) {
            instance = new ClientePersistence();
        }
        return instance;
    }

    @Override
    public void salvar(Cliente cliente) {
        databaseManager.persist(cliente);
    }
}
