package com.ankit.Yummy_Project.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {
    private final JWTHelper jwtUtil; // Utility for extracting and validating JWT tokens

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. Skip authentication for public endpoints (e.g., POST /api/v1/customer for registration)
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/v1/customer") && request.getMethod().equals("POST")) {
            return true; // Allow public access
        }

        // 2. Check if the request has a valid Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // If no token or invalid token is provided, return 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 3. Extract the token from the header
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        String username = jwtUtil.extractUsername(token); // Extract email (or username) from the token

        // 4. Validate the token and the extracted username
        if (username == null || !jwtUtil.validateToken(token, username)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Token is invalid
            return false;
        }

        // 5. Ownership Validation: Ensure the token's email matches the request's email
        String requestEmail = extractEmailFromRequest(request); // Extract email from the request
        if (requestEmail != null && !username.equals(requestEmail)) {
            // If the token email doesn't match the request email, return 403 Forbidden
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // 6. All checks passed, allow the request
        return true;
    }

    /**
     * Extracts the email from the request URI for endpoints like /api/v1/customer/{email}
     */
    private String extractEmailFromRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // Match DELETE endpoint: /api/v1/customer/delete/{email}
        if (requestURI.matches(".*/api/v1/customer/delete/[^/]+$")) {
            return requestURI.substring(requestURI.lastIndexOf("/") + 1);
        }

        // Match PUT endpoint: /api/v1/customer/update/{email}
        if (requestURI.matches(".*/api/v1/customer/update/[^/]+$") && request.getMethod().equals("PUT")) {
            return requestURI.substring(requestURI.lastIndexOf("/") + 1);
        }

        // Match GET endpoint: /api/v1/customer/{email}
        if (requestURI.matches(".*/api/v1/customer/[^/]+$")) {
            return requestURI.substring(requestURI.lastIndexOf("/") + 1);
        }

        return null; // No email found in the URI
    }
}
