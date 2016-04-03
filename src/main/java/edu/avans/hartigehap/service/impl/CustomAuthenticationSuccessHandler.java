package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.notifications.NotificationAdapterImpl;
import edu.avans.hartigehap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        User user = (User) authentication.getPrincipal();

        edu.avans.hartigehap.domain.User domainUser = userRepository.findByUsername(user.getUsername());
        try {
            new NotificationAdapterImpl(domainUser).send("Someone logged in from " + request.getRemoteUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}