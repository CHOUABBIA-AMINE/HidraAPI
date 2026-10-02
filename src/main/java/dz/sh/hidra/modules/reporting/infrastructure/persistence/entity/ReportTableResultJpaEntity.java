/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTableResultJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportTableResult.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ReportTableResult.
     */
    @Entity
    @Table(name = "hidra_reporting_table_result")
    public class ReportTableResultJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_section_result_id", nullable = false, length = 80)
    private String reportSectionResultId;

    @Column(name = "column_schema_json", nullable = false, columnDefinition = "jsonb")
    private String columnSchemaJson;

    @Column(name = "row_count", nullable = false)
    private Long rowCount;

    @Column(name = "content_reference", nullable = true, length = 500)
    private String contentReference;

    @Column(name = "checksum", nullable = true, length = 160)
    private String checksum;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ReportTableResultJpaEntity() {
            // Required by JPA.
        }

        public ReportTableResultJpaEntity(
                String id,
            String reportSectionResultId,
            String columnSchemaJson,
            Long rowCount,
            String contentReference,
            String checksum,
            Instant createdAt
        ) {
            this.id = id;
        this.reportSectionResultId = reportSectionResultId;
        this.columnSchemaJson = columnSchemaJson;
        this.rowCount = rowCount;
        this.contentReference = contentReference;
        this.checksum = checksum;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String reportSectionResultId() {
        return reportSectionResultId;
    }


    public String columnSchemaJson() {
        return columnSchemaJson;
    }


    public Long rowCount() {
        return rowCount;
    }


    public String contentReference() {
        return contentReference;
    }


    public String checksum() {
        return checksum;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
