package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class JogoPlataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    @OneToMany(mappedBy = "locacao")
    private List<ItemLocacao> itensLocacao = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    private BigDecimal precoDiario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public BigDecimal getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(BigDecimal precoDiario) {
        this.precoDiario = precoDiario;
    }

    public List<ItemLocacao> getItensLocacao() {
        return itensLocacao;
    }

    public void setItensLocacao(List<ItemLocacao> itensLocacao) {
        this.itensLocacao = itensLocacao;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
