/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportChartResultJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportChartResult.
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
     * Database-backed JPA entity for ReportChartResult.
     */
    @Entity
    @Table(name = "hidra_reporting_chart_result")
    public class ReportChartResultJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_section_result_id", nullable = false, length = 80)
    private String reportSectionResultId;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type", nullable = false, length = 40)
    private ReportChartType chartType;

    @Column(name = "series_schema_json", nullable = true, columnDefinition = "jsonb")
    private String seriesSchemaJson;

    @Column(name = "image_reference", nullable = true, length = 500)
    private String imageReference;

    @Column(name = "interactive_spec_reference", nullable = true, length = 500)
    private String interactiveSpecReference;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ReportChartResultJpaEntity() {
            // Required by JPA.
        }

        public ReportChartResultJpaEntity(
                String id,
            String reportSectionResultId,
            ReportChartType chartType,
            String seriesSchemaJson,
            String imageReference,
            String interactiveSpecReference,
            Instant createdAt
        ) {
            this.id = id;
        this.reportSectionResultId = reportSectionResultId;
        this.chartType = chartType;
        this.seriesSchemaJson = seriesSchemaJson;
        this.imageReference = imageReference;
        this.interactiveSpecReference = interactiveSpecReference;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String reportSectionResultId() {
        return reportSectionResultId;
    }


    public ReportChartType chartType() {
        return chartType;
    }


    public String seriesSchemaJson() {
        return seriesSchemaJson;
    }


    public String imageReference() {
        return imageReference;
    }


    public String interactiveSpecReference() {
        return interactiveSpecReference;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
