/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopOrganizationExternalReferenceValidator
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.integration
 *
 * @Description : No-op external reference validator.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.integration;

/**
 * No-op external reference validator.
 */
public class NoopOrganizationExternalReferenceValidator implements OrganizationExternalReferenceValidator {

    @Override
    public boolean exists(String targetModule, String targetType, String targetId) {
        return true;
    }
}
