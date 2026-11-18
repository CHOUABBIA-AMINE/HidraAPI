/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrationSyntheticFacilityOwnershipQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 * @Description : Current locked Topology facility ownership fact for Integration T26.
 */
package dz.sh.hidra.modules.topology.application.service;
import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.integration.IntegrationSyntheticFacilityOwnershipContract;
import java.util.Optional;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IntegrationSyntheticFacilityOwnershipQueryService
        implements IntegrationSyntheticFacilityOwnershipContract {
    private final ControlledDocumentFacilityOwnershipContract facilities;
    public IntegrationSyntheticFacilityOwnershipQueryService(
            ControlledDocumentFacilityOwnershipContract facilities) {
        this.facilities=Objects.requireNonNull(facilities);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> current(String facilityId) {
        return facilities.currentFacility(facilityId)
                .map(f -> new Facility(f.id(),f.companyId()));
    }
}
