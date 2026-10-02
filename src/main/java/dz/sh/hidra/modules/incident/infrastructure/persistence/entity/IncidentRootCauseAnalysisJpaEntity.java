/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRootCauseAnalysisJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentRootCauseAnalysis.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentRootCauseAnalysis.
     */
    @Entity
    @Table(name = "hidra_incident_root_cause_analysis")
    public class IncidentRootCauseAnalysisJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "root_cause_category_id", nullable = false, length = 80)
    private String rootCauseCategoryId;

    @Column(name = "root_cause_code_id", nullable = true, length = 80)
    private String rootCauseCodeId;

    @Column(name = "method_id", nullable = true, length = 80)
    private String methodId;

    @Column(name = "summary", nullable = false, columnDefinition = "text")
    private String summary;

    @Column(name = "analysis_details", nullable = true, columnDefinition = "text")
    private String analysisDetails;

    @Column(name = "contributing_factors", nullable = true, columnDefinition = "text")
    private String contributingFactors;

    @Column(name = "confidence_level_id", nullable = true, length = 80)
    private String confidenceLevelId;

    @Column(name = "performed_by_actor_id", nullable = false, length = 80)
    private String performedByActorId;

    @Column(name = "performed_at", nullable = false)
    private Instant performedAt;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

        protected IncidentRootCauseAnalysisJpaEntity() {
            // Required by JPA.
        }

        public IncidentRootCauseAnalysisJpaEntity(
                String id,
            String incidentId,
            String rootCauseCategoryId,
            String rootCauseCodeId,
            String methodId,
            String summary,
            String analysisDetails,
            String contributingFactors,
            String confidenceLevelId,
            String performedByActorId,
            Instant performedAt,
            String approvedByActorId,
            Instant approvedAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.rootCauseCategoryId = rootCauseCategoryId;
        this.rootCauseCodeId = rootCauseCodeId;
        this.methodId = methodId;
        this.summary = summary;
        this.analysisDetails = analysisDetails;
        this.contributingFactors = contributingFactors;
        this.confidenceLevelId = confidenceLevelId;
        this.performedByActorId = performedByActorId;
        this.performedAt = performedAt;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String rootCauseCategoryId() {
        return rootCauseCategoryId;
    }


    public String rootCauseCodeId() {
        return rootCauseCodeId;
    }


    public String methodId() {
        return methodId;
    }


    public String summary() {
        return summary;
    }


    public String analysisDetails() {
        return analysisDetails;
    }


    public String contributingFactors() {
        return contributingFactors;
    }


    public String confidenceLevelId() {
        return confidenceLevelId;
    }


    public String performedByActorId() {
        return performedByActorId;
    }


    public Instant performedAt() {
        return performedAt;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }

    }
