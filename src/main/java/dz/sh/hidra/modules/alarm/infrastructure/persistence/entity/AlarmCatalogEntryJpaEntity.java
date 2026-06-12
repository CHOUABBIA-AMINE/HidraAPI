/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmCatalogEntryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmCatalogEntry.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AlarmCatalogEntry.
     */
    @Entity
    @Table(name = "hidra_alarm_catalog_entry")
    public class AlarmCatalogEntryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "catalog_name", nullable = false, length = 80)
    private String catalogName;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "system_defined", nullable = false)
    private boolean systemDefined;

    @Column(name = "severity_rank", nullable = true)
    private Integer severityRank;

    @Column(name = "color_code", nullable = true, length = 40)
    private String colorCode;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AlarmCatalogEntryJpaEntity() {
            // Required by JPA.
        }

        public AlarmCatalogEntryJpaEntity(
                String id,
            String catalogName,
            String code,
            boolean active,
            int sortOrder,
            boolean systemDefined,
            Integer severityRank,
            String colorCode,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.catalogName = catalogName;
        this.code = code;
        this.active = active;
        this.sortOrder = sortOrder;
        this.systemDefined = systemDefined;
        this.severityRank = severityRank;
        this.colorCode = colorCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String catalogName() {
        return catalogName;
    }


    public String code() {
        return code;
    }


    public boolean active() {
        return active;
    }


    public int sortOrder() {
        return sortOrder;
    }


    public boolean systemDefined() {
        return systemDefined;
    }


    public Integer severityRank() {
        return severityRank;
    }


    public String colorCode() {
        return colorCode;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
