/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Application service for operational-plan creation and concurrency-protected metadata updates.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.command.UpdateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.mapper.PlanningApplicationMapper;
import dz.sh.hidra.modules.planning.application.port.in.CreateOperationalPlanUseCase;
import dz.sh.hidra.modules.planning.application.port.in.UpdateOperationalPlanUseCase;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanningId;
import java.time.Instant;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for operational plans.
 */
@Service
public class OperationalPlanApplicationService implements CreateOperationalPlanUseCase, UpdateOperationalPlanUseCase {

    private final OperationalPlanRepositoryPort repositoryPort;

    public OperationalPlanApplicationService(OperationalPlanRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Operational plan repository port must not be null.");
    }

    @Override
    public OperationalPlanSummaryDto createOperationalPlan(CreateOperationalPlanCommand command) {
        Objects.requireNonNull(command, "Create operational plan command must not be null.");
        Instant now = Instant.now();
        OperationalPlan plan = new OperationalPlan(
                PlanningId.newId().value(),
                command.periodId(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.planTypeId(),
                command.productTypeId(),
                command.topologyScopeType(),
                command.topologyScopeId(),
                command.topologyScopeCode(),
                command.topologyScopeNameSnapshot(),
                command.responsibleOrganizationUnitId(),
                OperationalPlanStatus.DRAFT,
                null,
                null,
                command.createdByActorId(),
                now,
                now
        );
        return PlanningApplicationMapper.toSummary(repositoryPort.save(plan));
    }

    @Override
    @Transactional
    public OperationalPlanUpdateResult updateOperationalPlan(UpdateOperationalPlanCommand command) {
        Objects.requireNonNull(command, "Update operational plan command must not be null.");
        String id = requireText(command.id(), "Operational plan id");
        if (command.expectedUpdatedAt() == null) {
            throw new IllegalArgumentException("expectedUpdatedAt must not be null.");
        }
        Instant expectedUpdatedAt = command.expectedUpdatedAt();
        String nameFr = requireText(command.nameFr(), "nameFr");
        String nameAr = normalize(command.nameAr());
        String nameEn = normalize(command.nameEn());
        String responsibleOrganizationUnitId = normalize(command.responsibleOrganizationUnitId());

        OperationalPlan current = repositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Unknown operational plan: " + id));
        if (!expectedUpdatedAt.equals(current.updatedAt())) {
            throw stale(id);
        }

        Instant refreshedUpdatedAt = Instant.now();
        if (!refreshedUpdatedAt.isAfter(current.updatedAt())) {
            refreshedUpdatedAt = current.updatedAt().plusNanos(1);
        }

        boolean updated = repositoryPort.updateMetadataIfUpdatedAtMatches(
                id,
                expectedUpdatedAt,
                nameAr,
                nameFr,
                nameEn,
                responsibleOrganizationUnitId,
                refreshedUpdatedAt
        );
        if (!updated) {
            throw stale(id);
        }

        return new OperationalPlanUpdateResult(
                id,
                nameAr,
                nameFr,
                nameEn,
                responsibleOrganizationUnitId,
                refreshedUpdatedAt
        );
    }

    private static ConcurrentModificationException stale(String id) {
        return new ConcurrentModificationException(
                "Operational plan " + id + " changed after the supplied expectedUpdatedAt token; refetch before retrying."
        );
    }

    private static String requireText(String value, String field) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(field + " must not be blank.");
        }
        return normalized;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
