/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentitySynchronizationRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Records item-level synchronization outcomes.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Records item-level synchronization outcomes.
 *
     * @param id id
 * @param jobId jobId
 * @param recordType recordType
 * @param externalReference externalReference
 * @param localReferenceId localReferenceId
 * @param operation operation
 * @param status status
 * @param message message
 * @param occurredAt occurredAt
 */
public record IdentitySynchronizationRecord(
        String id,
    String jobId,
    SyncRecordType recordType,
    String externalReference,
    String localReferenceId,
    SyncRecordOperation operation,
    SyncRecordStatus status,
    String message,
    Instant occurredAt
) {

    public IdentitySynchronizationRecord {
    id = normalize(id);
    jobId = normalize(jobId);
    externalReference = normalize(externalReference);
    localReferenceId = normalize(localReferenceId);
    message = normalize(message);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
