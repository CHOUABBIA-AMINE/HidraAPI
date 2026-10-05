/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : RiskRegisterAuditContractAdapterTest
 * @CreatedOn : 2025-06-26
 * @UpdatedOn : 2026-10-05
 * @Type : Class
 * @Layer : Audit Test
 * @Module : audit
 * @Package : dz.sh.hidra.modules.audit.infrastructure.integration
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.contract.risk.RiskRegisterAuditContract;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditCatalogEntryJpaEntity;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RiskRegisterAuditContractAdapterTest {

    @Test
    void resolvesRiskCreationTaxonomyAndAppendsAuditEvidence() {
        AuditCatalogEntryJpaRepository catalogs = mock(AuditCatalogEntryJpaRepository.class);
        RecordAuditEventUseCase recorder = mock(RecordAuditEventUseCase.class);
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue("EVENT_TYPE", "RISK_REGISTER_CREATED"))
                .thenReturn(Optional.of(catalog("event-type", "EVENT_TYPE", "RISK_REGISTER_CREATED")));
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue("EVENT_CATEGORY", "BUSINESS"))
                .thenReturn(Optional.of(catalog("category", "EVENT_CATEGORY", "BUSINESS")));
        AuditEventSummaryDto result = mock(AuditEventSummaryDto.class);
        when(result.id()).thenReturn("audit-1");
        when(recorder.recordAuditEvent(any())).thenReturn(result);

        RiskRegisterAuditContractAdapter adapter =
                new RiskRegisterAuditContractAdapter(catalogs, recorder);

        assertThat(adapter.appendCreated(new RiskRegisterAuditContract.CreationEvidence(
                "register-1",
                "REG-1",
                "Registre",
                "actor-1",
                "Actor One",
                "unit-1",
                null,
                Instant.parse("2026-10-05T00:00:00Z")
        ))).isEqualTo("audit-1");
    }

    private static AuditCatalogEntryJpaEntity catalog(String id, String name, String code) {
        Instant now = Instant.parse("2026-10-05T00:00:00Z");
        return new AuditCatalogEntryJpaEntity(id, name, code, true, 1, true, now, now);
    }
}
