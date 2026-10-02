/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.service
 *
 * @Description : Implements read-only HSE case and CAPA queries.
 *
 */
package dz.sh.hidra.modules.hse.application.service;

import dz.sh.hidra.modules.hse.application.port.in.HseQueryUseCase;
import dz.sh.hidra.modules.hse.application.port.out.HseCaseRepositoryPort;
import dz.sh.hidra.modules.hse.application.port.out.HseCorrectivePreventiveActionRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class HseQueryApplicationService implements HseQueryUseCase {

    private static final int MAX_PAGE_SIZE = 200;
    private final HseCaseRepositoryPort caseRepositoryPort;
    private final HseCorrectivePreventiveActionRepositoryPort capaRepositoryPort;

    public HseQueryApplicationService(
            HseCaseRepositoryPort caseRepositoryPort,
            HseCorrectivePreventiveActionRepositoryPort capaRepositoryPort
    ) {
        this.caseRepositoryPort = Objects.requireNonNull(caseRepositoryPort, "HseCaseRepositoryPort must not be null.");
        this.capaRepositoryPort = Objects.requireNonNull(capaRepositoryPort, "HseCorrectivePreventiveActionRepositoryPort must not be null.");
    }

    @Override
    public Page<HseCaseView> cases(int page, int size) {
        validatePage(page, size);
        List<HseCaseView> content = caseRepositoryPort.findAll(page, size).stream().map(this::toCaseView).toList();
        long total = caseRepositoryPort.count();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(content, page, size, total, totalPages, page + 1 < totalPages);
    }

    @Override
    public HseCaseView hseCase(String id) {
        String normalizedId = requireId(id, "case");
        return caseRepositoryPort.findById(normalizedId)
                .map(this::toCaseView)
                .orElseThrow(() -> new IllegalArgumentException("Unknown HSE case: " + id));
    }

    @Override
    public Page<CapaView> capas(int page, int size) {
        validatePage(page, size);
        List<CapaView> content = capaRepositoryPort.findAll(page, size).stream().map(this::toCapaView).toList();
        long total = capaRepositoryPort.count();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(content, page, size, total, totalPages, page + 1 < totalPages);
    }

    @Override
    public CapaView capa(String id) {
        String normalizedId = requireId(id, "CAPA");
        return capaRepositoryPort.findById(normalizedId)
                .map(this::toCapaView)
                .orElseThrow(() -> new IllegalArgumentException("Unknown HSE CAPA: " + id));
    }

    private void validatePage(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page must be >= 0.");
        if (size < 1 || size > MAX_PAGE_SIZE) throw new IllegalArgumentException("size must be between 1 and 200.");
    }

    private String requireId(String id, String resource) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("HSE " + resource + " id must not be blank.");
        return id.trim();
    }

    private HseCaseView toCaseView(HseCase hseCase) {
        return new HseCaseView(
                hseCase.id(), hseCase.caseNumber(), hseCase.title(), hseCase.description(),
                hseCase.caseTypeId(), hseCase.severityId(), hseCase.priorityId(),
                hseCase.status() == null ? null : hseCase.status().name(),
                hseCase.sourceType() == null ? null : hseCase.sourceType().name(),
                hseCase.incidentReferenceId(), hseCase.incidentCodeSnapshot(), hseCase.incidentTitleSnapshot(),
                hseCase.targetModule(), hseCase.targetTypeCode(), hseCase.targetId(), hseCase.targetCodeSnapshot(), hseCase.targetLabelSnapshot(),
                hseCase.occurredAt(), hseCase.reportedAt(), hseCase.reportedByActorId(), hseCase.reportedByDisplayNameSnapshot(),
                hseCase.responsibleOrganizationUnitId(), hseCase.responsibleOrganizationUnitNameSnapshot(),
                hseCase.workflowInstanceId(), hseCase.auditReferenceId(), hseCase.controlledAt(), hseCase.resolvedAt(),
                hseCase.closedAt(), hseCase.createdAt(), hseCase.updatedAt()
        );
    }

    private CapaView toCapaView(HseCorrectivePreventiveAction capa) {
        return new CapaView(
                capa.id(), capa.hseCaseId(), capa.actionNumber(), capa.actionTypeId(), capa.title(), capa.description(),
                capa.ownerActorId(), capa.ownerDisplayNameSnapshot(), capa.ownerOrganizationUnitId(), capa.ownerOrganizationUnitNameSnapshot(),
                capa.targetDate(), capa.completedAt(), capa.verificationRequired(), capa.verifiedByActorId(), capa.verifiedAt(),
                capa.status() == null ? null : capa.status().name(), capa.linkedWorkOrderId(), capa.workflowTaskId(),
                capa.createdAt(), capa.updatedAt()
        );
    }
}
