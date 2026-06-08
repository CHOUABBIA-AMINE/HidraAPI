/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowApplicationServiceTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Shared workflow application service test fixtures and in-memory ports.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowAuditEventPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowCatalogRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetLookupPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
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
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;

/**
 * Shared workflow application service test fixtures and in-memory ports.
 */
final class WorkflowApplicationServiceTestFixtures {

    private WorkflowApplicationServiceTestFixtures() {
        // Test utility class.
    }

    static PageRequest pageRequest() {
        return PageRequest.of(0, 20);
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

    static WorkflowActorReference secondActor() {
        return WorkflowActorReference.of("actor-002", "m.operator", "Mohammed Operator", "OPERATOR");
    }

    static WorkflowOrganizationReference organization() {
        return WorkflowOrganizationReference.of("org-trc", "TRC", "VALIDATOR");
    }

    static WorkflowPriorityReference priority() {
        return WorkflowPriorityReference.of("workflow-priority-normal", "NORMAL");
    }

    static WorkflowReasonReference reason() {
        return WorkflowReasonReference.of("workflow-reason-out-of-range", "OUT_OF_RANGE");
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
                WorkflowCorrelationId.of("corr-application-test"));
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

    static final class InMemoryDefinitionRepository implements WorkflowDefinitionRepositoryPort {

        private final Map<String, WorkflowDefinition> definitions = new LinkedHashMap<>();

        @Override
        public WorkflowDefinition save(WorkflowDefinition definition) {
            definitions.put(definition.id().value(), definition);
            return definition;
        }

        @Override
        public Optional<WorkflowDefinition> findById(WorkflowDefinitionId id) {
            return Optional.ofNullable(definitions.get(id.value()));
        }

        @Override
        public Optional<WorkflowDefinition> findByCode(WorkflowCode code) {
            return definitions.values().stream()
                    .filter(definition -> definition.code().equals(code))
                    .findFirst();
        }

        @Override
        public boolean existsByCode(WorkflowCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<WorkflowDefinition> findAll(
                String searchTerm,
                WorkflowTypeReference type,
                WorkflowDefinitionStatus status,
                PageRequest pageRequest) {

            List<WorkflowDefinition> filtered = definitions.values().stream()
                    .filter(definition -> searchTerm == null || definition.code().value().contains(searchTerm))
                    .filter(definition -> type == null || definition.type().id().equals(type.id()))
                    .filter(definition -> status == null || definition.status().equals(status))
                    .toList();

            return new PageResult<>(filtered, pageRequest.page(), pageRequest.size(), filtered.size(), filtered.isEmpty() ? 0 : 1);
        }
    }

    static final class InMemoryInstanceRepository implements WorkflowInstanceRepositoryPort {

        private final Map<String, WorkflowInstance> instances = new LinkedHashMap<>();

        @Override
        public WorkflowInstance save(WorkflowInstance instance) {
            instances.put(instance.id().value(), instance);
            return instance;
        }

        @Override
        public Optional<WorkflowInstance> findById(WorkflowInstanceId id) {
            return Optional.ofNullable(instances.get(id.value()));
        }

        @Override
        public Optional<WorkflowInstance> findOpenByTarget(WorkflowTargetReference target) {
            return instances.values().stream()
                    .filter(instance -> instance.target().targetId().equals(target.targetId()))
                    .filter(instance -> !instance.status().isTerminal())
                    .findFirst();
        }

        @Override
        public PageResult<WorkflowInstance> findAll(
                WorkflowDefinitionId definitionId,
                WorkflowTargetReference target,
                WorkflowInstanceStatus status,
                WorkflowActorReference startedBy,
                PageRequest pageRequest) {

            List<WorkflowInstance> filtered = instances.values().stream()
                    .filter(instance -> definitionId == null || instance.definitionId().equals(definitionId))
                    .filter(instance -> target == null || instance.target().targetId().equals(target.targetId()))
                    .filter(instance -> status == null || instance.status().equals(status))
                    .filter(instance -> startedBy == null || instance.startedBy().actorId().equals(startedBy.actorId()))
                    .toList();

            return new PageResult<>(filtered, pageRequest.page(), pageRequest.size(), filtered.size(), filtered.isEmpty() ? 0 : 1);
        }
    }

    static final class InMemoryTaskRepository implements WorkflowTaskRepositoryPort {

        private final Map<String, WorkflowTask> tasks = new LinkedHashMap<>();

        @Override
        public WorkflowTask save(WorkflowTask task) {
            tasks.put(task.id().value(), task);
            return task;
        }

        @Override
        public Optional<WorkflowTask> findById(WorkflowTaskId id) {
            return Optional.ofNullable(tasks.get(id.value()));
        }

        @Override
        public PageResult<WorkflowTask> findAll(
                WorkflowInstanceId instanceId,
                WorkflowTaskStatus status,
                WorkflowActorReference assignedActor,
                WorkflowOrganizationReference assignedOrganization,
                PageRequest pageRequest) {

            List<WorkflowTask> filtered = tasks.values().stream()
                    .filter(task -> instanceId == null || task.instanceId().equals(instanceId))
                    .filter(task -> status == null || task.status().equals(status))
                    .filter(task -> assignedActor == null || task.assignedActor() != null && task.assignedActor().actorId().equals(assignedActor.actorId()))
                    .filter(task -> assignedOrganization == null || task.assignedOrganization() != null && task.assignedOrganization().organizationUnitId().equals(assignedOrganization.organizationUnitId()))
                    .toList();

            return new PageResult<>(filtered, pageRequest.page(), pageRequest.size(), filtered.size(), filtered.isEmpty() ? 0 : 1);
        }

        @Override
        public PageResult<WorkflowTask> findVisibleToActor(
                WorkflowActorReference actor,
                WorkflowOrganizationReference organization,
                WorkflowTaskStatus status,
                PageRequest pageRequest) {

            return findAll(null, status, actor, organization, pageRequest);
        }
    }

    static final class InMemoryCatalogRepository implements WorkflowCatalogRepositoryPort {

        private final Map<String, WorkflowTypeCatalog> catalogs = new LinkedHashMap<>();

        void add(WorkflowTypeCatalog catalog) {
            catalogs.put(catalog.id().value(), catalog);
        }

        @Override
        public WorkflowTypeCatalog save(WorkflowTypeCatalog catalog) {
            add(catalog);
            return catalog;
        }

        @Override
        public Optional<WorkflowTypeCatalog> findById(WorkflowCatalogId id) {
            return Optional.ofNullable(catalogs.get(id.value()));
        }

        @Override
        public Optional<WorkflowTypeCatalog> findByCatalogNameAndCode(String catalogName, WorkflowCode code) {
            return catalogs.values().stream()
                    .filter(catalog -> catalog.matchesCatalog(catalogName))
                    .filter(catalog -> catalog.code().equals(code))
                    .findFirst();
        }

        @Override
        public boolean existsByCatalogNameAndCode(String catalogName, WorkflowCode code) {
            return findByCatalogNameAndCode(catalogName, code).isPresent();
        }

        @Override
        public PageResult<WorkflowTypeCatalog> findAll(
                String catalogName,
                Boolean active,
                String locale,
                PageRequest pageRequest) {

            List<WorkflowTypeCatalog> filtered = catalogs.values().stream()
                    .filter(catalog -> catalog.matchesCatalog(catalogName))
                    .filter(catalog -> active == null || Objects.equals(catalog.active(), active))
                    .toList();

            return new PageResult<>(filtered, pageRequest.page(), pageRequest.size(), filtered.size(), filtered.isEmpty() ? 0 : 1);
        }
    }

    static final class FixedTargetLookupPort implements WorkflowTargetLookupPort {

        private boolean canStart = true;
        private WorkflowTargetReference resolvedTarget = telemetryReadingTarget();

        void denyStart() {
            canStart = false;
        }

        @Override
        public boolean exists(WorkflowTargetReference target) {
            return target != null && target.isTelemetryReading();
        }

        @Override
        public boolean canStartWorkflow(WorkflowTargetReference target) {
            return canStart && exists(target);
        }

        @Override
        public WorkflowTargetReference resolve(WorkflowTargetReference target) {
            return resolvedTarget;
        }
    }

    static final class RecordingAuditEventPort implements WorkflowAuditEventPort {

        private int startedCount;
        private int actionCount;
        private int taskChangedCount;

        @Override
        public void recordWorkflowStarted(WorkflowInstance instance) {
            startedCount++;
        }

        @Override
        public void recordWorkflowAction(WorkflowAction action) {
            actionCount++;
        }

        @Override
        public void recordWorkflowTaskChanged(WorkflowTask task) {
            taskChangedCount++;
        }

        int startedCount() {
            return startedCount;
        }

        int actionCount() {
            return actionCount;
        }

        int taskChangedCount() {
            return taskChangedCount;
        }
    }
}
