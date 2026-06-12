/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.response
 *
 * @Description : REST response for document.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.response;

import dz.sh.hidra.modules.documents.domain.value.DocumentStatus;

import java.time.Instant;

/**
 * REST response for document.
 */
public record DocumentResponse(
        String id,
        String code,
        String titleFr,
        String documentTypeId,
        String classificationId,
        int confidentialityLevel,
        DocumentStatus status,
        String currentVersionId,
        String ownerModule,
        String ownerTargetId,
        Instant createdAt
) {
}
