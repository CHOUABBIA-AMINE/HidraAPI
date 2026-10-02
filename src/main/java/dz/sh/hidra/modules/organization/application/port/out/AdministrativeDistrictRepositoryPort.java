/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AdministrativeDistrictRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for AdministrativeDistrict.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.AdministrativeDistrict;

import java.util.Optional;

/**
 * Repository port for AdministrativeDistrict.
 */
public interface AdministrativeDistrictRepositoryPort {

    AdministrativeDistrict save(AdministrativeDistrict model);

    Optional<AdministrativeDistrict> findById(String id);
}
