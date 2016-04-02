package edu.avans.hartigehap.domain;

/**
 * @author thom145
 */
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "MENUCOMPONENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
/*
MenuComponent represents the interface for
both MenuItem and PredifinedMenu. This method is abstract because
we want to provide default implementations for these methods
*/
public abstract class MenuComponent extends DomainObjectNaturalId {
    private static final long serialVersionUID = 1L;

    public void add(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public abstract void print(int depth);
}