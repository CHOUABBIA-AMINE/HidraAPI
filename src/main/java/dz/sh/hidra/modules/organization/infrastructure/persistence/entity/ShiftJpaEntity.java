/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ShiftJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for multilingual organization shifts.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.ShiftType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Persists one multilingual shift while maintaining the legacy non-null {@code name} column during cutover.
 */
@Entity
@Table(name = "hidra_org_shift")
public class ShiftJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String legacyName;

    @Column(name = "name_ar", length = 255)
    private String nameAr;

    @Column(name = "name_fr", length = 255)
    private String nameFr;

    @Column(name = "name_en", length = 255)
    private String nameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_type", nullable = false, length = 80)
    private ShiftType shiftType;

    @Column(name = "start_time", nullable = false, length = 20)
    private String startTime;

    @Column(name = "end_time", nullable = false, length = 20)
    private String endTime;

    @Column(name = "timezone", nullable = false, length = 80)
    private String timezone;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ShiftJpaEntity() {
        // Required by JPA.
    }

    public ShiftJpaEntity(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            ShiftType shiftType,
            String startTime,
            String endTime,
            String timezone,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.legacyName = compatibilityName(code, nameAr, nameFr, nameEn);
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.shiftType = shiftType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timezone = timezone;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() { return id; }
    public String code() { return code; }
    public String nameAr() { return nameAr; }
    public String nameFr() { return nameFr; }
    public String nameEn() { return nameEn; }
    public ShiftType shiftType() { return shiftType; }
    public String startTime() { return startTime; }
    public String endTime() { return endTime; }
    public String timezone() { return timezone; }
    public boolean active() { return active; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    /**
     * Transitional value maintained only because the legacy database column is still non-null.
     */
    @Deprecated(forRemoval = true)
    public String legacyName() { return legacyName; }

    private static String compatibilityName(String code, String nameAr, String nameFr, String nameEn) {
        if (nameEn != null && !nameEn.isBlank()) {
            return nameEn.trim();
        }
        if (nameFr != null && !nameFr.isBlank()) {
            return nameFr.trim();
        }
        if (nameAr != null && !nameAr.isBlank()) {
            return nameAr.trim();
        }
        return code;
    }
}
