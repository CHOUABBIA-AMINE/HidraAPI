/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryUnitJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryUnit.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for TelemetryUnit.
     */
    @Entity
    @Table(name = "hidra_telemetry_unit")
    public class TelemetryUnitJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "symbol", nullable = false, length = 160)
    private String symbol;

    @Column(name = "dimension", nullable = false, length = 80)
    private String dimension;

    @Column(name = "base_unit_id", nullable = true, length = 80)
    private String baseUnitId;

    @Column(name = "to_base_factor", nullable = true, precision = 18, scale = 8)
    private BigDecimal toBaseFactor;

    @Column(name = "to_base_offset", nullable = true, precision = 18, scale = 8)
    private BigDecimal toBaseOffset;

    @Column(name = "display_precision", nullable = true)
    private Integer displayPrecision;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "system_defined", nullable = false)
    private boolean systemDefined;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetryUnitJpaEntity() {
            // Required by JPA.
        }

        public TelemetryUnitJpaEntity(
                String id,
            String code,
            String symbol,
            String dimension,
            String baseUnitId,
            BigDecimal toBaseFactor,
            BigDecimal toBaseOffset,
            Integer displayPrecision,
            boolean active,
            boolean systemDefined,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.symbol = symbol;
        this.dimension = dimension;
        this.baseUnitId = baseUnitId;
        this.toBaseFactor = toBaseFactor;
        this.toBaseOffset = toBaseOffset;
        this.displayPrecision = displayPrecision;
        this.active = active;
        this.systemDefined = systemDefined;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String symbol() {
        return symbol;
    }


    public String dimension() {
        return dimension;
    }


    public String baseUnitId() {
        return baseUnitId;
    }


    public BigDecimal toBaseFactor() {
        return toBaseFactor;
    }


    public BigDecimal toBaseOffset() {
        return toBaseOffset;
    }


    public Integer displayPrecision() {
        return displayPrecision;
    }


    public boolean active() {
        return active;
    }


    public boolean systemDefined() {
        return systemDefined;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
