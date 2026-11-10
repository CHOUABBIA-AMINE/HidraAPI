/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Checks raw reading shape and bounded time and hashes canonical replay data.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;


import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.HexFormat;

/** No physical qualification or trust promotion: this is a deterministic transport admission policy. */
public final class TelemetryIngestionPolicy {
    private TelemetryIngestionPolicy() { }
    public static void validateTime(Instant source, Instant received, Duration maxPast, Duration maxFuture) {
        if (source == null || received == null || maxPast == null || maxFuture == null
                || maxPast.isNegative() || maxPast.isZero()
                || maxFuture.isNegative() || maxFuture.isZero()
                || source.isBefore(received.minus(maxPast))
                || source.isAfter(received.plus(maxFuture))) {
            throw new InvalidTelemetryValueException("Source timestamp outside configured UTC admission window.");
        }
    }
    public static String shape(BigDecimal numeric, String text, Boolean bool) {
        int values = (numeric != null ? 1 : 0) + (text != null && !text.isBlank() ? 1 : 0)
                + (bool != null ? 1 : 0);
        if (values != 1) throw new InvalidTelemetryValueException("Exactly one typed observation required.");
        if (numeric != null) {
            if (numeric.precision() - numeric.scale() > 12 || numeric.scale() > 6)
                throw new InvalidTelemetryValueException("Numeric observation exceeds persistence precision.");
            return "NUMERIC";
        }
        return text != null && !text.isBlank() ? "TEXT" : "BOOLEAN";
    }
    public static String digest(String sourceId, String unitId, TelemetryReading reading) {
        StringBuilder data = new StringBuilder("HAPI-933-RAW-v1");
        append(data, sourceId);
        append(data, reading.pointId());
        append(data, reading.sourceSequenceNumber());
        append(data, reading.externalTagMappingId());
        append(data, reading.numericValue() == null ? null : reading.numericValue().stripTrailingZeros().toPlainString());
        append(data, reading.textValue());
        append(data, reading.booleanValue() == null ? null : reading.booleanValue().toString());
        append(data, unitId);
        append(data, reading.qualityCodeId());
        append(data, reading.sourceTimestamp().toString());
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(data.toString().getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 unavailable.", impossible);
        }
    }
    private static void append(StringBuilder target, String value) {
        if (value == null) target.append("|-1:");
        else target.append('|').append(value.length()).append(':').append(value);
    }
}
