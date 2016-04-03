package edu.avans.hartigehap.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author JKetelaar
 */
@Entity
@Table(name = "USER_ROLES")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"name"})
@NoArgsConstructor
public class UserRole extends DomainObject {

    private static final long serialVersionUID = 1L;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new ArrayList<User>();
    @NotEmpty(message = "{validation.user.role.NotEmpty.message}")
    @Size(min = 5, max = 60, message = "{validation.user.role.Size.message}")
    private String role;

    public UserRole(String role) {
        this.role = role;
    }
}
