/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderLifecycleGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.service
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.assets.domain.service;

import dz.sh.hidra.modules.assets.domain.value.MaintenanceWorkOrderStatus;
import java.util.Objects;
/** Aggregate transition policy; no infrastructure or cross-module imports. */
public final class IntegrityWorkOrderLifecycleGuard {
    public MaintenanceWorkOrderStatus next(MaintenanceWorkOrderStatus current,String operation,
            String actorId,String assignedActorId,String completionId,String expectedCompletionId,
            String verifierDecision) {
        return switch(operation) {
            case "ASSIGN" -> {
                if(current!=MaintenanceWorkOrderStatus.DRAFT)throw new IllegalStateException("Work order is not assignable.");
                yield MaintenanceWorkOrderStatus.SCHEDULED;
            }
            case "START" -> {
                if(current!=MaintenanceWorkOrderStatus.SCHEDULED
                        || !Objects.equals(actorId,assignedActorId))
                    throw new IllegalStateException("Only assigned actor may start scheduled work.");
                yield MaintenanceWorkOrderStatus.IN_PROGRESS;
            }
            case "EXECUTE" -> {
                if(current!=MaintenanceWorkOrderStatus.IN_PROGRESS
                        || !Objects.equals(actorId,assignedActorId))
                    throw new IllegalStateException("Only assigned actor may complete work.");
                yield MaintenanceWorkOrderStatus.COMPLETED;
            }
            case "VERIFY" -> {
                if(current!=MaintenanceWorkOrderStatus.COMPLETED
                    || Objects.equals(actorId,assignedActorId)
                    || completionId==null || !Objects.equals(completionId,expectedCompletionId)
                    || !java.util.Set.of("ACCEPT","REJECT").contains(verifierDecision))
                    throw new IllegalStateException("Independent verification requires the exact completion receipt.");
                yield "ACCEPT".equals(verifierDecision)?MaintenanceWorkOrderStatus.VERIFIED:MaintenanceWorkOrderStatus.COMPLETED;
            }
            default -> throw new IllegalArgumentException("Unknown governed work-order transition.");
        };
    }
    public static String required(String s,int maximum) {
        if(s==null||s.isBlank()||s.length()>maximum||!s.equals(s.strip()))
            throw new IllegalArgumentException("Invalid bounded maintenance declaration.");
        return s;
    }
}
