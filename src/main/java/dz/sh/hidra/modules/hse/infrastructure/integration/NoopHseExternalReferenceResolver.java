/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopHseExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.integration
 *
 * @Description : No-op HSE external reference resolver.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.integration;

/**
 * No-op HSE external reference resolver.
 */
public class NoopHseExternalReferenceResolver implements HseExternalReferenceResolver {

    @Override
    public boolean targetExists(String targetModule, String targetTypeCode, String targetId) {
        return true;
    }
}
