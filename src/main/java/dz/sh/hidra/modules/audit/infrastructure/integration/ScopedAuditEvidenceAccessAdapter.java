/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Bounded T28 governed Risk approval scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.port.out.ScopedAuditEvidenceAccessPort;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.Scope;
import dz.sh.hidra.modules.identity.application.contract.audit.ScopedAuditEvidenceAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.audit.AuditFacilityOwnershipContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class ScopedAuditEvidenceAccessAdapter implements ScopedAuditEvidenceAccessPort {
    private final ScopedAuditEvidenceAuthorizationContract identity;
    private final AuditFacilityOwnershipContract topology;
    public ScopedAuditEvidenceAccessAdapter(ScopedAuditEvidenceAuthorizationContract identity,
            AuditFacilityOwnershipContract topology) {
        this.identity=Objects.requireNonNull(identity);this.topology=Objects.requireNonNull(topology);
    }
    @Override public String nativeActor(){return identity.nativeActor();}
    @Override public boolean permitted(String actor,Scope scope,boolean export) {
        if(!identity.permitted(actor,scope.companyId(),scope.facilityId(),export))return false;
        return topology.current(scope.facilityId()).filter(f->f.id().equals(scope.facilityId())
                &&f.companyId().equals(scope.companyId())).isPresent();
    }
}
