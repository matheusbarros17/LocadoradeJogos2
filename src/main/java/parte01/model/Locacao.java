package parte01.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "locacao")
    private List<ItemLocacao> itensLocacao = new ArrayList<>();

    private LocalDate dataLocacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public List<ItemLocacao> getItensLocacao() {
        return itensLocacao;
    }

    public void setItensLocacao(List<ItemLocacao> itensLocacao) {
        this.itensLocacao = itensLocacao;
    }

    public void addItemLocacao(ItemLocacao itemLocacao) {
        itensLocacao.add(itemLocacao);
        itemLocacao.setLocacao(this);
    }
}
