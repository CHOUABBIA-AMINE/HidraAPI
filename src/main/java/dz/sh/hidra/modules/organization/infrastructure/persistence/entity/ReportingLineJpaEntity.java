/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
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

import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Persists reporting-line identity while governing source and target subject types.
 *
 * <p>Business role: stores effective-dated reporting relations without owning the
 * referenced employee, position, or organization-unit rows.</p>
 *
 * <p>Architecture role: persistence representation of {@code ReportingLine}. Subject
 * discriminator columns remain VARCHAR and are mapped with {@link EnumType#STRING}.</p>
 *
 * <p>Validation: JPA enum mapping accepts only governed enum names. Database CHECK/FK
 * preflight and constraints are deliberately deferred to ORG-046.</p>
 *
 * <p>Usage: infrastructure only; domain/application layers must use typed domain
 * references rather than this entity.</p>
 */
@Entity
@Table(name = "hidra_org_reporting_line")
public class ReportingLineJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reporting_line_type", nullable = false, length = 80)
    private ReportingLineType reportingLineType;

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
            ReportingLineType reportingLineType,
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

    /**
     * Transitional constructor for legacy persistence callers with textual types.
     */
    @Deprecated(forRemoval = true)
    public ReportingLineJpaEntity(
            String id,
            ReportingLineType reportingLineType,
            String sourceType,
            String sourceId,
            String targetType,
            String targetId,
            Instant validFrom,
            Instant validTo,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                reportingLineType,
                ReportingSubjectType.from(sourceType),
                sourceId,
                ReportingSubjectType.from(targetType),
                targetId,
                validFrom,
                validTo,
                active,
                createdAt,
                updatedAt
        );
    }

    public String id() {
        return id;
    }

    public ReportingLineType reportingLineType() {
        return reportingLineType;
    }

    public ReportingSubjectType sourceSubjectType() {
        return sourceType;
    }

    @Deprecated(forRemoval = true)
    public String sourceType() {
        return sourceType.name();
    }

    public String sourceId() {
        return sourceId;
    }

    public ReportingSubjectType targetSubjectType() {
        return targetType;
    }

    @Deprecated(forRemoval = true)
    public String targetType() {
        return targetType.name();
    }

    public String targetId() {
        return targetId;
    }

    public Instant validFrom() {
        return validFrom;
    }

    public Instant validTo() {
        return validTo;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
