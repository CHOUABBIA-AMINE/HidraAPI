/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidQualificationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Requires actual evidence and derives qualification values instead of caller approval flags.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationGasFluidRevisionContract;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort.Qualification;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidApprovalEvidencePort;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevisionTest;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.CustodyGasFluidRevisionCodec;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class CustodyGasFluidQualificationServiceTest {

    final CustodyGasFluidRevisionRepositoryPort repository = mock(CustodyGasFluidRevisionRepositoryPort.class);
    final CustodyGasFluidApprovalEvidencePort approvals = mock(CustodyGasFluidApprovalEvidencePort.class);
    final SimulationProductCandidateContract products = mock(SimulationProductCandidateContract.class);
    final Instant at = CustodyGasFluidRevisionTest.AT.plusSeconds(100);
    final dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision source = CustodyGasFluidRevisionTest.fixture("r1");
    final CustodyGasFluidRevisionCodec codec = new CustodyGasFluidRevisionCodec();
    final String digest = codec.sha256(codec.encode(source));
    final Qualification qualification = new Qualification("q1", source.sourceId(), source.revisionId(), digest, at,
            "instance", "task", "action", "actor", "Synthetic Reviewer", at, "def", 1, "type", "purpose");
    @BeforeEach void setup() {
        when(repository.findStored(source.sourceId(), source.revisionId())).thenReturn(Optional.of(
                new CustodyGasFluidRevisionRepositoryPort.StoredRevision(source, CustodyGasFluidRevisionCodec.FORMAT, digest)));
        when(repository.findQualification("q1")).thenReturn(Optional.of(qualification));
        var p = source.productSnapshot();
        when(products.resolve(p.id())).thenReturn(Optional.of(new SimulationProductCandidateContract.Candidate(
                p.id(), p.catalogName(), p.code(), p.active(), p.createdAt(), p.updatedAt())));
        when(approvals.resolve(eq(digest), eq(source.governanceBinding()), eq("instance"), eq("task"), eq("action"), any()))
                .thenReturn(Optional.of(new CustodyGasFluidApprovalEvidencePort.Evidence("instance", "task", "action", "def", 1,
                        "type", "purpose", "actor", "Synthetic Reviewer", at)));
    }

    CustodyGasFluidQualificationService service() { return new CustodyGasFluidQualificationService(repository, approvals, products); }
    @Test void derivesEvidenceAndQualifiedTimeOnServerAndReplaysOriginalQualification() {
        when(repository.findQualification("new-q")).thenReturn(Optional.empty());
        when(repository.appendQualification(any(), any(), any())).thenAnswer(i -> i.getArgument(2));
        Instant before = Instant.now(); var q = service().qualify("new-q", source.sourceId(), source.revisionId(), "instance", "task", "action");
        assertFalse(q.qualifiedAt().isBefore(before)); assertEquals("actor", q.approverId());
        assertEquals(digest, q.payloadSha256()); assertEquals(at, q.approvedAt());
        assertEquals(qualification, service().qualify("q1", source.sourceId(), source.revisionId(), "instance", "task", "action"));
    }
    @Test void missingActualApprovalAndWithdrawnProductCannotRegister() {
        when(approvals.resolve(any(), any(), any(), any(), any(), any())).thenReturn(Optional.empty());
        assertThrows(InvalidCustodyValueException.class, () -> service().qualify("new-q", source.sourceId(), source.revisionId(), "instance", "task", "action"));
        verify(repository, never()).appendQualification(any(), any(), any());
        setup(); when(products.resolve(any())).thenReturn(Optional.empty());
        assertThrows(InvalidCustodyValueException.class, () -> service().qualify("new-q", source.sourceId(), source.revisionId(), "instance", "task", "action"));
    }
}
