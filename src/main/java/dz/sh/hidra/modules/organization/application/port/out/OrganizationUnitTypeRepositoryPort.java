/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for OrganizationUnitType.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitType;

import java.util.Optional;

/**
 * Repository port for OrganizationUnitType.
 */
public interface OrganizationUnitTypeRepositoryPort {

    OrganizationUnitType save(OrganizationUnitType model);

    Optional<OrganizationUnitType> findById(String id);
}
