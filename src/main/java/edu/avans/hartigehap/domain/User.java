package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author JKetelaar
 */
@Entity
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"username", "password"})
@NoArgsConstructor
public class User extends DomainObject {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{validation.username.NotEmpty.message}")
    @Size(min = 5, max = 60, message = "{validation.username.Size.message}")
    private String username;

    @NotEmpty(message = "{validation.password.NotEmpty.message}")
    @Size(min = 5, max = 60, message = "{validation.password.Size.message}")
    private String password;

    private boolean enabled;

    @ManyToMany
    private List<UserRole> roles = new ArrayList<>();

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public void updateEditableFields(User customer) {
        username = customer.username;
        password = customer.password;
        enabled = customer.enabled;
    }

}
