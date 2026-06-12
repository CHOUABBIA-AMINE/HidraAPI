/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentSearchProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.projection
 *
 * @Description : Document search projection.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.projection;

import dz.sh.hidra.modules.documents.domain.value.DocumentStatus;

import java.time.Instant;

/**
 * Document search projection.
 */
public record DocumentSearchProjection(
        String documentId,
        String code,
        String titleFr,
        String documentTypeId,
        String classificationId,
        DocumentStatus status,
        String ownerModule,
        String ownerTargetId,
        Instant createdAt
) {
}
