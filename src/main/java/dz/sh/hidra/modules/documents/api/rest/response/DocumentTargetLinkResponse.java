/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLinkResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.response
 *
 * @Description : REST response for document target link.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.response;

import java.time.Instant;

/**
 * REST response for document target link.
 */
public record DocumentTargetLinkResponse(
        String id,
        String documentId,
        String documentVersionId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String linkRoleId,
        boolean primaryLink,
        Instant linkedAt,
        boolean active
) {
}
