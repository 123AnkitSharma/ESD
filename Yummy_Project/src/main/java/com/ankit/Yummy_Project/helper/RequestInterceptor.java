package com.ankit.Yummy_Project.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {
    private final JWTHelper jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = authorizationHeader.substring(7); // Extract token from "Bearer {token}"
        String username = jwtUtil.extractUsername(token); // Email from token's `sub` claim

        if (username == null || !jwtUtil.validateToken(token, username)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // Ownership Validation
        String requestEmail = extractEmailFromRequest(request);
        if (requestEmail != null && !username.equals(requestEmail)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    private String extractEmailFromRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // Match `/api/v1/customer/{email}` for email extraction
        if (requestURI.matches(".*/api/v1/customer/[^/]+$")) {
            return requestURI.substring(requestURI.lastIndexOf("/") + 1);
        }

        // Additional logic for extracting email from the body (e.g., for POST or PUT requests)
        // Can be added here if required

        return null;
    }
}
