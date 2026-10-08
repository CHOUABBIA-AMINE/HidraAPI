/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentAuditContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Enforces the accepted Simulation owner boundary.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.contract.risk.RiskAssessmentAuditContract.ApprovalEvidence;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.service.AuditInputPolicy;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditCatalogEntryJpaEntity;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class RiskAssessmentAuditContractAdapterTest {
    private static final Instant NOW=Instant.parse("2026-10-08T00:00:00Z");
    private final AuditCatalogEntryJpaRepository catalogs=mock(AuditCatalogEntryJpaRepository.class);
    private final RecordAuditEventUseCase recorder=mock(RecordAuditEventUseCase.class);
    private final RiskAssessmentAuditContractAdapter adapter=
            new RiskAssessmentAuditContractAdapter(catalogs,recorder,new AuditInputPolicy());

    @Test void emitsSanitizedScalarEvidenceWithActualTimeAndNoInventedActor() {
        taxonomy();var result=mock(AuditEventSummaryDto.class);when(result.id()).thenReturn("event");when(recorder.recordAuditEvent(any())).thenReturn(result);
        assertThat(adapter.appendApproved(evidence("actor"))).isEqualTo("event");
        var captor=org.mockito.ArgumentCaptor.forClass(RecordAuditEventCommand.class);verify(recorder).recordAuditEvent(captor.capture());
        var command=captor.getValue();assertThat(command.sourceEventId()).isEqualTo("recommendation");
        assertThat(command.actorId()).isEqualTo("actor");assertThat(command.occurredAt()).isEqualTo(NOW);
        assertThat(command.payloadJson()).contains("reviewerId","reviewActionId","workflowActionId","approvedAt").doesNotContain("description");
        assertThat(command.targetId()).isEqualTo("recommendation");
    }
    @Test void suppliedActorIsPreservedAndCredentialMaterialIsRejected() {
        taxonomy();var result=mock(AuditEventSummaryDto.class);when(result.id()).thenReturn("event");when(recorder.recordAuditEvent(any())).thenReturn(result);
        adapter.appendApproved(evidence("actor"));
        var captor=org.mockito.ArgumentCaptor.forClass(RecordAuditEventCommand.class);verify(recorder).recordAuditEvent(captor.capture());
        assertThat(captor.getValue().actorId()).isEqualTo("actor");
        assertThatThrownBy(() -> adapter.appendApproved(evidence("token=secret-value"))).hasMessageContaining("Credential");
    }
    @Test void missingTaxonomyFailsRatherThanOmittingAudit() {
        assertThatThrownBy(() -> adapter.appendApproved(evidence("actor"))).hasMessageContaining("taxonomy");
        verifyNoInteractions(recorder);
    }
    private void taxonomy() {
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue("EVENT_TYPE","RISK_ASSESSMENT_APPROVED"))
                .thenReturn(Optional.of(new AuditCatalogEntryJpaEntity("event-type","EVENT_TYPE","RISK_ASSESSMENT_APPROVED",true,0,true,NOW,NOW)));
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue("EVENT_CATEGORY","BUSINESS"))
                .thenReturn(Optional.of(new AuditCatalogEntryJpaEntity("category","EVENT_CATEGORY","BUSINESS",true,0,true,NOW,NOW)));
    }
    private ApprovalEvidence evidence(String actor) {return new ApprovalEvidence("recommendation","A-1",actor,"username","Actor","reviewer","w","t","action","review",NOW);}
}
