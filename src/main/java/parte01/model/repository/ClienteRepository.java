package parte01.model.repository;

import parte01.model.Cliente;

public interface ClienteRepository {
    public void salvar (Cliente cliente);
    public Cliente obterClientePorEmail(String email);
}
