/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentOwnerAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.integration
 * @Description : Only Documents consumer of current Identity and Topology controlled document owner exports.
 */
package dz.sh.hidra.modules.documents.infrastructure.integration;

import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentAuthorizationPort;
import dz.sh.hidra.modules.identity.application.contract.documents.ControlledDocumentScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import dz.sh.hidra.modules.identity.application.contract.reporting.ScopedReportAuthorizationContract;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class ControlledDocumentOwnerAccessAdapter
        implements ControlledDocumentAuthorizationPort {
    private final ControlledDocumentScopedAuthorizationContract identity;
    private final ControlledDocumentFacilityOwnershipContract topology;
    private final ScopedReportAuthorizationContract reporting;
    @Override public void requireReportingRead(String company,String facility) {
        reporting.require("reporting:scoped-report:read",company,facility);
    }
    public ControlledDocumentOwnerAccessAdapter(ControlledDocumentScopedAuthorizationContract identity,
            ControlledDocumentFacilityOwnershipContract topology,ScopedReportAuthorizationContract reporting) {
        this.reporting=Objects.requireNonNull(reporting);
        this.identity=Objects.requireNonNull(identity);
        this.topology=Objects.requireNonNull(topology);
    }
    @Override public String currentLocalActor(){return identity.currentActor();}
    @Override public void require(String permission,String companyId,String facilityId){
        identity.require(permission,companyId,facilityId);
    }
    @Override public Facility requireActiveFacility(String facilityId){
        var f=topology.currentFacility(facilityId)
                .orElseThrow(()->new NoSuchElementException("Protected Documents resource unavailable."));
        return new Facility(f.id(),f.companyId(),f.code(),f.label());
    }
}
