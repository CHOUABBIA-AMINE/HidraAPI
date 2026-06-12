/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportReproducibilityGuard
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.service
 *
 * @Description : Guards report reproducibility and boundary safety.
 *
 */
package dz.sh.hidra.modules.reporting.domain.service;

import dz.sh.hidra.modules.reporting.domain.exception.ReportingBoundaryViolationException;
import dz.sh.hidra.modules.reporting.domain.policy.ReportingBoundaryPolicy;

/**
 * Guards report reproducibility and boundary safety.
 */
public class ReportReproducibilityGuard {

    public void ensureReadOnlySourceOperation(String operationName) {
        if (ReportingBoundaryPolicy.isForbiddenOperationalMutation(operationName)) {
            throw new ReportingBoundaryViolationException("Reporting must not modify source-of-truth operational state.");
        }
    }

    public void ensureNoSecretMaterial(String value) {
        if (ReportingBoundaryPolicy.containsSecretMaterial(value)) {
            throw new ReportingBoundaryViolationException("Reporting definitions, templates, and parameters must not embed secrets.");
        }
    }

    public void ensureNoForeignInternals(String typeName) {
        if (ReportingBoundaryPolicy.isForbiddenForeignInternalImport(typeName)) {
            throw new ReportingBoundaryViolationException("Reporting must use ports, DTOs, projections, and stable references instead of foreign internals.");
        }
    }
}
