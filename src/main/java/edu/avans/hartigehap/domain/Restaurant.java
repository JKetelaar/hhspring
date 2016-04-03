package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

/**
 * @author Erco
 */
@Entity
@Table(name = "RESTAURANTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"menu", "diningTables", "customers"})
@NoArgsConstructor
public class Restaurant extends DomainObjectNaturalId {
    private static final long serialVersionUID = 1L;

    private String imageFileName;

    // unidirectional one-to-one
    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu = new Menu();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Collection<DiningTable> diningTables = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Collection<PredefinedMenu> predefinedMenus = new ArrayList<>();

    // no cascading
    @ManyToMany(mappedBy = "restaurants")
    private Collection<Customer> customers = new ArrayList<>();
    @ManyToMany(mappedBy = "restaurants")
    private List<Owner> owners;

    public Restaurant(String name, String imageFileName) {
        super(name);
        this.imageFileName = imageFileName;
    }

    // business methods
    public void warmup() {
        Iterator<DiningTable> diningTableIterator = diningTables.iterator();
        while (diningTableIterator.hasNext()) {
            diningTableIterator.next().getId();
        }

        Iterator<MenuItem> mealsIterator = menu.getMeals().iterator();
        while (mealsIterator.hasNext()) {
            MenuItem mi = mealsIterator.next();
            mi.getId();
            Iterator<FoodCategory> fcIterator = mi.getFoodCategories().iterator();
            while (fcIterator.hasNext()) {
                fcIterator.next().getId();
            }
        }

        Iterator<MenuItem> drinksIterator = menu.getDrinks().iterator();
        while (drinksIterator.hasNext()) {
            MenuItem mi = drinksIterator.next();
            mi.getId();
            Iterator<FoodCategory> fcIterator = mi.getFoodCategories().iterator();
            while (fcIterator.hasNext()) {
                fcIterator.next().getId();
            }
        }

        Iterator<FoodCategory> foodCategoryIterator = menu.getFoodCategories().iterator();
        while (foodCategoryIterator.hasNext()) {
            FoodCategory fc = foodCategoryIterator.next();
            Iterator<MenuItem> miIterator = fc.getMenuItems().iterator();
            while (miIterator.hasNext()) {
                miIterator.next().getId();
            }
        }

    }
}
