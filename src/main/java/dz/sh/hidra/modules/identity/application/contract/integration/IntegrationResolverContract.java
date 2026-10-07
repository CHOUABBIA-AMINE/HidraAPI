/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationResolverContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.integration
 *
 * @Description : Enforces Integration evidence integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.integration;
import java.time.Instant;
/** Identity controls resolver eligibility; existence is not a permission grant. */
public interface IntegrationResolverContract {
    boolean eligibleResolver(String actorId, Instant at);
}
