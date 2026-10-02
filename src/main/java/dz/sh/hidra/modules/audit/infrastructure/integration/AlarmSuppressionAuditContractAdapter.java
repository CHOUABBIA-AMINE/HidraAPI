/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionAuditContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Resolves Audit taxonomy and appends canonical alarm suppression expiry evidence.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.contract.alarm.AlarmSuppressionAuditContract;
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
 * Converts Alarm suppression expiry evidence to one Audit-owned event.
 *
 * <p>The adapter fails closed when required Audit taxonomy is not provisioned.</p>
 */
@Component
public final class AlarmSuppressionAuditContractAdapter implements AlarmSuppressionAuditContract {

    private static final String EVENT_TYPE = "EVENT_TYPE";
    private static final String EVENT_CATEGORY = "EVENT_CATEGORY";
    private static final String EVENT_TYPE_CODE = "ALARM_SUPPRESSION_EXPIRED";
    private static final String EVENT_CATEGORY_CODE = "BUSINESS";
    private static final String ALARM = "alarm";
    private static final String TARGET_TYPE = "ALARM_SUPPRESSION";
    private static final String ACTION_CODE = "EXPIRE_ALARM_SUPPRESSION";

    private final AuditCatalogEntryJpaRepository catalogRepository;
    private final RecordAuditEventUseCase recordAuditEventUseCase;

    public AlarmSuppressionAuditContractAdapter(
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
    public String appendExpiry(ExpiryEvidence evidence) {
        Objects.requireNonNull(evidence, "Alarm suppression expiry evidence must not be null.");
        String suppressionId = requireText(evidence.suppressionId(), "Suppression id must not be blank.");
        String scopeType = requireText(evidence.scopeType(), "Suppression scope type must not be blank.");
        String scopeReferenceId = requireText(
                evidence.scopeReferenceId(),
                "Suppression scope reference id must not be blank."
        );
        String actorId = requireText(evidence.systemActorId(), "Suppression expiry system actor must not be blank.");
        Instant expiredAt = Objects.requireNonNull(evidence.expiredAt(), "Suppression expiry instant must not be null.");

        AuditCatalogEntryJpaEntity eventType = catalog(EVENT_TYPE, EVENT_TYPE_CODE);
        AuditCatalogEntryJpaEntity category = catalog(EVENT_CATEGORY, EVENT_CATEGORY_CODE);

        AuditEventSummaryDto saved = recordAuditEventUseCase.recordAuditEvent(
                new RecordAuditEventCommand(
                        eventType.id(),
                        category.id(),
                        null,
                        ALARM,
                        "AlarmSuppressionExpiryOrchestrator",
                        suppressionId,
                        ACTION_CODE,
                        null,
                        actorId,
                        AuditActorType.SCHEDULED_JOB,
                        "Hidra scheduled job",
                        null,
                        ALARM,
                        TARGET_TYPE,
                        suppressionId,
                        scopeType + ":" + scopeReferenceId,
                        null,
                        AuditOperation.UPDATE,
                        "EXPIRED",
                        null,
                        "Automatic suppression expiry",
                        null,
                        null,
                        null,
                        null,
                        evidence.correlationId(),
                        null,
                        expiredAt,
                        null
                )
        );
        return saved.id();
    }

    private AuditCatalogEntryJpaEntity catalog(String catalogName, String code) {
        return catalogRepository.findFirstByCatalogNameAndCodeAndActiveTrue(catalogName, code)
                .orElseThrow(() -> new IllegalStateException(
                        "Required active Audit catalog entry is not provisioned: "
                                + catalogName + "/" + code
                ));
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
