/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystemJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for ExternalSystem.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.repository;

import dz.sh.hidra.modules.integration.infrastructure.persistence.entity.ExternalSystemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ExternalSystem.
 */
@Repository
public interface ExternalSystemJpaRepository extends JpaRepository<ExternalSystemJpaEntity, String> {

    boolean existsByCode(String code);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_integration_catalog_entry
            WHERE id = :systemTypeId
              AND catalog_name = 'EXTERNAL_SYSTEM_TYPE'
            """, nativeQuery = true)
    boolean isExternalSystemType(@Param("systemTypeId") String systemTypeId);
}
