/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound port for workflow definition persistence.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;
import java.util.Optional;

/**
 * Outbound port for workflow definition persistence.
 *
 * <p>Architecture role:
 * Outbound application port for workflow definition storage. Infrastructure adapters implement this interface. Application and domain layers must not depend on JPA, SQL, REST, Spring Data, or external module implementation classes.
 */
public interface WorkflowDefinitionRepositoryPort {

    WorkflowDefinition save(WorkflowDefinition definition);

    Optional<WorkflowDefinition> findById(WorkflowDefinitionId id);

    Optional<WorkflowDefinition> findByCode(WorkflowCode code);

    boolean existsByCode(WorkflowCode code);

    PageResult<WorkflowDefinition> findAll(
            String searchTerm,
            WorkflowTypeReference type,
            WorkflowDefinitionStatus status,
            PageRequest pageRequest);
}
