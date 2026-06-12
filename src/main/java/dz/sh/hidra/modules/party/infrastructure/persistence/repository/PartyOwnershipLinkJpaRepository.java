/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyOwnershipLinkJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for PartyOwnershipLink.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.repository;

import dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyOwnershipLinkJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for PartyOwnershipLink.
 */
@Repository
public interface PartyOwnershipLinkJpaRepository extends JpaRepository<PartyOwnershipLinkJpaEntity, String> {
}
