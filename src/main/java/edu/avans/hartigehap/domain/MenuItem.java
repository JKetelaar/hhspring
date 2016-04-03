package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Erco, JKetelaar
 */
@Entity
@Table(name = "MENUITEMS")
// images are stored in a separate database table (optional)
@SecondaryTable(name = "MENUITEM_IMAGES", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public abstract class MenuItem extends MenuComponent {
    private static final long serialVersionUID = 1L;

    // image stored in the database
    @Column(name = "IMAGE", table = "MENUITEM_IMAGES")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    // filename of image stored in the database
    @Column(name = "IMAGEFILENAME")
    private String imageFileName;

    // no cascade
    @ManyToMany
    private Collection<FoodCategory> foodCategories = new ArrayList<FoodCategory>();

    public MenuItem(String id, String imageFileName, int price) {
        super.setId(id);
        super.setPrice(price);
        this.imageFileName = imageFileName;
    }

    public void addFoodCategories(Collection<FoodCategory> foodCategories) {
        setFoodCategories(foodCategories);
        for (FoodCategory foodCategory : foodCategories) {
            foodCategory.getMenuItems().add(this);
        }
    }

    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }

    public void print(int depth) {
        System.out.println(getId());
        System.out.println("---------------------");

        Iterator iterator = menuComponents.iterator();
        while (iterator.hasNext()) {
            for (int i = 0; i < depth; i++) {
                System.out.print("\t");
            }
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            menuComponent.print(depth + 1);
        }
    }
}
