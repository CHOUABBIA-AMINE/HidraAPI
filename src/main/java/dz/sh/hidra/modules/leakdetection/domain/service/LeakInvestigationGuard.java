/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.service
 * @Description : Pure suspected leak case investigation and independent disposition invariants.
 */
package dz.sh.hidra.modules.leakdetection.domain.service;

import java.util.Set;

/** A suspected leak is not an independently field-verified leak. */
public final class LeakInvestigationGuard {
    public void require(String parent, String state, String operation, String investigator,
                        String actor, boolean evidence, boolean actions) {
        if (parent == null || state == null || actor == null || actor.isBlank())
            throw new IllegalStateException("Missing leak investigation state or actor.");
        if (Set.of("CLOSED","VERIFIED_LEAK","DISMISSED","ESCALATED").contains(parent))
            throw new IllegalStateException("Terminal Leak case cannot be changed.");
        switch(operation) {
            case "ASSIGN" -> {
                if (!"NEW".equals(state) || !"OPEN".equals(parent))
                    throw new IllegalStateException("Only a new open Leak case can be assigned.");
            }
            case "EVIDENCE","ACTION" -> {
                if (!Set.of("ASSIGNED","INVESTIGATING","INSUFFICIENT_EVIDENCE").contains(state)
                        || !Set.of("OPEN","UNDER_INVESTIGATION").contains(parent)
                        || !actor.equals(investigator))
                    throw new IllegalStateException("Only assigned investigator can add Leak evidence or verification.");
            }
            case "SUBMIT" -> {
                if (!"INVESTIGATING".equals(state) || !"UNDER_INVESTIGATION".equals(parent)
                        || !actor.equals(investigator) || !evidence || !actions)
                    throw new IllegalStateException("Leak disposition requires attributable evidence and verification.");
            }
            case "REVIEW" -> {
                if (!"SUBMITTED".equals(state) || !"UNDER_INVESTIGATION".equals(parent)
                        || actor.equals(investigator))
                    throw new IllegalStateException("Independent reviewer required for suspected Leak disposition.");
            }
            default -> throw new IllegalArgumentException("Unsupported Leak investigation transition.");
        }
    }
}
