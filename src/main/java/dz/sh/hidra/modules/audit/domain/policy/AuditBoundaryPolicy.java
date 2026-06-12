/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.policy
 *
 * @Description : Validates audit ownership boundaries.
 *
 */
package dz.sh.hidra.modules.audit.domain.policy;

import java.util.Locale;

/**
 * Validates audit ownership boundaries.
 */
public final class AuditBoundaryPolicy {

    private AuditBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenBusinessOperation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase(Locale.ROOT);
        return normalized.contains("approve_business")
                || normalized.contains("reject_business")
                || normalized.contains("validate_business")
                || normalized.contains("repair")
                || normalized.contains("reconcile_business")
                || normalized.contains("calculate_business")
                || normalized.contains("notify")
                || normalized.contains("integrate");
    }

    public static boolean isSensitiveFieldPath(String fieldPath) {
        if (fieldPath == null) {
            return false;
        }
        String normalized = fieldPath.toLowerCase(Locale.ROOT);
        return normalized.contains("password")
                || normalized.contains("token")
                || normalized.contains("secret")
                || normalized.contains("privatekey")
                || normalized.contains("credential")
                || normalized.contains("apikey")
                || normalized.contains("session");
    }
}
