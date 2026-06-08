/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPoliciesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Unit tests for workflow domain policies.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.WorkflowDomainTestFixtures;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;

/**
 * Unit tests for workflow domain policies.
 */
class WorkflowPoliciesTest {

    @Test
    void targetPolicyAcceptsTelemetryReadingAndRejectsOtherTargetsForV1() {
        WorkflowTargetPolicy policy = new WorkflowTargetPolicy();

        policy.requireTelemetryReadingTarget(WorkflowDomainTestFixtures.telemetryReadingTarget());

        assertThrows(
                BusinessRuleViolationException.class,
                () -> policy.requireTelemetryReadingTarget(WorkflowDomainTestFixtures.nonTelemetryTarget()));
    }

    @Test
    void assignmentPolicyRequiresActorOrOrganizationTarget() {
        WorkflowAssignmentPolicy policy = new WorkflowAssignmentPolicy();

        policy.requireValidAssignmentTarget(WorkflowDomainTestFixtures.actor(), null);

        assertThrows(BusinessRuleViolationException.class, () -> policy.requireValidAssignmentTarget(null, null));
    }

    @Test
    void decisionPolicyRequiresCommentForCorrectionRequest() {
        WorkflowTask task = WorkflowDomainTestFixtures.openTask(WorkflowDomainTestFixtures.startedInstance());
        WorkflowDecisionPolicy policy = new WorkflowDecisionPolicy();

        assertThrows(
                BusinessRuleViolationException.class,
                () -> policy.requireDecisionCanBeApplied(
                        task,
                        WorkflowDecision.REQUEST_CORRECTION,
                        WorkflowDomainTestFixtures.reason(),
                        null));
    }

    @Test
    void transitionPolicyResolvesApplicableTransition() {
        WorkflowDefinition definition = WorkflowDomainTestFixtures.activeDefinitionWithTransition();
        WorkflowTransitionPolicy policy = new WorkflowTransitionPolicy();

        WorkflowTransition transition = policy.requireApplicableTransition(
                definition,
                definition.steps().get(0).id(),
                WorkflowDecision.APPROVE,
                null,
                null);

        assertEquals(WorkflowDecision.APPROVE, transition.decision());
    }

    @Test
    void delegationPolicyRejectsDelegationToSameActor() {
        WorkflowTask task = WorkflowDomainTestFixtures.openTask(WorkflowDomainTestFixtures.startedInstance());
        WorkflowDelegationPolicy policy = new WorkflowDelegationPolicy();

        assertThrows(
                BusinessRuleViolationException.class,
                () -> policy.requireTaskCanBeDelegated(
                        task,
                        WorkflowDomainTestFixtures.actor(),
                        WorkflowDomainTestFixtures.actor(),
                        null,
                        WorkflowDomainTestFixtures.reason()));
    }
}
