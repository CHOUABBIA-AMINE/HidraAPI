/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchySnapshotRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for OrganizationHierarchySnapshot.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.OrganizationHierarchySnapshot;

import java.util.Optional;

/**
 * Repository port for OrganizationHierarchySnapshot.
 */
public interface OrganizationHierarchySnapshotRepositoryPort {

    OrganizationHierarchySnapshot save(OrganizationHierarchySnapshot model);

    Optional<OrganizationHierarchySnapshot> findById(String id);
}
