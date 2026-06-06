/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Unit tests for OrganizationUnitRepositoryAdapter persistence mapping and station scope columns.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitRepositoryAdapter;

/**
 * Tests the organization unit persistence adapter.
 */
class OrganizationUnitRepositoryAdapterTest {

    private final OrganizationPersistenceMapper mapper = new OrganizationPersistenceMapper();

    @Test
    void shouldPersistStationOrganizationUnitWithNeutralOperationalScopeColumns() {
        OrganizationUnitJpaRepository jpaRepository = mock(OrganizationUnitJpaRepository.class);
        OrganizationUnitRepositoryAdapter adapter = new OrganizationUnitRepositoryAdapter(jpaRepository, mapper);
        OrganizationUnit stationUnit = stationOrganizationUnit();

        when(jpaRepository.save(any(OrganizationUnitJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrganizationUnit savedUnit = adapter.save(stationUnit);

        assertEquals(stationUnit.id(), savedUnit.id());
        assertEquals(OrganizationUnitTypeReference.STATION, savedUnit.type());
        assertTrue(savedUnit.operationalScopeReference().isPresent());
        assertEquals("CS-EAST-01", savedUnit.operationalScopeReference().orElseThrow().scopeCode());
        verify(jpaRepository).save(any(OrganizationUnitJpaEntity.class));
    }

    @Test
    void shouldMapStationOperationalScopeToPersistenceEntityColumns() {
        OrganizationUnit stationUnit = stationOrganizationUnit();

        OrganizationUnitJpaEntity entity = mapper.toEntity(stationUnit);

        assertEquals("organization-out-station", entity.getTypeId());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", entity.getOperationalScopeType());
        assertEquals("station-001", entity.getOperationalScopeId());
        assertEquals("CS-EAST-01", entity.getOperationalScopeCode());
        assertEquals("Compression Station East 01", entity.getOperationalScopeName());
    }

    @Test
    void shouldFindOrganizationUnitsByOperationalScope() {
        OrganizationUnitJpaRepository jpaRepository = mock(OrganizationUnitJpaRepository.class);
        OrganizationUnitRepositoryAdapter adapter = new OrganizationUnitRepositoryAdapter(jpaRepository, mapper);
        OrganizationUnit stationUnit = stationOrganizationUnit();
        OperationalScopeReference scopeReference = stationUnit.operationalScopeReference().orElseThrow();

        when(jpaRepository.findByOperationalScopeTypeAndOperationalScopeCode("TOPOLOGY_COMPRESSION_STATION", "CS-EAST-01"))
                .thenReturn(List.of(mapper.toEntity(stationUnit)));

        List<OrganizationUnit> foundUnits = adapter.findByOperationalScope(scopeReference);

        assertEquals(1, foundUnits.size());
        assertEquals("CS_EAST_01", foundUnits.get(0).code().value());
        assertEquals("CS-EAST-01", foundUnits.get(0).operationalScopeReference().orElseThrow().scopeCode());
        verify(jpaRepository).findByOperationalScopeTypeAndOperationalScopeCode("TOPOLOGY_COMPRESSION_STATION", "CS-EAST-01");
    }

    @Test
    void shouldFindOrganizationUnitByCode() {
        OrganizationUnitJpaRepository jpaRepository = mock(OrganizationUnitJpaRepository.class);
        OrganizationUnitRepositoryAdapter adapter = new OrganizationUnitRepositoryAdapter(jpaRepository, mapper);
        OrganizationUnit regionUnit = OrganizationUnit.create(
                OrganizationUnitCode.of("REGION_EAST"),
                OrganizationUnitName.of("Operational East Region"),
                OrganizationUnitTypeReference.REGION,
                null,
                null);

        when(jpaRepository.findByCode("REGION_EAST")).thenReturn(Optional.of(mapper.toEntity(regionUnit)));

        Optional<OrganizationUnit> foundUnit = adapter.findByCode(OrganizationUnitCode.of("REGION_EAST"));

        assertTrue(foundUnit.isPresent());
        assertEquals("REGION_EAST", foundUnit.orElseThrow().code().value());
        verify(jpaRepository).findByCode("REGION_EAST");
    }

    private static OrganizationUnit stationOrganizationUnit() {
        return OrganizationUnit.createStation(
                OrganizationUnitCode.of("CS_EAST_01"),
                OrganizationUnitName.of("Compression Station East 01"),
                null,
                new OperationalScopeReference(
                        OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                        "station-001",
                        "CS-EAST-01",
                        "Compression Station East 01"));
    }
}
