/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow catalog persistence.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import java.util.Optional;

/**
 * Outbound port for workflow catalog persistence.
 *
 * <p>Architecture role:
 * Outbound application port for workflow controlled vocabulary catalog storage.
 */
public interface WorkflowCatalogRepositoryPort {

    WorkflowTypeCatalog save(WorkflowTypeCatalog catalog);

    Optional<WorkflowTypeCatalog> findById(WorkflowCatalogId id);

    Optional<WorkflowTypeCatalog> findByCatalogNameAndCode(String catalogName, WorkflowCode code);

    boolean existsByCatalogNameAndCode(String catalogName, WorkflowCode code);

    PageResult<WorkflowTypeCatalog> findAll(
            String catalogName,
            Boolean active,
            String locale,
            PageRequest pageRequest);
}
