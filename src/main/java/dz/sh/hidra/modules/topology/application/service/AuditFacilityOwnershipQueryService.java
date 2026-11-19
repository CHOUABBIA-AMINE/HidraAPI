/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditFacilityOwnershipQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Bounded T28 governed Risk approval scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.audit.AuditFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import java.util.Optional;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditFacilityOwnershipQueryService implements AuditFacilityOwnershipContract {
    private final ControlledDocumentFacilityOwnershipContract facilities;
    public AuditFacilityOwnershipQueryService(ControlledDocumentFacilityOwnershipContract facilities) {
        this.facilities=Objects.requireNonNull(facilities);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> current(String id){
        return facilities.currentFacility(id).map(f->new Facility(f.id(),f.companyId()));
    }
}
