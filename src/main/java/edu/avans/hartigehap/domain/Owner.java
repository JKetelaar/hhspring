package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

/**
 * @author thom145
 */
    //maakt dat de klasse gezien wordt als entity door JPA en zodoende gepersisteerd wordt.
    //ook child classes worden gepersisteerd
    @Entity
    //legt de tabelnaam vast. Default zou zijn de klassenaam, Owner dus.
    @Table(name ="OWNERS")
    //om problemen met circulaire afhankelijkheden te voorkomen bij JSON serialisatie
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    //generatie van getters/setters door Lombok
    @Getter
    @Setter
    //generatie van toString() door Lombok
    @ToString(callSuper=true, includeFieldNames=true, of= {"name"})
    public class Owner extends DomainObject{
    private static final long serialVersionUID = 1L;
    //als override van de default
    @Column(name = "Name")
    private String name;

    @ManyToMany(cascade=javax.persistence.CascadeType.ALL)
    private List<Restaurant> restaurants;
}
