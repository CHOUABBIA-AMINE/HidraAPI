/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenancePriorityClassifier
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.service
 *
 * @Description : Classifies maintenance priority from condition status.
 *
 */
package dz.sh.hidra.modules.assets.domain.service;

import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;

/**
 * Classifies maintenance priority from condition status.
 */
public class MaintenancePriorityClassifier {

    public String classifyPriority(AssetConditionStatus conditionStatus) {
        if (conditionStatus == null || conditionStatus == AssetConditionStatus.UNKNOWN) {
            return "UNKNOWN";
        }
        return switch (conditionStatus) {
            case CRITICAL -> "P1";
            case DEGRADED -> "P2";
            case WATCH -> "P3";
            case NORMAL -> "P4";
            default -> "UNKNOWN";
        };
    }
}
