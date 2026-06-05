/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for topology equipment persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.EquipmentJpaEntity;

/**
 * Spring Data repository for topology equipment persistence entities.
 *
 * <p>Business role:
 * Provides storage access for topology equipment/component references.
 *
 * <p>Architecture role:
 * This is an infrastructure repository used only by topology persistence adapters. Application
 * services must depend on outbound ports, not on this interface.
 *
 * <p>Validation:
 * Domain validation occurs before mapping. Database constraints enforce required fields and code
 * uniqueness.
 *
 * <p>Usage:
 * Use only from topology repository adapters created in TOP-012.
 */
public interface EquipmentJpaRepository extends JpaRepository<EquipmentJpaEntity, String> {

    Optional<EquipmentJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<EquipmentJpaEntity> findByEquipmentType(String equipmentType);

    List<EquipmentJpaEntity> findByParentAssetTypeAndParentAssetId(String parentAssetType, String parentAssetId);

    List<EquipmentJpaEntity> findByStatus(String status);
}
