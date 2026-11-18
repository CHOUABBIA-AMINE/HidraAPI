/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ReportingFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.reporting
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.topology.application.contract.reporting;
import java.util.Optional;
public interface ReportingFacilityOwnershipContract {
    record Facility(String id, String companyId) {}
    Optional<Facility> current(String facility);
}
