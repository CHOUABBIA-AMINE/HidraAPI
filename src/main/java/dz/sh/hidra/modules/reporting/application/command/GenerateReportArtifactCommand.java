/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GenerateReportArtifactCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.command
 *
 * @Description : Command to register a generated report artifact.
 *
 */
package dz.sh.hidra.modules.reporting.application.command;

import dz.sh.hidra.modules.reporting.domain.value.ReportArtifactType;
import dz.sh.hidra.modules.reporting.domain.value.ReportFormat;

import java.time.Instant;

/**
 * Command to register a generated report artifact.
 */
public record GenerateReportArtifactCommand(
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
