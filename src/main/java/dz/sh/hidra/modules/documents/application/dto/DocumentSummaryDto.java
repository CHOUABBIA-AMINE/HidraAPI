/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.dto
 *
 * @Description : Document summary DTO.
 *
 */
package dz.sh.hidra.modules.documents.application.dto;

import dz.sh.hidra.modules.documents.domain.value.DocumentStatus;

import java.time.Instant;

/**
 * Document summary DTO.
 */
public record DocumentSummaryDto(
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
