/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLinkSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.dto
 *
 * @Description : Document target link summary DTO.
 *
 */
package dz.sh.hidra.modules.documents.application.dto;

import java.time.Instant;

/**
 * Document target link summary DTO.
 */
public record DocumentTargetLinkSummaryDto(
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
