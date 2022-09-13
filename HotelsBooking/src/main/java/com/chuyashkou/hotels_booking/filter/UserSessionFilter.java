package com.chuyashkou.hotels_booking.filter;

import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserSessionFilter extends GenericFilterBean {

    private static final String USER = "user";
    private static final String ANONYMOUS_USER = "anonymousUser";

    private final UserService userService;

    public UserSessionFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();

        Optional<User> user = Optional.empty();
        SecurityContext context = SecurityContextHolder.getContext();
        Principal principal = context.getAuthentication();

        if (Objects.nonNull(principal) && !principal.getName().equals(ANONYMOUS_USER) && session.getAttribute(USER) == null) {
            user = userService.findByEmail(principal.getName());
        }
        user.ifPresent(u -> {
            u.setPassword(null);
            session.setAttribute(USER, u);
        });
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
