/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GenerateReportArtifactRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.request
 *
 * @Description : REST request for generate report artifact.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.request;

import dz.sh.hidra.modules.reporting.domain.value.ReportArtifactType;
import dz.sh.hidra.modules.reporting.domain.value.ReportFormat;
import java.time.Instant;

/**
 * REST request for generate report artifact.
 */
public record GenerateReportArtifactRequest(
        String reportRunId,
        ReportArtifactType artifactType,
        ReportFormat format,
        String fileName,
        String mimeType,
        String storageObjectReferenceId,
        String documentReferenceId,
        String checksum,
        Long sizeBytes,
        Instant expiresAt
) {
}
