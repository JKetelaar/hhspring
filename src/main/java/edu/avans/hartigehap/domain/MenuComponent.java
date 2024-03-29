package edu.avans.hartigehap.domain;

/**
 * @author thom145, JKetelaar
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "MENUCOMPONENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor

/**
 * MenuComponent represents the interface for
 * both MenuItem and PredefinedMenu. This method is abstract because
 * we want to provide default implementations for these methods
 */
public abstract class MenuComponent extends DomainObjectNaturalId {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "parent", targetEntity = MenuComponent.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<MenuComponent> menuComponents = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MenuComponent parent;

    private int price;

    public void add(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    public abstract void print(int depth);

    // Imagine putting this in the database...
    @Transient()
    public boolean isRoot() {
        return (parent == null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void setParent(MenuComponent parent) {
        this.parent = parent;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}