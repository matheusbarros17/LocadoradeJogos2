package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    @OneToMany(mappedBy = "reserva")
    private List<JogoPlataforma> jogoPlataformas = new ArrayList<>();

    private LocalDate dataReserva;

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

    public List<JogoPlataforma> getJogoPlataformas() {
        return jogoPlataformas;
    }

    public void setJogoPlataformas(List<JogoPlataforma> jogoPlataformas) {
        this.jogoPlataformas = jogoPlataformas;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }
}
