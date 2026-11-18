/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrationSyntheticFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.integration
 * @Description : Current active physical facility company ownership scalar export for Integration.
 */
package dz.sh.hidra.modules.topology.application.contract.integration;
import java.util.Optional;
public interface IntegrationSyntheticFacilityOwnershipContract {
    record Facility(String id,String companyId) {}
    Optional<Facility> current(String facilityId);
}
