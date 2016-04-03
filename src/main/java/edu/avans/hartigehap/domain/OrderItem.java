package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Erco
 */
@Entity
@Table(name = "ORDERITEMS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"menuItem", "quantity"})
@NoArgsConstructor
public class OrderItem extends DomainObject {
    private static final long serialVersionUID = 1L;

    // unidirectional many-to-one; deliberately no cascade
    @ManyToOne
    private MenuItem menuItem;

    private int quantity = 0;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        assert quantity > 0 : "quantity cannot be below 0";
        this.quantity--;
    }

    public String description() {
        return menuItem.getId() + " (" + quantity + "x)";
    }

    @Transient
    public int getPrice() {
        return menuItem.getPrice() * quantity;
    }
}
