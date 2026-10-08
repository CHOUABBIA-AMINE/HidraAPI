/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrixCellJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository and fail-closed semantic lookups for RiskMatrixCell.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.repository;

import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.RiskMatrixCellJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persists matrix cells and verifies coordinate/catalog-family semantics before save.
 */
@Repository
public interface RiskMatrixCellJpaRepository
        extends JpaRepository<RiskMatrixCellJpaEntity, String> {

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_risk_matrix_cell cell
                WHERE cell.id <> :cellId
                  AND cell.risk_matrix_id = :riskMatrixId
                  AND cell.likelihood_level_id = :likelihoodLevelId
                  AND cell.consequence_level_id = :consequenceLevelId
            )
            """, nativeQuery = true)
    boolean existsOtherAtCoordinate(
            @Param("cellId") String cellId,
            @Param("riskMatrixId") String riskMatrixId,
            @Param("likelihoodLevelId") String likelihoodLevelId,
            @Param("consequenceLevelId") String consequenceLevelId
    );

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_risk_catalog_entry entry
                WHERE entry.id = :entryId
                  AND entry.catalog_name = :catalogName
            )
            """, nativeQuery = true)
    boolean existsCatalogEntryInFamily(
            @Param("entryId") String entryId,
            @Param("catalogName") String catalogName
    );

    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_READ)
    @org.springframework.data.jpa.repository.Query("select e from RiskMatrixCellJpaEntity e where e.id = :id")
    java.util.Optional<dz.sh.hidra.modules.risk.infrastructure.persistence.entity.RiskMatrixCellJpaEntity> findLocked(
            @org.springframework.data.repository.query.Param("id") String id);
}
