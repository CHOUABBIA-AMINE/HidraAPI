/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Tests workflow persistence mapper conversions.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeCatalogJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;

/**
 * Tests workflow persistence mapper conversions.
 */
class WorkflowPersistenceMapperTest {

    private final WorkflowPersistenceMapper mapper = WorkflowPersistenceTestFixtures.mapper();

    @Test
    void catalogRoundTripPreservesCatalogIdentityCodeAndTranslations() {
        WorkflowTypeCatalog catalog = WorkflowPersistenceTestFixtures.catalog();

        WorkflowTypeCatalogJpaEntity entity = mapper.toEntity(catalog);
        WorkflowTypeCatalog restored = mapper.toDomain(entity, WorkflowPersistenceTestFixtures.translationEntities());

        assertEquals(catalog.id(), restored.id());
        assertEquals("PRIORITY", restored.catalogName());
        assertEquals("NORMAL", restored.code().value());
        assertEquals(1, restored.translations().size());
        assertEquals("Normale", restored.defaultTranslation().orElseThrow().name().value());
    }

    @Test
    void definitionRoundTripPreservesPrimaryDefinitionFields() {
        WorkflowDefinition definition = WorkflowPersistenceTestFixtures.activeDefinition();

        WorkflowDefinitionJpaEntity entity = mapper.toEntity(definition);
        WorkflowDefinition restored = mapper.toDomain(entity, definition.steps().stream().map(mapper::toEntity).toList(), definition.transitions().stream().map(mapper::toEntity).toList());

        assertEquals(definition.id(), restored.id());
        assertEquals("TELEMETRY_READING_VALIDATION", restored.code().value());
        assertEquals(WorkflowDefinitionStatus.ACTIVE, restored.status());
        assertEquals(2, restored.steps().size());
        assertEquals(1, restored.transitions().size());
    }

    @Test
    void instanceRoundTripPreservesTargetActorStatusAndCorrelation() {
        WorkflowDefinition definition = WorkflowPersistenceTestFixtures.activeDefinition();
        WorkflowInstance instance = WorkflowPersistenceTestFixtures.startedInstance(definition);

        WorkflowInstanceJpaEntity entity = mapper.toEntity(instance);
        WorkflowInstance restored = mapper.toDomain(entity, java.util.List.of(), java.util.List.of(), java.util.List.of());

        assertEquals(instance.id(), restored.id());
        assertTrue(restored.target().isTelemetryReading());
        assertEquals(WorkflowInstanceStatus.STARTED, restored.status());
        assertEquals("corr-persistence-test", restored.correlationId().value());
        assertEquals("Abir MEDJERAB", restored.startedBy().actorDisplayNameSnapshot());
    }

    @Test
    void taskRoundTripPreservesAssignmentPriorityDueDateAndStatus() {
        WorkflowInstance instance = WorkflowPersistenceTestFixtures.startedInstance(WorkflowPersistenceTestFixtures.activeDefinition());
        WorkflowTask task = WorkflowPersistenceTestFixtures.openTask(instance);

        WorkflowTaskJpaEntity entity = mapper.toEntity(task);
        WorkflowTask restored = mapper.toDomain(entity);

        assertEquals(task.id(), restored.id());
        assertEquals(WorkflowTaskStatus.OPEN, restored.status());
        assertNotNull(restored.priority());
        assertEquals("NORMAL", restored.priority().code());
        assertNotNull(restored.dueDate());
    }
}
