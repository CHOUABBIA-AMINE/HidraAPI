/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriodJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyMeasurementPeriod.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import dz.sh.hidra.modules.custody.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for CustodyMeasurementPeriod.
     */
    @Entity
    @Table(name = "hidra_custody_measurement_period")
    public class CustodyMeasurementPeriodJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "period_code", nullable = false, length = 80)
    private String periodCode;

    @Column(name = "agreement_id", nullable = false, length = 80)
    private String agreementId;

    @Column(name = "transfer_point_id", nullable = false, length = 80)
    private String transferPointId;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CustodyPeriodStatus status;

    @Column(name = "locked_by_actor_id", nullable = true, length = 80)
    private String lockedByActorId;

    @Column(name = "locked_at", nullable = true)
    private Instant lockedAt;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyMeasurementPeriodJpaEntity() {
            // Required by JPA.
        }

        public CustodyMeasurementPeriodJpaEntity(
                String id,
            String periodCode,
            String agreementId,
            String transferPointId,
            Instant periodStart,
            Instant periodEnd,
            CustodyPeriodStatus status,
            String lockedByActorId,
            Instant lockedAt,
            String approvedByActorId,
            Instant approvedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.periodCode = periodCode;
        this.agreementId = agreementId;
        this.transferPointId = transferPointId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.status = status;
        this.lockedByActorId = lockedByActorId;
        this.lockedAt = lockedAt;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String periodCode() {
        return periodCode;
    }


    public String agreementId() {
        return agreementId;
    }


    public String transferPointId() {
        return transferPointId;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public CustodyPeriodStatus status() {
        return status;
    }


    public String lockedByActorId() {
        return lockedByActorId;
    }


    public Instant lockedAt() {
        return lockedAt;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
