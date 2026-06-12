/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityAssessmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryQualityAssessment.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetryQualityAssessment.
     */
    @Entity
    @Table(name = "hidra_telemetry_quality_assessment")
    public class TelemetryQualityAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "reading_id", nullable = false, length = 80)
    private String readingId;

    @Column(name = "point_id", nullable = false, length = 80)
    private String pointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_status", nullable = false, length = 40)
    private AssessmentStatus assessmentStatus;

    @Column(name = "input_quality_code_id", nullable = false, length = 80)
    private String inputQualityCodeId;

    @Column(name = "resolved_quality_code_id", nullable = false, length = 80)
    private String resolvedQualityCodeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "trust_level", nullable = false, length = 40)
    private TrustLevel trustLevel;

    @Column(name = "validation_rule_id", nullable = true, length = 80)
    private String validationRuleId;

    @Column(name = "reason_code", nullable = true, length = 80)
    private String reasonCode;

    @Column(name = "reason_message", nullable = true, columnDefinition = "text")
    private String reasonMessage;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

        protected TelemetryQualityAssessmentJpaEntity() {
            // Required by JPA.
        }

        public TelemetryQualityAssessmentJpaEntity(
                String id,
            String readingId,
            String pointId,
            AssessmentStatus assessmentStatus,
            String inputQualityCodeId,
            String resolvedQualityCodeId,
            TrustLevel trustLevel,
            String validationRuleId,
            String reasonCode,
            String reasonMessage,
            Instant assessedAt,
            String assessedByActorId,
            String workflowInstanceId
        ) {
            this.id = id;
        this.readingId = readingId;
        this.pointId = pointId;
        this.assessmentStatus = assessmentStatus;
        this.inputQualityCodeId = inputQualityCodeId;
        this.resolvedQualityCodeId = resolvedQualityCodeId;
        this.trustLevel = trustLevel;
        this.validationRuleId = validationRuleId;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
        this.assessedAt = assessedAt;
        this.assessedByActorId = assessedByActorId;
        this.workflowInstanceId = workflowInstanceId;
        }


    public String id() {
        return id;
    }


    public String readingId() {
        return readingId;
    }


    public String pointId() {
        return pointId;
    }


    public AssessmentStatus assessmentStatus() {
        return assessmentStatus;
    }


    public String inputQualityCodeId() {
        return inputQualityCodeId;
    }


    public String resolvedQualityCodeId() {
        return resolvedQualityCodeId;
    }


    public TrustLevel trustLevel() {
        return trustLevel;
    }


    public String validationRuleId() {
        return validationRuleId;
    }


    public String reasonCode() {
        return reasonCode;
    }


    public String reasonMessage() {
        return reasonMessage;
    }


    public Instant assessedAt() {
        return assessedAt;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }

    }
