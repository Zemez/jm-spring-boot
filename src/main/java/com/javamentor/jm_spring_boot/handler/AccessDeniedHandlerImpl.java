package com.javamentor.jm_spring_boot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException, ServletException {
        logger.debug("AccessDeniedException: {}", ex.getMessage());
        request.getSession().setAttribute("error", ex.getMessage());
        if (request.getRequestURI().contains("/api/") || request.getHeader("Content-Type").contains("application/json")) {
            response.setContentType("application/json");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else if (request.getHeader("Referer") == null) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

}
