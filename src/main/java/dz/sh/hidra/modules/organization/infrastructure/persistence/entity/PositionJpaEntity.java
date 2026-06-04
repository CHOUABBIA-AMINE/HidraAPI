/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an organization position.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of an organization position.
 *
 * <p>Business role:
 * This entity stores operational positions and functions such as Station Team Leader, Station
 * Boss, Region Director, Gas Flux Director, or Department Chief. It is not an identity role.
 *
 * <p>Architecture role:
 * This class belongs to organization persistence infrastructure and must not leak into API or
 * application contracts.
 *
 * <p>Validation:
 * Domain validation is performed before mapping. Database constraints protect required fields and
 * uniqueness of position code.
 *
 * <p>Usage:
 * Use only inside persistence infrastructure.
 */
@Entity
@Table(name = "hidra_org_position")
public class PositionJpaEntity {

    /** Stable position identifier. */
    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    /** Unique position business code. */
    @Column(name = "code", nullable = false, unique = true, length = 80)
    private String code;

    /** Position display title. */
    @Column(name = "title", nullable = false, length = 120)
    private String title;

    /** Optional position description. */
    @Column(name = "description", length = 500)
    private String description;

    /** Whether the position can be used for assignments. */
    @Column(name = "active", nullable = false)
    private boolean active;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected PositionJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
