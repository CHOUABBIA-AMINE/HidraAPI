/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SparePartJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SparePart.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SparePart.
     */
    @Entity
    @Table(name = "hidra_asset_spare_part")
    public class SparePartJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "part_number", nullable = false, length = 80)
    private String partNumber;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "manufacturer_party_id", nullable = true, length = 80)
    private String manufacturerPartyId;

    @Column(name = "manufacturer_name_snapshot", nullable = true, length = 255)
    private String manufacturerNameSnapshot;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "category_id", nullable = true, length = 80)
    private String categoryId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected SparePartJpaEntity() {
            // Required by JPA.
        }

        public SparePartJpaEntity(
                String id,
            String partNumber,
            String name,
            String description,
            String manufacturerPartyId,
            String manufacturerNameSnapshot,
            String unitId,
            String categoryId,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partNumber = partNumber;
        this.name = name;
        this.description = description;
        this.manufacturerPartyId = manufacturerPartyId;
        this.manufacturerNameSnapshot = manufacturerNameSnapshot;
        this.unitId = unitId;
        this.categoryId = categoryId;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partNumber() {
        return partNumber;
    }


    public String name() {
        return name;
    }


    public String description() {
        return description;
    }


    public String manufacturerPartyId() {
        return manufacturerPartyId;
    }


    public String manufacturerNameSnapshot() {
        return manufacturerNameSnapshot;
    }


    public String unitId() {
        return unitId;
    }


    public String categoryId() {
        return categoryId;
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
