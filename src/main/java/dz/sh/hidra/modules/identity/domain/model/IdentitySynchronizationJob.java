/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentitySynchronizationJob
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Records one synchronization execution with an external identity provider.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Records one synchronization execution with an external identity provider.
 *
     * @param id id
 * @param identityProviderId identityProviderId
 * @param syncType syncType
 * @param triggerType triggerType
 * @param startedAt startedAt
 * @param completedAt completedAt
 * @param status status
 * @param usersCreated usersCreated
 * @param usersUpdated usersUpdated
 * @param usersDisabled usersDisabled
 * @param groupsCreated groupsCreated
 * @param groupsUpdated groupsUpdated
 * @param membershipsUpdated membershipsUpdated
 * @param errorMessage errorMessage
 * @param correlationId correlationId
 */
public record IdentitySynchronizationJob(
        String id,
    String identityProviderId,
    SyncType syncType,
    SyncTriggerType triggerType,
    Instant startedAt,
    Instant completedAt,
    SyncJobStatus status,
    int usersCreated,
    int usersUpdated,
    int usersDisabled,
    int groupsCreated,
    int groupsUpdated,
    int membershipsUpdated,
    String errorMessage,
    String correlationId
) {

    public IdentitySynchronizationJob {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("IdentitySynchronizationJob id must not be blank.");
        }
        // HRA-051 required: identityProviderId
        if (identityProviderId == null || identityProviderId.isBlank()) {
            throw new InvalidIdentityValueException("IdentitySynchronizationJob identity provider id must not be blank.");
        }
        // HRA-051 required: syncType
        if (syncType == null) {
            throw new InvalidIdentityValueException("IdentitySynchronizationJob sync type must not be null.");
        }
        // HRA-051 required: triggerType
        if (triggerType == null) {
            throw new InvalidIdentityValueException("IdentitySynchronizationJob trigger type must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidIdentityValueException("IdentitySynchronizationJob started at must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("IdentitySynchronizationJob status must not be null.");
        }

    id = normalize(id);
    identityProviderId = normalize(identityProviderId);
    errorMessage = normalize(errorMessage);
    correlationId = normalize(correlationId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
