package com.pragma.powerup.plazamicroservice.configuration.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@CommonsLog
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    private final List<String> excludedPrefixes = Arrays.asList("/swagger-ui/**", "/actuator/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(req);
        log.info("doFilterInternal: token -> " + token);
        if (token != null && jwtProvider.validateToken(token)) {

            UserDetails userDetails = jwtProvider.loadPrincipalUser(token);
            log.info("doFilterInternal: userName -> " + userDetails.getUsername());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filterChain.doFilter(req, res);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // return everything after "Bearer "
        }
        return null;
    }


}
