/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationAuditContractAdapterTest
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

import dz.sh.hidra.modules.audit.application.contract.simulation.SimulationRecommendationAuditContract.PublicationEvidence;
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

class SimulationRecommendationAuditContractAdapterTest {
    private static final Instant NOW=Instant.parse("2026-10-08T00:00:00Z");
    private final AuditCatalogEntryJpaRepository catalogs=mock(AuditCatalogEntryJpaRepository.class);
    private final RecordAuditEventUseCase recorder=mock(RecordAuditEventUseCase.class);
    private final SimulationRecommendationAuditContractAdapter adapter=
            new SimulationRecommendationAuditContractAdapter(catalogs,recorder,new AuditInputPolicy());

    @Test void emitsSanitizedScalarEvidenceWithActualTimeAndNoInventedActor() {
        taxonomy();var result=mock(AuditEventSummaryDto.class);when(result.id()).thenReturn("event");when(recorder.recordAuditEvent(any())).thenReturn(result);
        assertThat(adapter.appendPublished(evidence(null))).isEqualTo("event");
        var captor=org.mockito.ArgumentCaptor.forClass(RecordAuditEventCommand.class);verify(recorder).recordAuditEvent(captor.capture());
        var command=captor.getValue();assertThat(command.sourceEventId()).isEqualTo("recommendation");
        assertThat(command.actorId()).isNull();assertThat(command.occurredAt()).isEqualTo(NOW);
        assertThat(command.payloadJson()).contains("runId","candidateId","recommendationTypeId","publishedAt").doesNotContain("description");
        assertThat(command.targetId()).isEqualTo("recommendation");
    }
    @Test void suppliedActorIsPreservedAndCredentialMaterialIsRejected() {
        taxonomy();var result=mock(AuditEventSummaryDto.class);when(result.id()).thenReturn("event");when(recorder.recordAuditEvent(any())).thenReturn(result);
        adapter.appendPublished(evidence("actor"));
        var captor=org.mockito.ArgumentCaptor.forClass(RecordAuditEventCommand.class);verify(recorder).recordAuditEvent(captor.capture());
        assertThat(captor.getValue().actorId()).isEqualTo("actor");
        assertThatThrownBy(() -> adapter.appendPublished(evidence("token=secret-value"))).hasMessageContaining("Credential");
    }
    @Test void missingTaxonomyFailsRatherThanOmittingAudit() {
        assertThatThrownBy(() -> adapter.appendPublished(evidence(null))).hasMessageContaining("taxonomy");
        verifyNoInteractions(recorder);
    }
    private void taxonomy() {
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue("EVENT_TYPE","SIMULATION_RECOMMENDATION_PUBLISHED"))
                .thenReturn(Optional.of(new AuditCatalogEntryJpaEntity("event-type","EVENT_TYPE","SIMULATION_RECOMMENDATION_PUBLISHED",true,0,true,NOW,NOW)));
        when(catalogs.findFirstByCatalogNameAndCodeAndActiveTrue("EVENT_CATEGORY","BUSINESS"))
                .thenReturn(Optional.of(new AuditCatalogEntryJpaEntity("category","EVENT_CATEGORY","BUSINESS",true,0,true,NOW,NOW)));
    }
    private PublicationEvidence evidence(String actor) {return new PublicationEvidence("recommendation","run","candidate","type",actor,NOW);}
}
