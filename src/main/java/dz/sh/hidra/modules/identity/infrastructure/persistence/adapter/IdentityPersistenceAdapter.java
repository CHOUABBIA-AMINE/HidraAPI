/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistenceAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Coordinates identity persistence adapters.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;

import java.util.Objects;

/**
 * Coordinates identity persistence adapter dependencies.
 */
public class IdentityPersistenceAdapter {

    private final UserRepositoryPort userRepositoryPort;

    public IdentityPersistenceAdapter(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort, "User repository port must not be null.");
    }

    public UserRepositoryPort userRepositoryPort() {
        return userRepositoryPort;
    }
}
