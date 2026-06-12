/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportScheduleRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Repository port for ReportSchedule.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.model.ReportSchedule;

import java.util.Optional;

/**
 * Repository port for ReportSchedule.
 */
public interface ReportScheduleRepositoryPort {

    ReportSchedule save(ReportSchedule model);

    Optional<ReportSchedule> findById(String id);
}
