/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportOutputArtifactResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.response
 *
 * @Description : REST response for report output artifact.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.response;

import dz.sh.hidra.modules.reporting.domain.value.ReportArtifactType;
import dz.sh.hidra.modules.reporting.domain.value.ReportFormat;
import java.time.Instant;

/**
 * REST response for report output artifact.
 */
public record ReportOutputArtifactResponse(
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
