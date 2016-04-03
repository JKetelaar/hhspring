package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author Thom145
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public class Condiment extends MenuItemDecorator {
    private static final long serialVersionUID = 1L;

    public Condiment(Drink drink, String id, String imageFileName, int price) {
        super(drink, id, imageFileName, price);
    }

    @Override
    public int getPrice() {
        return super.getExtraPrice() + getMenuItem().getPrice();
    }
}