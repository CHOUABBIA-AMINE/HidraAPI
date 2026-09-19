/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraEffectivePermissionResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Resolves the effective permission codes of the current authenticated principal.
 *
 */
package dz.sh.hidra.platform.security;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Combines token authorities with principal-specific permission sources.
 */
@Component
public final class HidraEffectivePermissionResolver {

    public static final String ALL_PERMISSIONS = "*";
    private static final String ADMIN_AUTHORITY = "ROLE_HIDRA_ADMIN";
    private static final String SCOPE_PREFIX = "SCOPE_";
    private static final String PERMISSION_PREFIX = "PERMISSION_";

    private final List<HidraEffectivePermissionSource> permissionSources;

    public HidraEffectivePermissionResolver(List<HidraEffectivePermissionSource> permissionSources) {
        this.permissionSources = List.copyOf(Objects.requireNonNull(permissionSources, "Permission sources must not be null."));
    }

    public Set<String> resolve(Authentication authentication) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Set.of();
        }

        boolean administratorAuthority = authentication.getAuthorities().stream()
                .anyMatch(authority -> ADMIN_AUTHORITY.equals(normalize(authority.getAuthority())));
        LinkedHashSet<String> permissions = new LinkedHashSet<>();
        boolean currentAdministratorGrant = false;
        authentication.getAuthorities().forEach(authority -> addAuthority(permissions, authority.getAuthority()));
        // Never trust a wildcard from a JWT scope or authority claim on its own.
        permissions.remove(ALL_PERMISSIONS);

        String principalName = normalize(authentication.getName());
        if (principalName != null) {
            for (HidraEffectivePermissionSource source : permissionSources) {
                Set<String> sourcePermissions = source.resolve(principalName);
                if (sourcePermissions != null) {
                    currentAdministratorGrant |= sourcePermissions.contains(ALL_PERMISSIONS);
                    sourcePermissions.stream()
                            .map(HidraEffectivePermissionResolver::normalize)
                            .filter(Objects::nonNull)
                            .filter(permission -> !ALL_PERMISSIONS.equals(permission))
                            .forEach(permissions::add);
                }
            }
        }

        // A signed but stale ROLE_HIDRA_ADMIN is insufficient: Identity must confirm
        // a currently active, global administrator grant on every protected request.
        if (administratorAuthority && currentAdministratorGrant) {
            permissions.add(ALL_PERMISSIONS);
        }
        return Set.copyOf(permissions);
    }

    public boolean hasPermission(Authentication authentication, String permissionCode) {
        String normalized = normalize(permissionCode);
        if (normalized == null) {
            return false;
        }
        Set<String> permissions = resolve(authentication);
        return permissions.contains(ALL_PERMISSIONS) || permissions.contains(normalized);
    }

    private static void addAuthority(Set<String> permissions, String authority) {
        String normalized = normalize(authority);
        if (normalized == null) {
            return;
        }
        if (ADMIN_AUTHORITY.equals(normalized)) {
            return;
        }
        if (normalized.startsWith(SCOPE_PREFIX) && normalized.length() > SCOPE_PREFIX.length()) {
            permissions.add(normalized.substring(SCOPE_PREFIX.length()));
            return;
        }
        if (normalized.startsWith(PERMISSION_PREFIX) && normalized.length() > PERMISSION_PREFIX.length()) {
            permissions.add(normalized.substring(PERMISSION_PREFIX.length()));
            return;
        }
        if (normalized.indexOf(':') > 0) {
            permissions.add(normalized);
        }
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
