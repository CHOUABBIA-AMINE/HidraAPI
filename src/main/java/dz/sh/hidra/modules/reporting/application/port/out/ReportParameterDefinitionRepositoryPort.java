/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterDefinitionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Repository port for ReportParameterDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.model.ReportParameterDefinition;

import java.util.Optional;

/**
 * Repository port for ReportParameterDefinition.
 */
public interface ReportParameterDefinitionRepositoryPort {

    ReportParameterDefinition save(ReportParameterDefinition model);

    Optional<ReportParameterDefinition> findById(String id);
}
