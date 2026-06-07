/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryLocalizedName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Multilingual telemetry name projection value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Multilingual name projection for user-facing telemetry entities and catalog entries.
 *
 * <p>Business rule:
 * French is mandatory in the first implementation because it is the default business language.
 * Arabic and English are optional but must be supported by the contract.
 *
 * @param nameAr Arabic label, optional
 * @param nameFr French label, mandatory
 * @param nameEn English label, optional
 */
public record TelemetryLocalizedName(String nameAr, String nameFr, String nameEn) implements ValueObject {

    public TelemetryLocalizedName {
        nameAr = normalizeOptional(nameAr);
        nameFr = normalizeRequired(nameFr, "nameFr");
        nameEn = normalizeOptional(nameEn);
    }

    public static TelemetryLocalizedName of(String nameAr, String nameFr, String nameEn) {
        return new TelemetryLocalizedName(nameAr, nameFr, nameEn);
    }

    private static String normalizeRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryLocalizedName " + fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 160) {
            throw new InvalidValueObjectException("TelemetryLocalizedName " + fieldName + " length must not exceed 160 characters.");
        }

        return normalized;
    }

    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 160) {
            throw new InvalidValueObjectException("TelemetryLocalizedName optional value length must not exceed 160 characters.");
        }

        return normalized;
    }
}
