/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringEvaluationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Repository port for MonitoringEvaluation.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringEvaluation;

import java.util.Optional;

/**
 * Repository port for MonitoringEvaluation.
 */
public interface MonitoringEvaluationRepositoryPort {

    MonitoringEvaluation save(MonitoringEvaluation model);

    Optional<MonitoringEvaluation> findById(String id);
}
