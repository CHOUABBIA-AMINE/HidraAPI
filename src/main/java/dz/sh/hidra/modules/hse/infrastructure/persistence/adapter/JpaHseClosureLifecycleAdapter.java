/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseClosureLifecycleAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseClosureLifecyclePort;
import dz.sh.hidra.modules.hse.domain.model.HseClosure;
import dz.sh.hidra.modules.hse.domain.service.HseCaseClosureGuard;
import dz.sh.hidra.modules.hse.domain.value.HseId;
import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.HseCaseStatusHistoryJpaEntity;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class JpaHseClosureLifecycleAdapter implements HseClosureLifecyclePort {
    private final HseCaseJpaRepository cases;
    private final HseClosureJpaRepository closures;
    private final HseCaseStatusHistoryJpaRepository histories;
    private final HseActorContract actors;
    private final HseWorkflowReferenceContract workflows;
    public JpaHseClosureLifecycleAdapter(HseCaseJpaRepository cases,HseClosureJpaRepository closures,
            HseCaseStatusHistoryJpaRepository histories,HseActorContract actors,HseWorkflowReferenceContract workflows) {
        this.cases=Objects.requireNonNull(cases);this.closures=Objects.requireNonNull(closures);
        this.histories=Objects.requireNonNull(histories);this.actors=Objects.requireNonNull(actors);this.workflows=Objects.requireNonNull(workflows);
    }
    @Override @Transactional
    public HseClosure close(HseClosure requested) {
        Objects.requireNonNull(requested);
        var parent=cases.findByIdForUpdate(requested.hseCaseId()).map(HsePersistenceMapper::toDomain).orElse(null);
        new HseCaseClosureGuard().ensureCanClose(parent,requested.impactAssessed(),requested.capaCompleted(),requested.evidenceReviewed());
        Instant at=Instant.now().truncatedTo(ChronoUnit.MICROS);
        var actor=actors.currentActor(at);
        if(actor==null || actor.id()==null || !actor.id().equals(requested.closedByActorId()))
            throw new SecurityException("Closing actor must be the authenticated eligible actor.");
        if(requested.workflowInstanceId()!=null && !workflows.caseMatches(requested.workflowInstanceId(),parent.id()))
            throw new IllegalArgumentException("Closure Workflow must target the actual HSE case.");
        if(closures.existsById(requested.id())) throw new IllegalArgumentException("Closure evidence is append-only.");
        var closure=new HseClosure(requested.id(),parent.id(),requested.closureSummary(),requested.impactAssessed(),
                requested.capaCompleted(),requested.evidenceReviewed(),requested.regulatoryReviewed(),actor.id(),actor.displayName(),at,requested.workflowInstanceId());
        closures.saveAndFlush(HsePersistenceMapper.toEntity(closure));
        histories.saveAndFlush(new HseCaseStatusHistoryJpaEntity(HseId.newId().value(),parent.id(),parent.status(),HseCaseStatus.CLOSED,
                null,closure.closureSummary(),actor.id(),actor.displayName(),at,closure.id()));
        cases.saveAndFlush(HsePersistenceMapper.toEntity(parent.closedAt(at)));
        return closure;
    }
}
