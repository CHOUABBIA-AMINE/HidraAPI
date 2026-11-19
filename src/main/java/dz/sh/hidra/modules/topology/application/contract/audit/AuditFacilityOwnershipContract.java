/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.audit
 *
 * @Description : Defines exact T28 governed scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.audit;

import java.util.Optional;
/** Current active facility ownership, not a cached Company or Topology aggregate. */
public interface AuditFacilityOwnershipContract {
    record Facility(String id,String companyId){}
    Optional<Facility> current(String facilityId);
}
