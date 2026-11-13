/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleNativeScopeAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.security
 *
 * @Description : Joins Topology ownership, Identity permissions and Telemetry binding eligibility.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.monitoring.MonitoringRuleScopedAuthorizationContract;
import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleAuthorizationPort;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringRulePointEligibilityContract;
import dz.sh.hidra.modules.topology.application.contract.monitoring.MonitoringRuleAssetOwnershipContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public final class MonitoringRuleNativeScopeAdapter implements MonitoringRuleAuthorizationPort {
    private final MonitoringRuleAssetOwnershipContract topology;
    private final MonitoringRuleScopedAuthorizationContract identity;
    private final MonitoringRulePointEligibilityContract telemetry;
    public MonitoringRuleNativeScopeAdapter(MonitoringRuleAssetOwnershipContract topology,
            MonitoringRuleScopedAuthorizationContract identity,
            MonitoringRulePointEligibilityContract telemetry){
        this.topology=Objects.requireNonNull(topology);this.identity=Objects.requireNonNull(identity);
        this.telemetry=Objects.requireNonNull(telemetry);
    }
    @Override public Scope require(Operation op,String type,String id,String expectedCompany){
        if(op==null)throw unavailable();
        var owner=topology.current(type,id).orElseThrow(MonitoringRuleNativeScopeAdapter::unavailable);
        if(!owner.type().equals(type)||!owner.id().equals(id)
                ||(expectedCompany!=null&&!expectedCompany.equals(owner.companyPartyId())))
            throw unavailable();
        var nativeOp=switch(op){
            case CREATE -> MonitoringRuleScopedAuthorizationContract.Operation.CREATE;
            case REVISE -> MonitoringRuleScopedAuthorizationContract.Operation.REVISE;
            case ACTIVATE -> MonitoringRuleScopedAuthorizationContract.Operation.ACTIVATE;
            case EVALUATE -> MonitoringRuleScopedAuthorizationContract.Operation.EVALUATE;
            case READ -> MonitoringRuleScopedAuthorizationContract.Operation.READ;
        };
        return new Scope(owner.companyPartyId(),
                identity.require(nativeOp,owner.companyPartyId(),type,id));
    }
    @Override public void requirePoint(String point,String type,String id,String unit,Instant at){
        telemetry.requireEligible(point,type,id,unit,at);
    }
    private static AccessDeniedException unavailable(){
        return new AccessDeniedException("Protected Monitoring rule unavailable.");
    }
}
