/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectAssessmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DefectAssessment.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for DefectAssessment.
     */
    @Entity
    @Table(name = "hidra_integrity_defect_assessment")
    public class DefectAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "defect_id", nullable = false, length = 80)
    private String defectId;

    @Column(name = "assessment_method_id", nullable = false, length = 80)
    private String assessmentMethodId;

    @Column(name = "assessment_number", nullable = false, length = 80)
    private String assessmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessed_severity", nullable = true, length = 40)
    private FindingSeverity assessedSeverity;

    @Column(name = "failure_pressure", nullable = true, precision = 18, scale = 6)
    private BigDecimal failurePressure;

    @Column(name = "pressure_unit_id", nullable = true, length = 80)
    private String pressureUnitId;

    @Column(name = "safety_factor", nullable = true, precision = 18, scale = 6)
    private BigDecimal safetyFactor;

    @Column(name = "fit_for_service", nullable = false)
    private boolean fitForService;

    @Column(name = "assessment_summary", nullable = true, columnDefinition = "text")
    private String assessmentSummary;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

        protected DefectAssessmentJpaEntity() {
            // Required by JPA.
        }

        public DefectAssessmentJpaEntity(
                String id,
            String defectId,
            String assessmentMethodId,
            String assessmentNumber,
            FindingSeverity assessedSeverity,
            BigDecimal failurePressure,
            String pressureUnitId,
            BigDecimal safetyFactor,
            boolean fitForService,
            String assessmentSummary,
            String assessedByActorId,
            Instant assessedAt,
            String approvedByActorId,
            Instant approvedAt
        ) {
            this.id = id;
        this.defectId = defectId;
        this.assessmentMethodId = assessmentMethodId;
        this.assessmentNumber = assessmentNumber;
        this.assessedSeverity = assessedSeverity;
        this.failurePressure = failurePressure;
        this.pressureUnitId = pressureUnitId;
        this.safetyFactor = safetyFactor;
        this.fitForService = fitForService;
        this.assessmentSummary = assessmentSummary;
        this.assessedByActorId = assessedByActorId;
        this.assessedAt = assessedAt;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        }


    public String id() {
        return id;
    }


    public String defectId() {
        return defectId;
    }


    public String assessmentMethodId() {
        return assessmentMethodId;
    }


    public String assessmentNumber() {
        return assessmentNumber;
    }


    public FindingSeverity assessedSeverity() {
        return assessedSeverity;
    }


    public BigDecimal failurePressure() {
        return failurePressure;
    }


    public String pressureUnitId() {
        return pressureUnitId;
    }


    public BigDecimal safetyFactor() {
        return safetyFactor;
    }


    public boolean fitForService() {
        return fitForService;
    }


    public String assessmentSummary() {
        return assessmentSummary;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public Instant assessedAt() {
        return assessedAt;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }

    }
