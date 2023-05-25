package com.pragma.powerup.plazamicroservice.configuration.security.jwt;


import com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity.PrincipalUser;
import com.pragma.powerup.plazamicroservice.domain.spi.IJwtProviderConfigurationPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Component
@CommonsLog
public class JwtProvider implements IJwtProviderConfigurationPort {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;


    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public String getRoleFromToken(String token){

        List<String> role = (List<String> )Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("roles");
        return role.get(0);

    }

    public String getIdFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().get("id").toString();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("token mal formado");
        } catch (UnsupportedJwtException e) {
            log.error("token no soportado");
        } catch (ExpiredJwtException e) {
            log.error("token expirado");
        } catch (IllegalArgumentException e) {
            log.error("token vacío");
        } catch (SignatureException e) {
            log.error("fail en la firma");
        }
        return false;
    }

    public UserDetails loadPrincipalUser(String token){

        String id = getIdFromToken(token);
        String userName = getUserNameFromToken(token);

        return PrincipalUser.build(id, userName, getAuthorities(token));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {

        String role = getRoleFromToken(token);
        SimpleGrantedAuthority simpleAuthorities = new SimpleGrantedAuthority(role);

        return Collections.singletonList(simpleAuthorities);
    }


}
