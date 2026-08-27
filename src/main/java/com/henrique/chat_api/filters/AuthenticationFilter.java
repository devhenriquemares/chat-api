package com.henrique.chat_api.filters;

import com.henrique.chat_api.configurations.UserDetailsConfig;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.exceptions.EmailAlreadyExistsException;
import com.henrique.chat_api.exceptions.EmailNotVerifiedException;
import com.henrique.chat_api.services.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsConfig userDetailsConfig;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String tokenPrefix = "Bearer ";

            if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String token = authHeader.substring(tokenPrefix.length());
            final String email = jwtService.extractAllClaims(token).getSubject();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null && email != null) {
                UserAccount user = userDetailsConfig.loadUserByUsername(email);
                Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

                if (user.isVerified()) {
                    authorities.add(new SimpleGrantedAuthority("EMAIL_VERIFIED"));
                }

                if (jwtService.isValid(token, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (JwtException | EmailNotVerifiedException | IllegalArgumentException ex) {
            log.info("Authentication error", ex);
            String message = ex instanceof EmailNotVerifiedException ? ex.getMessage() : "Invalid JWT token";
            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(message);
        }
    }
}
