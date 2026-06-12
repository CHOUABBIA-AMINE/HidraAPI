/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSensitiveDataGuard
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.service
 *
 * @Description : Guards audit sensitive data masking policy.
 *
 */
package dz.sh.hidra.modules.audit.domain.service;

import dz.sh.hidra.modules.audit.domain.exception.AuditBoundaryViolationException;
import dz.sh.hidra.modules.audit.domain.policy.AuditBoundaryPolicy;

/**
 * Guards audit sensitive data masking policy.
 */
public class AuditSensitiveDataGuard {

    public void ensureSensitiveValueIsMasked(String fieldPath, boolean masked) {
        if (AuditBoundaryPolicy.isSensitiveFieldPath(fieldPath) && !masked) {
            throw new AuditBoundaryViolationException("Sensitive audit value must be masked, hashed, redacted, or reference-only.");
        }
    }
}
