package parte01.model;

import parte01.model.repository.CalculadoraPrecoLocacao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ItemLocacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jogoplataforma_id")
    private JogoPlataforma jogoPlataforma;

    @ManyToOne
    @JoinColumn(name = "locacao_id")
    private Locacao locacao;

    private Integer dias;
    private Integer quantidade;
    private BigDecimal valorLocacaoJogo;

    @Transient
    private CalculadoraPrecoLocacao calculadoraPrecoLocacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JogoPlataforma getJogoPlataforma() {
        return jogoPlataforma;
    }

    public void setJogoPlataforma(JogoPlataforma jogoPlataforma) {
        this.jogoPlataforma = jogoPlataforma;
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorLocacaoJogo() {
        return valorLocacaoJogo;
    }

    public void setValorLocacaoJogo(BigDecimal valorLocacaoJogo) {
        this.valorLocacaoJogo = valorLocacaoJogo;
    }

    public void setCalculadoraPrecoLocacao(CalculadoraPrecoLocacao calculadoraPrecoLocacao) {
        this.calculadoraPrecoLocacao = calculadoraPrecoLocacao;
    }

    public BigDecimal calcularPrecoLocacao(JogoPlataforma jogoPlataforma, int dias) {
        if (calculadoraPrecoLocacao != null) {
            return calculadoraPrecoLocacao.calcularPrecoLocacao(jogoPlataforma, dias);
        } else {
            throw new IllegalStateException("A estratégia de cálculo de preço não foi configurada.");
        }
    }
}
