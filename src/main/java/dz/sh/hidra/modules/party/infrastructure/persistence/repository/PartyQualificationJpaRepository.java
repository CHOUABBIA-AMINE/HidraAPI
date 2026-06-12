/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyQualificationJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for PartyQualification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.repository;

import dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyQualificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for PartyQualification.
 */
@Repository
public interface PartyQualificationJpaRepository extends JpaRepository<PartyQualificationJpaEntity, String> {
}
