package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author thom145
 */
@Entity
@Table(name = "CONCRETEORDERITEM")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public class ConcreteOrderItem extends OrderItem{
    private static final long serialVersionUID = 1L;

    public ConcreteOrderItem(MenuItem menuItem, int quanity){
        super(menuItem, quanity);
    }
}
