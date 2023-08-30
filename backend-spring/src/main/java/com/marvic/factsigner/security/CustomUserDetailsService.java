package com.marvic.factsigner.security;

import com.marvic.factsigner.model.sistema.Usuario;
import com.marvic.factsigner.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository repository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
          Usuario usuario = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with usuarioname or email: "+ usernameOrEmail));

        List<String> roles = Collections.emptyList();
        if (isNotEmpty(usuario.getRoles())) {
            roles = Arrays.asList(usuario.getRoles().split(","));
        }

        Set<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
                usuario.getPassword(),
                authorities);
    }

}
