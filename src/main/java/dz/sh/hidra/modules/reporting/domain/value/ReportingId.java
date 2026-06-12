/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Stable reporting identifier.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;

import java.util.UUID;

/**
 * Stable reporting identifier.
 *
 * @param value identifier value
 */
public record ReportingId(String value) {

    public ReportingId {
        value = requireText(value, "Reporting ID must not be null or blank.");
    }

    public static ReportingId of(String value) {
        return new ReportingId(value);
    }

    public static ReportingId newId() {
        return new ReportingId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidReportingValueException(message);
        }
        return value.trim();
    }
}
