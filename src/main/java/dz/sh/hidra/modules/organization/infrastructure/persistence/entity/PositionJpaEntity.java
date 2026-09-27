/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for multilingual organization positions.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.PositionLevel;
import dz.sh.hidra.modules.organization.domain.value.PositionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Persists one multilingual organization position.
 *
 * <p>The legacy {@code description} column remains read-only during cutover so old data is preserved.
 */
@Entity
@Table(name = "hidra_org_position")
public class PositionJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "title_ar", length = 255)
    private String titleAr;

    @Column(name = "title_fr", length = 255)
    private String titleFr;

    @Column(name = "title_en", length = 255)
    private String titleEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", length = 80)
    private PositionLevel level;

    @Column(name = "description_ar", columnDefinition = "text")
    private String descriptionAr;

    @Column(name = "description_fr", columnDefinition = "text")
    private String descriptionFr;

    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;

    @Column(name = "description", insertable = false, updatable = false, columnDefinition = "text")
    private String legacyDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PositionStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected PositionJpaEntity() {
        // Required by JPA.
    }

    public PositionJpaEntity(
            String id,
            String code,
            String titleAr,
            String titleFr,
            String titleEn,
            PositionLevel level,
            String descriptionAr,
            String descriptionFr,
            String descriptionEn,
            PositionStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.titleAr = titleAr;
        this.titleFr = titleFr;
        this.titleEn = titleEn;
        this.level = level;
        this.descriptionAr = descriptionAr;
        this.descriptionFr = descriptionFr;
        this.descriptionEn = descriptionEn;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() { return id; }
    public String code() { return code; }
    public String titleAr() { return titleAr; }
    public String titleFr() { return titleFr; }
    public String titleEn() { return titleEn; }
    public PositionLevel level() { return level; }
    public String descriptionAr() { return descriptionAr; }
    public String descriptionFr() { return descriptionFr; }
    public String descriptionEn() { return descriptionEn; }
    public PositionStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    /**
     * Transitional recovery-only value from the pre-ORG-036 language-ambiguous column.
     */
    @Deprecated(forRemoval = true)
    public String legacyDescription() { return legacyDescription; }
}
