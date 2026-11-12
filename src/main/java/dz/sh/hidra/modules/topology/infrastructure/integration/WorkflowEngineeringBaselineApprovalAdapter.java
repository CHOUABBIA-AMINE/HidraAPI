/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEngineeringBaselineApprovalAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.Action;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineApprovalPort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public final class WorkflowEngineeringBaselineApprovalAdapter
        implements EngineeringBaselineApprovalPort, WorkflowOwnedTargetLookup {
    private final ObjectProvider<RevisionApprovalEvidenceContract> evidence;
    private final ObjectProvider<EngineeringBaselinePublicationUseCase> owner;
    private final TransactionTemplate detached;

    public WorkflowEngineeringBaselineApprovalAdapter(ObjectProvider<RevisionApprovalEvidenceContract> evidence,
            ObjectProvider<EngineeringBaselinePublicationUseCase> owner, PlatformTransactionManager manager) {
        this.evidence = evidence;
        this.owner = owner;
        this.detached = new TransactionTemplate(manager);
        this.detached.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
    }

    @Override
    public Approval requireApproval(Submission s, Decision d, Instant at) {
        var request = new RevisionApprovalEvidenceContract.Request(module(), s.sha256(), d.definitionId(),
                d.definitionVersion(), d.targetTypeId(), d.purposeId(), d.instanceId(), d.taskId(), d.actionId(), at);
        var binding = new RevisionApprovalEvidenceContract.BaselineBinding(s.workflowLocator(), s.submittedBy(),
                s.id().toString(), s.submittedAt());
        var resolved = evidence.getObject().resolveBaseline(request, binding)
                .orElseThrow(() -> new AccessDeniedException("Exact baseline approval unavailable"));
        return new Approval(d, resolved.actorId(), resolved.actedAt());
    }

    @Override public String module() { return "topology"; }
    @Override public Set<String> targetTypeCodes() { return Set.of(BASELINE_TARGET); }
    @Override public Optional<Target> eligibleTarget(String type, String digest) { return Optional.empty(); }

    @Override
    public Optional<Target> eligibleTarget(String type, String digest, String locator) {
        return lookup(type, digest, locator, Action.TARGET);
    }

    @Override
    public boolean protectedReadAllowed(String type, String digest, String locator, ProtectedRead access) {
        return lookup(type, digest, locator, Action.TARGET).isPresent();
    }

    @Override
    public boolean protectedReviewAllowed(String type, String digest, String locator) {
        return lookup(type, digest, locator, Action.REVIEW).isPresent();
    }

    private Optional<Target> lookup(String type, String digest, String locator, Action action) {
        if (!BASELINE_TARGET.equals(type) || locator == null || locator.length() > 1024) return Optional.empty();
        try {
            String[] parts = locator.split("[.]", -1);
            if (parts.length != 4 || !"baseline-v1".equals(parts[0])) return Optional.empty();
            var command = new Command(action, decode(parts[1]), decode(parts[2]), UUID.fromString(parts[3]),
                    null, 0, digest, null, 0, null);
            return detached.execute(status -> {
                boolean scaffold = status instanceof DefaultTransactionStatus s && s.isNewSynchronization();
                if (TransactionSynchronizationManager.isActualTransactionActive()
                        || (TransactionSynchronizationManager.isSynchronizationActive() && (!scaffold
                            || !TransactionSynchronizationManager.getSynchronizations().isEmpty())))
                    return Optional.empty();
                if (scaffold) TransactionSynchronizationManager.clearSynchronization();
                try {
                    var s = owner.getObject().execute(command).submission();
                    return s.workflowLocator().equals(locator) ? Optional.of(new Target(digest, locator, "Engineering baseline revision"))
                            : Optional.empty();
                } finally {
                    if (scaffold) TransactionSynchronizationManager.initSynchronization();
                }
            });
        } catch (RuntimeException unavailable) {
            return Optional.empty();
        }
    }

    private static String decode(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
