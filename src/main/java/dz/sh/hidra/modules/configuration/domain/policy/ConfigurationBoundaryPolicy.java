/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.policy
 *
 * @Description : Validates configuration ownership and secret-reference boundaries.
 *
 */
package dz.sh.hidra.modules.configuration.domain.policy;

import java.util.Locale;

/**
 * Validates configuration ownership and secret-reference boundaries.
 */
public final class ConfigurationBoundaryPolicy {

    private ConfigurationBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenBusinessTaxonomy(String taxonomyName) {
        if (taxonomyName == null) {
            return false;
        }
        String normalized = taxonomyName.toLowerCase(Locale.ROOT);
        return normalized.contains("facilitytype")
                || normalized.contains("equipmenttype")
                || normalized.contains("telemetryqualitycode")
                || normalized.contains("workflowdefinition")
                || normalized.contains("monitoringthreshold")
                || normalized.contains("alarmseverity")
                || normalized.contains("incidentclassification")
                || normalized.contains("hsecomplianceobligation")
                || normalized.contains("custodycalculationformula")
                || normalized.contains("assetmaintenancestrategy")
                || normalized.contains("identitypermission")
                || normalized.contains("organizationhierarchy");
    }

    public static boolean containsSecretMaterial(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        return normalized.contains("password")
                || normalized.contains("secret=")
                || normalized.contains("token=")
                || normalized.contains("private_key")
                || normalized.contains("credential_value")
                || normalized.contains("apikey");
    }
}
