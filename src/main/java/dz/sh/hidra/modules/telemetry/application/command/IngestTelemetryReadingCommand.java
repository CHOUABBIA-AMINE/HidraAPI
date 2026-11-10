/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IngestTelemetryReadingCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Untrusted typed raw-reading ingestion request after point path binding.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;


import java.math.BigDecimal;
import java.time.Instant;

/** Source/company/actor are resolved at the server; only the source reference is asserted by a client. */
public record IngestTelemetryReadingCommand(
        String pointId, String sourceId, String sourceSequenceNumber,
        BigDecimal numericValue, String textValue, Boolean booleanValue,
        String unitId, String qualityCodeId, Instant sourceTimestamp) { }
