/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalResourceAccessPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import java.util.List;
import java.util.Objects;

/** Pure fail-closed conjunction of current Identity and Organization evidence over Topology lineage. */
public final class RegionalResourceAccessPolicy {
    public enum Grant { PERMIT, EXPLICIT_DENY, INDETERMINATE, NO_GRANT }
    public record Candidate(Grant grant, boolean qualifyingResponsibility) {
        public Candidate { Objects.requireNonNull(grant, "grant"); }
    }
    public boolean permits(boolean qualifiedOwner, List<Candidate> ancestry) {
        if (!qualifiedOwner || ancestry == null || ancestry.isEmpty()) return false;
        boolean admitted = false;
        for (Candidate candidate : ancestry) {
            if (candidate == null || candidate.grant() == Grant.EXPLICIT_DENY
                    || candidate.grant() == Grant.INDETERMINATE) return false;
            admitted |= candidate.grant() == Grant.PERMIT && candidate.qualifyingResponsibility();
        }
        return admitted;
    }
}
