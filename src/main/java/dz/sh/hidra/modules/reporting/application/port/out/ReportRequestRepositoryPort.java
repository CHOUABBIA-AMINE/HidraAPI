/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequestRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Repository port for ReportRequest.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.model.ReportRequest;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for ReportRequest.
 */
public interface ReportRequestRepositoryPort {

    ReportRequest save(ReportRequest model);

    Optional<ReportRequest> findById(String id);

    List<AccessPolicyView> accessPoliciesForDefinition(String reportDefinitionId);

    record AccessPolicyView(
            String scopeType,
            String scopeReferenceId,
            String permissionCode
    ) { }
}
