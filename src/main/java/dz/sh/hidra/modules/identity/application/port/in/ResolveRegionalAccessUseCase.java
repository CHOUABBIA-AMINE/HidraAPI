/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolveRegionalAccessUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-24
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import java.util.Objects;

/** Read-only, current-request resource decision. No caller-supplied actor is accepted. */
public interface ResolveRegionalAccessUseCase {
    enum ResourceKind { PIPELINE_SYSTEM, PIPELINE, PIPELINE_SEGMENT, FACILITY, EQUIPMENT }
    record Query(ResourceKind resourceKind, String resourceId, String companyPartyId) {
        public Query {
            Objects.requireNonNull(resourceKind, "resourceKind");
            resourceId = exact(resourceId);
            companyPartyId = exact(companyPartyId);
        }
        private static String exact(String input) {
            if (input == null || input.isBlank() || !input.equals(input.trim())
                    || input.length() > 80 || input.indexOf('\u0000') >= 0)
                throw new IllegalArgumentException("Canonical resource reference required");
            return input;
        }
    }
    boolean permitted(Query query);

    /** Minimal owner-qualified registry coordinates, not an authorization grant. */
    record ScopeView(long id, ResourceKind type, String targetId) { }

    /** Resolve only the current authenticated employee for the requested company. */
    default java.util.List<ScopeView> currentScopes(String companyPartyId) {
        return java.util.List.of();
    }
}
