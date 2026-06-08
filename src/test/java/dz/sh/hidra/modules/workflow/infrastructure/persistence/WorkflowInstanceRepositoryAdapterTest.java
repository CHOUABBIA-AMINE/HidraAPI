/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Tests workflow instance repository adapter.
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
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.WorkflowInstanceRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowActionJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowInstanceJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTaskJpaRepository;

/**
 * Tests workflow instance repository adapter.
 */
class WorkflowInstanceRepositoryAdapterTest {

    @Test
    void saveFindOpenByTargetAndListInstancesThroughJpaRepositories() {
        WorkflowPersistenceMapper mapper = WorkflowPersistenceTestFixtures.mapper();
        WorkflowDefinition definition = WorkflowPersistenceTestFixtures.activeDefinition();
        WorkflowInstance instance = WorkflowPersistenceTestFixtures.startedInstance(definition);
        WorkflowTask task = WorkflowPersistenceTestFixtures.openTask(instance);
        WorkflowInstanceJpaEntity instanceEntity = mapper.toEntity(instance);
        WorkflowTaskJpaEntity taskEntity = mapper.toEntity(task);

        WorkflowInstanceJpaRepository instanceRepository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowInstanceJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "save" -> arguments[0];
                    case "findById" -> Optional.of(instanceEntity);
                    case "findFirstByTargetModuleAndTargetTypeIdAndTargetIdAndStatusNotIn" -> Optional.of(instanceEntity);
                    case "findByDefinitionId", "findByStatus", "findByStartedByActorId", "findByTargetModuleAndTargetTypeIdAndTargetId", "findAll" ->
                            WorkflowPersistenceTestFixtures.pageOf(instanceEntity);
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowTaskJpaRepository taskRepository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowTaskJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "findByInstanceId" -> WorkflowPersistenceTestFixtures.pageOf(taskEntity);
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowActionJpaRepository actionRepository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowActionJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "save" -> arguments[0];
                    case "findByInstanceIdOrderByActedAtAsc" -> WorkflowPersistenceTestFixtures.emptyActionEntities();
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowInstanceRepositoryAdapter adapter = new WorkflowInstanceRepositoryAdapter(
                instanceRepository,
                taskRepository,
                actionRepository,
                mapper);

        assertEquals(instance.id(), adapter.save(instance).id());
        assertTrue(adapter.findById(instance.id()).isPresent());
        assertTrue(adapter.findOpenByTarget(instance.target()).isPresent());
        assertEquals(1, adapter.findAll(definition.id(), null, WorkflowInstanceStatus.STARTED, null, PageRequest.of(0, 20)).items().size());
    }
}
