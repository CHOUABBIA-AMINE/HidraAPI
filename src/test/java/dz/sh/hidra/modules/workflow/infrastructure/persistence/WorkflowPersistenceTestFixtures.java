/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPersistenceTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Shared workflow persistence test fixtures.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageImpl;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeTranslation;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowActionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeCatalogJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeTranslationJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;

/**
 * Shared workflow persistence test fixtures.
 */
final class WorkflowPersistenceTestFixtures {

    private WorkflowPersistenceTestFixtures() {
        // Test utility class.
    }

    static WorkflowPersistenceMapper mapper() {
        return new WorkflowPersistenceMapper();
    }

    static WorkflowLocalizedName localizedName() {
        return WorkflowLocalizedName.of("تحقق القياسات", "Validation télémétrie", "Telemetry validation");
    }

    static WorkflowTypeReference workflowType() {
        return WorkflowTypeReference.of("workflow-type-telemetry-validation", "TELEMETRY_VALIDATION");
    }

    static WorkflowTargetTypeReference telemetryReadingTargetType() {
        return WorkflowTargetTypeReference.of("workflow-target-type-telemetry-reading", "TELEMETRY_READING");
    }

    static WorkflowTargetReference telemetryReadingTarget() {
        return WorkflowTargetReference.of(
                "telemetry",
                telemetryReadingTargetType(),
                "reading-001",
                "READING-001",
                "Telemetry reading reading-001");
    }

    static WorkflowActorReference actor() {
        return WorkflowActorReference.of("actor-001", "a.medjerab", "Abir MEDJERAB", "SUPERVISOR");
    }

    static WorkflowPriorityReference priority() {
        return WorkflowPriorityReference.of("workflow-priority-normal", "NORMAL");
    }

    static WorkflowTypeCatalog catalog() {
        WorkflowCatalogId catalogId = WorkflowCatalogId.of("workflow-priority-normal");

        return WorkflowTypeCatalog.restore(
                catalogId,
                "PRIORITY",
                WorkflowCode.of("NORMAL"),
                true,
                20,
                true,
                List.of(WorkflowTypeTranslation.create(
                        catalogId,
                        "fr",
                        WorkflowName.of("Normale"),
                        "Priorité normale")),
                Instant.now(),
                Instant.now());
    }

    static WorkflowDefinition activeDefinition() {
        WorkflowDefinition definition = WorkflowDefinition.create(
                WorkflowCode.of("TELEMETRY_READING_VALIDATION"),
                localizedName(),
                workflowType());

        WorkflowStep review = WorkflowStep.create(
                definition.id(),
                "SUPERVISOR_REVIEW",
                WorkflowLocalizedName.of(null, "Revue superviseur", "Supervisor review"),
                0,
                true);

        WorkflowStep completed = WorkflowStep.create(
                definition.id(),
                "COMPLETED",
                WorkflowLocalizedName.of(null, "Terminé", "Completed"),
                1,
                true);

        WorkflowDefinition withSteps = definition.addStep(review).addStep(completed);

        WorkflowTransition transition = WorkflowTransition.create(
                withSteps.id(),
                review.id(),
                completed.id(),
                dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision.APPROVE,
                false,
                false);

        return withSteps.addTransition(transition).activate();
    }

    static WorkflowInstance startedInstance(WorkflowDefinition definition) {
        return WorkflowInstance.start(
                definition.id(),
                definition.version(),
                telemetryReadingTarget(),
                definition.firstStep().orElseThrow().id(),
                actor(),
                WorkflowCorrelationId.of("corr-persistence-test"));
    }

    static WorkflowTask openTask(WorkflowInstance instance) {
        return WorkflowTask.open(
                instance.id(),
                instance.currentStepId(),
                actor(),
                null,
                priority(),
                WorkflowDueDate.of(Instant.now().plusSeconds(3600)));
    }

    static WorkflowTypeCatalogJpaEntity catalogEntity() {
        return mapper().toEntity(catalog());
    }

    static List<WorkflowTypeTranslationJpaEntity> translationEntities() {
        return catalog().translations().stream().map(mapper()::toEntity).toList();
    }

    static WorkflowDefinitionJpaEntity definitionEntity(WorkflowDefinition definition) {
        return mapper().toEntity(definition);
    }

    static WorkflowInstanceJpaEntity instanceEntity(WorkflowInstance instance) {
        return mapper().toEntity(instance);
    }

    static WorkflowTaskJpaEntity taskEntity(WorkflowTask task) {
        return mapper().toEntity(task);
    }

    static List<WorkflowActionJpaEntity> emptyActionEntities() {
        return List.of();
    }

    static <T> T repositoryProxy(Class<T> type, InvocationHandler handler) {
        Object proxy = Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class<?>[] {type},
                (Object object, Method method, Object[] arguments) -> {
                    if ("toString".equals(method.getName())) {
                        return type.getSimpleName() + "TestProxy";
                    }
                    if ("hashCode".equals(method.getName())) {
                        return System.identityHashCode(object);
                    }
                    if ("equals".equals(method.getName())) {
                        return object == arguments[0];
                    }
                    return handler.invoke(object, method, arguments);
                });

        return type.cast(proxy);
    }

    static <T> PageImpl<T> pageOf(T entity) {
        return new PageImpl<>(List.of(entity));
    }
}
