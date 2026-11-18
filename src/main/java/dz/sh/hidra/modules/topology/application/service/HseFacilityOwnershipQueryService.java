/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseFacilityOwnershipQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 * @Description : Topology-owned FACILITY query reusing the current locked ownership evidence.
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.hse.HseFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class HseFacilityOwnershipQueryService implements HseFacilityOwnershipContract {
    private final ControlledDocumentFacilityOwnershipContract owner;
    public HseFacilityOwnershipQueryService(ControlledDocumentFacilityOwnershipContract owner){this.owner=owner;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> active(String id){
        return owner.currentFacility(id).map(x->new Facility(x.id(),x.companyId(),x.code(),x.label()));
    }
}
