/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegisterAuditContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Appends canonical RiskRegister creation Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.contract.risk.RiskRegisterAuditContract;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditCatalogEntryJpaEntity;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class RiskRegisterAuditContractAdapter implements RiskRegisterAuditContract {

    private static final String EVENT_TYPE = "EVENT_TYPE";
    private static final String EVENT_CATEGORY = "EVENT_CATEGORY";
    private static final String EVENT_TYPE_CODE = "RISK_REGISTER_CREATED";
    private static final String EVENT_CATEGORY_CODE = "BUSINESS";

    private final AuditCatalogEntryJpaRepository catalogRepository;
    private final RecordAuditEventUseCase recordAuditEventUseCase;

    public RiskRegisterAuditContractAdapter(
            AuditCatalogEntryJpaRepository catalogRepository,
            RecordAuditEventUseCase recordAuditEventUseCase
    ) {
        this.catalogRepository = Objects.requireNonNull(catalogRepository);
        this.recordAuditEventUseCase = Objects.requireNonNull(recordAuditEventUseCase);
    }

    @Override
    public String appendCreated(CreationEvidence evidence) {
        Objects.requireNonNull(evidence, "RiskRegister creation evidence must not be null.");
        AuditCatalogEntryJpaEntity eventType = catalog(EVENT_TYPE, EVENT_TYPE_CODE);
        AuditCatalogEntryJpaEntity category = catalog(EVENT_CATEGORY, EVENT_CATEGORY_CODE);

        return recordAuditEventUseCase.recordAuditEvent(new RecordAuditEventCommand(
                eventType.id(),
                category.id(),
                null,
                "risk",
                "RiskApplicationService",
                evidence.registerId(),
                "CREATE_RISK_REGISTER",
                null,
                evidence.actorId(),
                AuditActorType.USER,
                evidence.actorDisplayName(),
                null,
                "risk",
                "RISK_REGISTER",
                evidence.registerId(),
                evidence.registerCode(),
                evidence.registerLabel(),
                AuditOperation.CREATE,
                null,
                null,
                "Risk register created",
                null,
                null,
                null,
                null,
                evidence.correlationId(),
                null,
                evidence.occurredAt(),
                null
        )).id();
    }

    private AuditCatalogEntryJpaEntity catalog(String catalogName, String code) {
        return catalogRepository.findFirstByCatalogNameAndCodeAndActiveTrue(catalogName, code)
                .orElseThrow(() -> new IllegalStateException(
                        "Required active Audit catalog entry is not provisioned: "
                                + catalogName + "/" + code
                ));
    }
}
