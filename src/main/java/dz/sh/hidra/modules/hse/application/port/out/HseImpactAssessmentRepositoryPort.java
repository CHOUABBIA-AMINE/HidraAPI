/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseImpactAssessmentRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for HseImpactAssessment.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.HseImpactAssessment;

import java.util.Optional;

/**
 * Repository port for HseImpactAssessment.
 */
public interface HseImpactAssessmentRepositoryPort {

    HseImpactAssessment save(HseImpactAssessment model);

    Optional<HseImpactAssessment> findById(String id);
}
