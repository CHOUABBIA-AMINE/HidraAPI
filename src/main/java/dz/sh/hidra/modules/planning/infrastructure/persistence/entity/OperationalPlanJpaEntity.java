/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OperationalPlan.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OperationalPlan.
     */
    @Entity
    @Table(name = "hidra_planning_operational_plan")
    public class OperationalPlanJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "period_id", nullable = false, length = 80)
    private String periodId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "plan_type_id", nullable = false, length = 80)
    private String planTypeId;

    @Column(name = "product_type_id", nullable = true, length = 80)
    private String productTypeId;

    @Column(name = "topology_scope_type", nullable = false, length = 160)
    private String topologyScopeType;

    @Column(name = "topology_scope_id", nullable = false, length = 80)
    private String topologyScopeId;

    @Column(name = "topology_scope_code", nullable = false, length = 160)
    private String topologyScopeCode;

    @Column(name = "topology_scope_name_snapshot", nullable = true, length = 500)
    private String topologyScopeNameSnapshot;

    @Column(name = "responsible_organization_unit_id", nullable = true, length = 80)
    private String responsibleOrganizationUnitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OperationalPlanStatus status;

    @Column(name = "current_revision_id", nullable = true, length = 80)
    private String currentRevisionId;

    @Column(name = "approved_revision_id", nullable = true, length = 80)
    private String approvedRevisionId;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected OperationalPlanJpaEntity() {
            // Required by JPA.
        }

        public OperationalPlanJpaEntity(
                String id,
            String periodId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String planTypeId,
            String productTypeId,
            String topologyScopeType,
            String topologyScopeId,
            String topologyScopeCode,
            String topologyScopeNameSnapshot,
            String responsibleOrganizationUnitId,
            OperationalPlanStatus status,
            String currentRevisionId,
            String approvedRevisionId,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.periodId = periodId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.planTypeId = planTypeId;
        this.productTypeId = productTypeId;
        this.topologyScopeType = topologyScopeType;
        this.topologyScopeId = topologyScopeId;
        this.topologyScopeCode = topologyScopeCode;
        this.topologyScopeNameSnapshot = topologyScopeNameSnapshot;
        this.responsibleOrganizationUnitId = responsibleOrganizationUnitId;
        this.status = status;
        this.currentRevisionId = currentRevisionId;
        this.approvedRevisionId = approvedRevisionId;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String periodId() {
        return periodId;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String planTypeId() {
        return planTypeId;
    }


    public String productTypeId() {
        return productTypeId;
    }


    public String topologyScopeType() {
        return topologyScopeType;
    }


    public String topologyScopeId() {
        return topologyScopeId;
    }


    public String topologyScopeCode() {
        return topologyScopeCode;
    }


    public String topologyScopeNameSnapshot() {
        return topologyScopeNameSnapshot;
    }


    public String responsibleOrganizationUnitId() {
        return responsibleOrganizationUnitId;
    }


    public OperationalPlanStatus status() {
        return status;
    }


    public String currentRevisionId() {
        return currentRevisionId;
    }


    public String approvedRevisionId() {
        return approvedRevisionId;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
