/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-03
 *
 * @Type        : Class
 * @Layer       : Workflow Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Verifies HMR-003 WorkflowDefinition semantic remediation.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JpaWorkflowDefinitionRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowDefinitionJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowDefinitionSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-03T00:00:00Z");

    @Test
    void rejectsVersionBelowOne() {
        assertThatThrownBy(() -> definition("wf-1", "WF", "Validation", "TYPE", WorkflowDefinitionStatus.DRAFT, 0))
                .isInstanceOf(InvalidWorkflowValueException.class)
                .hasMessageContaining("version");
    }

    @Test
    void rejectsBlankFrenchName() {
        assertThatThrownBy(() -> definition("wf-1", "WF", " ", "TYPE", WorkflowDefinitionStatus.DRAFT, 1))
                .isInstanceOf(InvalidWorkflowValueException.class)
                .hasMessageContaining("French name");
    }

    @Test
    void rejectsDuplicateCodeAndVersionBeforeSave() {
        WorkflowDefinitionJpaRepository repository = mock(WorkflowDefinitionJpaRepository.class);
        JpaWorkflowDefinitionRepositoryAdapter adapter = new JpaWorkflowDefinitionRepositoryAdapter(repository);
        WorkflowDefinition model = definition("wf-2", "WF", "Validation", "TYPE", WorkflowDefinitionStatus.DRAFT, 2);

        when(repository.existsByCodeAndVersionAndIdNot("WF", 2, "wf-2")).thenReturn(true);

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidWorkflowValueException.class)
                .hasMessageContaining("code and version");

        verify(repository, never()).save(any());
    }

    @Test
    void rejectsStructuralMutationOfActiveDefinition() {
        WorkflowDefinitionJpaRepository repository = mock(WorkflowDefinitionJpaRepository.class);
        JpaWorkflowDefinitionRepositoryAdapter adapter = new JpaWorkflowDefinitionRepositoryAdapter(repository);
        WorkflowDefinition model = definition("wf-1", "WF-CHANGED", "Validation", "TYPE", WorkflowDefinitionStatus.ACTIVE, 1);

        when(repository.existsByCodeAndVersionAndIdNot("WF-CHANGED", 1, "wf-1")).thenReturn(false);
        when(repository.findById("wf-1")).thenReturn(java.util.Optional.of(
                entity("wf-1", "WF", "Validation", "TYPE", WorkflowDefinitionStatus.ACTIVE, 1)
        ));

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidWorkflowValueException.class)
                .hasMessageContaining("structural fields");

        verify(repository, never()).save(any());
    }

    @Test
    void allowsLifecycleDeactivationWithoutStructuralMutation() {
        WorkflowDefinitionJpaRepository repository = mock(WorkflowDefinitionJpaRepository.class);
        JpaWorkflowDefinitionRepositoryAdapter adapter = new JpaWorkflowDefinitionRepositoryAdapter(repository);
        WorkflowDefinition model = definition("wf-1", "WF", "Validation", "TYPE", WorkflowDefinitionStatus.INACTIVE, 1);
        WorkflowDefinitionJpaEntity entity = entity("wf-1", "WF", "Validation", "TYPE", WorkflowDefinitionStatus.ACTIVE, 1);

        when(repository.existsByCodeAndVersionAndIdNot("WF", 1, "wf-1")).thenReturn(false);
        when(repository.findById("wf-1")).thenReturn(java.util.Optional.of(entity));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        WorkflowDefinition saved = adapter.save(model);

        assertThat(saved.status()).isEqualTo(WorkflowDefinitionStatus.INACTIVE);
    }

    @Test
    void additiveMigrationEnforcesVersionAndUniqueBusinessIdentity() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql"
        ));

        assertThat(sql).contains("CHECK (version >= 1)");
        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr003_workflow_definition_code_version");
        assertThat(sql).contains("ON hidra_workflow_definition (code, version)");
    }

    private static WorkflowDefinition definition(
            String id,
            String code,
            String nameFr,
            String typeId,
            WorkflowDefinitionStatus status,
            int version
    ) {
        return new WorkflowDefinition(
                id, code, null, nameFr, null, typeId, status, version, NOW, NOW
        );
    }

    private static WorkflowDefinitionJpaEntity entity(
            String id,
            String code,
            String nameFr,
            String typeId,
            WorkflowDefinitionStatus status,
            int version
    ) {
        return new WorkflowDefinitionJpaEntity(
                id, code, null, nameFr, null, typeId, status, version, NOW, NOW
        );
    }
}
