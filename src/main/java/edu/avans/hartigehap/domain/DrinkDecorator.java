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
public abstract class DrinkDecorator extends Drink {
    private static final long serialVersionUID = 1L;

    // unidirectional one-to-one and no cascading
    @OneToOne
    private Drink drink;

    private int price;

    public DrinkDecorator(Drink drink, String id, String imageFileName, int price, Size size) {
        super(id, imageFileName, price, size);
        this.drink = drink;
    }

    @Override
    public int getPrice() {
        return this.price + super.getPrice();
    }
}
