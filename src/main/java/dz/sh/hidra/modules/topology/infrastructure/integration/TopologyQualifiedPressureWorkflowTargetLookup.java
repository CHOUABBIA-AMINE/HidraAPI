/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyQualifiedPressureWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Authoritative current Topology owner admission for unapproved T36 Workflow targets.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.port.in.QualifiedPressureWorkflowCandidateReadUseCase;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Canonical locator is addressing, never an approval or bearer of permission.
 * Suspend Workflow's transaction and any empty Spring synchronization before
 * invoking the separately protected T07 EXACT read. Revalidate on each action.
 */
@Component
public class TopologyQualifiedPressureWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    public static final String TARGET_TYPE="OPERATING_PRESSURE_RESTRICTION";
    private final QualifiedPressureWorkflowCandidateReadUseCase owner;
    private final TransactionTemplate detached;

    public TopologyQualifiedPressureWorkflowTargetLookup(
            QualifiedPressureWorkflowCandidateReadUseCase owner, PlatformTransactionManager manager) {
        this.owner=Objects.requireNonNull(owner);
        detached=new TransactionTemplate(Objects.requireNonNull(manager));
        detached.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
    }

    @Override public String module() { return "topology"; }
    @Override public Set<String> targetTypeCodes(){return Set.of(TARGET_TYPE);}
    @Override public Optional<Target> eligibleTarget(String typeCode,String id){return Optional.empty();}

    @Override public boolean protectedReadAllowed(String typeCode, String id, String locator,
            ProtectedRead operation) {
        // A pending proposal is never a public Workflow resource. Re-run
        // current protected Topology/T07 owner checks for each task/history read.
        return operation != null && eligibleTarget(typeCode,id,locator).isPresent();
    }

    @Override public boolean pressureStartAllowed(String id, String locator, String actor) {
        return evaluate(TARGET_TYPE, id, locator, candidate -> owner.permitsStart(candidate, actor)).isPresent();
    }

    @Override public boolean pressureReviewAllowed(String id, String locator, String actor) {
        return evaluate(TARGET_TYPE, id, locator, candidate -> owner.permitsReview(candidate, actor)).isPresent();
    }

    @Override public Optional<Target> eligibleTarget(String typeCode,String id,String locator) {
        return evaluate(typeCode, id, locator, candidate -> owner.validatedLocator(candidate).filter(locator::equals).isPresent());
    }

    private Optional<Target> evaluate(String typeCode, String id, String locator,
                                      java.util.function.Predicate<UUID> admission) {
        if(!TARGET_TYPE.equals(typeCode) || id==null || locator==null) return Optional.empty();
        final UUID revision;
        try {
            revision=UUID.fromString(id);
            if(!revision.toString().equals(id) || !("t36-v1:"+id).equals(locator))
                return Optional.empty();
        } catch(IllegalArgumentException invalid) {
            return Optional.empty();
        }
        try {
            return detached.execute(status -> {
                boolean scaffold=status instanceof DefaultTransactionStatus tx && tx.isNewSynchronization();
                if(TransactionSynchronizationManager.isActualTransactionActive()
                        || TransactionSynchronizationManager.isSynchronizationActive() &&
                            (!scaffold || !TransactionSynchronizationManager.getSynchronizations().isEmpty()))
                    return Optional.empty();
                if(scaffold) TransactionSynchronizationManager.clearSynchronization();
                try {
                    return admission.test(revision)
                            ? Optional.of(new Target(id, locator, "Proposed operating pressure restriction"))
                            : Optional.empty();
                } finally {
                    if(scaffold) TransactionSynchronizationManager.initSynchronization();
                }
            });
        } catch(RuntimeException denied) {
            return Optional.empty();
        }
    }
}
