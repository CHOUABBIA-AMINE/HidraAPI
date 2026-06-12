/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ReportDefinition.
     */
    @Entity
    @Table(name = "hidra_reporting_report_definition")
    public class ReportDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "report_category_id", nullable = false, length = 80)
    private String reportCategoryId;

    @Column(name = "owner_module", nullable = false, length = 80)
    private String ownerModule;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "current_template_version_id", nullable = true, length = 80)
    private String currentTemplateVersionId;

    @Column(name = "requires_approval", nullable = false)
    private boolean requiresApproval;

    @Column(name = "restricted", nullable = false)
    private boolean restricted;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportDefinitionJpaEntity() {
            // Required by JPA.
        }

        public ReportDefinitionJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String reportCategoryId,
            String ownerModule,
            String description,
            boolean active,
            String currentTemplateVersionId,
            boolean requiresApproval,
            boolean restricted,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.reportCategoryId = reportCategoryId;
        this.ownerModule = ownerModule;
        this.description = description;
        this.active = active;
        this.currentTemplateVersionId = currentTemplateVersionId;
        this.requiresApproval = requiresApproval;
        this.restricted = restricted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
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


    public String reportCategoryId() {
        return reportCategoryId;
    }


    public String ownerModule() {
        return ownerModule;
    }


    public String description() {
        return description;
    }


    public boolean active() {
        return active;
    }


    public String currentTemplateVersionId() {
        return currentTemplateVersionId;
    }


    public boolean requiresApproval() {
        return requiresApproval;
    }


    public boolean restricted() {
        return restricted;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
