package model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class JogoPlataformaId implements Serializable {

    @Column(name = "jogo_id")
    private Long jogoId;

    @Column(name = "plataforma_id")
    private Long plataformaId;

    public Long getJogoId() {
        return jogoId;
    }

    public void setJogoId(Long jogoId) {
        this.jogoId = jogoId;
    }

    public Long getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(Long plataformaId) {
        this.plataformaId = plataformaId;
    }
}
