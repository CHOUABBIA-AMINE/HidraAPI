/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportSectionResultJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportSectionResult.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import dz.sh.hidra.modules.reporting.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ReportSectionResult.
     */
    @Entity
    @Table(name = "hidra_reporting_section_result")
    public class ReportSectionResultJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_run_id", nullable = false, length = 80)
    private String reportRunId;

    @Column(name = "section_definition_id", nullable = false, length = 80)
    private String sectionDefinitionId;

    @Column(name = "section_code", nullable = false, length = 120)
    private String sectionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportSectionResultStatus status;

    @Column(name = "result_reference", nullable = true, length = 500)
    private String resultReference;

    @Column(name = "row_count", nullable = true)
    private Long rowCount;

    @Column(name = "warning_count", nullable = true)
    private Long warningCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ReportSectionResultJpaEntity() {
            // Required by JPA.
        }

        public ReportSectionResultJpaEntity(
                String id,
            String reportRunId,
            String sectionDefinitionId,
            String sectionCode,
            ReportSectionResultStatus status,
            String resultReference,
            Long rowCount,
            Long warningCount,
            Instant createdAt
        ) {
            this.id = id;
        this.reportRunId = reportRunId;
        this.sectionDefinitionId = sectionDefinitionId;
        this.sectionCode = sectionCode;
        this.status = status;
        this.resultReference = resultReference;
        this.rowCount = rowCount;
        this.warningCount = warningCount;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String reportRunId() {
        return reportRunId;
    }


    public String sectionDefinitionId() {
        return sectionDefinitionId;
    }


    public String sectionCode() {
        return sectionCode;
    }


    public ReportSectionResultStatus status() {
        return status;
    }


    public String resultReference() {
        return resultReference;
    }


    public Long rowCount() {
        return rowCount;
    }


    public Long warningCount() {
        return warningCount;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
