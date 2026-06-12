/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.integration
 *
 * @Description : Resolves HSE external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.integration;

/**
 * Resolves HSE external references without importing foreign domain models.
 */
public interface HseExternalReferenceResolver {

    boolean targetExists(String targetModule, String targetTypeCode, String targetId);
}
