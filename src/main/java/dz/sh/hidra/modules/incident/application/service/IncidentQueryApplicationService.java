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
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationSourcePort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationStorePort;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class IncidentQueryApplicationService implements IncidentQueryUseCase {

    private static final int MAX_PAGE_SIZE = 200;
    private final IncidentRepositoryPort incidentRepositoryPort;
    private final IncidentEscalationAuthorizationPort scope;
    private final IncidentEscalationSourcePort sources;
    private final IncidentEscalationStorePort links;

    public IncidentQueryApplicationService(IncidentRepositoryPort incidentRepositoryPort,
            IncidentEscalationAuthorizationPort scope,
            IncidentEscalationSourcePort sources,
            IncidentEscalationStorePort links) {
        this.incidentRepositoryPort = Objects.requireNonNull(incidentRepositoryPort, "IncidentRepositoryPort must not be null.");
        this.scope = Objects.requireNonNull(scope, "Incident current scope required.");
        this.sources = Objects.requireNonNull(sources, "Incident protected source owner required.");
        this.links = Objects.requireNonNull(links, "Incident source correlation required.");
    }

    @Override
    @Transactional
    public Page<IncidentView> incidents(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page must be >= 0.");
        if (size < 1 || size > MAX_PAGE_SIZE) throw new IllegalArgumentException("size must be between 1 and 200.");
        var assets=scope.permittedAssets("incident:incidents:read");
        var authorized=incidentRepositoryPort.findAuthorized(assets,deniedOrigins(assets),page,size);
        List<IncidentView> content=authorized.content().stream().map(this::toView).toList();
        long total=authorized.total();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(content, page, size, total, totalPages, page + 1 < totalPages);
    }

    @Override
    @Transactional
    public IncidentView incident(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("incident id must not be blank.");
        scope.requireIncident("incident:incidents:read",id.trim());
        links.byIncident(id.trim()).ifPresent(this::requireSourceRead);
        return incidentRepositoryPort.findById(id.trim())
                .map(this::toView)
                .orElseThrow(() -> new IllegalArgumentException("Unknown incident: " + id));
    }


    /**
     * Prevent qualified T14 case IDs and totals from crossing the source owner's
     * current READ boundary. The repository excludes denied IDs in SQL before
     * paging/counting. An owner denial excludes the case; DB failures propagate.
     */
    private List<String> deniedOrigins(List<IncidentEscalationAuthorizationPort.AssetRef> assets) {
        var denied=new java.util.ArrayList<String>();
        for(var correlation:links.correlationsForAssets(assets)) {
            try {
                requireSourceRead(correlation);
            } catch (org.springframework.security.access.AccessDeniedException |
                     java.util.NoSuchElementException | IllegalArgumentException unavailable) {
                // Mask this case in SQL before paging and counting.
                denied.add(correlation.incidentId());
            }
        }
        return denied;
    }
    private void requireSourceRead(IncidentEscalationStorePort.Correlation correlation) {
        scope.requireAsset("incident:incidents:read",correlation.companyId(),
                correlation.assetType(),correlation.assetId());
        var history=links.timeline(correlation.incidentId());
        if(history.isEmpty())throw new java.util.NoSuchElementException("No verified escalation origin.");
        for(var event:history) {
            var qualified=sources.authorized(event.sourceType(),event.sourceId());
            if(!correlation.evaluationId().equals(qualified.evaluationId())
                 ||!correlation.companyId().equals(qualified.companyId())
                 ||!correlation.assetType().equals(qualified.assetType())
                 ||!correlation.assetId().equals(qualified.assetId()))
                throw new java.util.NoSuchElementException("Source ownership does not match.");
        }
    }

    private static boolean redactedSource(Incident incident) {
        return incident.sourceType() == dz.sh.hidra.modules.incident.domain.value.IncidentSourceType.ALARM
            || incident.sourceType() == dz.sh.hidra.modules.incident.domain.value.IncidentSourceType.LEAK_CANDIDATE;
    }

    private IncidentView toView(Incident incident) {
        return new IncidentView(
                incident.id(), incident.incidentNumber(), incident.title(), incident.description(),
                incident.classificationId(), incident.severityId(), incident.priorityId(),
                incident.status() == null ? null : incident.status().name(),
                incident.sourceType() == null ? null : incident.sourceType().name(),
                redactedSource(incident) ? null : incident.sourceReferenceId(),
                redactedSource(incident) ? null : incident.sourceReferenceCode(), incident.detectedAt(),
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
