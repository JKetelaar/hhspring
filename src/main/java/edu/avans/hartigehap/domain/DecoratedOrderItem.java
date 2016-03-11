package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thom145
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public abstract class DecoratedOrderItem extends OrderItem {

    @OneToOne
    private OrderItem orderItem;

    public DecoratedOrderItem(OrderItem orderItem, MenuItem menuItem) {
        super(menuItem, menuItem.getPrice());
        this.orderItem = orderItem;
    }

    @Override
    @Transient
    public int getPrice() {
        return getMenuItem().getPrice() * getQuantity() + orderItem.getPrice();
    }

    @Override
    public String description() {
        return orderItem.description() + " + extra " + super.description();
    }
}
