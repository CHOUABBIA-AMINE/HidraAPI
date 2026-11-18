/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ReportingFacilityOwnershipQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.topology.application.service;
import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.reporting.ReportingFacilityOwnershipContract;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportingFacilityOwnershipQueryService implements ReportingFacilityOwnershipContract {
    private final ControlledDocumentFacilityOwnershipContract facilities;
    public ReportingFacilityOwnershipQueryService(ControlledDocumentFacilityOwnershipContract facilities) {
        this.facilities = facilities;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> current(String facility) {
        return facilities.currentFacility(facility).map(f -> new Facility(f.id(),f.companyId()));
    }
}
