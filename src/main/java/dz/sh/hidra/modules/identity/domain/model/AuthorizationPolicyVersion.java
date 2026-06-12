/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyVersion
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Versions ABAC policy definitions.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Versions ABAC policy definitions.
 *
     * @param id id
 * @param policyId policyId
 * @param versionNumber versionNumber
 * @param status status
 * @param effectiveFrom effectiveFrom
 * @param effectiveTo effectiveTo
 * @param approvedByWorkflowId approvedByWorkflowId
 * @param createdAt createdAt
 * @param activatedAt activatedAt
 */
public record AuthorizationPolicyVersion(
        String id,
    String policyId,
    int versionNumber,
    PolicyVersionStatus status,
    Instant effectiveFrom,
    Instant effectiveTo,
    String approvedByWorkflowId,
    Instant createdAt,
    Instant activatedAt
) {

    public AuthorizationPolicyVersion {
    id = normalize(id);
    policyId = normalize(policyId);
    approvedByWorkflowId = normalize(approvedByWorkflowId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
