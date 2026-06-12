/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditRetentionPolicyJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditRetentionPolicy.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AuditRetentionPolicy.
     */
    @Entity
    @Table(name = "hidra_audit_retention_policy")
    public class AuditRetentionPolicyJpaEntity {

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

    @Column(name = "event_category_id", nullable = true, length = 80)
    private String eventCategoryId;

    @Column(name = "retention_days", nullable = false)
    private Integer retentionDays;

    @Column(name = "archive_after_days", nullable = true)
    private Integer archiveAfterDays;

    @Column(name = "legal_hold_supported", nullable = false)
    private boolean legalHoldSupported;

    @Column(name = "purge_allowed", nullable = false)
    private boolean purgeAllowed;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to", nullable = true)
    private LocalDate validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AuditRetentionPolicyJpaEntity() {
            // Required by JPA.
        }

        public AuditRetentionPolicyJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String eventCategoryId,
            Integer retentionDays,
            Integer archiveAfterDays,
            boolean legalHoldSupported,
            boolean purgeAllowed,
            boolean active,
            LocalDate validFrom,
            LocalDate validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.eventCategoryId = eventCategoryId;
        this.retentionDays = retentionDays;
        this.archiveAfterDays = archiveAfterDays;
        this.legalHoldSupported = legalHoldSupported;
        this.purgeAllowed = purgeAllowed;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
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


    public String eventCategoryId() {
        return eventCategoryId;
    }


    public Integer retentionDays() {
        return retentionDays;
    }


    public Integer archiveAfterDays() {
        return archiveAfterDays;
    }


    public boolean legalHoldSupported() {
        return legalHoldSupported;
    }


    public boolean purgeAllowed() {
        return purgeAllowed;
    }


    public boolean active() {
        return active;
    }


    public LocalDate validFrom() {
        return validFrom;
    }


    public LocalDate validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
