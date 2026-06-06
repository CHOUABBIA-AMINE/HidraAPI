/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for facility persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;

/**
 * Spring Data repository for facility persistence entities.
 */
public interface FacilityJpaRepository extends JpaRepository<FacilityJpaEntity, String> {

    Optional<FacilityJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<FacilityJpaEntity> findByFacilityTypeId(String facilityTypeId);

    List<FacilityJpaEntity> findByProductTypeId(String productTypeId);

    List<FacilityJpaEntity> findByStatus(String status);

    List<FacilityJpaEntity> findByOrganizationUnitReferenceCode(String organizationUnitReferenceCode);
}
