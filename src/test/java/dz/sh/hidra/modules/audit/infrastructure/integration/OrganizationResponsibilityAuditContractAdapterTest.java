/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityAuditContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Verifies Organization responsibility audit evidence resolves controlled Audit taxonomy.
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
import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
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

class OrganizationResponsibilityAuditContractAdapterTest {

    @Test
    void resolvesCatalogCodesAndAppendsAuditEvent() {
        AuditCatalogEntryJpaRepository catalogs = mock(AuditCatalogEntryJpaRepository.class);
        RecordAuditEventUseCase useCase = mock(RecordAuditEventUseCase.class);
        Instant now = Instant.parse("2026-09-29T09:00:00Z");

        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(
                "EVENT_TYPE",
                "ORGANIZATION_RESPONSIBILITY_ASSIGNED"
        )).thenReturn(Optional.of(catalog(
                "event-type-id",
                "EVENT_TYPE",
                "ORGANIZATION_RESPONSIBILITY_ASSIGNED",
                now
        )));
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(
                "EVENT_CATEGORY",
                "BUSINESS"
        )).thenReturn(Optional.of(catalog("business-id", "EVENT_CATEGORY", "BUSINESS", now)));
        when(useCase.recordAuditEvent(any(RecordAuditEventCommand.class)))
                .thenReturn(new AuditEventSummaryDto(
                        "audit-1",
                        "organization",
                        "ASSIGN_RESPONSIBILITY",
                        AuditEventStatus.RECORDED,
                        "actor-1",
                        AuditActorType.USER,
                        "organization",
                        "RESPONSIBILITY_ASSIGNMENT",
                        "assignment-1",
                        AuditOperation.CREATE,
                        "correlation-1",
                        now,
                        now
                ));

        var adapter = new OrganizationResponsibilityAuditContractAdapter(catalogs, useCase);
        String id = adapter.append(new OrganizationResponsibilityAuditContract.Event(
                "ORGANIZATION_RESPONSIBILITY_ASSIGNED",
                "BUSINESS",
                "ResponsibilityAssignmentApplicationService",
                "ASSIGN_RESPONSIBILITY",
                "actor-1",
                "operator",
                "Operator",
                "RESPONSIBILITY_ASSIGNMENT",
                "assignment-1",
                OrganizationResponsibilityAuditContract.Operation.CREATE,
                "APPROVE",
                null,
                "wf-1",
                "task-1",
                "action-1",
                "request-1",
                "correlation-1",
                now
        ));

        assertThat(id).isEqualTo("audit-1");
        ArgumentCaptor<RecordAuditEventCommand> command =
                ArgumentCaptor.forClass(RecordAuditEventCommand.class);
        verify(useCase).recordAuditEvent(command.capture());
        assertThat(command.getValue().eventTypeId()).isEqualTo("event-type-id");
        assertThat(command.getValue().eventCategoryId()).isEqualTo("business-id");
        assertThat(command.getValue().workflowInstanceId()).isEqualTo("wf-1");
        assertThat(command.getValue().correlationId()).isEqualTo("correlation-1");
    }

    @Test
    void failsClosedWhenAuditTaxonomyIsNotProvisioned() {
        AuditCatalogEntryJpaRepository catalogs = mock(AuditCatalogEntryJpaRepository.class);
        RecordAuditEventUseCase useCase = mock(RecordAuditEventUseCase.class);
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(
                "EVENT_TYPE",
                "MISSING_TYPE"
        )).thenReturn(Optional.empty());

        var adapter = new OrganizationResponsibilityAuditContractAdapter(catalogs, useCase);

        assertThatThrownBy(() -> adapter.append(new OrganizationResponsibilityAuditContract.Event(
                "MISSING_TYPE",
                "BUSINESS",
                "service",
                "ACTION",
                "actor-1",
                null,
                null,
                "RESPONSIBILITY_ASSIGNMENT",
                "assignment-1",
                OrganizationResponsibilityAuditContract.Operation.UPDATE,
                null,
                null,
                null,
                null,
                null,
                null,
                "correlation-1",
                Instant.parse("2026-09-29T09:00:00Z")
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
                id,
                catalogName,
                code,
                true,
                1,
                true,
                now,
                now
        );
    }
}
