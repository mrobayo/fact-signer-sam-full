package com.marvic.factsigner.util;

import com.marvic.factsigner.security.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security Helper
 */
public final class SecurityHelper {

    public static CustomUser getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return (CustomUser) auth.getPrincipal();
        }
        return null;
    }

    public static boolean isPermitted(CustomUser user, String empresaId) {
        if (user != null) {
            return user.getAuthorities().stream().anyMatch((authority -> {
                return authority.getAuthority().contains(empresaId);
            }));
        }
        return false;
    }

    public static boolean isNotPermitted(CustomUser user, String empresaId) {
        return !isPermitted(user, empresaId);
    }
}
