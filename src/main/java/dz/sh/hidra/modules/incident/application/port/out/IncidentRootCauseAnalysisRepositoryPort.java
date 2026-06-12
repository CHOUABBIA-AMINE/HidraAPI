/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRootCauseAnalysisRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentRootCauseAnalysis.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentRootCauseAnalysis;

import java.util.Optional;

/**
 * Repository port for IncidentRootCauseAnalysis.
 */
public interface IncidentRootCauseAnalysisRepositoryPort {

    IncidentRootCauseAnalysis save(IncidentRootCauseAnalysis model);

    Optional<IncidentRootCauseAnalysis> findById(String id);
}
