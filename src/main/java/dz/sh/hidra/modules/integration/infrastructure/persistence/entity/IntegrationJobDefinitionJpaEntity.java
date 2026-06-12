/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobDefinitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationJobDefinition.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrationJobDefinition.
     */
    @Entity
    @Table(name = "hidra_integration_job_definition")
    public class IntegrationJobDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "connector_instance_id", nullable = false, length = 80)
    private String connectorInstanceId;

    @Column(name = "mapping_profile_id", nullable = true, length = 80)
    private String mappingProfileId;

    @Column(name = "job_type_id", nullable = false, length = 80)
    private String jobTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 30)
    private IntegrationDirection direction;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "schedule_expression", nullable = true, length = 255)
    private String scheduleExpression;

    @Column(name = "manual_run_allowed", nullable = false)
    private boolean manualRunAllowed;

    @Column(name = "retry_policy_id", nullable = true, length = 80)
    private String retryPolicyId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationJobDefinitionJpaEntity() {
            // Required by JPA.
        }

        public IntegrationJobDefinitionJpaEntity(
                String id,
            String code,
            String nameFr,
            String nameAr,
            String nameEn,
            String connectorInstanceId,
            String mappingProfileId,
            String jobTypeId,
            IntegrationDirection direction,
            String targetModule,
            String scheduleExpression,
            boolean manualRunAllowed,
            String retryPolicyId,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.connectorInstanceId = connectorInstanceId;
        this.mappingProfileId = mappingProfileId;
        this.jobTypeId = jobTypeId;
        this.direction = direction;
        this.targetModule = targetModule;
        this.scheduleExpression = scheduleExpression;
        this.manualRunAllowed = manualRunAllowed;
        this.retryPolicyId = retryPolicyId;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String connectorInstanceId() {
        return connectorInstanceId;
    }


    public String mappingProfileId() {
        return mappingProfileId;
    }


    public String jobTypeId() {
        return jobTypeId;
    }


    public IntegrationDirection direction() {
        return direction;
    }


    public String targetModule() {
        return targetModule;
    }


    public String scheduleExpression() {
        return scheduleExpression;
    }


    public boolean manualRunAllowed() {
        return manualRunAllowed;
    }


    public String retryPolicyId() {
        return retryPolicyId;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
