/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseClosureGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.service
 *
 * @Description : Guards HSE case closure rules.
 *
 */
package dz.sh.hidra.modules.hse.domain.service;

import dz.sh.hidra.modules.hse.domain.exception.HseBoundaryViolationException;
import dz.sh.hidra.modules.hse.domain.model.HseCase;

/**
 * Guards HSE case closure rules.
 */
public class HseCaseClosureGuard {

    public void ensureCanClose(HseCase hseCase, boolean impactAssessed, boolean capaCompleted, boolean evidenceReviewed) {
        if (hseCase == null) {
            throw new HseBoundaryViolationException("HSE case must not be null.");
        }
        if (hseCase.closedLifecycle()) {
            throw new HseBoundaryViolationException("Closed HSE cases cannot be closed again.");
        }
        if (!impactAssessed || !capaCompleted || !evidenceReviewed) {
            throw new HseBoundaryViolationException("HSE closure requires impact assessment, CAPA completion, and evidence review.");
        }
    }
}
