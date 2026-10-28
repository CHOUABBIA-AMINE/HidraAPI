/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines owner-private physical unit and installation evidence persistence.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Topology-private storage contract; NOT an authorized public use case.
 * Protected application orchestration must validate current owner permissions
 * and execute close-plus-replacement in one physical transaction.
 */
public interface InstalledUnitRepositoryPort {
    InstalledUnit register(InstalledUnit unit);
    Optional<InstalledUnit> findUnit(String companyPartyId, String unitId);
    InstalledUnitInstallation record(InstalledUnitInstallation installation);
    InstalledUnitInstallation close(String companyPartyId, String installationId,
            long expectedRevision, Instant effectiveUntil);
    Optional<InstalledUnitInstallation> findInstallation(String companyPartyId, String installationId);
    List<InstalledUnitInstallation> history(String companyPartyId, String installationId);
}
