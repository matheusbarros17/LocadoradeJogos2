package parte01.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @OneToMany(mappedBy = "jogo")
    private List<JogoPlataforma> jogoPlataformas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<JogoPlataforma> getJogoPlataformas() {
        return jogoPlataformas;
    }

    public void setJogoPlataformas(List<JogoPlataforma> jogoPlataformas) {
        this.jogoPlataformas = jogoPlataformas;
    }

}
