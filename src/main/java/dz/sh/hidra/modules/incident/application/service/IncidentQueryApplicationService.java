/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 *
 * @Description : Implements read-only incident register and detail queries.
 *
 */
package dz.sh.hidra.modules.incident.application.service;

import dz.sh.hidra.modules.incident.application.port.in.IncidentQueryUseCase;
import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class IncidentQueryApplicationService implements IncidentQueryUseCase {

    private static final int MAX_PAGE_SIZE = 200;
    private final IncidentRepositoryPort incidentRepositoryPort;

    public IncidentQueryApplicationService(IncidentRepositoryPort incidentRepositoryPort) {
        this.incidentRepositoryPort = Objects.requireNonNull(incidentRepositoryPort, "IncidentRepositoryPort must not be null.");
    }

    @Override
    public Page<IncidentView> incidents(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page must be >= 0.");
        if (size < 1 || size > MAX_PAGE_SIZE) throw new IllegalArgumentException("size must be between 1 and 200.");
        List<IncidentView> content = incidentRepositoryPort.findAll(page, size).stream().map(this::toView).toList();
        long total = incidentRepositoryPort.count();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(content, page, size, total, totalPages, page + 1 < totalPages);
    }

    @Override
    public IncidentView incident(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("incident id must not be blank.");
        return incidentRepositoryPort.findById(id.trim())
                .map(this::toView)
                .orElseThrow(() -> new IllegalArgumentException("Unknown incident: " + id));
    }

    private IncidentView toView(Incident incident) {
        return new IncidentView(
                incident.id(), incident.incidentNumber(), incident.title(), incident.description(),
                incident.classificationId(), incident.severityId(), incident.priorityId(),
                incident.status() == null ? null : incident.status().name(),
                incident.sourceType() == null ? null : incident.sourceType().name(),
                incident.sourceReferenceId(), incident.sourceReferenceCode(), incident.detectedAt(),
                incident.reportedAt(), incident.occurredAt(), incident.topologyAssetTypeCode(),
                incident.topologyAssetId(), incident.topologyAssetCode(), incident.topologyAssetNameSnapshot(),
                incident.locationDescriptionAr(), incident.locationDescriptionLt(), incident.latitude(), incident.longitude(),
                incident.responsibleOrganizationUnitId(), incident.responsibleOrganizationUnitCode(),
                incident.responsibleOrganizationUnitNameSnapshot(), incident.responsibleActorId(),
                incident.responsibleActorNameSnapshot(), incident.workflowInstanceId(), incident.currentEscalationLevel(),
                incident.containedAt(), incident.resolvedAt(), incident.closedAt(), incident.cancelledAt(),
                incident.createdAt(), incident.updatedAt()
        );
    }
}
