/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportPublicationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Repository port for ReportPublication.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.model.ReportPublication;

import java.util.Optional;

/**
 * Repository port for ReportPublication.
 */
public interface ReportPublicationRepositoryPort {

    ReportPublication save(ReportPublication model);

    Optional<ReportPublication> findById(String id);
}
