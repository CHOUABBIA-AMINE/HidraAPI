/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for Party.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.repository;

import dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Party.
 */
@Repository
public interface PartyJpaRepository extends JpaRepository<PartyJpaEntity, String> {
}
