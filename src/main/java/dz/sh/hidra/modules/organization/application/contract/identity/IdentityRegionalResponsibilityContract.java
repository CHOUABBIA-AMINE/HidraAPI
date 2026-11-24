/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRegionalResponsibilityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-24
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.identity
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.identity;

import java.time.Instant;
import java.util.List;

/** Organization-owned eligible scope facts; this contract never creates Identity permission. */
public interface IdentityRegionalResponsibilityContract {
    record Scope(String type, String targetId) {
        public Scope {
            if (type == null || type.isBlank() || targetId == null || targetId.isBlank())
                throw new IllegalArgumentException("Owner-typed scope required");
        }
    }
    List<Scope> eligibleScopes(String userId, String employeeId, List<Scope> ancestry, Instant at);

    /** Internal candidates only; Identity must independently establish company/resource permission. */
    record Candidate(long id, String type, String targetId) {
        public Candidate {
            if (id <= 0 || type == null || type.isBlank() || targetId == null || targetId.isBlank())
                throw new IllegalArgumentException("Registered physical scope required");
        }
    }

    /** A legacy implementation cannot grant discovery by omission. */
    default List<Candidate> currentCandidates(String userId, String employeeId, Instant at) {
        return List.of();
    }
}
