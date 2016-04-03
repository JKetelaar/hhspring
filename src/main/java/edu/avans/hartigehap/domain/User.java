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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private NotificationAdapter.Type type;

    @NotEmpty(message = "{validation.password.NotEmpty.message}")
    @Size(min = 5, max = 60, message = "{validation.password.Size.message}")
    private String password;

    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> properties = new HashMap<>();

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

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getPhone() {
        return this.properties.get("phone");
    }

    public void setPhone(String phone) {
        this.properties.put("phine", phone);
    }

    public String getEmail() {
        return this.properties.get("email");
    }

    public void setEmail(String email) {
        this.properties.put("email", email);
    }
}
