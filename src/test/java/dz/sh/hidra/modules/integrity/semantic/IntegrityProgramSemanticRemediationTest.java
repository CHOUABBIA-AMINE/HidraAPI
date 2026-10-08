/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityProgramSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Integrity Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.semantic
 *
 * @Description : Verifies HMR-050 IntegrityProgram catalog-family and Organization owner semantics.
 *
 */
package dz.sh.hidra.modules.integrity.semantic;

import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityProgramCommand;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityAssessmentRepositoryPort;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCaseRepositoryPort;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityProgramRepositoryPort;
import dz.sh.hidra.modules.integrity.application.service.IntegrityApplicationService;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityProgram;
import dz.sh.hidra.modules.integrity.domain.value.IntegrityProgramStatus;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.JpaIntegrityProgramRepositoryAdapter;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.IntegrityCatalogEntryJpaEntity;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCatalogEntryJpaRepository;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityProgramJpaRepository;
import dz.sh.hidra.modules.organization.application.contract.integrity.IntegrityOrganizationUnitReferenceContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IntegrityProgramSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-06T00:00:00Z");

    @Test
    void rejectsProgramTypeOutsideActiveIntegrityProgramTypeFamily() {
        IntegrityProgramJpaRepository programJpaRepository = mock(IntegrityProgramJpaRepository.class);
        IntegrityCatalogEntryJpaRepository catalogRepository = mock(IntegrityCatalogEntryJpaRepository.class);
        JpaIntegrityProgramRepositoryAdapter adapter =
                new JpaIntegrityProgramRepositoryAdapter(programJpaRepository, catalogRepository);

        when(catalogRepository.findById("type-1")).thenReturn(Optional.of(catalog("OTHER_FAMILY", true)));

        assertThatThrownBy(() -> adapter.save(program("type-1", null, null)))
                .isInstanceOf(InvalidIntegrityValueException.class)
                .hasMessageContaining("INTEGRITY_PROGRAM_TYPE");
    }

    @Test
    void rejectsInactiveIntegrityProgramType() {
        IntegrityProgramJpaRepository programJpaRepository = mock(IntegrityProgramJpaRepository.class);
        IntegrityCatalogEntryJpaRepository catalogRepository = mock(IntegrityCatalogEntryJpaRepository.class);
        JpaIntegrityProgramRepositoryAdapter adapter =
                new JpaIntegrityProgramRepositoryAdapter(programJpaRepository, catalogRepository);

        when(catalogRepository.findById("type-1"))
                .thenReturn(Optional.of(catalog("INTEGRITY_PROGRAM_TYPE", false)));

        assertThatThrownBy(() -> adapter.save(program("type-1", null, null)))
                .isInstanceOf(InvalidIntegrityValueException.class)
                .hasMessageContaining("active INTEGRITY_PROGRAM_TYPE");
    }

    @Test
    void validatesOrganizationOwnerAndPreservesSnapshot() {
        IntegrityProgramRepositoryPort programRepository = mock(IntegrityProgramRepositoryPort.class);
        IntegrityAssessmentRepositoryPort assessmentRepository = mock(IntegrityAssessmentRepositoryPort.class);
        IntegrityCaseRepositoryPort caseRepository = mock(IntegrityCaseRepositoryPort.class);
        IntegrityOrganizationUnitReferenceContract organizationContract =
                mock(IntegrityOrganizationUnitReferenceContract.class);

        IntegrityApplicationService service = new IntegrityApplicationService(
                programRepository,
                assessmentRepository,
                caseRepository,
                organizationContract,
                mock(dz.sh.hidra.modules.integrity.application.port.out.PipelineDefectRepositoryPort.class)
        );

        when(organizationContract.exists("org-1")).thenReturn(true);
        when(programRepository.save(any(IntegrityProgram.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.createIntegrityProgram(command("org-1", "Direction Transport"));

        ArgumentCaptor<IntegrityProgram> captor = ArgumentCaptor.forClass(IntegrityProgram.class);
        verify(programRepository).save(captor.capture());
        assertThat(captor.getValue().ownerOrganizationUnitId()).isEqualTo("org-1");
        assertThat(captor.getValue().ownerOrganizationUnitNameSnapshot()).isEqualTo("Direction Transport");
    }

    @Test
    void failsClosedWhenOrganizationOwnerCannotResolve() {
        IntegrityProgramRepositoryPort programRepository = mock(IntegrityProgramRepositoryPort.class);
        IntegrityAssessmentRepositoryPort assessmentRepository = mock(IntegrityAssessmentRepositoryPort.class);
        IntegrityCaseRepositoryPort caseRepository = mock(IntegrityCaseRepositoryPort.class);
        IntegrityOrganizationUnitReferenceContract organizationContract =
                mock(IntegrityOrganizationUnitReferenceContract.class);

        IntegrityApplicationService service = new IntegrityApplicationService(
                programRepository,
                assessmentRepository,
                caseRepository,
                organizationContract,
                mock(dz.sh.hidra.modules.integrity.application.port.out.PipelineDefectRepositoryPort.class)
        );

        when(organizationContract.exists("missing-org")).thenReturn(false);

        assertThatThrownBy(() -> service.createIntegrityProgram(command("missing-org", "Historical snapshot")))
                .isInstanceOf(InvalidIntegrityValueException.class)
                .hasMessageContaining("existing OrganizationUnit");
    }

    private static IntegrityCatalogEntryJpaEntity catalog(String family, boolean active) {
        return new IntegrityCatalogEntryJpaEntity(
                "type-1", family, "PROGRAM", active, 0, true, NOW, NOW
        );
    }

    private static IntegrityProgram program(String programTypeId, String ownerId, String ownerSnapshot) {
        return new IntegrityProgram(
                "program-1",
                "IP-001",
                null,
                "Programme intégrité",
                null,
                null,
                programTypeId,
                ownerId,
                ownerSnapshot,
                IntegrityProgramStatus.DRAFT,
                null,
                null,
                null,
                null,
                "actor-1",
                NOW,
                NOW
        );
    }

    private static CreateIntegrityProgramCommand command(String ownerId, String ownerSnapshot) {
        return new CreateIntegrityProgramCommand(
                "IP-001",
                null,
                "Programme intégrité",
                null,
                null,
                "type-1",
                ownerId,
                ownerSnapshot,
                null,
                null,
                "actor-1"
        );
    }
}
