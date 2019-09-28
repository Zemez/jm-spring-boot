package com.javamentor.jm_spring_boot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException, ServletException {
        logger.debug("AccessDeniedException: {}", ex.toString());
        logger.debug("Request URI: {}", request.getRequestURI());
        logger.debug("{}: {}", HttpHeaders.CONTENT_TYPE, request.getHeader(HttpHeaders.CONTENT_TYPE));
        logger.debug("Headers: {}", Collections.list(request.getHeaderNames()).stream()
                .map(x -> x + ": " + request.getHeader(x))
                .collect(Collectors.joining(", ")));
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (request.getRequestURI().contains("/api/") || (contentType != null && contentType.contains("application/json"))) {
            response.setContentType("application/json");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } else if (request.getHeader("Referer") == null) {
            request.getSession().setAttribute("error", ex.getMessage());
            response.sendRedirect("/");
        } else {
            request.getSession().setAttribute("error", ex.getMessage());
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

}
