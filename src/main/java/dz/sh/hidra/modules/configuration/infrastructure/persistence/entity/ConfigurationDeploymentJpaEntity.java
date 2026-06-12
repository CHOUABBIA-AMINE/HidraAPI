/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDeploymentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationDeployment.
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
     * Database-backed JPA entity for ConfigurationDeployment.
     */
    @Entity
    @Table(name = "hidra_configuration_deployment")
    public class ConfigurationDeploymentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "deployment_number", nullable = false, length = 120)
    private String deploymentNumber;

    @Column(name = "change_request_id", nullable = true, length = 80)
    private String changeRequestId;

    @Column(name = "profile_id", nullable = true, length = 80)
    private String profileId;

    @Column(name = "environment", nullable = false, length = 40)
    private String environment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DeploymentStatus status;

    @Column(name = "deployed_by_actor_id", nullable = true, length = 80)
    private String deployedByActorId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "rollback_deployment_id", nullable = true, length = 80)
    private String rollbackDeploymentId;

    @Column(name = "failure_reason", nullable = true, length = 2000)
    private String failureReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ConfigurationDeploymentJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationDeploymentJpaEntity(
                String id,
            String deploymentNumber,
            String changeRequestId,
            String profileId,
            String environment,
            DeploymentStatus status,
            String deployedByActorId,
            Instant startedAt,
            Instant completedAt,
            String rollbackDeploymentId,
            String failureReason,
            Instant createdAt
        ) {
            this.id = id;
        this.deploymentNumber = deploymentNumber;
        this.changeRequestId = changeRequestId;
        this.profileId = profileId;
        this.environment = environment;
        this.status = status;
        this.deployedByActorId = deployedByActorId;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.rollbackDeploymentId = rollbackDeploymentId;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String deploymentNumber() {
        return deploymentNumber;
    }


    public String changeRequestId() {
        return changeRequestId;
    }


    public String profileId() {
        return profileId;
    }


    public String environment() {
        return environment;
    }


    public DeploymentStatus status() {
        return status;
    }


    public String deployedByActorId() {
        return deployedByActorId;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String rollbackDeploymentId() {
        return rollbackDeploymentId;
    }


    public String failureReason() {
        return failureReason;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
