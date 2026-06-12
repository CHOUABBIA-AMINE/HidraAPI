/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportSectionDefinitionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportSectionDefinition.
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
     * Database-backed JPA entity for ReportSectionDefinition.
     */
    @Entity
    @Table(name = "hidra_reporting_section_definition")
    public class ReportSectionDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "title_ar", nullable = true, length = 160)
    private String titleAr;

    @Column(name = "title_fr", nullable = false, length = 160)
    private String titleFr;

    @Column(name = "title_en", nullable = true, length = 160)
    private String titleEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "section_type", nullable = false, length = 40)
    private ReportSectionType sectionType;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "visible_by_default", nullable = false)
    private boolean visibleByDefault;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportSectionDefinitionJpaEntity() {
            // Required by JPA.
        }

        public ReportSectionDefinitionJpaEntity(
                String id,
            String reportDefinitionId,
            String code,
            String titleAr,
            String titleFr,
            String titleEn,
            ReportSectionType sectionType,
            int sortOrder,
            boolean visibleByDefault,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportDefinitionId = reportDefinitionId;
        this.code = code;
        this.titleAr = titleAr;
        this.titleFr = titleFr;
        this.titleEn = titleEn;
        this.sectionType = sectionType;
        this.sortOrder = sortOrder;
        this.visibleByDefault = visibleByDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
    }


    public String code() {
        return code;
    }


    public String titleAr() {
        return titleAr;
    }


    public String titleFr() {
        return titleFr;
    }


    public String titleEn() {
        return titleEn;
    }


    public ReportSectionType sectionType() {
        return sectionType;
    }


    public int sortOrder() {
        return sortOrder;
    }


    public boolean visibleByDefault() {
        return visibleByDefault;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
