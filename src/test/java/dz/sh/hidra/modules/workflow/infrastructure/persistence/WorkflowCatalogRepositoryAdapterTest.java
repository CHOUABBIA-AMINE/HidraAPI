/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Tests workflow catalog repository adapter.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.WorkflowCatalogRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTypeCatalogJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTypeCatalogJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTypeTranslationJpaRepository;

/**
 * Tests workflow catalog repository adapter.
 */
class WorkflowCatalogRepositoryAdapterTest {

    @Test
    void saveFindResolveAndListCatalogEntriesThroughJpaRepositories() {
        WorkflowPersistenceMapper mapper = WorkflowPersistenceTestFixtures.mapper();
        WorkflowTypeCatalog catalog = WorkflowPersistenceTestFixtures.catalog();
        WorkflowTypeCatalogJpaEntity catalogEntity = mapper.toEntity(catalog);

        WorkflowTypeCatalogJpaRepository catalogRepository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowTypeCatalogJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "save" -> arguments[0];
                    case "findById" -> Optional.of(catalogEntity);
                    case "findByCatalogNameAndCode" -> Optional.of(catalogEntity);
                    case "existsByCatalogNameAndCode" -> true;
                    case "findByCatalogName", "findByActive", "findByCatalogNameAndActive", "findAll" ->
                            WorkflowPersistenceTestFixtures.pageOf(catalogEntity);
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowTypeTranslationJpaRepository translationRepository = WorkflowPersistenceTestFixtures.repositoryProxy(
                WorkflowTypeTranslationJpaRepository.class,
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "save" -> arguments[0];
                    case "findByTypeId" -> WorkflowPersistenceTestFixtures.translationEntities();
                    default -> throw new UnsupportedOperationException(method.getName());
                });

        WorkflowCatalogRepositoryAdapter adapter = new WorkflowCatalogRepositoryAdapter(
                catalogRepository,
                translationRepository,
                mapper);

        assertEquals("NORMAL", adapter.save(catalog).code().value());
        assertTrue(adapter.findById(catalog.id()).isPresent());
        assertTrue(adapter.findByCatalogNameAndCode("PRIORITY", WorkflowCode.of("NORMAL")).isPresent());
        assertTrue(adapter.existsByCatalogNameAndCode("PRIORITY", WorkflowCode.of("NORMAL")));
        assertEquals(1, adapter.findAll("PRIORITY", true, "fr", PageRequest.of(0, 20)).items().size());
    }
}
