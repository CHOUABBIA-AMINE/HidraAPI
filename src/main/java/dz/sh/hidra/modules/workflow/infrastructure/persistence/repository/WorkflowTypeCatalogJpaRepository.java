/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeCatalogJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for workflow catalog entries.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeCatalogJpaEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for workflow catalog entries.
 */
public interface WorkflowTypeCatalogJpaRepository extends JpaRepository<WorkflowTypeCatalogJpaEntity, String> {

    Optional<WorkflowTypeCatalogJpaEntity> findByCatalogNameAndCode(String catalogName, String code);

    boolean existsByCatalogNameAndCode(String catalogName, String code);

    Page<WorkflowTypeCatalogJpaEntity> findByCatalogName(String catalogName, Pageable pageable);

    Page<WorkflowTypeCatalogJpaEntity> findByActive(Boolean active, Pageable pageable);

    Page<WorkflowTypeCatalogJpaEntity> findByCatalogNameAndActive(String catalogName, Boolean active, Pageable pageable);
}
