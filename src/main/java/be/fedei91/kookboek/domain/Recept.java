package be.fedei91.kookboek.domain;

import javax.persistence.*;

@Entity
@Table(name = "recepten")
public class Recept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ingredienten;
    private String beschrijving;

    public Recept(String ingredienten, String beschrijving) {
        this.ingredienten = ingredienten;
        this.beschrijving = beschrijving;
    }

    protected Recept() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredienten() {
        return ingredienten;
    }

    public void setIngredienten(String ingredienten) {
        this.ingredienten = ingredienten;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
}
