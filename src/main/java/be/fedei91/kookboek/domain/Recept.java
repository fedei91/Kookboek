package be.fedei91.kookboek.domain;

import javax.persistence.*;

@Entity
@Table(name = "recepten")
public class Recept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naam;
    private String ingredienten;
    private String instructies;

    public Recept(String naam, String ingredienten, String instructies) {
        this.naam = naam;
        this.ingredienten = ingredienten;
        this.instructies = instructies;
    }

    protected Recept() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() { return naam; }

    public void setNaam(String naam) { this.naam = naam; }

    public String getIngredienten() {
        return ingredienten;
    }

    public void setIngredienten(String ingredienten) {
        this.ingredienten = ingredienten;
    }

    public String getInstructies() {
        return instructies;
    }

    public void setInstructies(String instructies) {
        this.instructies = instructies;
    }
}
