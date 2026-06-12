/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HazardReportRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for HazardReport.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.HazardReport;

import java.util.Optional;

/**
 * Repository port for HazardReport.
 */
public interface HazardReportRepositoryPort {

    HazardReport save(HazardReport model);

    Optional<HazardReport> findById(String id);
}
