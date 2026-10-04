/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository and hierarchy/type policy queries for OrganizationUnit.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persists OrganizationUnit and exposes fail-closed hierarchy/type checks.
 */
@Repository
public interface OrganizationUnitJpaRepository
        extends JpaRepository<OrganizationUnitJpaEntity, String> {

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_org_unit_type unit_type
                WHERE unit_type.id = :unitTypeId
                  AND unit_type.active = true
            )
            """, nativeQuery = true)
    boolean existsActiveUnitType(@Param("unitTypeId") String unitTypeId);

    @Query(value = """
            WITH RECURSIVE ancestry(id, parent_unit_id, path, cycle) AS (
                SELECT
                    unit.id,
                    unit.parent_unit_id,
                    ARRAY[unit.id]::varchar[],
                    false
                FROM hidra_org_unit unit
                WHERE unit.id = :parentUnitId

                UNION ALL

                SELECT
                    parent.id,
                    parent.parent_unit_id,
                    ancestry.path || parent.id,
                    parent.id = ANY(ancestry.path)
                FROM ancestry
                JOIN hidra_org_unit parent
                  ON parent.id = ancestry.parent_unit_id
                WHERE ancestry.parent_unit_id IS NOT NULL
                  AND NOT ancestry.cycle
            )
            SELECT EXISTS (
                SELECT 1
                FROM ancestry
                WHERE id = :unitId
                   OR cycle = true
            )
            """, nativeQuery = true)
    boolean wouldCreateHierarchyCycle(
            @Param("unitId") String unitId,
            @Param("parentUnitId") String parentUnitId
    );
}
