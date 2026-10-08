/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateChangeSemanticRemediationTest
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

import dz.sh.hidra.modules.simulation.domain.model.SimulationCandidateChange;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JpaSimulationCandidateChangeRepositoryAdapter;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationCatalogEntryJpaEntity;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyTargetContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class SimulationCandidateChangeSemanticRemediationTest {
    private static final Instant NOW = Instant.parse("2026-10-08T00:00:00Z");
    private final SimulationCandidateChangeJpaRepository repository = mock(SimulationCandidateChangeJpaRepository.class);
    private final SimulationCatalogEntryJpaRepository catalogs = mock(SimulationCatalogEntryJpaRepository.class);
    private final SimulationTopologyTargetContract targets = mock(SimulationTopologyTargetContract.class);
    private final JpaSimulationCandidateChangeRepositoryAdapter adapter =
            new JpaSimulationCandidateChangeRepositoryAdapter(repository, catalogs, targets);

    @Test void requiredValuesFailBeforePersistence() {
        for (String value : new String[]{null,""," "}) {
            assertThatThrownBy(() -> change(value,"value")).isInstanceOf(InvalidSimulationValueException.class);
            assertThatThrownBy(() -> change("PIPELINE",value)).isInstanceOf(InvalidSimulationValueException.class);
        }
        assertThat(change(" PIPELINE "," value ").afterValue()).isEqualTo("value");
        assertThat(change("PIPELINE","value").descriptiveOnly()).isTrue();
    }
    @Test void missingWrongFamilyAndInactiveCatalogFailClosed() {
        when(catalogs.findLockedById("type")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> adapter.save(change("PIPELINE","value"))).isInstanceOf(InvalidSimulationValueException.class);
        for (var type : new SimulationCatalogEntryJpaEntity[]{catalog("OTHER",true),catalog("SIMULATION_CHANGE_TYPE",false)}) {
            when(catalogs.findLockedById("type")).thenReturn(Optional.of(type));
            assertThatThrownBy(() -> adapter.save(change("PIPELINE","value"))).isInstanceOf(InvalidSimulationValueException.class);
        }
        verify(repository,never()).save(any());
    }
    @Test void ownerLookupRejectsMissingTargetAndAdmitsExistingTarget() {
        when(catalogs.findLockedById("type")).thenReturn(Optional.of(catalog("SIMULATION_CHANGE_TYPE",true)));
        assertThatThrownBy(() -> adapter.save(change("PIPELINE","value"))).hasMessageContaining("Topology");
        verify(repository,never()).save(any());
        when(targets.exists("PIPELINE","target")).thenReturn(true);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(adapter.save(change("PIPELINE","value")).targetId()).isEqualTo("target");
    }
    @Test void retiredHistoricalTypeIsPreservedAndCompatibilityFailsClosed() {
        var model=change("PIPELINE","value");
        when(repository.findById("change")).thenReturn(Optional.of(SimulationPersistenceMapper.toEntity(model)));
        when(catalogs.findLockedById("type")).thenReturn(Optional.of(catalog("SIMULATION_CHANGE_TYPE",false)));
        when(targets.exists("PIPELINE","target")).thenReturn(true);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(adapter.save(model)).isEqualTo(model);
        assertThatThrownBy(() -> new JpaSimulationCandidateChangeRepositoryAdapter(repository).save(model))
                .hasMessageContaining("unavailable");
    }
    private static SimulationCandidateChange change(String type,String after) {
        return new SimulationCandidateChange("change","candidate","type",type,"target",null,after,null,false,false,true,null,NOW);
    }
    private static SimulationCatalogEntryJpaEntity catalog(String family,boolean active) {
        return new SimulationCatalogEntryJpaEntity("type",family,"code",active,0,false,NOW,NOW);
    }
}
