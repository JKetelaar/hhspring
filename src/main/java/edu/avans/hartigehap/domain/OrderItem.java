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
 * @author Erco, JKetelaar
 */
@Entity
@Table(name = "ORDERITEMS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"menuComponent", "quantity"})
@NoArgsConstructor
public class OrderItem extends DomainObject {
    private static final long serialVersionUID = 1L;

    // unidirectional many-to-one; deliberately no cascade
    @ManyToOne
    private MenuComponent menuComponent;

    private int quantity = 0;

    public OrderItem(MenuComponent menuComponent, int quantity) {
        this.menuComponent = menuComponent;
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
        return menuComponent.getId() + " (" + quantity + "x)";
    }

    @Transient
    public int getPrice() {
        return menuComponent.getPrice() * quantity;
    }

    /**
     * Allowing compatibility with the code, for the previous #getMenuItem
     *
     * @return MenuComponent
     */
    @Deprecated
    @Transient
    public MenuComponent getMenuItem(){
        return menuComponent;
    }
}
