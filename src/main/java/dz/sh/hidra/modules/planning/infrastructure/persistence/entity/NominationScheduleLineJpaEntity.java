/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationScheduleLineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NominationScheduleLine.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NominationScheduleLine.
     */
    @Entity
    @Table(name = "hidra_planning_nomination_schedule_line")
    public class NominationScheduleLineJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "nomination_id", nullable = false, length = 80)
    private String nominationId;

    @Column(name = "sequence_number", nullable = false)
    private int sequenceNumber;

    @Column(name = "line_start", nullable = false)
    private Instant lineStart;

    @Column(name = "line_end", nullable = false)
    private Instant lineEnd;

    @Column(name = "planned_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal plannedQuantity;

    @Column(name = "quantity_unit_id", nullable = true, length = 80)
    private String quantityUnitId;

    @Column(name = "planned_rate", nullable = true, precision = 18, scale = 6)
    private BigDecimal plannedRate;

    @Column(name = "rate_unit_id", nullable = true, length = 80)
    private String rateUnitId;

    @Column(name = "notes", nullable = true, length = 500)
    private String notes;

        protected NominationScheduleLineJpaEntity() {
            // Required by JPA.
        }

        public NominationScheduleLineJpaEntity(
                String id,
            String nominationId,
            int sequenceNumber,
            Instant lineStart,
            Instant lineEnd,
            BigDecimal plannedQuantity,
            String quantityUnitId,
            BigDecimal plannedRate,
            String rateUnitId,
            String notes
        ) {
            this.id = id;
        this.nominationId = nominationId;
        this.sequenceNumber = sequenceNumber;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.plannedQuantity = plannedQuantity;
        this.quantityUnitId = quantityUnitId;
        this.plannedRate = plannedRate;
        this.rateUnitId = rateUnitId;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String nominationId() {
        return nominationId;
    }


    public int sequenceNumber() {
        return sequenceNumber;
    }


    public Instant lineStart() {
        return lineStart;
    }


    public Instant lineEnd() {
        return lineEnd;
    }


    public BigDecimal plannedQuantity() {
        return plannedQuantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public BigDecimal plannedRate() {
        return plannedRate;
    }


    public String rateUnitId() {
        return rateUnitId;
    }


    public String notes() {
        return notes;
    }

    }
