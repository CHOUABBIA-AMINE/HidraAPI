/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.service
 * @Description : Pure transition, owner separation and closure eligibility invariants for enrolled HSE CAPAs.
 */
package dz.sh.hidra.modules.hse.domain.service;

import dz.sh.hidra.modules.hse.domain.value.CapaStatus;
import dz.sh.hidra.modules.hse.domain.model.HseCapaVerification;
import java.util.List;
import java.util.Objects;
public final class HseCapaLifecycleGuard {
    public CapaStatus next(CapaStatus state,String operation,boolean hasEvidence,
                           boolean approval,boolean independent, HseCapaVerification.Finding finding) {
        Objects.requireNonNull(state);Objects.requireNonNull(operation);
        return switch(operation) {
            case "APPROVE" -> require(state==CapaStatus.PROPOSED&&approval,CapaStatus.APPROVED);
            case "ASSIGN" -> require(state==CapaStatus.APPROVED,CapaStatus.IN_PROGRESS);
            case "EVIDENCE" -> require(state==CapaStatus.IN_PROGRESS,state);
            case "COMPLETE" -> require(state==CapaStatus.IN_PROGRESS&&hasEvidence,CapaStatus.COMPLETED);
            case "VERIFY" -> {
                require(state==CapaStatus.COMPLETED&&hasEvidence&&independent
                        &&finding!=null,CapaStatus.COMPLETED);
                yield finding==HseCapaVerification.Finding.EFFECTIVE
                        ? CapaStatus.VERIFIED : CapaStatus.COMPLETED;
            }
            case "CLOSE" -> require(state==CapaStatus.VERIFIED&&hasEvidence&&independent,CapaStatus.CLOSED);
            default -> throw new IllegalArgumentException("Unknown T17 CAPA transition.");
        };
    }
    public void mayCloseCase(List<CapaStatus> states,boolean acceptedEvidence,
                             boolean impactAttested,boolean approvedIndependentDecision) {
        if(states==null||states.isEmpty()||states.stream().anyMatch(s->s!=CapaStatus.VERIFIED)
                ||!acceptedEvidence||!impactAttested||!approvedIndependentDecision)
            throw new IllegalStateException("Authoritative CAPA, impact, evidence and independent HSE case review required.");
    }
    public void independent(String proposer,String assignee,String verifier) {
        if(verifier==null||verifier.isBlank()||verifier.equals(proposer)||verifier.equals(assignee))
            throw new SecurityException("Independent CAPA reviewer required.");
    }
    private static CapaStatus require(boolean allowed,CapaStatus next){
        if(!allowed)throw new IllegalStateException("Ineligible enrolled CAPA transition.");
        return next;
    }
}
