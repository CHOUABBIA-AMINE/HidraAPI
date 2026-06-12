/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.mapper
 *
 * @Description : Maps identity domain objects to persistence payload wrappers.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;

/**
 * Maps identity domain objects to persistence payload wrappers.
 */
public final class IdentityPersistenceMapper {

    private IdentityPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(user.id(), user.toString());
    }
}
