/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowValueObjectsTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Unit tests for workflow domain value objects.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.workflow.domain.WorkflowDomainTestFixtures;

/**
 * Unit tests for workflow domain value objects.
 */
class WorkflowValueObjectsTest {

    @Test
    void workflowCodeNormalizesAndAcceptsSupportedSeparators() {
        WorkflowCode code = WorkflowCode.of(" telemetry.flow/validation-1 ");

        assertEquals("TELEMETRY.FLOW/VALIDATION-1", code.value());
    }

    @Test
    void workflowCodeRejectsBlankAndUnsupportedCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> WorkflowCode.of(" "));
        assertThrows(InvalidValueObjectException.class, () -> WorkflowCode.of("BAD CODE"));
    }

    @Test
    void localizedNameRequiresFrenchAndKeepsMultilingualLabels() {
        WorkflowLocalizedName name = WorkflowLocalizedName.of(" عربي ", " Français ", " English ");

        assertEquals("عربي", name.nameAr());
        assertEquals("Français", name.nameFr());
        assertEquals("English", name.nameEn());

        assertThrows(InvalidValueObjectException.class, () -> WorkflowLocalizedName.of("ar", " ", "en"));
    }

    @Test
    void targetReferenceIdentifiesTelemetryReadingOnlyForTelemetryModuleAndTargetType() {
        assertTrue(WorkflowDomainTestFixtures.telemetryReadingTarget().isTelemetryReading());
        assertFalse(WorkflowDomainTestFixtures.nonTelemetryTarget().isTelemetryReading());
    }

    @Test
    void technicalEnumsExposeLifecycleClassificationRules() {
        assertTrue(WorkflowDecision.REJECT.requiresReason());
        assertTrue(WorkflowDecision.REQUEST_CORRECTION.requiresReason());
        assertFalse(WorkflowDecision.APPROVE.requiresReason());

        assertTrue(WorkflowTaskStatus.APPROVED.isClosed());
        assertFalse(WorkflowTaskStatus.OPEN.isClosed());

        assertTrue(WorkflowInstanceStatus.COMPLETED.isTerminal());
        assertFalse(WorkflowInstanceStatus.IN_PROGRESS.isTerminal());

        assertTrue(WorkflowDefinitionStatus.ACTIVE.canStartInstance());
        assertFalse(WorkflowDefinitionStatus.DRAFT.canStartInstance());
    }
}
