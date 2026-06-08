/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Unit tests for workflow definition domain model.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.WorkflowDomainTestFixtures;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;

/**
 * Unit tests for workflow definition domain model.
 */
class WorkflowDefinitionTest {

    @Test
    void createDefinitionStartsAsDraftWithoutStepsOrTransitions() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.draftDefinition();

        assertEquals(WorkflowDefinitionStatus.DRAFT, definition.status());
        assertEquals(1, definition.version().value());
        assertTrue(definition.steps().isEmpty());
        assertTrue(definition.transitions().isEmpty());
        assertFalse(definition.firstStep().isPresent());
    }

    @Test
    void addStepRejectsForeignDefinitionStep() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.draftDefinition();

        WorkflowStep foreignStep = WorkflowStep.create(
                WorkflowDefinitionId.of("foreign-definition"),
                "REVIEW",
                WorkflowDomainTestFixtures.localizedName(),
                0,
                true);

        assertThrows(BusinessRuleViolationException.class, () -> definition.addStep(foreignStep));
    }

    @Test
    void addStepRejectsDuplicateStepCode() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.draftDefinition();

        WorkflowStep first = WorkflowStep.create(
                definition.id(),
                "REVIEW",
                WorkflowDomainTestFixtures.localizedName(),
                0,
                true);

        WorkflowStep duplicateCode = WorkflowStep.create(
                definition.id(),
                "REVIEW",
                WorkflowLocalizedName.of(null, "Autre revue", "Other review"),
                1,
                true);

        WorkflowDefinition withFirstStep = definition.addStep(first);

        assertThrows(BusinessRuleViolationException.class, () -> withFirstStep.addStep(duplicateCode));
    }

    @Test
    void activateRequiresAtLeastOneStep() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.draftDefinition();

        assertThrows(BusinessRuleViolationException.class, definition::activate);
    }

    @Test
    void firstStepUsesSmallestStepOrder() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.definitionWithTwoSteps();

        assertEquals(0, definition.firstStep().orElseThrow().stepOrder());
    }

    @Test
    void addTransitionRequiresExistingStepsAndActivatesValidDefinition() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.definitionWithTwoSteps();
        WorkflowStep from = definition.steps().get(0);
        WorkflowStep to = definition.steps().get(1);

        WorkflowTransition transition = WorkflowTransition.create(
                definition.id(),
                from.id(),
                to.id(),
                WorkflowDecision.APPROVE,
                false,
                false);

        WorkflowDefinition withTransition = definition.addTransition(transition);
        WorkflowDefinition active = withTransition.activate();

        assertEquals(1, withTransition.transitions().size());
        assertEquals(WorkflowDefinitionStatus.ACTIVE, active.status());
        assertSame(transition, withTransition.transitions().get(0));
    }
}
