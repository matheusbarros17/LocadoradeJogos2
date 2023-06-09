package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Plataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "plataforma")
    private List<JogoPlataforma> jogoPlataformas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<JogoPlataforma> getJogoPlataformas() {
        return jogoPlataformas;
    }

    public void setJogoPlataformas(List<JogoPlataforma> jogoPlataformas) {
        this.jogoPlataformas = jogoPlataformas;
    }
}

