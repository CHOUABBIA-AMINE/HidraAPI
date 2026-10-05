/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepAssignmentRuleSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Workflow Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Verifies HMR-044 assignment-mode family and candidate-source semantics.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStepAssignmentRule;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowStepAssignmentRuleSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void rejectsRuleWithoutAnyCandidateSourceStrategy() {
        assertThatThrownBy(() -> rule(null, null, null, null, null))
                .isInstanceOf(InvalidWorkflowValueException.class)
                .hasMessageContaining("candidate-pool strategy");
    }

    @Test
    void acceptsConcreteCandidatePoolStrategy() {
        WorkflowStepAssignmentRule rule = rule(
                null,
                "PIPELINE_OPERATOR",
                null,
                null,
                null
        );

        assertThat(rule.roleCode()).isEqualTo("PIPELINE_OPERATOR");
    }

    private static WorkflowStepAssignmentRule rule(
            String actorId,
            String roleCode,
            String organizationUnitId,
            String organizationRoleCode,
            String targetOwnerMode
    ) {
        return new WorkflowStepAssignmentRule(
                "rule-1",
                "definition-1",
                "step-1",
                "assignment-mode-1",
                actorId,
                roleCode,
                organizationUnitId,
                organizationRoleCode,
                targetOwnerMode,
                true,
                NOW,
                NOW
        );
    }
}
