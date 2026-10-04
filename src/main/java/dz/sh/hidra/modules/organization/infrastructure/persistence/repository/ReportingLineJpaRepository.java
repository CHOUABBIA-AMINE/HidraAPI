/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository and fail-closed policy queries for ReportingLine.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persists reporting lines and exposes same-module integrity/policy checks required before save.
 */
@Repository
public interface ReportingLineJpaRepository extends JpaRepository<ReportingLineJpaEntity, String> {

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_org_employee
                WHERE id = :id
                  AND status = 'ACTIVE'
            )
            """, nativeQuery = true)
    boolean existsActiveEmployee(@Param("id") String id);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_org_position
                WHERE id = :id
            )
            """, nativeQuery = true)
    boolean existsPosition(@Param("id") String id);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_org_unit
                WHERE id = :id
            )
            """, nativeQuery = true)
    boolean existsOrganizationUnit(@Param("id") String id);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_org_reporting_line line
                JOIN hidra_org_reporting_line_type type
                  ON type.id = line.reporting_line_type_id
                WHERE line.id <> :lineId
                  AND line.active = true
                  AND line.source_type = 'EMPLOYEE'
                  AND line.source_id = :employeeId
                  AND type.code = 'LINE'
            )
            """, nativeQuery = true)
    boolean existsOtherActiveEmployeeLine(
            @Param("lineId") String lineId,
            @Param("employeeId") String employeeId
    );

    @Query(value = """
            WITH RECURSIVE reachable(subject_type, subject_id) AS (
                SELECT CAST(:targetType AS varchar(80)), CAST(:targetId AS varchar(80))
                UNION
                SELECT line.target_type, line.target_id
                FROM hidra_org_reporting_line line
                JOIN hidra_org_reporting_line_type type
                  ON type.id = line.reporting_line_type_id
                 AND type.code = 'LINE'
                JOIN reachable current_subject
                  ON line.source_type = current_subject.subject_type
                 AND line.source_id = current_subject.subject_id
                WHERE line.id <> :lineId
                  AND line.active = true
            )
            SELECT EXISTS (
                SELECT 1
                FROM reachable
                WHERE subject_type = :sourceType
                  AND subject_id = :sourceId
            )
            """, nativeQuery = true)
    boolean wouldCreateLineCycle(
            @Param("lineId") String lineId,
            @Param("sourceType") String sourceType,
            @Param("sourceId") String sourceId,
            @Param("targetType") String targetType,
            @Param("targetId") String targetId
    );
}
