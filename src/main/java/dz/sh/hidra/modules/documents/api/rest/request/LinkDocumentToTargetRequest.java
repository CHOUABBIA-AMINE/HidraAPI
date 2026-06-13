/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LinkDocumentToTargetRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.request
 *
 * @Description : REST request for link document to target.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.request;

/**
 * REST request for link document to target.
 */
public record LinkDocumentToTargetRequest(
        String documentId,
        String documentVersionId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String linkRoleId,
        boolean primaryLink,
        String linkedByActorId
) {
}
