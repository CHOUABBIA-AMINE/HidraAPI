/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionCampaignJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for InspectionCampaign.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for InspectionCampaign.
     */
    @Entity
    @Table(name = "hidra_integrity_inspection_campaign")
    public class InspectionCampaignJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "program_id", nullable = true, length = 80)
    private String programId;

    @Column(name = "campaign_number", nullable = false, length = 80)
    private String campaignNumber;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "inspection_type_id", nullable = false, length = 80)
    private String inspectionTypeId;

    @Column(name = "contractor_party_id", nullable = true, length = 80)
    private String contractorPartyId;

    @Column(name = "contractor_name_snapshot", nullable = true, length = 255)
    private String contractorNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private InspectionCampaignStatus status;

    @Column(name = "planned_start_at", nullable = true)
    private Instant plannedStartAt;

    @Column(name = "planned_end_at", nullable = true)
    private Instant plannedEndAt;

    @Column(name = "actual_start_at", nullable = true)
    private Instant actualStartAt;

    @Column(name = "actual_end_at", nullable = true)
    private Instant actualEndAt;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected InspectionCampaignJpaEntity() {
            // Required by JPA.
        }

        public InspectionCampaignJpaEntity(
                String id,
            String programId,
            String campaignNumber,
            String name,
            String inspectionTypeId,
            String contractorPartyId,
            String contractorNameSnapshot,
            InspectionCampaignStatus status,
            Instant plannedStartAt,
            Instant plannedEndAt,
            Instant actualStartAt,
            Instant actualEndAt,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.programId = programId;
        this.campaignNumber = campaignNumber;
        this.name = name;
        this.inspectionTypeId = inspectionTypeId;
        this.contractorPartyId = contractorPartyId;
        this.contractorNameSnapshot = contractorNameSnapshot;
        this.status = status;
        this.plannedStartAt = plannedStartAt;
        this.plannedEndAt = plannedEndAt;
        this.actualStartAt = actualStartAt;
        this.actualEndAt = actualEndAt;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String programId() {
        return programId;
    }


    public String campaignNumber() {
        return campaignNumber;
    }


    public String name() {
        return name;
    }


    public String inspectionTypeId() {
        return inspectionTypeId;
    }


    public String contractorPartyId() {
        return contractorPartyId;
    }


    public String contractorNameSnapshot() {
        return contractorNameSnapshot;
    }


    public InspectionCampaignStatus status() {
        return status;
    }


    public Instant plannedStartAt() {
        return plannedStartAt;
    }


    public Instant plannedEndAt() {
        return plannedEndAt;
    }


    public Instant actualStartAt() {
        return actualStartAt;
    }


    public Instant actualEndAt() {
        return actualEndAt;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
