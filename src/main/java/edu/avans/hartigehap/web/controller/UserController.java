package edu.avans.hartigehap.web.controller;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.NotificationAdapter;
import edu.avans.hartigehap.domain.User;
import edu.avans.hartigehap.repository.UserRepository;
import edu.avans.hartigehap.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.util.Locale;

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

    /**
     * Retrieves the page to edit your profile
     *
     * @param uiModel
     *
     * @return Path of the edit page
     */
    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public String editProfile(Model uiModel) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        if (user == null) {
            return "errors/404";
        }

        uiModel.addAttribute("user", user);
        uiModel.addAttribute("types", NotificationAdapter.Type.values());

        return "hartigehap/profile";
    }

    /**
     * Allows you to actually edit your profile
     *
     * @param newUser
     *
     * @return Path to the result page
     */
    @RequestMapping(value = "/profile/edit", method = RequestMethod.PUT)
    public String editProfile(@RequestBody User newUser) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        log.info("Updating user: " + user.getUsername());

        user.updateEditableFields(newUser);
        userRepository.save(user);

        return "redirect:/profile/edit";
    }
}
