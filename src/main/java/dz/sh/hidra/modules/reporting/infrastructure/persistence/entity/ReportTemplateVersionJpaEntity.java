/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTemplateVersionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportTemplateVersion.
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
     * Database-backed JPA entity for ReportTemplateVersion.
     */
    @Entity
    @Table(name = "hidra_reporting_report_template_version")
    public class ReportTemplateVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_template_id", nullable = false, length = 80)
    private String reportTemplateId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportTemplateVersionStatus status;

    @Column(name = "layout_content_reference", nullable = false, length = 500)
    private String layoutContentReference;

    @Column(name = "style_reference", nullable = true, length = 500)
    private String styleReference;

    @Column(name = "checksum", nullable = false, length = 160)
    private String checksum;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name_snapshot", nullable = true, length = 160)
    private String createdByDisplayNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "activated_at", nullable = true)
    private Instant activatedAt;

    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;

        protected ReportTemplateVersionJpaEntity() {
            // Required by JPA.
        }

        public ReportTemplateVersionJpaEntity(
                String id,
            String reportTemplateId,
            int versionNumber,
            ReportTemplateVersionStatus status,
            String layoutContentReference,
            String styleReference,
            String checksum,
            String createdByActorId,
            String createdByDisplayNameSnapshot,
            Instant createdAt,
            Instant activatedAt,
            Instant retiredAt
        ) {
            this.id = id;
        this.reportTemplateId = reportTemplateId;
        this.versionNumber = versionNumber;
        this.status = status;
        this.layoutContentReference = layoutContentReference;
        this.styleReference = styleReference;
        this.checksum = checksum;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayNameSnapshot = createdByDisplayNameSnapshot;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
        this.retiredAt = retiredAt;
        }


    public String id() {
        return id;
    }


    public String reportTemplateId() {
        return reportTemplateId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public ReportTemplateVersionStatus status() {
        return status;
    }


    public String layoutContentReference() {
        return layoutContentReference;
    }


    public String styleReference() {
        return styleReference;
    }


    public String checksum() {
        return checksum;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayNameSnapshot() {
        return createdByDisplayNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant activatedAt() {
        return activatedAt;
    }


    public Instant retiredAt() {
        return retiredAt;
    }

    }
