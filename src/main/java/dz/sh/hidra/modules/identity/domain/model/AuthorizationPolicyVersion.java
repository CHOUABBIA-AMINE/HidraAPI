/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("AuthorizationPolicyVersion id must not be blank.");
        }
        // HRA-051 required: policyId
        if (policyId == null || policyId.isBlank()) {
            throw new InvalidIdentityValueException("AuthorizationPolicyVersion policy id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("AuthorizationPolicyVersion status must not be null.");
        }
        // HRA-051 required: effectiveFrom
        if (effectiveFrom == null) {
            throw new InvalidIdentityValueException("AuthorizationPolicyVersion effective from must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidIdentityValueException("AuthorizationPolicyVersion effective to must not be before effective from.");
        }

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
