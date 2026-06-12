/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentImpactAssessmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentImpactAssessment.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentImpactAssessment;

import java.util.Optional;

/**
 * Repository port for IncidentImpactAssessment.
 */
public interface IncidentImpactAssessmentRepositoryPort {

    IncidentImpactAssessment save(IncidentImpactAssessment model);

    Optional<IncidentImpactAssessment> findById(String id);
}
