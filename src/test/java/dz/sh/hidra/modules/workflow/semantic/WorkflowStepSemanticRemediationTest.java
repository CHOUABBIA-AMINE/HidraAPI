/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Workflow Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Verifies HMR-043 WorkflowStep ordering, uniqueness and SCC reference integrity.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowStepSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void rejectsNegativeStepOrder() {
        assertThatThrownBy(() -> step(-1, null))
                .isInstanceOf(InvalidWorkflowValueException.class)
                .hasMessageContaining("greater than or equal to zero");
    }

    @Test
    void migrationProtectsUniquenessAndDefaultAssignmentRuleReference() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_043__hmr_043_workflow_workflow_step.sql"
        ));

        assertThat(sql)
                .contains("CHECK (step_order >= 0)")
                .contains("UNIQUE (definition_id, code)")
                .contains("UNIQUE (definition_id, step_order)")
                .contains("FOREIGN KEY (default_assignment_rule_id)")
                .contains("REFERENCES hidra_workflow_step_assignment_rule (id)");
    }

    private static WorkflowStep step(int order, String defaultRuleId) {
        return new WorkflowStep(
                "step-1",
                "definition-1",
                "STEP-1",
                null,
                "Étape 1",
                null,
                order,
                true,
                null,
                defaultRuleId,
                null,
                true,
                true,
                true,
                NOW,
                NOW
        );
    }
}
