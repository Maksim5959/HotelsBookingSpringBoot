package com.chuyashkou.hotels_booking.filter;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebFilter(filterName = "PostAuthenticationFilter", urlPatterns = {"/login", "/registration"})
public class PostAuthenticationFilter implements Filter {

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        SecurityContext context = SecurityContextHolder.getContext();
        Principal principal = context.getAuthentication();
        if (!principal.getName().equals(ANONYMOUS_USER)) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");
        }
        chain.doFilter(request, response);
    }
}
