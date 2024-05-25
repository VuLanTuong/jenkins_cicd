package com.example.authservice.authen;

import com.example.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Getter@Setter @AllArgsConstructor @NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long userId;
    private String username;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private Set<Role> roles = new HashSet<>();
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setRoles(Set<String> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
        }
        this.authorities = authorities;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            System.out.println("role" + role);
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toUpperCase()));
        }
        return authorities;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", roles=" + roles +
                '}';
    }
}
