/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalIdentityProviderClient
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.integration
 *
 * @Description : Defines external identity provider client contract.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.integration;

import java.util.List;
import java.util.Map;

/**
 * External identity provider client contract.
 */
public interface ExternalIdentityProviderClient {

    List<Map<String, String>> fetchUsers(String providerCode);

    List<Map<String, String>> fetchGroups(String providerCode);
}
