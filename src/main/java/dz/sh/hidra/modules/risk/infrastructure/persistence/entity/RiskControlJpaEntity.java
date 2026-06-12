/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskControlJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskControl.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RiskControl.
     */
    @Entity
    @Table(name = "hidra_risk_control")
    public class RiskControlJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "control_code", nullable = false, length = 80)
    private String controlCode;

    @Column(name = "control_name", nullable = false, length = 255)
    private String controlName;

    @Column(name = "control_type_id", nullable = false, length = 80)
    private String controlTypeId;

    @Column(name = "control_owner_organization_unit_id", nullable = true, length = 80)
    private String controlOwnerOrganizationUnitId;

    @Column(name = "control_owner_name_snapshot", nullable = true, length = 500)
    private String controlOwnerNameSnapshot;

    @Column(name = "effectiveness_level_id", nullable = true, length = 80)
    private String effectivenessLevelId;

    @Column(name = "effectiveness_justification", nullable = true, columnDefinition = "text")
    private String effectivenessJustification;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "verified_by_actor_id", nullable = true, length = 80)
    private String verifiedByActorId;

    @Column(name = "verified_at", nullable = true)
    private Instant verifiedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskControlJpaEntity() {
            // Required by JPA.
        }

        public RiskControlJpaEntity(
                String id,
            String riskAssessmentId,
            String controlCode,
            String controlName,
            String controlTypeId,
            String controlOwnerOrganizationUnitId,
            String controlOwnerNameSnapshot,
            String effectivenessLevelId,
            String effectivenessJustification,
            boolean verified,
            String verifiedByActorId,
            Instant verifiedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.controlCode = controlCode;
        this.controlName = controlName;
        this.controlTypeId = controlTypeId;
        this.controlOwnerOrganizationUnitId = controlOwnerOrganizationUnitId;
        this.controlOwnerNameSnapshot = controlOwnerNameSnapshot;
        this.effectivenessLevelId = effectivenessLevelId;
        this.effectivenessJustification = effectivenessJustification;
        this.verified = verified;
        this.verifiedByActorId = verifiedByActorId;
        this.verifiedAt = verifiedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String controlCode() {
        return controlCode;
    }


    public String controlName() {
        return controlName;
    }


    public String controlTypeId() {
        return controlTypeId;
    }


    public String controlOwnerOrganizationUnitId() {
        return controlOwnerOrganizationUnitId;
    }


    public String controlOwnerNameSnapshot() {
        return controlOwnerNameSnapshot;
    }


    public String effectivenessLevelId() {
        return effectivenessLevelId;
    }


    public String effectivenessJustification() {
        return effectivenessJustification;
    }


    public boolean verified() {
        return verified;
    }


    public String verifiedByActorId() {
        return verifiedByActorId;
    }


    public Instant verifiedAt() {
        return verifiedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
