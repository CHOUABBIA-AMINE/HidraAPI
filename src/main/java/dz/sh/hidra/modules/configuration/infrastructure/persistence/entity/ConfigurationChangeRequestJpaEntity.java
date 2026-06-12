/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationChangeRequestJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationChangeRequest.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.configuration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ConfigurationChangeRequest.
     */
    @Entity
    @Table(name = "hidra_configuration_change_request")
    public class ConfigurationChangeRequestJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "request_number", nullable = false, length = 120)
    private String requestNumber;

    @Column(name = "definition_id", nullable = true, length = 80)
    private String definitionId;

    @Column(name = "profile_id", nullable = true, length = 80)
    private String profileId;

    @Column(name = "feature_flag_id", nullable = true, length = 80)
    private String featureFlagId;

    @Column(name = "requested_value", nullable = true, length = 4000)
    private String requestedValue;

    @Column(name = "requested_json_value", nullable = true, columnDefinition = "jsonb")
    private String requestedJsonValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ChangeRequestStatus status;

    @Column(name = "reason", nullable = true, length = 1000)
    private String reason;

    @Column(name = "requested_by_actor_id", nullable = false, length = 80)
    private String requestedByActorId;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationChangeRequestJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationChangeRequestJpaEntity(
                String id,
            String requestNumber,
            String definitionId,
            String profileId,
            String featureFlagId,
            String requestedValue,
            String requestedJsonValue,
            ChangeRequestStatus status,
            String reason,
            String requestedByActorId,
            Instant requestedAt,
            String workflowInstanceId,
            String approvedByActorId,
            Instant approvedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.requestNumber = requestNumber;
        this.definitionId = definitionId;
        this.profileId = profileId;
        this.featureFlagId = featureFlagId;
        this.requestedValue = requestedValue;
        this.requestedJsonValue = requestedJsonValue;
        this.status = status;
        this.reason = reason;
        this.requestedByActorId = requestedByActorId;
        this.requestedAt = requestedAt;
        this.workflowInstanceId = workflowInstanceId;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String requestNumber() {
        return requestNumber;
    }


    public String definitionId() {
        return definitionId;
    }


    public String profileId() {
        return profileId;
    }


    public String featureFlagId() {
        return featureFlagId;
    }


    public String requestedValue() {
        return requestedValue;
    }


    public String requestedJsonValue() {
        return requestedJsonValue;
    }


    public ChangeRequestStatus status() {
        return status;
    }


    public String reason() {
        return reason;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
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
