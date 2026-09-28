/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDeployment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Configuration deployment record.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Configuration deployment record.
     *
         * @param id id
     * @param deploymentNumber deploymentNumber
     * @param changeRequestId changeRequestId
     * @param profileId profileId
     * @param environment environment
     * @param status status
     * @param deployedByActorId deployedByActorId
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param rollbackDeploymentId rollbackDeploymentId
     * @param failureReason failureReason
     * @param createdAt createdAt
     */
    public record ConfigurationDeployment(
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

        public ConfigurationDeployment {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationDeployment id must not be blank.");
        }
        // HRA-051 required: deploymentNumber
        if (deploymentNumber == null || deploymentNumber.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationDeployment deployment number must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidConfigurationValueException("ConfigurationDeployment status must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidConfigurationValueException("ConfigurationDeployment started at must not be null.");
        }

        id = normalize(id);
        deploymentNumber = normalize(deploymentNumber);
        changeRequestId = normalize(changeRequestId);
        profileId = normalize(profileId);
        environment = normalize(environment);
        deployedByActorId = normalize(deployedByActorId);
        rollbackDeploymentId = normalize(rollbackDeploymentId);
        failureReason = normalize(failureReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
