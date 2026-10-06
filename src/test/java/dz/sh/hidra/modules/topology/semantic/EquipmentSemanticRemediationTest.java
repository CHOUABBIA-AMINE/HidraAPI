/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.semantic
 *
 * @Description : Verifies catalog-only Equipment classification and Party-owned manufacturer validation.
 *
 */
package dz.sh.hidra.modules.topology.semantic;

import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.value.EquipmentStatus;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JpaEquipmentRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentTypeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyReferenceContract;
import java.time.Instant;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class EquipmentSemanticRemediationTest {
    @Test
    void rejectsMissingManufacturerAndPropagatesOwnerLookupFailureBeforeWrite() {
        var repository = mock(EquipmentJpaRepository.class);
        var parties = mock(TopologyPartyReferenceContract.class);
        var adapter = new JpaEquipmentRepositoryAdapter(repository, parties);
        assertThatThrownBy(() -> adapter.save(equipment("manufacturer")))
            .isInstanceOf(InvalidTopologyValueException.class);
        when(parties.exists("manufacturer")).thenThrow(new IllegalStateException("owner unavailable"));
        assertThatThrownBy(() -> adapter.save(equipment("manufacturer")))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("owner unavailable");
        verifyNoInteractions(repository);
    }
    @Test
    void preservesHistoricalManufacturerSnapshotsOnEverySave() {
        var repository = mock(EquipmentJpaRepository.class);
        var parties = mock(TopologyPartyReferenceContract.class);
        when(parties.exists("manufacturer")).thenReturn(true);
        when(repository.saveAndFlush(any(EquipmentJpaEntity.class))).thenAnswer(i -> i.getArgument(0));
        var adapter = new JpaEquipmentRepositoryAdapter(repository, parties);
        var saved = adapter.save(equipment("manufacturer"));
        assertThat(saved.manufacturerPartyCodeSnapshot()).isEqualTo("OLD-CODE");
        assertThat(saved.manufacturerPartyNameSnapshot()).isEqualTo("Historical name");
        adapter.save(saved);
        verify(parties, times(2)).exists("manufacturer");
        assertThat(TopologyPersistenceMapper.toDomain(TopologyPersistenceMapper.toEntity(saved))).isEqualTo(saved);
    }
    @Test
    void allowsOptionalManufacturerAndCustomCatalogIdentityWithoutEnumClassification() {
        var repository = mock(EquipmentJpaRepository.class);
        var parties = mock(TopologyPartyReferenceContract.class);
        when(repository.saveAndFlush(any(EquipmentJpaEntity.class))).thenAnswer(i -> i.getArgument(0));
        var saved = new JpaEquipmentRepositoryAdapter(repository, parties).save(equipment(null));
        assertThat(saved.equipmentTypeId()).isEqualTo("custom-catalog-type");
        verifyNoInteractions(parties);
        assertThat(Arrays.stream(Equipment.class.getRecordComponents()).map(x -> x.getName())).doesNotContain("equipmentKind");
        assertThat(Arrays.stream(EquipmentTypeJpaEntity.class.getDeclaredFields()).map(x -> x.getName())).doesNotContain("equipmentKind");
    }
    private Equipment equipment(String manufacturer) {
        var now = Instant.parse("2026-10-06T00:00:00Z");
        return new Equipment("equipment","EQ-1","name",null,null,null,"custom-catalog-type",
            manufacturer,"OLD-CODE","Historical name",EquipmentStatus.ACTIVE,null,null,now,now);
    }
}
