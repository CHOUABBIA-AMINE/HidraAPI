/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementScopedAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Enforces separate current signed capture/read grants for company and transfer-point.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CustodyMeasurementScopedAuthorizationQueryService implements CustodyMeasurementScopedAuthorizationContract {
    private static final Set<String> OPS=Set.of("custody:measurements:capture","custody:measurements:read",
        "custody:periods:read","custody:periods:create","custody:reconciliations:read","custody:reconciliations:reconcile",
        "custody:tickets:read","custody:tickets:finalize","custody:tickets:correct",
        "custody:discrepancies:read","custody:discrepancies:dispose");
    private final TelemetryNativeLocalActorPort actors;
    private final IncidentExplicitGrantEvidencePort direct;
    private final AuthorizationEvidencePort evidence;
    private final AuthorizationAssertionPort assertions;
    private final AuthorizationPolicyEvaluator policy=new AuthorizationPolicyEvaluator();
    public CustodyMeasurementScopedAuthorizationQueryService(TelemetryNativeLocalActorPort actors,
        IncidentExplicitGrantEvidencePort direct,AuthorizationEvidencePort evidence,
        AuthorizationAssertionPort assertions){
        this.actors=actors;this.direct=direct;this.evidence=evidence;this.assertions=assertions;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String actor(){return actors.currentLocalActor();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void require(String permission,String company,String point) {
        String actor=actor();
        if(actor==null || actor.isBlank() || !OPS.contains(permission)
            || company==null || company.isBlank() || point==null || point.isBlank()
            || company.length()>80 || point.length()>80)deny();
        String companyRef="custody-company:"+company;
        String pointRef="custody-transfer-point:"+point;
        if(companyRef.length()>120 || pointRef.length()>120)deny();
        Instant at=Instant.now();
        VerifiedAuthorizationAssertion assertion=assertions.currentFor(actor);
        for(String ref:new String[]{companyRef,pointRef}) {
            if(!direct.currentDirectPermit(actor,permission,ScopeType.CUSTOM,ref,at))deny();
            var scope=new AuthorizationScope(ScopeType.CUSTOM,ref,null);
            var request=new AuthorizationEvaluationRequest(actor,permission,null,null,scope);
            if(policy.evaluate(request,evidence.load(request,at,assertion),at).decision()!=AuthorizationDecisionValue.PERMIT)deny();
        }
    }
    private static void deny(){throw new NoSuchElementException("Protected custody measurement unavailable.");}
}
