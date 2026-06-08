/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Tests workflow definition repository adapter.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.WorkflowDefinitionRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowDefinitionJpaRepository;

/**
 * Tests workflow definition repository adapter.
 */
class WorkflowDefinitionRepositoryAdapterTest {

    @Test
    void saveFindExistsAndListDefinitionsThroughJpaRepository() {
        WorkflowPersistenceMapper mapper = WorkflowPersistenceTestFixtures.mapper();
        WorkflowDefinition definition = WorkflowPersistenceTestFixtures.activeDefinition();
        WorkflowDefinitionJpaEntity entity = mapper.toEntity(definition);

        WorkflowDefinitionJpaRepository repository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowDefinitionJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "save" -> arguments[0];
                    case "findById" -> Optional.of(entity);
                    case "findByCode" -> Optional.of(entity);
                    case "existsByCode" -> true;
                    case "findByStatus", "findByTypeId", "findByTypeIdAndStatus", "findByCodeContainingIgnoreCase", "findAll" ->
                            WorkflowPersistenceTestFixtures.pageOf(entity);
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowDefinitionRepositoryAdapter adapter = new WorkflowDefinitionRepositoryAdapter(repository, mapper);

        assertEquals("TELEMETRY_READING_VALIDATION", adapter.save(definition).code().value());
        assertTrue(adapter.findById(definition.id()).isPresent());
        assertTrue(adapter.findByCode(WorkflowCode.of("TELEMETRY_READING_VALIDATION")).isPresent());
        assertTrue(adapter.existsByCode(WorkflowCode.of("TELEMETRY_READING_VALIDATION")));
        assertEquals(1, adapter.findAll(null, null, WorkflowDefinitionStatus.ACTIVE, PageRequest.of(0, 20)).items().size());
    }
}
