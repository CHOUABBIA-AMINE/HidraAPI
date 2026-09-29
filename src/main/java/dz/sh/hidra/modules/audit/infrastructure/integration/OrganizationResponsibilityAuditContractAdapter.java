/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityAuditContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Resolves Audit catalog codes and appends Organization responsibility evidence.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditCatalogEntryJpaEntity;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Converts semantic Organization responsibility audit codes to active Audit catalog IDs.
 *
 * <p>The adapter fails closed when a required Audit taxonomy entry has not been provisioned.
 * It never invents catalog identifiers.</p>
 */
@Component
public final class OrganizationResponsibilityAuditContractAdapter
        implements OrganizationResponsibilityAuditContract {

    private static final String EVENT_TYPE = "EVENT_TYPE";
    private static final String EVENT_CATEGORY = "EVENT_CATEGORY";
    private static final String ORGANIZATION = "organization";

    private final AuditCatalogEntryJpaRepository catalogRepository;
    private final RecordAuditEventUseCase recordAuditEventUseCase;

    public OrganizationResponsibilityAuditContractAdapter(
            AuditCatalogEntryJpaRepository catalogRepository,
            RecordAuditEventUseCase recordAuditEventUseCase
    ) {
        this.catalogRepository = Objects.requireNonNull(
                catalogRepository,
                "Audit catalog repository must not be null."
        );
        this.recordAuditEventUseCase = Objects.requireNonNull(
                recordAuditEventUseCase,
                "Record audit event use case must not be null."
        );
    }

    @Override
    public String append(Event event) {
        Objects.requireNonNull(event, "Organization responsibility audit event must not be null.");
        AuditCatalogEntryJpaEntity eventType = catalog(EVENT_TYPE, event.eventTypeCode());
        AuditCatalogEntryJpaEntity category = catalog(EVENT_CATEGORY, event.eventCategoryCode());

        AuditEventSummaryDto saved = recordAuditEventUseCase.recordAuditEvent(
                new RecordAuditEventCommand(
                        eventType.id(),
                        category.id(),
                        null,
                        ORGANIZATION,
                        event.sourceComponent(),
                        null,
                        event.actionCode(),
                        null,
                        event.actorId(),
                        AuditActorType.USER,
                        event.actorDisplayName(),
                        event.actorUsername(),
                        ORGANIZATION,
                        event.targetType(),
                        event.targetId(),
                        null,
                        null,
                        AuditOperation.valueOf(event.operation().name()),
                        event.decisionCode(),
                        null,
                        event.reasonText(),
                        event.workflowInstanceId(),
                        event.workflowTaskId(),
                        event.workflowActionId(),
                        event.requestId(),
                        event.correlationId(),
                        null,
                        event.occurredAt() == null ? Instant.now() : event.occurredAt(),
                        null
                )
        );
        return saved.id();
    }

    private AuditCatalogEntryJpaEntity catalog(String catalogName, String code) {
        String normalized = requireText(code, "Audit catalog code must not be blank.");
        return catalogRepository.findFirstByCatalogNameAndCodeAndActiveTrue(catalogName, normalized)
                .orElseThrow(() -> new IllegalStateException(
                        "Required active Audit catalog entry is not provisioned: "
                                + catalogName + "/" + normalized
                ));
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
