/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyJpaRepositoryContractsTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Reflection tests for topology Spring Data repository contracts.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineAppurtenanceJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;

/**
 * Reflection tests for topology Spring Data repository contracts.
 */
class TopologyJpaRepositoryContractsTest {

    @Test
    void shouldExtendJpaRepositoryForEveryTopologyRepository() {
        assertRepository(PipelineSystemJpaRepository.class, PipelineSystemJpaEntity.class);
        assertRepository(PipelineJpaRepository.class, PipelineJpaEntity.class);
        assertRepository(FacilityJpaRepository.class, FacilityJpaEntity.class);
        assertRepository(TopologyNodeJpaRepository.class, TopologyNodeJpaEntity.class);
        assertRepository(PipelineSegmentJpaRepository.class, PipelineSegmentJpaEntity.class);
        assertRepository(PipelineAppurtenanceJpaRepository.class, PipelineAppurtenanceJpaEntity.class);
        assertRepository(TopologyConnectionJpaRepository.class, TopologyConnectionJpaEntity.class);
        assertRepository(EquipmentJpaRepository.class, EquipmentJpaEntity.class);
    }

    @Test
    void shouldExposeCodeLookupMethodsForEveryTopologyRepository() throws NoSuchMethodException {
        assertCodeLookup(PipelineSystemJpaRepository.class, PipelineSystemJpaEntity.class);
        assertCodeLookup(PipelineJpaRepository.class, PipelineJpaEntity.class);
        assertCodeLookup(FacilityJpaRepository.class, FacilityJpaEntity.class);
        assertCodeLookup(TopologyNodeJpaRepository.class, TopologyNodeJpaEntity.class);
        assertCodeLookup(PipelineSegmentJpaRepository.class, PipelineSegmentJpaEntity.class);
        assertCodeLookup(PipelineAppurtenanceJpaRepository.class, PipelineAppurtenanceJpaEntity.class);
        assertCodeLookup(TopologyConnectionJpaRepository.class, TopologyConnectionJpaEntity.class);
        assertCodeLookup(EquipmentJpaRepository.class, EquipmentJpaEntity.class);
    }

    @Test
    void shouldExposeExpectedCatalogForeignKeyFinderMethods() throws NoSuchMethodException {
        assertListMethod(PipelineSystemJpaRepository.class, "findByProductTypeId", String.class);
        assertListMethod(PipelineSystemJpaRepository.class, "findByStatus", String.class);
        assertListMethod(PipelineJpaRepository.class, "findByPipelineSystemId", String.class);
        assertListMethod(PipelineJpaRepository.class, "findByProductTypeId", String.class);
        assertListMethod(FacilityJpaRepository.class, "findByFacilityTypeId", String.class);
        assertListMethod(FacilityJpaRepository.class, "findByProductTypeId", String.class);
        assertListMethod(FacilityJpaRepository.class, "findByOrganizationUnitReferenceCode", String.class);
        assertListMethod(TopologyNodeJpaRepository.class, "findByNodeTypeId", String.class);
        assertListMethod(TopologyNodeJpaRepository.class, "findByFacilityId", String.class);
        assertListMethod(TopologyNodeJpaRepository.class, "findByPipelineAppurtenanceId", String.class);
        assertListMethod(PipelineSegmentJpaRepository.class, "findByPipelineId", String.class);
        assertListMethod(PipelineAppurtenanceJpaRepository.class, "findByAppurtenanceTypeId", String.class);
        assertListMethod(PipelineAppurtenanceJpaRepository.class, "findByValveTypeId", String.class);
        assertListMethod(TopologyConnectionJpaRepository.class, "findByConnectionTypeId", String.class);
        assertListMethod(TopologyConnectionJpaRepository.class, "findByLinkedAssetTypeAndLinkedAssetId", String.class, String.class);
        assertListMethod(EquipmentJpaRepository.class, "findByEquipmentTypeId", String.class);
        assertListMethod(EquipmentJpaRepository.class, "findByParentAssetTypeAndParentAssetId", String.class, String.class);
    }

    private static void assertRepository(Class<?> repositoryType, Class<?> entityType) {
        assertTrue(JpaRepository.class.isAssignableFrom(repositoryType));
        assertTrue(repositoryType.getName().contains("topology.infrastructure.persistence.repository"));
        assertTrue(entityType.getName().contains("topology.infrastructure.persistence.entity"));
    }

    private static void assertCodeLookup(Class<?> repositoryType, Class<?> entityType) throws NoSuchMethodException {
        Method findByCode = repositoryType.getMethod("findByCode", String.class);
        Method existsByCode = repositoryType.getMethod("existsByCode", String.class);

        assertEquals(Optional.class, findByCode.getReturnType());
        assertEquals(boolean.class, existsByCode.getReturnType());
        assertTrue(findByCode.toGenericString().contains(entityType.getSimpleName()));
    }

    private static void assertListMethod(Class<?> repositoryType, String methodName, Class<?>... parameterTypes)
            throws NoSuchMethodException {

        Method method = repositoryType.getMethod(methodName, parameterTypes);
        assertEquals(List.class, method.getReturnType());
    }
}
