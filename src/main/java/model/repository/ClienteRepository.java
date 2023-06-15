package model.repository;

import model.Cliente;

public interface ClienteRepository {
    public void salvar (Cliente cliente);
    public Cliente obterClientePorEmail(String email);
}
