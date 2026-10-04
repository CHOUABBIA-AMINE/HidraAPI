/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for typed Organization reporting lines.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Persists reporting-line identity, catalog classification and typed Organization subjects.
 */
@Entity
@Table(name = "hidra_org_reporting_line")
public class ReportingLineJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "reporting_line_type_id", nullable = false)
    private ReportingLineTypeJpaEntity reportingLineType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 80)
    private ReportingSubjectType sourceType;

    @Column(name = "source_id", nullable = false, length = 80)
    private String sourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 80)
    private ReportingSubjectType targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ReportingLineJpaEntity() {
        // Required by JPA.
    }

    public ReportingLineJpaEntity(
            String id,
            ReportingLineTypeJpaEntity reportingLineType,
            ReportingSubjectType sourceType,
            String sourceId,
            ReportingSubjectType targetType,
            String targetId,
            Instant validFrom,
            Instant validTo,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.reportingLineType = reportingLineType;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() { return id; }
    public ReportingLineTypeJpaEntity reportingLineType() { return reportingLineType; }
    public ReportingSubjectType sourceSubjectType() { return sourceType; }

    @Deprecated(forRemoval = true)
    public String sourceType() { return sourceType.name(); }

    public String sourceId() { return sourceId; }
    public ReportingSubjectType targetSubjectType() { return targetType; }

    @Deprecated(forRemoval = true)
    public String targetType() { return targetType.name(); }

    public String targetId() { return targetId; }
    public Instant validFrom() { return validFrom; }
    public Instant validTo() { return validTo; }
    public boolean active() { return active; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
