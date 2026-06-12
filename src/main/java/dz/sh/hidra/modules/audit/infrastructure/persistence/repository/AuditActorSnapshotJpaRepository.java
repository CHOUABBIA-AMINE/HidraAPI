/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActorSnapshotJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AuditActorSnapshot.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.repository;

import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditActorSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AuditActorSnapshot.
 */
@Repository
public interface AuditActorSnapshotJpaRepository extends JpaRepository<AuditActorSnapshotJpaEntity, String> {
}
