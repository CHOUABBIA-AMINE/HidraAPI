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
 */
public interface EquipmentJpaRepository extends JpaRepository<EquipmentJpaEntity, String> {

    Optional<EquipmentJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<EquipmentJpaEntity> findByEquipmentTypeId(String equipmentTypeId);

    List<EquipmentJpaEntity> findByParentAssetTypeAndParentAssetId(String parentAssetType, String parentAssetId);

    List<EquipmentJpaEntity> findByStatus(String status);
}
