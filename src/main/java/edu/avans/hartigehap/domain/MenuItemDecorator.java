package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Thom145
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public abstract class MenuItemDecorator extends MenuItem {
    private static final long serialVersionUID = 1L;

    // unidirectional one-to-one and no cascading
    @OneToOne
    private MenuItem menuItem;

    private int extraPrice;

    public MenuItemDecorator(MenuItem drink, String id, String imageFileName, int price) {
        super(id, imageFileName, drink.getPrice());
        this.menuItem = drink;
        this.extraPrice = price;
    }

    @Override
    public abstract int getPrice();
}
