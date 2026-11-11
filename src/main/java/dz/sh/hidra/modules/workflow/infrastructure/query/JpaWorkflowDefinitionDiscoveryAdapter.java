/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowDefinitionDiscoveryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.query
 *
 * @Description : Discovers only owner-authorized supported active Workflow process bindings.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.query;

import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowDefinitionDiscoveryUseCase;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.*;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaWorkflowDefinitionDiscoveryAdapter implements WorkflowDefinitionDiscoveryUseCase {
    private static final String TYPE = "INSTALLED_CHARACTERISTIC_LINK_REVISION";
    private static final String PURPOSE = "INSTALLED_CHARACTERISTIC_LINK_APPROVAL";
    private static final String PLANNING_TYPE = "PLAN_REVISION";
    private static final String PLANNING_PURPOSE = "PLAN_REVISION_APPROVAL";
    private static final Set<String> DECISIONS = Set.of("APPROVE", "REJECT");
    private final EntityManager entities;
    private final CurrentSecurityContext security;
    private final WorkflowActorContract actors;
    private final WorkflowTransitionApplicationService reviewerPolicy;
    private final List<WorkflowOwnedTargetLookup> owners;

    public JpaWorkflowDefinitionDiscoveryAdapter(EntityManager entities, CurrentSecurityContext security,
            WorkflowActorContract actors, WorkflowTransitionApplicationService reviewerPolicy,
            List<WorkflowOwnedTargetLookup> owners) {
        this.entities = Objects.requireNonNull(entities);
        this.security = Objects.requireNonNull(security);
        this.actors = Objects.requireNonNull(actors);
        this.reviewerPolicy = Objects.requireNonNull(reviewerPolicy);
        this.owners = List.copyOf(owners);
    }

    @Override
    public Optional<TargetHandoff> installedCharacteristicLinkTarget(String companyPartyId,
            String installedUnitId, String linkId, long revisionId) {
        var principal=security.currentPrincipal().filter(p -> p.authenticated()).orElse(null);
        if(principal==null || actors.eligibleActor(principal.actorId().value(),Instant.now())
                .filter(a -> principal.actorId().value().equals(a.id())).isEmpty())
            return Optional.empty();
        var matching=owners.stream().filter(o -> "topology".equals(o.module())
                && o.targetTypeCodes().contains(TYPE)).toList();
        if(matching.size()!=1) return Optional.empty();
        // Owner authorization precedes even catalog/target disclosure. This is an address, not proof.
        WorkflowOwnedTargetLookup.RevisionTarget revision;
        try {
            revision=matching.getFirst().describeInstalledCharacteristicLink(
                    new WorkflowOwnedTargetLookup.InstalledCharacteristicLinkAddress(
                            companyPartyId,installedUnitId,linkId,revisionId)).orElse(null);
        } catch(RuntimeException unavailable) {
            return Optional.empty();
        }
        if(revision==null) return Optional.empty();
        var types=activeCatalog("WORKFLOW_TARGET_TYPE",TYPE);
        var purposes=activeCatalog("WORKFLOW_PURPOSE",PURPOSE);
        if(types.size()!=1 || purposes.size()!=1) return Optional.empty();
        return Optional.of(new TargetHandoff("topology",types.getFirst().id(),purposes.getFirst().id(),
                revision.target().id(),revision.target().code(),revision.revisionId(),revision.digestFormat()));
    }

    private List<WorkflowCatalogEntryJpaEntity> activeCatalog(String catalog,String code) {
        return entities.createQuery("select c from WorkflowCatalogEntryJpaEntity c "
                    + "where c.catalogName = :catalog and c.code = :code and c.active = true",
                    WorkflowCatalogEntryJpaEntity.class)
                .setParameter("catalog",catalog).setParameter("code",code).getResultList();
    }

    @Override
    @Transactional
    public List<AvailableDefinition> discover(String module, String targetTypeId,
            String workflowPurposeId, String targetId, String locator) {
        var principal = security.currentPrincipal().filter(p -> p.authenticated()).orElse(null);
        if (principal == null) return List.of();
        String starter = principal.actorId().value();
        if (actors.eligibleActor(starter, Instant.now()).filter(a -> starter.equals(a.id())).isEmpty())
            return List.of();
        boolean topology = "topology".equals(module);
        boolean planning = "planning".equals(module);
        if ((!topology && !planning) || targetId == null || targetId.isBlank()
                || targetTypeId == null || workflowPurposeId == null
                || topology && (!targetId.matches("[0-9a-f]{64}") || locator == null || locator.isBlank()))
            return List.of();

        String expectedType = topology ? TYPE : PLANNING_TYPE;
        String expectedPurpose = topology ? PURPOSE : PLANNING_PURPOSE;
        var type = entities.find(WorkflowCatalogEntryJpaEntity.class, targetTypeId);
        var purpose = entities.find(WorkflowCatalogEntryJpaEntity.class, workflowPurposeId);
        if (type == null || !type.active() || !"WORKFLOW_TARGET_TYPE".equals(type.catalogName())
                || !expectedType.equals(type.code()) || purpose == null || !purpose.active()
                || !"WORKFLOW_PURPOSE".equals(purpose.catalogName())
                || !expectedPurpose.equals(purpose.code()))
            return List.of();

        var matching = owners.stream().filter(o -> module.equals(o.module())
                && o.targetTypeCodes().contains(expectedType)).toList();
        if (matching.size() != 1) return List.of();
        try {
            boolean allowed = topology
                    ? matching.getFirst().protectedReadAllowed(
                            expectedType, targetId, locator, WorkflowOwnedTargetLookup.ProtectedRead.REVISION)
                    : matching.getFirst().protectedReadAllowed(
                            expectedType, targetId, WorkflowOwnedTargetLookup.ProtectedRead.REVISION);
            if (!allowed) return List.of();
        } catch (RuntimeException denied) {
            return List.of();
        }

        return entities.createQuery("select b from WorkflowDefinitionTargetBindingJpaEntity b "
                    + "where b.targetModule = :module and b.targetTypeId = :type "
                    + "and b.workflowPurposeId = :purpose and b.active = true",
                    WorkflowDefinitionTargetBindingJpaEntity.class)
                .setParameter("module", module).setParameter("type", targetTypeId)
                .setParameter("purpose", workflowPurposeId).getResultList().stream()
                .map(b -> definition(b, starter))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(AvailableDefinition::code))
                .toList();
    }

    private AvailableDefinition definition(WorkflowDefinitionTargetBindingJpaEntity binding, String starter) {
        var d = entities.find(WorkflowDefinitionJpaEntity.class, binding.definitionId());
        if (d == null || d.status() != WorkflowDefinitionStatus.ACTIVE || d.version() <= 0)
            return null;

        var steps = entities.createQuery("select s from WorkflowStepJpaEntity s "
                    + "where s.definitionId = :definition order by s.stepOrder, s.id",
                    WorkflowStepJpaEntity.class)
                .setParameter("definition", d.id()).getResultList();
        if (steps.isEmpty()) return null;
        var first = steps.getFirst();
        var transitions = entities.createQuery("select t from WorkflowTransitionJpaEntity t "
                    + "where t.definitionId = :definition and t.fromStepId = :step order by t.id",
                    WorkflowTransitionJpaEntity.class)
                .setParameter("definition", d.id()).setParameter("step", first.id()).getResultList();
        if (transitions.isEmpty() || transitions.stream().anyMatch(t ->
                !DECISIONS.contains(t.decision().name())
                || t.conditionExpression() != null || t.targetModuleCallback() != null
                || t.fromStepId().equals(t.toStepId())
                || steps.stream().noneMatch(s -> s.id().equals(t.toStepId()))))
            return null;
        // The first task is manual-starter-created. Discover only explicitly configured,
        // current eligible, independent human assignees; never enumerate foreign Users.
        var choices = entities.createQuery("select r from WorkflowStepAssignmentRuleJpaEntity r "
                    + "where r.definitionId = :definition and r.stepId = :step and r.active = true",
                    WorkflowStepAssignmentRuleJpaEntity.class)
                .setParameter("definition", d.id()).setParameter("step", first.id())
                .getResultList().stream()
                .filter(r -> r.actorId() != null && r.roleCode() == null
                        && r.organizationRoleCode() == null && r.targetOwnerMode() == null
                        && !starter.equals(r.actorId()))
                .map(r -> actors.eligibleActor(r.actorId(), Instant.now()).orElse(null))
                .filter(Objects::nonNull)
                .filter(a -> reviewerPolicy.eligibleIndependentHumanReviewer(a.id(), starter))
                .map(a -> new AssignmentChoice(a.id(), a.username(), a.displayName()))
                .distinct().toList();
        if (choices.isEmpty()) return null;
        return new AvailableDefinition(d.id(), d.code(), d.nameFr(), d.version(),
                binding.targetModule(), binding.targetTypeId(), binding.workflowPurposeId(),
                first.id(), choices,
                transitions.stream().map(t -> new AvailableDecision(t.id(), t.decision().name(),
                        t.fromStepId(), t.toStepId(), t.reasonRequired() || "REJECT".equals(t.decision().name()), t.commentRequired(),
                        t.requiredPermissionCode())).toList());
    }
}
