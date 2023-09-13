package com.marvic.factsigner.security;

import com.marvic.factsigner.model.sistema.Usuario;
import com.marvic.factsigner.repository.UsuarioRepository;
import com.marvic.factsigner.util.Utils;
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

    private final UsuarioRepository repository;

    public CustomUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        String username = Utils.coalesce(usernameOrEmail, "").toUpperCase();
        Usuario usuario = repository.findByUsernameOrEmail(username, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with usuarioname or email: "+ usernameOrEmail));

        // Add roles
        List<String> roles = Collections.emptyList();
        if (isNotEmpty(usuario.getRoles())) {
            roles = Arrays.asList(usuario.getRoles().split(","));
        }
        Set<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        // Add empresas
        List<String> empresas = Collections.emptyList();
        if (isNotEmpty(usuario.getEmpresas())) {
            empresas = Arrays.asList(usuario.getEmpresas().split(","));
        }
        empresas.forEach((empresa) -> {
            authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", empresa)));
        });

        CustomUser user = new CustomUser(
                usuario.getId(),
                usuario.getPassword(),
                authorities);

        user.setUsuarioId(usuario.getId());
        user.setEmail(usuario.getEmail());
        // user.setPuntos(puntos.toArray(new String[0]));
//        return new org.springframework.security.core.userdetails.User(
//                usuario.getEmail(),
//                usuario.getPassword(),
//                authorities);
        return user;
    }

}
