package com.marvic.factsigner.security;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.payload.sistema.PuntoVentaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class CustomUser extends User {

    private String usuarioId;
    private String email;
    private String puntoVentaId;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
