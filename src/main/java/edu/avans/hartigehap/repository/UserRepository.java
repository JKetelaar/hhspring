package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.User;
import edu.avans.hartigehap.domain.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author JKetelaar
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findByUsername(String username);

    List<User> findByUsername(String username, boolean includeEnabled);

    List<User> findByUserRole(UserRole userRole);

    List<User> findByUserRole(UserRole userRole, boolean includeEnabled);
}
