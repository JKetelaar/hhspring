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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Iterator;
import java.util.ArrayList;

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

    //Menu can have any number of children of type MenuComponent
    //I'm using an internal ArrayList to hold these
    private ArrayList<MenuComponent> menuComponents = new ArrayList<>();
    @Column(name = "ID")
    String id;
    @Column(name = "DESCRIPTION")
    String description;

    public PredefinedMenu(String id, String description){
        this.id = id;
        this.description = description;
    }

    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }

    public  MenuComponent getChild(int i){
        return (MenuComponent)menuComponents.get(i);
    }

    public boolean isVegetarian(){
        throw new UnsupportedOperationException();
    }

    public void print(int depth) {
        System.out.print("\n" + id);
        System.out.println(", " + description);
        System.out.println("---------------------");

        Iterator iterator = menuComponents.iterator();
        while(iterator.hasNext()){
            MenuComponent menuComponent = (MenuComponent)iterator.next();
            menuComponent.print(depth);
        }
    }
}