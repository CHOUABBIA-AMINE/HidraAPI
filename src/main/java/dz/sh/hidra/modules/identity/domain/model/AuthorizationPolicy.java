/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents an ABAC policy container.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Represents an ABAC policy container.
 *
     * @param id id
 * @param code code
 * @param name name
 * @param description description
 * @param policyDomain policyDomain
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record AuthorizationPolicy(
        String id,
    String code,
    String name,
    String description,
    String policyDomain,
    PolicyStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public AuthorizationPolicy {
    id = normalize(id);
    code = normalize(code);
    name = normalize(name);
    description = normalize(description);
    policyDomain = normalize(policyDomain);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
