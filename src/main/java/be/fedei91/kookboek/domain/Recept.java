package be.fedei91.kookboek.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "recepten")

/**
 * Voeg deze regels toe:
 *      @XmlRootElement
 *      @@XmlAccessorType(XmlAccessType.FIELD
 * en geef mee aan in de GET request dat je een XML header accept:
 *      Accept: application/xml
*/

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

    protected Recept() {
    }

    public Recept withId(long id) {
        var receptMetId = new Recept(naam, ingredienten, instructies);
        receptMetId.id = id;
        return receptMetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

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
