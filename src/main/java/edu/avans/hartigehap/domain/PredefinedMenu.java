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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Entity
@Table(name = "MENUCOMPOSITE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor

/*
Menu overrides the methods that make sense like a way
to add and remove menu items from its menuComponents
getName() and getDescription methods to return the
name and the description of the menu
*/
public class PredefinedMenu extends MenuComponent {
    private static final long serialVersionUID = 1L;

    @Column(name = "DESCRIPTION")
    String description;

    @ManyToMany
    private Collection<Restaurant> restaurants = new ArrayList<>();

    public PredefinedMenu(String id, String description) {
        super.setId(id);
        this.description = description;
    }

    // Should be important to read other committed changes
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }

    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }

    public boolean isVegetarian() {
        throw new UnsupportedOperationException();
    }

    public void print(int depth) {
        System.out.print("\n" + super.getId());
        System.out.println(", " + description);
        System.out.println("---------------------");

        Iterator iterator = menuComponents.iterator();
        while (iterator.hasNext()) {
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            menuComponent.print(depth);
        }
    }
}