/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCorrectivePreventiveActionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for HseCorrectivePreventiveAction.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.repository;

import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.HseCorrectivePreventiveActionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for HseCorrectivePreventiveAction.
 */
@Repository
public interface HseCorrectivePreventiveActionJpaRepository extends JpaRepository<HseCorrectivePreventiveActionJpaEntity, String> {
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select c from HseCorrectivePreventiveActionJpaEntity c where c.id = :id")
    java.util.Optional<HseCorrectivePreventiveActionJpaEntity> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") String id);

    @org.springframework.data.jpa.repository.Query(value =
      "SELECT c.* FROM hidra_hse_capa c WHERE c.id=:id AND NOT EXISTS "+
      "(SELECT 1 FROM hidra_hse_t17_case_scope s WHERE s.case_id=c.hse_case_id)",
      nativeQuery=true)
    java.util.Optional<HseCorrectivePreventiveActionJpaEntity> findLegacyById(
      @org.springframework.data.repository.query.Param("id") String id);

    @org.springframework.data.jpa.repository.Query(value =
      "SELECT c.* FROM hidra_hse_capa c WHERE NOT EXISTS "+
      "(SELECT 1 FROM hidra_hse_t17_case_scope s WHERE s.case_id=c.hse_case_id) "+
      "ORDER BY c.updated_at DESC,c.id ASC",
      countQuery="SELECT count(*) FROM hidra_hse_capa c WHERE NOT EXISTS "+
      "(SELECT 1 FROM hidra_hse_t17_case_scope s WHERE s.case_id=c.hse_case_id)",nativeQuery=true)
    org.springframework.data.domain.Page<HseCorrectivePreventiveActionJpaEntity> findAllLegacy(
       org.springframework.data.domain.Pageable pageable);

    @org.springframework.data.jpa.repository.Query(value =
      "SELECT count(*) FROM hidra_hse_capa c WHERE NOT EXISTS "+
      "(SELECT 1 FROM hidra_hse_t17_case_scope s WHERE s.case_id=c.hse_case_id)",nativeQuery=true)
    long countLegacy();

    @org.springframework.data.jpa.repository.Query(value =
      "SELECT EXISTS(SELECT 1 FROM hidra_hse_capa c JOIN "+
      "hidra_hse_t17_case_scope s ON s.case_id=c.hse_case_id WHERE c.id=:id)",nativeQuery=true)
    boolean existsEnrolled(@org.springframework.data.repository.query.Param("id") String id);

}
