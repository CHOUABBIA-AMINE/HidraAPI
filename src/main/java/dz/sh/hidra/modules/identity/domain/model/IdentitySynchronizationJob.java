/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentitySynchronizationJob
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
