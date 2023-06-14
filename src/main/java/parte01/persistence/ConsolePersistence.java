package parte01.persistence;

import parte01.model.Acessorio;
import parte01.model.Console;
import parte01.model.repository.ConsoleRepository;
import parte01.model.repository.DatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConsolePersistence implements ConsoleRepository {

    private DatabaseManager databaseManager;
    private static ConsolePersistence instance;

    private ConsolePersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static ConsolePersistence getInstance() {
        if (instance == null) {
            instance = new ConsolePersistence();
        }
        return instance;
    }

    @Override
    public List<Console> buscarInformacoesJogoPorNome(String nomeConsole) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            TypedQuery<Console> query = entityManager.createQuery(
                    "SELECT DISTINCT c FROM Console c JOIN FETCH c.acessorios WHERE c.nome = :nome", Console.class);
            query.setParameter("nome", nomeConsole);
            List<Console> consoles = query.getResultList();

            databaseManager.commitTransaction(entityManager);

            return consoles;
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public Console verificarDisponibilidadeConsoleAcessorio(Long idConsole, Long idAcessorio) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            // Verifica se o console com o ID fornecido existe
            Console console = entityManager.find(Console.class, idConsole);
            if (console == null) {
                return null; // Se o console não for encontrado, retorna null (indisponível)
            }

            // Verifica se o console possui o acessório com o ID fornecido
            List<Acessorio> acessorios = console.getAcessorios();
            boolean possuiAcessorio = false;
            for (Acessorio acessorio : acessorios) {
                if (acessorio.getId().equals(idAcessorio)) {
                    possuiAcessorio = true;
                    break;
                }
            }

            if (!possuiAcessorio) {
                return null; // Se o console não possui o acessório, retorna null (indisponível)
            }

            // Verifica se o console está alugado
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM UtilizacaoDoConsolePeloCliente u WHERE u.console.id = :consoleId");
            query.setParameter("consoleId", idConsole);
            long utilizacoes = (long) query.getSingleResult();

            if (utilizacoes > 0) {
                return null; // Se o console está alugado, retorna null (indisponível)
            }

            return console; // Retorna o objeto Console se estiver disponível
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}
