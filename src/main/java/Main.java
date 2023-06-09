import model.Cliente;
import model.repository.ClienteRepository;
import persistence.ClientePersistence;

public class Main {

    public static void main(String[] args) {
        // Obter instância do repositório de clientes
        ClienteRepository clienteRepository = ClientePersistence.getInstance();

        // Criar um novo cliente
        Cliente cliente = new Cliente();
        cliente.setNome("João da Silva");
        cliente.setEmail("joao@example.com");
        cliente.setTelefone("123456789");
        cliente.setSenha("123456");

        // Salvar o cliente no repositório
        clienteRepository.salvar(cliente);

    }
}
