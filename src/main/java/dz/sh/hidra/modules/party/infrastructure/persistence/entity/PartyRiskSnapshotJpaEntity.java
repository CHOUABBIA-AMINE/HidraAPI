/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRiskSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyRiskSnapshot.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import dz.sh.hidra.modules.party.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PartyRiskSnapshot.
     */
    @Entity
    @Table(name = "hidra_party_risk_snapshot")
    public class PartyRiskSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 80)
    private RiskLevel riskLevel;

    @Column(name = "risk_source_module", nullable = true, length = 80)
    private String riskSourceModule;

    @Column(name = "risk_source_reference_id", nullable = true, length = 120)
    private String riskSourceReferenceId;

    @Column(name = "risk_reason", nullable = true, columnDefinition = "text")
    private String riskReason;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    @Column(name = "valid_until", nullable = true)
    private Instant validUntil;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected PartyRiskSnapshotJpaEntity() {
            // Required by JPA.
        }

        public PartyRiskSnapshotJpaEntity(
                String id,
            String partyId,
            RiskLevel riskLevel,
            String riskSourceModule,
            String riskSourceReferenceId,
            String riskReason,
            Instant assessedAt,
            Instant validUntil,
            Instant createdAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.riskLevel = riskLevel;
        this.riskSourceModule = riskSourceModule;
        this.riskSourceReferenceId = riskSourceReferenceId;
        this.riskReason = riskReason;
        this.assessedAt = assessedAt;
        this.validUntil = validUntil;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public RiskLevel riskLevel() {
        return riskLevel;
    }


    public String riskSourceModule() {
        return riskSourceModule;
    }


    public String riskSourceReferenceId() {
        return riskSourceReferenceId;
    }


    public String riskReason() {
        return riskReason;
    }


    public Instant assessedAt() {
        return assessedAt;
    }


    public Instant validUntil() {
        return validUntil;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
