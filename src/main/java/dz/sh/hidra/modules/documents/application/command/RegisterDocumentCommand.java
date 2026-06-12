/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterDocumentCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.command
 *
 * @Description : Command to register document metadata.
 *
 */
package dz.sh.hidra.modules.documents.application.command;

/**
 * Command to register document metadata.
 */
public record RegisterDocumentCommand(
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
