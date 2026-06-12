/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDataSourceBindingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Repository port for ReportDataSourceBinding.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.model.ReportDataSourceBinding;

import java.util.Optional;

/**
 * Repository port for ReportDataSourceBinding.
 */
public interface ReportDataSourceBindingRepositoryPort {

    ReportDataSourceBinding save(ReportDataSourceBinding model);

    Optional<ReportDataSourceBinding> findById(String id);
}
