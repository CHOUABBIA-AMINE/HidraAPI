/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterDocumentRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.request
 *
 * @Description : REST request to register document.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.request;

/**
 * REST request to register document.
 */
public record RegisterDocumentRequest(
        String code,
        String titleAr,
        String titleFr,
        String titleEn,
        String documentTypeId,
        String documentCategoryId,
        String classificationId,
        int confidentialityLevel,
        String ownerModule,
        String ownerTargetTypeCode,
        String ownerTargetId,
        String ownerTargetCodeSnapshot,
        String ownerTargetLabelSnapshot,
        String createdByActorId,
        String createdByDisplayNameSnapshot
) {
}
