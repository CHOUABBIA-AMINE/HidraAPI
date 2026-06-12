/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportOutputArtifactSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.dto
 *
 * @Description : Report output artifact summary DTO.
 *
 */
package dz.sh.hidra.modules.reporting.application.dto;

import dz.sh.hidra.modules.reporting.domain.value.ReportArtifactType;
import dz.sh.hidra.modules.reporting.domain.value.ReportFormat;

import java.time.Instant;

/**
 * Report output artifact summary DTO.
 */
public record ReportOutputArtifactSummaryDto(
        String id,
        String reportRunId,
        ReportArtifactType artifactType,
        ReportFormat format,
        String fileName,
        String mimeType,
        String documentReferenceId,
        String checksum,
        Long sizeBytes,
        Instant generatedAt
) {
}
