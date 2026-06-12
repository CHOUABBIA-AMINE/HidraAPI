/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for OrganizationUnitTypeTranslation.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitTypeTranslation;

import java.util.Optional;

/**
 * Repository port for OrganizationUnitTypeTranslation.
 */
public interface OrganizationUnitTypeTranslationRepositoryPort {

    OrganizationUnitTypeTranslation save(OrganizationUnitTypeTranslation model);

    Optional<OrganizationUnitTypeTranslation> findById(String id);
}
