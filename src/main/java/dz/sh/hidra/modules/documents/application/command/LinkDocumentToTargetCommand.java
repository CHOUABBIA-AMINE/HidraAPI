/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LinkDocumentToTargetCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.command
 *
 * @Description : Command to link a document to a business target.
 *
 */
package dz.sh.hidra.modules.documents.application.command;

/**
 * Command to link a document to a business target.
 */
public record LinkDocumentToTargetCommand(
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
