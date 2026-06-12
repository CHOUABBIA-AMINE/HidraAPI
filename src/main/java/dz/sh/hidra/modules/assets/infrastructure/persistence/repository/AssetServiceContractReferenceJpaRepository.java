/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetServiceContractReferenceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AssetServiceContractReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.repository;

import dz.sh.hidra.modules.assets.infrastructure.persistence.entity.AssetServiceContractReferenceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AssetServiceContractReference.
 */
@Repository
public interface AssetServiceContractReferenceJpaRepository extends JpaRepository<AssetServiceContractReferenceJpaEntity, String> {
}
