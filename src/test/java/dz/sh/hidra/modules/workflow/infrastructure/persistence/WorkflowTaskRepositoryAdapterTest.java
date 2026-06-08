/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Tests workflow task repository adapter.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.WorkflowTaskRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTaskJpaRepository;

/**
 * Tests workflow task repository adapter.
 */
class WorkflowTaskRepositoryAdapterTest {

    @Test
    void saveFindListAndVisibleTaskQueriesThroughJpaRepository() {
        WorkflowPersistenceMapper mapper = WorkflowPersistenceTestFixtures.mapper();
        WorkflowDefinition definition = WorkflowPersistenceTestFixtures.activeDefinition();
        WorkflowInstance instance = WorkflowPersistenceTestFixtures.startedInstance(definition);
        WorkflowTask task = WorkflowPersistenceTestFixtures.openTask(instance);
        WorkflowTaskJpaEntity entity = mapper.toEntity(task);

        WorkflowTaskJpaRepository repository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowTaskJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "save" -> arguments[0];
                    case "findById" -> Optional.of(entity);
                    case "findByInstanceId", "findByStatus", "findByAssignedActorId", "findByAssignedOrganizationUnitId",
                         "findByAssignedActorIdAndStatus", "findByAssignedOrganizationUnitIdAndStatus",
                         "findByInstanceIdAndStatus", "findAll" -> WorkflowPersistenceTestFixtures.pageOf(entity);
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowTaskRepositoryAdapter adapter = new WorkflowTaskRepositoryAdapter(repository, mapper);

        assertEquals(task.id(), adapter.save(task).id());
        assertTrue(adapter.findById(task.id()).isPresent());
        assertEquals(1, adapter.findAll(instance.id(), WorkflowTaskStatus.OPEN, null, null, PageRequest.of(0, 20)).items().size());
        assertEquals(1, adapter.findVisibleToActor(WorkflowPersistenceTestFixtures.actor(), null, null, PageRequest.of(0, 20)).items().size());
    }
}
