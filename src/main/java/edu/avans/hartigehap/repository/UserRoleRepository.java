package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.User;
import edu.avans.hartigehap.domain.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author JKetelaar
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {
    List<UserRole> findRoleByName(String name);
}
