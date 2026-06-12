/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAcknowledgementRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Repository port for MonitoringAcknowledgement.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringAcknowledgement;

import java.util.Optional;

/**
 * Repository port for MonitoringAcknowledgement.
 */
public interface MonitoringAcknowledgementRepositoryPort {

    MonitoringAcknowledgement save(MonitoringAcknowledgement model);

    Optional<MonitoringAcknowledgement> findById(String id);
}
