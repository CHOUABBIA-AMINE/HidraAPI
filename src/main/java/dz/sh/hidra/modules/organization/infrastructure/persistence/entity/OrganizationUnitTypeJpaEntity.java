/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for embedded multilingual organization unit types.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Persists one multilingual organization-unit type.
 *
 * <p>The legacy {@code description} column is mapped read-only so ORG-037 updates cannot erase recovery data.
 */
@Entity
@Table(name = "hidra_org_unit_type")
public class OrganizationUnitTypeJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false, length = 80)
    private OrganizationUnitKind kind;

    @Column(name = "name_ar", length = 255)
    private String nameAr;

    @Column(name = "name_fr", length = 255)
    private String nameFr;

    @Column(name = "name_en", length = 255)
    private String nameEn;

    @Column(name = "description_ar", columnDefinition = "text")
    private String descriptionAr;

    @Column(name = "description_fr", columnDefinition = "text")
    private String descriptionFr;

    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;

    @Column(name = "description", insertable = false, updatable = false, columnDefinition = "text")
    private String legacyDescription;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected OrganizationUnitTypeJpaEntity() {
        // Required by JPA.
    }

    public OrganizationUnitTypeJpaEntity(
            String id,
            String code,
            OrganizationUnitKind kind,
            String nameAr,
            String nameFr,
            String nameEn,
            String descriptionAr,
            String descriptionFr,
            String descriptionEn,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.kind = kind;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.descriptionAr = descriptionAr;
        this.descriptionFr = descriptionFr;
        this.descriptionEn = descriptionEn;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() { return id; }
    public String code() { return code; }
    public OrganizationUnitKind kind() { return kind; }
    public String nameAr() { return nameAr; }
    public String nameFr() { return nameFr; }
    public String nameEn() { return nameEn; }
    public String descriptionAr() { return descriptionAr; }
    public String descriptionFr() { return descriptionFr; }
    public String descriptionEn() { return descriptionEn; }
    public boolean active() { return active; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    /**
     * Transitional recovery-only value from the pre-ORG-036 language-ambiguous column.
     */
    @Deprecated(forRemoval = true)
    public String legacyDescription() { return legacyDescription; }
}
