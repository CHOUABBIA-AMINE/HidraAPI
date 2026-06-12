/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAlertCandidateRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Repository port for MonitoringAlertCandidate.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringAlertCandidate;

import java.util.Optional;

/**
 * Repository port for MonitoringAlertCandidate.
 */
public interface MonitoringAlertCandidateRepositoryPort {

    MonitoringAlertCandidate save(MonitoringAlertCandidate model);

    Optional<MonitoringAlertCandidate> findById(String id);
}
