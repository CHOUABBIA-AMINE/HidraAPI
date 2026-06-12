/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Nomination.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for Nomination.
     */
    @Entity
    @Table(name = "hidra_planning_nomination")
    public class NominationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "scenario_id", nullable = true, length = 80)
    private String scenarioId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "nomination_type_id", nullable = false, length = 80)
    private String nominationTypeId;

    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    @Column(name = "quantity", nullable = false, precision = 18, scale = 6)
    private BigDecimal quantity;

    @Column(name = "quantity_unit_id", nullable = false, length = 80)
    private String quantityUnitId;

    @Column(name = "rate", nullable = true, precision = 18, scale = 6)
    private BigDecimal rate;

    @Column(name = "rate_unit_id", nullable = true, length = 80)
    private String rateUnitId;

    @Column(name = "source_asset_type", nullable = true, length = 160)
    private String sourceAssetType;

    @Column(name = "source_asset_id", nullable = true, length = 80)
    private String sourceAssetId;

    @Column(name = "source_asset_code", nullable = true, length = 160)
    private String sourceAssetCode;

    @Column(name = "destination_asset_type", nullable = true, length = 160)
    private String destinationAssetType;

    @Column(name = "destination_asset_id", nullable = true, length = 80)
    private String destinationAssetId;

    @Column(name = "destination_asset_code", nullable = true, length = 160)
    private String destinationAssetCode;

    @Column(name = "shipper_party_id", nullable = true, length = 80)
    private String shipperPartyId;

    @Column(name = "shipper_party_code_snapshot", nullable = true, length = 160)
    private String shipperPartyCodeSnapshot;

    @Column(name = "counterparty_id", nullable = true, length = 80)
    private String counterpartyId;

    @Column(name = "contract_reference_id", nullable = true, length = 80)
    private String contractReferenceId;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NominationStatus status;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NominationJpaEntity() {
            // Required by JPA.
        }

        public NominationJpaEntity(
                String id,
            String revisionId,
            String scenarioId,
            String code,
            String nominationTypeId,
            String productTypeId,
            BigDecimal quantity,
            String quantityUnitId,
            BigDecimal rate,
            String rateUnitId,
            String sourceAssetType,
            String sourceAssetId,
            String sourceAssetCode,
            String destinationAssetType,
            String destinationAssetId,
            String destinationAssetCode,
            String shipperPartyId,
            String shipperPartyCodeSnapshot,
            String counterpartyId,
            String contractReferenceId,
            Integer priority,
            NominationStatus status,
            Instant periodStart,
            Instant periodEnd,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.revisionId = revisionId;
        this.scenarioId = scenarioId;
        this.code = code;
        this.nominationTypeId = nominationTypeId;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
        this.quantityUnitId = quantityUnitId;
        this.rate = rate;
        this.rateUnitId = rateUnitId;
        this.sourceAssetType = sourceAssetType;
        this.sourceAssetId = sourceAssetId;
        this.sourceAssetCode = sourceAssetCode;
        this.destinationAssetType = destinationAssetType;
        this.destinationAssetId = destinationAssetId;
        this.destinationAssetCode = destinationAssetCode;
        this.shipperPartyId = shipperPartyId;
        this.shipperPartyCodeSnapshot = shipperPartyCodeSnapshot;
        this.counterpartyId = counterpartyId;
        this.contractReferenceId = contractReferenceId;
        this.priority = priority;
        this.status = status;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String revisionId() {
        return revisionId;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String code() {
        return code;
    }


    public String nominationTypeId() {
        return nominationTypeId;
    }


    public String productTypeId() {
        return productTypeId;
    }


    public BigDecimal quantity() {
        return quantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public BigDecimal rate() {
        return rate;
    }


    public String rateUnitId() {
        return rateUnitId;
    }


    public String sourceAssetType() {
        return sourceAssetType;
    }


    public String sourceAssetId() {
        return sourceAssetId;
    }


    public String sourceAssetCode() {
        return sourceAssetCode;
    }


    public String destinationAssetType() {
        return destinationAssetType;
    }


    public String destinationAssetId() {
        return destinationAssetId;
    }


    public String destinationAssetCode() {
        return destinationAssetCode;
    }


    public String shipperPartyId() {
        return shipperPartyId;
    }


    public String shipperPartyCodeSnapshot() {
        return shipperPartyCodeSnapshot;
    }


    public String counterpartyId() {
        return counterpartyId;
    }


    public String contractReferenceId() {
        return contractReferenceId;
    }


    public Integer priority() {
        return priority;
    }


    public NominationStatus status() {
        return status;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
