package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public class Drink extends MenuItem {
    private static final long serialVersionUID = 1L;

    private Size size;

    public Drink(String id, String imageFileName, int price, Size size) {
        super(id, imageFileName, price);
        this.size = size;

    }

    public enum Size {
        SMALL, MEDIUM, LARGE
    }
}
