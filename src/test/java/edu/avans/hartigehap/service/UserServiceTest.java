package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.User;
import edu.avans.hartigehap.domain.UserRole;
import edu.avans.hartigehap.repository.UserRepository;
import edu.avans.hartigehap.repository.UserRoleRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author JKetelaar
 */
public class UserServiceTest extends AbstractTransactionRollbackTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Testing the findBy methods of both repositories
     */
    @Test
    public void testFindBy(){
        User user = userRepository.findByUsername("jketelaar");
        assertEquals(user.getEmail(), "jeroenketelaar@me.com");

        UserRole role = userRoleRepository.findByRole("ROLE_MANAGER");
        assertEquals(role.getUsers().size(), 1);
    }

    @Test
    public void creation(){
        User user = new User("jketelaar2", "auditt02", true);
        user = userRepository.save(user);

        List<UserRole> userRoleList = new ArrayList<>();

        UserRole userRole = new UserRole("ROLE_MANAGER_TEST");
        userRole = userRoleRepository.save(userRole);
        userRoleList.add(userRole);

        user.setRoles(userRoleList);

        assertEquals(user.getRoles().size(), 1);
        assertEquals(user.getRoles().get(0).getRole(), "ROLE_MANAGER_TEST");
    }
}
