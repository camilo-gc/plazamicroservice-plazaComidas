package com.pragma.powerup.plazamicroservice.configuration.security;

import com.pragma.powerup.plazamicroservice.configuration.security.jwt.JwtEntryPoint;
import com.pragma.powerup.plazamicroservice.configuration.security.jwt.JwtTokenFilter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@CommonsLog
public class MainSecurity {


    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests(requests -> requests
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/dish/restaurant/{idRestaurant}/category/{idCategory}").permitAll()
                        .requestMatchers("/restaurant/new").hasRole("ADMIN")
                        .requestMatchers("/restaurant/{id}").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers("/dish/{id}", "/restaurant/new/employee", "/dish/{id}/active").hasRole("OWNER")
                        .requestMatchers("/restaurant").hasRole("CLIENT")
                        .requestMatchers("/order").hasRole("CLIENT")
                        .requestMatchers("/order/assign", "/order/ready/{id}", "/order/deliver/{id}").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
