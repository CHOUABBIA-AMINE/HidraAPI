/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTypeCatalogTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Unit tests for workflow catalog domain model.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowName;

/**
 * Unit tests for workflow catalog domain model.
 */
class WorkflowTypeCatalogTest {

    @Test
    void catalogNormalizesNameAndResolvesLocalizedTranslations() {
        WorkflowCatalogId catalogId = WorkflowCatalogId.of("workflow-priority-normal");

        WorkflowTypeTranslation fr = WorkflowTypeTranslation.create(
                catalogId,
                "fr-DZ",
                WorkflowName.of("Normale"),
                "Priorité normale");

        WorkflowTypeTranslation en = WorkflowTypeTranslation.create(
                catalogId,
                "en",
                WorkflowName.of("Normal"),
                "Normal priority");

        WorkflowTypeCatalog catalog = WorkflowTypeCatalog.restore(
                catalogId,
                "workflow type",
                WorkflowCode.of("normal"),
                true,
                10,
                false,
                List.of(fr, en),
                Instant.now(),
                Instant.now());

        assertEquals("WORKFLOW_TYPE", catalog.catalogName());
        assertTrue(catalog.matchesCatalog("workflow-type"));
        assertTrue(catalog.translationForLocale("fr").orElseThrow().isFrench());
        assertEquals("Normale", catalog.defaultTranslation().orElseThrow().name().value());
    }

    @Test
    void catalogRejectsTranslationsFromAnotherCatalog() {
        WorkflowCatalogId catalogId = WorkflowCatalogId.of("workflow-priority-normal");

        WorkflowTypeTranslation foreignTranslation = WorkflowTypeTranslation.create(
                WorkflowCatalogId.of("foreign-catalog"),
                "fr",
                WorkflowName.of("Étranger"),
                null);

        assertThrows(
                BusinessRuleViolationException.class,
                () -> WorkflowTypeCatalog.restore(
                        catalogId,
                        "PRIORITY",
                        WorkflowCode.of("NORMAL"),
                        true,
                        10,
                        false,
                        List.of(foreignTranslation),
                        Instant.now(),
                        Instant.now()));
    }

    @Test
    void systemDefinedCatalogCannotBeDeactivatedButCustomCatalogCanBeDeactivated() {
        WorkflowCatalogId catalogId = WorkflowCatalogId.of("workflow-priority-normal");

        WorkflowTypeCatalog systemDefined = WorkflowTypeCatalog.restore(
                catalogId,
                "PRIORITY",
                WorkflowCode.of("NORMAL"),
                true,
                10,
                true,
                List.of(),
                Instant.now(),
                Instant.now());

        WorkflowTypeCatalog custom = WorkflowTypeCatalog.restore(
                WorkflowCatalogId.of("workflow-priority-custom"),
                "PRIORITY",
                WorkflowCode.of("CUSTOM"),
                true,
                20,
                false,
                List.of(),
                Instant.now(),
                Instant.now());

        assertThrows(BusinessRuleViolationException.class, systemDefined::deactivate);
        assertFalse(custom.deactivate().active());
    }
}
