/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.policy
 *
 * @Description : Validates notification ownership and provider-secret boundaries.
 *
 */
package dz.sh.hidra.modules.notification.domain.policy;

import java.util.Locale;

/**
 * Validates notification ownership and provider-secret boundaries.
 */
public final class NotificationBoundaryPolicy {

    private NotificationBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenBusinessOwnership(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase(Locale.ROOT);
        return normalized.contains("alarmseverity")
                || normalized.contains("incidentstatus")
                || normalized.contains("workflowapproval")
                || normalized.contains("hsecasestatus")
                || normalized.contains("auditledger")
                || normalized.contains("assetworkorder")
                || normalized.contains("custodyticket");
    }

    public static boolean containsProviderSecret(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        return normalized.contains("password")
                || normalized.contains("secret=")
                || normalized.contains("token=")
                || normalized.contains("apikey")
                || normalized.contains("private_key")
                || normalized.contains("credential_value");
    }
}
