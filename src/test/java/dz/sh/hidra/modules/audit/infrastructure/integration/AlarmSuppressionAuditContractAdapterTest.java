/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionAuditContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Verifies Alarm suppression expiry Audit evidence is catalog-backed and system-attributed.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.contract.alarm.AlarmSuppressionAuditContract;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditEventStatus;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditCatalogEntryJpaEntity;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class AlarmSuppressionAuditContractAdapterTest {

    @Test
    void appendsCatalogBackedScheduledJobExpiryEvidence() {
        AuditCatalogEntryJpaRepository catalogs = mock(AuditCatalogEntryJpaRepository.class);
        RecordAuditEventUseCase useCase = mock(RecordAuditEventUseCase.class);
        Instant now = Instant.parse("2026-10-02T09:30:00Z");

        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(
                "EVENT_TYPE", "ALARM_SUPPRESSION_EXPIRED"
        )).thenReturn(Optional.of(catalog(
                "audit-event-alarm-suppression-expired", "EVENT_TYPE", "ALARM_SUPPRESSION_EXPIRED", now
        )));
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(
                "EVENT_CATEGORY", "BUSINESS"
        )).thenReturn(Optional.of(catalog("audit-category-business", "EVENT_CATEGORY", "BUSINESS", now)));
        when(useCase.recordAuditEvent(any(RecordAuditEventCommand.class)))
                .thenReturn(new AuditEventSummaryDto(
                        "audit-1", "alarm", "EXPIRE_ALARM_SUPPRESSION", AuditEventStatus.RECORDED,
                        "hidra-system", AuditActorType.SCHEDULED_JOB, "alarm", "ALARM_SUPPRESSION",
                        "suppression-1", AuditOperation.UPDATE, "corr-1", now, now
                ));

        var adapter = new AlarmSuppressionAuditContractAdapter(catalogs, useCase);
        String id = adapter.appendExpiry(new AlarmSuppressionAuditContract.ExpiryEvidence(
                "suppression-1", "TOPOLOGY_ASSET", "asset-1", "hidra-system", "corr-1", now
        ));

        assertThat(id).isEqualTo("audit-1");
        ArgumentCaptor<RecordAuditEventCommand> command =
                ArgumentCaptor.forClass(RecordAuditEventCommand.class);
        verify(useCase).recordAuditEvent(command.capture());
        assertThat(command.getValue().actorType()).isEqualTo(AuditActorType.SCHEDULED_JOB);
        assertThat(command.getValue().targetType()).isEqualTo("ALARM_SUPPRESSION");
        assertThat(command.getValue().targetId()).isEqualTo("suppression-1");
        assertThat(command.getValue().targetCodeSnapshot()).isEqualTo("TOPOLOGY_ASSET:asset-1");
        assertThat(command.getValue().decisionCode()).isEqualTo("EXPIRED");
    }

    @Test
    void failsClosedWhenExpiryAuditTaxonomyIsMissing() {
        AuditCatalogEntryJpaRepository catalogs = mock(AuditCatalogEntryJpaRepository.class);
        RecordAuditEventUseCase useCase = mock(RecordAuditEventUseCase.class);
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(
                "EVENT_TYPE", "ALARM_SUPPRESSION_EXPIRED"
        )).thenReturn(Optional.empty());

        var adapter = new AlarmSuppressionAuditContractAdapter(catalogs, useCase);

        assertThatThrownBy(() -> adapter.appendExpiry(new AlarmSuppressionAuditContract.ExpiryEvidence(
                "suppression-1", "SOURCE", "source-1", "hidra-system", "corr-1",
                Instant.parse("2026-10-02T09:30:00Z")
        ))).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not provisioned");

        verify(useCase, never()).recordAuditEvent(any());
    }

    private static AuditCatalogEntryJpaEntity catalog(
            String id,
            String catalogName,
            String code,
            Instant now
    ) {
        return new AuditCatalogEntryJpaEntity(
                id, catalogName, code, true, 1, true, now, now
        );
    }
}
