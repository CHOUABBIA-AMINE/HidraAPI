/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationExternalReferenceValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.integration
 *
 * @Description : Validates external neutral references.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.integration;

/**
 * Validates external neutral references without importing external module domain models.
 */
public interface OrganizationExternalReferenceValidator {

    boolean exists(String targetModule, String targetType, String targetId);
}
