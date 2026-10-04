/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityProviderJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for IdentityProvider.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.IdentityProviderJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for IdentityProvider.
 */
@Repository
public interface IdentityProviderJpaRepository extends JpaRepository<IdentityProviderJpaEntity, String> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, String id);

    Optional<IdentityProviderJpaEntity> findByProviderTypeAndIssuerUriAndStatus(
            ProviderType providerType,
            String issuerUri,
            IdentityProviderStatus status
    );

    default Optional<IdentityProviderJpaEntity> findByProviderTypeAndIssuerUri(
            ProviderType providerType,
            String issuerUri
    ) {
        return findByProviderTypeAndIssuerUriAndStatus(
                providerType,
                issuerUri,
                IdentityProviderStatus.ACTIVE
        );
    }
}
