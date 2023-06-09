package model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class JogoPlataforma {

    @EmbeddedId
    private JogoPlataformaId jogoPlataformaId;

    private BigDecimal precoDiario;

    @ManyToOne
    @JoinColumn(name = "jogo_id", insertable = false, updatable = false)
    private Jogo jogo;

    @ManyToOne
    @JoinColumn(name = "plataforma_id", insertable = false, updatable = false)
    private Plataforma plataforma;

    public JogoPlataformaId getJogoPlataformaId() {
        return jogoPlataformaId;
    }

    public void setJogoPlataformaId(JogoPlataformaId jogoPlataformaId) {
        this.jogoPlataformaId = jogoPlataformaId;
    }

    public BigDecimal getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(BigDecimal precoDiario) {
        this.precoDiario = precoDiario;
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
}
