/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EnvironmentalEventJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for EnvironmentalEvent.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for EnvironmentalEvent.
     */
    @Entity
    @Table(name = "hidra_hse_environmental_event")
    public class EnvironmentalEventJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "event_number", nullable = false, length = 80)
    private String eventNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 80)
    private EnvironmentalEventType eventType;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "substance_id", nullable = true, length = 80)
    private String substanceId;

    @Column(name = "quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal quantity;

    @Column(name = "quantity_unit_id", nullable = true, length = 80)
    private String quantityUnitId;

    @Column(name = "medium_affected_id", nullable = true, length = 80)
    private String mediumAffectedId;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 80)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private HseImpactSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportStatus status;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "linked_hse_case_id", nullable = true, length = 80)
    private String linkedHseCaseId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected EnvironmentalEventJpaEntity() {
            // Required by JPA.
        }

        public EnvironmentalEventJpaEntity(
                String id,
            String eventNumber,
            EnvironmentalEventType eventType,
            String title,
            String description,
            String substanceId,
            BigDecimal quantity,
            String quantityUnitId,
            String mediumAffectedId,
            String targetModule,
            String targetTypeCode,
            String targetId,
            HseImpactSeverity severity,
            ReportStatus status,
            Instant occurredAt,
            String linkedHseCaseId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.eventNumber = eventNumber;
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.substanceId = substanceId;
        this.quantity = quantity;
        this.quantityUnitId = quantityUnitId;
        this.mediumAffectedId = mediumAffectedId;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.severity = severity;
        this.status = status;
        this.occurredAt = occurredAt;
        this.linkedHseCaseId = linkedHseCaseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String eventNumber() {
        return eventNumber;
    }


    public EnvironmentalEventType eventType() {
        return eventType;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String substanceId() {
        return substanceId;
    }


    public BigDecimal quantity() {
        return quantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public String mediumAffectedId() {
        return mediumAffectedId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public String targetId() {
        return targetId;
    }


    public HseImpactSeverity severity() {
        return severity;
    }


    public ReportStatus status() {
        return status;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public String linkedHseCaseId() {
        return linkedHseCaseId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
