/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseTopologyReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.integrity
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.integrity;

import java.util.Optional;
public interface IntegrityCaseTopologyReferenceContract {
    record Asset(String id,String code,String label) {}
    Optional<Asset> resolve(String type,String id);
}
