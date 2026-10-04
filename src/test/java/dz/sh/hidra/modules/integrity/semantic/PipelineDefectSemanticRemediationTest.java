/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDefectSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Integrity Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.semantic
 *
 * @Description : Verifies HMR-019 PipelineDefect source-finding provenance remediation.
 *
 */
package dz.sh.hidra.modules.integrity.semantic;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.model.PipelineDefect;
import dz.sh.hidra.modules.integrity.domain.value.DefectStatus;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.JpaPipelineDefectRepositoryAdapter;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.InspectionFindingJpaRepository;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.PipelineDefectJpaRepository;
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

class PipelineDefectSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void nullSourceFindingRemainsValid() {
        var defectRepository = mock(PipelineDefectJpaRepository.class);
        var findingRepository = mock(InspectionFindingJpaRepository.class);
        var adapter = new JpaPipelineDefectRepositoryAdapter(defectRepository, findingRepository);
        when(defectRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PipelineDefect saved = adapter.save(defect(null));

        assertThat(saved.sourceFindingId()).isNull();
        verify(findingRepository, never()).existsById(any());
    }

    @Test
    void nonNullSourceFindingMustExist() {
        var defectRepository = mock(PipelineDefectJpaRepository.class);
        var findingRepository = mock(InspectionFindingJpaRepository.class);
        var adapter = new JpaPipelineDefectRepositoryAdapter(defectRepository, findingRepository);
        when(findingRepository.existsById("finding-1")).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(defect("finding-1")))
                .isInstanceOf(InvalidIntegrityValueException.class)
                .hasMessageContaining("existing InspectionFinding");

        verify(defectRepository, never()).save(any());
    }

    @Test
    void existingSourceFindingPreservesProvenance() {
        var defectRepository = mock(PipelineDefectJpaRepository.class);
        var findingRepository = mock(InspectionFindingJpaRepository.class);
        var adapter = new JpaPipelineDefectRepositoryAdapter(defectRepository, findingRepository);
        when(findingRepository.existsById("finding-1")).thenReturn(true);
        when(defectRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PipelineDefect saved = adapter.save(defect("finding-1"));

        assertThat(saved.sourceFindingId()).isEqualTo("finding-1");
    }

    @Test
    void migrationProtectsNullableSameModuleSourceFindingReference() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_019__hmr_019_integrity_pipeline_defect.sql"
        ));

        assertThat(sql).contains("fk_hmr019_integrity_pipeline_defect_source_finding");
        assertThat(sql).contains("FOREIGN KEY (source_finding_id)");
        assertThat(sql).contains("REFERENCES hidra_integrity_inspection_finding (id)");
        assertThat(sql).contains("ON DELETE RESTRICT");
        assertThat(sql).contains("VALIDATE CONSTRAINT fk_hmr019_integrity_pipeline_defect_source_finding");
    }

    private static PipelineDefect defect(String sourceFindingId) {
        return new PipelineDefect(
                "defect-1",
                "DEF-001",
                "defect-type-1",
                null,
                DefectStatus.OPEN,
                null,
                "PIPELINE_SEGMENT",
                "segment-1",
                "SEG-1",
                null,
                null,
                null,
                "Detected defect",
                NOW,
                null,
                sourceFindingId,
                NOW,
                NOW
        );
    }
}
