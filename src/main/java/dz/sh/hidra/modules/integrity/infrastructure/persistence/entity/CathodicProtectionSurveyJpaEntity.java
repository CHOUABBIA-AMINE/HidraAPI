/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionSurveyJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CathodicProtectionSurvey.
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
     * Database-backed JPA entity for CathodicProtectionSurvey.
     */
    @Entity
    @Table(name = "hidra_integrity_cathodic_protection_survey")
    public class CathodicProtectionSurveyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "survey_number", nullable = false, length = 80)
    private String surveyNumber;

    @Column(name = "survey_type_id", nullable = false, length = 80)
    private String surveyTypeId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private InspectionCampaignStatus status;

    @Column(name = "survey_start_at", nullable = true)
    private Instant surveyStartAt;

    @Column(name = "survey_end_at", nullable = true)
    private Instant surveyEndAt;

    @Column(name = "performed_by_party_id", nullable = true, length = 80)
    private String performedByPartyId;

    @Column(name = "summary", nullable = true, columnDefinition = "text")
    private String summary;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CathodicProtectionSurveyJpaEntity() {
            // Required by JPA.
        }

        public CathodicProtectionSurveyJpaEntity(
                String id,
            String surveyNumber,
            String surveyTypeId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            InspectionCampaignStatus status,
            Instant surveyStartAt,
            Instant surveyEndAt,
            String performedByPartyId,
            String summary,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.surveyNumber = surveyNumber;
        this.surveyTypeId = surveyTypeId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.status = status;
        this.surveyStartAt = surveyStartAt;
        this.surveyEndAt = surveyEndAt;
        this.performedByPartyId = performedByPartyId;
        this.summary = summary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String surveyNumber() {
        return surveyNumber;
    }


    public String surveyTypeId() {
        return surveyTypeId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCodeSnapshot() {
        return topologyAssetCodeSnapshot;
    }


    public InspectionCampaignStatus status() {
        return status;
    }


    public Instant surveyStartAt() {
        return surveyStartAt;
    }


    public Instant surveyEndAt() {
        return surveyEndAt;
    }


    public String performedByPartyId() {
        return performedByPartyId;
    }


    public String summary() {
        return summary;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
