/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDocumentReferenceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for CustodyDocumentReference.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.repository;

import dz.sh.hidra.modules.custody.infrastructure.persistence.entity.CustodyDocumentReferenceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for CustodyDocumentReference.
 */
@Repository
public interface CustodyDocumentReferenceJpaRepository extends JpaRepository<CustodyDocumentReferenceJpaEntity, String> {
}
