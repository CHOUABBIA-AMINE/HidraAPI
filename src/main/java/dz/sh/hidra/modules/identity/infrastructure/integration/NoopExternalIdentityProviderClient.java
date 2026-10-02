/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopExternalIdentityProviderClient
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.integration
 *
 * @Description : No-op external identity provider client.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.integration;

import java.util.List;
import java.util.Map;

/**
 * No-op external identity provider client.
 */
public class NoopExternalIdentityProviderClient implements ExternalIdentityProviderClient {

    @Override
    public List<Map<String, String>> fetchUsers(String providerCode) {
        return List.of();
    }

    @Override
    public List<Map<String, String>> fetchGroups(String providerCode) {
        return List.of();
    }
}
