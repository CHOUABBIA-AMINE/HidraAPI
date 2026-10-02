/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Provides telemetry time-series queries and authoritative reference metadata for HidraWeb.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface TelemetryQueryUseCase {

    Page<ReadingView> readings(String pointId, Instant from, Instant to, String state, int page, int size);

    ReadingView latestReading(String pointId);

    List<ReadingView> trend(String pointId, Instant from, Instant to, int limit);

    List<String> readingStates();

    List<QualityCodeView> qualityCodes();

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record ReadingView(
            String id,
            String pointId,
            BigDecimal numericValue,
            String textValue,
            Boolean booleanValue,
            String unitId,
            String qualityCodeId,
            Instant sourceTimestamp,
            Instant receivedAt,
            String state,
            String correlationId,
            String rejectionReason
    ) { }

    record QualityCodeView(
            String id,
            String code,
            boolean active,
            int sortOrder,
            boolean systemDefined,
            Map<String, TranslationView> translations
    ) { }

    record TranslationView(String locale, String name, String description) { }
}
