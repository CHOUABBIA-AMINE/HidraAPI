/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.value
 *
 * @Description : Stable risk identifier.
 *
 */
package dz.sh.hidra.modules.risk.domain.value;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;

import java.util.UUID;

/**
 * Stable risk identifier.
 *
 * @param value identifier value
 */
public record RiskId(String value) {

    public RiskId {
        value = requireText(value, "Risk ID must not be null or blank.");
    }

    public static RiskId of(String value) {
        return new RiskId(value);
    }

    public static RiskId newId() {
        return new RiskId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidRiskValueException(message);
        }
        return value.trim();
    }
}
