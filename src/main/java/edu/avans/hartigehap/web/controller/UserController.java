package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.Bill;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.UserRepository;
import edu.avans.hartigehap.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;

/**
 * @author JKetelaar
 */
@Controller
@PreAuthorize("isAuthenticated()")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public String editProfile( Model uiModel ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();

        return "hartigehap/profile";
    }

}
