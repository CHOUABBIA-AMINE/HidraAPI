/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for ReportingLine and its Organization-owned type catalog.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import java.util.Optional;

/**
 * Repository port for ReportingLine.
 */
public interface ReportingLineRepositoryPort {

    ReportingLine save(ReportingLine model);

    Optional<ReportingLine> findById(String id);

    Optional<ReportingLineType> findTypeByCode(String code);
}
