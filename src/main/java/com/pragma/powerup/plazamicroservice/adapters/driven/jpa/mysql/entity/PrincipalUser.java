package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PrincipalUser implements UserDetails {

    private final String id;
    private final String userName;
    private final Collection<? extends GrantedAuthority> authorities;

    public PrincipalUser(String id, String userName, Collection<? extends GrantedAuthority> authorities) {
        this.id=id;
        this.userName = userName;
        this.authorities = authorities;
    }

    public static PrincipalUser build(String id, String userName, Collection<? extends GrantedAuthority> authorities) {
        return new PrincipalUser(id, userName, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return null;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId(){
        return id;
    }


    @Override
    public String toString() {
        return "PrincipalUser{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
