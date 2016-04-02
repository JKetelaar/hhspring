package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Thom145
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter @Setter
@NoArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
public abstract class DecoratedDrink extends Drink {
    private static final long serialVersionUID = 1L;

    // unidirectional one-to-one and no cascading
    @OneToOne
    private Drink drink;

    public DecoratedDrink(Drink drink, String id, String imageFileName, int price, Size size) {
        super(id, imageFileName, price, size);
        this.drink = drink;
    }
}
