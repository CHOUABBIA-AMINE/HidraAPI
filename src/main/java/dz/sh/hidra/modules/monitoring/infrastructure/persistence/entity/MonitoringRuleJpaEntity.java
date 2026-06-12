/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MonitoringRule.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for MonitoringRule.
     */
    @Entity
    @Table(name = "hidra_monitoring_rule")
    public class MonitoringRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false, length = 80)
    private MonitoringRuleType ruleType;

    @Column(name = "evaluation_frequency_id", nullable = true, length = 80)
    private String evaluationFrequencyId;

    @Column(name = "topology_asset_type", nullable = true, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = true, length = 160)
    private String topologyAssetCode;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Column(name = "planning_target_type_id", nullable = true, length = 80)
    private String planningTargetTypeId;

    @Column(name = "expression", nullable = true, columnDefinition = "text")
    private String expression;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MonitoringLifecycleStatus status;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MonitoringRuleJpaEntity() {
            // Required by JPA.
        }

        public MonitoringRuleJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            MonitoringRuleType ruleType,
            String evaluationFrequencyId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String telemetryPointId,
            String planningTargetTypeId,
            String expression,
            MonitoringLifecycleStatus status,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.ruleType = ruleType;
        this.evaluationFrequencyId = evaluationFrequencyId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.telemetryPointId = telemetryPointId;
        this.planningTargetTypeId = planningTargetTypeId;
        this.expression = expression;
        this.status = status;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public MonitoringRuleType ruleType() {
        return ruleType;
    }


    public String evaluationFrequencyId() {
        return evaluationFrequencyId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public String telemetryPointId() {
        return telemetryPointId;
    }


    public String planningTargetTypeId() {
        return planningTargetTypeId;
    }


    public String expression() {
        return expression;
    }


    public MonitoringLifecycleStatus status() {
        return status;
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
