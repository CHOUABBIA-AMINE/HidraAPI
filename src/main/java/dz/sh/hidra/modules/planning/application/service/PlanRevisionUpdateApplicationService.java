/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionUpdateApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Applies concurrency-protected metadata updates to the current planning revision.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.UpdatePlanRevisionUseCase;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PlanRevisionUpdateApplicationService implements UpdatePlanRevisionUseCase {

    private final PlanRevisionRepositoryPort revisionRepository;
    private final OperationalPlanRepositoryPort planRepository;

    public PlanRevisionUpdateApplicationService(
            PlanRevisionRepositoryPort revisionRepository,
            OperationalPlanRepositoryPort planRepository
    ) {
        this.revisionRepository = Objects.requireNonNull(revisionRepository, "PlanRevisionRepositoryPort must not be null.");
        this.planRepository = Objects.requireNonNull(planRepository, "OperationalPlanRepositoryPort must not be null.");
    }

    @Override
    @Transactional
    public PlanRevision update(String revisionId, Command command) {
        if (revisionId == null || revisionId.isBlank()) {
            throw new IllegalArgumentException("revisionId must not be null or blank.");
        }
        Objects.requireNonNull(command, "Plan revision update command must not be null.");

        PlanRevision revision = revisionRepository.findByIdForUpdate(revisionId.trim())
                .orElseThrow(() -> new NoSuchElementException("Unknown plan revision: " + revisionId.trim()));
        OperationalPlan plan = planRepository.findById(revision.planId())
                .orElseThrow(() -> new NoSuchElementException("Unknown operational plan: " + revision.planId()));

        if (!revision.id().equals(plan.currentRevisionId())) {
            throw new PlanningRevisionConflictException(
                    "Plan revision is no longer the current editable revision. Refetch the operational plan before retrying."
            );
        }
        if (!command.expectedUpdatedAt().equals(revision.updatedAt())) {
            throw new PlanningRevisionConflictException(
                    "Plan revision changed after it was loaded. Refetch the revision before retrying."
            );
        }

        Instant updatedAt = Instant.now();
        if (!updatedAt.isAfter(revision.updatedAt())) {
            updatedAt = revision.updatedAt().plusNanos(1);
        }

        return revisionRepository.save(new PlanRevision(
                revision.id(),
                revision.planId(),
                revision.revisionNumber(),
                revision.revisionCode(),
                revision.status(),
                command.changeReasonCodeId(),
                command.changeReasonText(),
                revision.baseRevisionId(),
                revision.submittedByActorId(),
                revision.submittedAt(),
                revision.approvedByActorId(),
                revision.approvedAt(),
                revision.workflowInstanceId(),
                revision.createdAt(),
                updatedAt
        ));
    }
}
