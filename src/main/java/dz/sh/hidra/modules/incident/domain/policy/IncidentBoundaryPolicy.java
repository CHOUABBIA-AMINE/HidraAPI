/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.policy
 *
 * @Description : Validates incident ownership boundaries.
 *
 */
package dz.sh.hidra.modules.incident.domain.policy;

/**
 * Validates incident ownership boundaries.
 */
public final class IncidentBoundaryPolicy {

    private IncidentBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenMutationTarget(String targetName) {
        if (targetName == null) {
            return false;
        }
        String normalized = targetName.toLowerCase();
        return normalized.contains("telemetry")
                || normalized.contains("monitoring")
                || normalized.contains("alarm")
                || normalized.contains("leak")
                || normalized.contains("pipeline")
                || normalized.contains("facility")
                || normalized.contains("equipment")
                || normalized.contains("workflowtask");
    }

    public static boolean isHseSpecificTopic(String topicName) {
        if (topicName == null) {
            return false;
        }
        String normalized = topicName.toLowerCase();
        return normalized.contains("injury")
                || normalized.contains("emission")
                || normalized.contains("spill")
                || normalized.contains("regulatory")
                || normalized.contains("environmental damage");
    }
}
