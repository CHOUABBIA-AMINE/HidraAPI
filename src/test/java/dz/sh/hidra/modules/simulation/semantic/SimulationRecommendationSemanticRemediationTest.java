/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.semantic
 *
 * @Description : Enforces the accepted Simulation owner boundary.
 *
 */
package dz.sh.hidra.modules.simulation.semantic;

import dz.sh.hidra.modules.simulation.domain.model.SimulationRecommendation;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRecommendationStatus;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JpaSimulationRecommendationRepositoryAdapter;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationCatalogEntryJpaEntity;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.audit.application.contract.simulation.SimulationRecommendationAuditContract;
import dz.sh.hidra.modules.simulation.application.service.SimulationApplicationService;
import dz.sh.hidra.modules.simulation.application.command.PublishSimulationRecommendationCommand;
import dz.sh.hidra.modules.simulation.application.port.out.*;
import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class SimulationRecommendationSemanticRemediationTest {
    private static final Instant NOW=Instant.parse("2026-10-08T00:00:00Z");
    private final SimulationRecommendationJpaRepository repository=mock(SimulationRecommendationJpaRepository.class);
    private final SimulationCatalogEntryJpaRepository catalogs=mock(SimulationCatalogEntryJpaRepository.class);
    private final SimulationOptimizationCandidateJpaRepository candidates=mock(SimulationOptimizationCandidateJpaRepository.class);
    private final SimulationRunJpaRepository runs=mock(SimulationRunJpaRepository.class);
    private final SimulationRecommendationAuditContract audit=mock(SimulationRecommendationAuditContract.class);
    private final EntityManager em=mock(EntityManager.class);
    private final JpaSimulationRecommendationRepositoryAdapter adapter=
            new JpaSimulationRecommendationRepositoryAdapter(repository,catalogs,candidates,runs,audit,em);

    @Test void requiredContentFailsAndOptionalMetadataRemainsOptional() {
        for(String blank:new String[]{null,""," "}) {
            assertThatThrownBy(() -> model(blank,"Description",NOW,null,null)).isInstanceOf(InvalidSimulationValueException.class);
            assertThatThrownBy(() -> model("Title",blank,NOW,null,null)).isInstanceOf(InvalidSimulationValueException.class);
        }
        assertThatThrownBy(() -> model("Title","Description",null,null,null)).isInstanceOf(InvalidSimulationValueException.class);
        assertThat(model(" Title "," Description ",NOW,null,null).title()).isEqualTo("Title");
        assertThat(model("Title","Description",NOW,null,null).publishedByActorId()).isNull();
    }
    @Test void missingRunCandidateWrongFamilyInactiveAndMissingCatalogFailClosed() {
        var model=model("Title","Description",NOW,"candidate","confidence");
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("run");
        when(runs.existsById("run")).thenReturn(true);
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("candidate");
        when(candidates.existsById("candidate")).thenReturn(true);
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("catalog");
        for(var row:new SimulationCatalogEntryJpaEntity[]{catalog("type","OTHER",true),catalog("type","SIMULATION_RECOMMENDATION_TYPE",false)}) {
            when(catalogs.findLockedById("type")).thenReturn(Optional.of(row));
            assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("SIMULATION_RECOMMENDATION_TYPE");
        }
        when(catalogs.findLockedById("type")).thenReturn(Optional.of(catalog("type","SIMULATION_RECOMMENDATION_TYPE",true)));
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("catalog");
        for(var row:new SimulationCatalogEntryJpaEntity[]{catalog("confidence","OTHER",true),catalog("confidence","SIMULATION_CONFIDENCE_LEVEL",false)}) {
            when(catalogs.findLockedById("confidence")).thenReturn(Optional.of(row));
            assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("SIMULATION_CONFIDENCE_LEVEL");
        }
        verify(em,never()).persist(any());verifyNoInteractions(audit);
    }
    @Test void publicationUsesActualTimeFlushesBeforeAuditAndPreservesAbsentActor() {
        eligible();when(audit.appendPublished(any())).thenReturn("audit");
        Instant before=Instant.now();var saved=adapter.publish(model("Title","Description",NOW,null,null));
        assertThat(saved.publishedAt()).isBetween(before,Instant.now());
        var order=inOrder(em,audit);order.verify(em).persist(any());order.verify(em).flush();order.verify(audit).appendPublished(any());
        var captor=org.mockito.ArgumentCaptor.forClass(SimulationRecommendationAuditContract.PublicationEvidence.class);
        verify(audit).appendPublished(captor.capture());
        assertThat(captor.getValue().actorId()).isNull();
        assertThat(captor.getValue().occurredAt()).isEqualTo(saved.publishedAt());
        verifyNoInteractions(candidates);
    }
    @Test void genericSaveDuplicateIdMissingReceiptAndCompatibilityCannotBypassPublication() {
        var model=model("Title","Description",NOW,null,null);
        assertThatThrownBy(() -> adapter.save(model)).hasMessageContaining("audited");
        assertThatThrownBy(() -> new JpaSimulationRecommendationRepositoryAdapter(repository).publish(model)).hasMessageContaining("unavailable");
        eligible();when(repository.existsById("recommendation")).thenReturn(true);
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("new recommendation");
        when(repository.existsById("recommendation")).thenReturn(false);
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("receipt");
        when(audit.appendPublished(any())).thenThrow(new IllegalStateException("Audit denied"));
        assertThatThrownBy(() -> adapter.publish(model)).hasMessageContaining("Audit denied");
    }
    @Test void applicationPublicationSelectsAuditedPortAndLegacyPortFailsClosed() {
        var port=mock(SimulationRecommendationRepositoryPort.class);
        var service=new SimulationApplicationService(mock(SimulationModelRepositoryPort.class),mock(SimulationScenarioRepositoryPort.class),
                mock(SimulationRunRepositoryPort.class),port,mock(SimulationTopologyScopeContract.class));
        when(port.publish(any())).thenAnswer(i -> i.getArgument(0));
        service.publishSimulationRecommendation(new PublishSimulationRecommendationCommand("run",null,"type","Title","Description",null,null,null,null));
        verify(port).publish(any());verify(port,never()).save(any());
        SimulationRecommendationRepositoryPort legacy=new SimulationRecommendationRepositoryPort() {
            public SimulationRecommendation save(SimulationRecommendation m){throw new AssertionError("Legacy save bypass");}
            public Optional<SimulationRecommendation> findById(String id){return Optional.empty();}
        };
        assertThatThrownBy(() -> legacy.publish(model("Title","Description",NOW,null,null))).hasMessageContaining("unavailable");
    }
    private void eligible() {
        when(runs.existsById("run")).thenReturn(true);
        when(catalogs.findLockedById("type")).thenReturn(Optional.of(catalog("type","SIMULATION_RECOMMENDATION_TYPE",true)));
    }
    private static SimulationCatalogEntryJpaEntity catalog(String id,String family,boolean active) {
        return new SimulationCatalogEntryJpaEntity(id,family,"code",active,0,false,NOW,NOW);
    }
    private static SimulationRecommendation model(String title,String description,Instant created,String candidate,String confidence) {
        return new SimulationRecommendation("recommendation","run",candidate,"type",SimulationRecommendationStatus.PUBLISHED,title,description,
                confidence,null,null,null,NOW,created);
    }
}
