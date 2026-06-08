/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeTranslationJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for workflow catalog translations.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeTranslationJpaEntity;

/**
 * Spring Data repository for workflow catalog translations.
 */
public interface WorkflowTypeTranslationJpaRepository extends JpaRepository<WorkflowTypeTranslationJpaEntity, String> {

    List<WorkflowTypeTranslationJpaEntity> findByTypeId(String typeId);

    List<WorkflowTypeTranslationJpaEntity> findByTypeIdIn(java.util.Collection<String> typeIds);

    void deleteByTypeId(String typeId);
}
