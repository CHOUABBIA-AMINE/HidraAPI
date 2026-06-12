/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationScope
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Represents an identity authorization scope using neutral references.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Authorization scope stored as neutral references only.
 *
 * @param scopeType scope type
 * @param scopeReferenceId stable external scope reference
 * @param scopeCodeSnapshot optional code snapshot
 */
public record AuthorizationScope(
        ScopeType scopeType,
        String scopeReferenceId,
        String scopeCodeSnapshot
) {

    public AuthorizationScope {
        scopeReferenceId = normalize(scopeReferenceId);
        scopeCodeSnapshot = normalize(scopeCodeSnapshot);
        if (scopeType == null) {
            scopeType = ScopeType.GLOBAL;
        }
        if (scopeType == ScopeType.GLOBAL) {
            scopeReferenceId = null;
            scopeCodeSnapshot = null;
        }
    }

    public static AuthorizationScope global() {
        return new AuthorizationScope(ScopeType.GLOBAL, null, null);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
