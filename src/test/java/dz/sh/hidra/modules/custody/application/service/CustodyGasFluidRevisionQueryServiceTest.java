/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Checks complete qualified export, withdrawn evidence and explicit corruption failures.
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

class CustodyGasFluidRevisionQueryServiceTest {

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

    CustodyGasFluidRevisionQueryService service() { return new CustodyGasFluidRevisionQueryService(repository, approvals, products); }
    @Test void exportsCompleteQualifiedSyntheticFactsAndExactEvidence() {
        var result = service().resolve(source.sourceId(), source.revisionId(), "q1", at, SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).orElseThrow();
        assertEquals(digest, result.stored().sha256()); assertEquals("q1", result.qualification().qualificationId());
        assertEquals(SimulationGasFluidRevisionContract.Origin.SYNTHETIC, result.stored().revision().origin());
        assertEquals(source.components().getFirst().moleFraction(), result.stored().revision().components().getFirst().moleFraction());
        assertEquals(source.method().minimumPressurePascalsAbsolute(), result.stored().revision().method().minimumPressurePascalsAbsolute());
        assertTrue(service().resolve(source.sourceId(), source.revisionId(), "q1", at.minusNanos(1), SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
        assertTrue(service().resolve(source.sourceId(), "missing", "q1", at, SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
    }
    @Test void withdrawalChangedProductAndForgedQualificationRemainUnavailable() {
        when(approvals.resolve(any(), any(), any(), any(), any(), any())).thenReturn(Optional.empty());
        assertTrue(service().resolve(source.sourceId(), source.revisionId(), "q1", at, SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
        setup(); var p = source.productSnapshot();
        when(products.resolve(p.id())).thenReturn(Optional.of(new SimulationProductCandidateContract.Candidate(p.id(), p.catalogName(),
                p.code(), false, p.createdAt(), p.updatedAt())));
        assertTrue(service().resolve(source.sourceId(), source.revisionId(), "q1", at, SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
        setup(); when(repository.findQualification("q1")).thenReturn(Optional.of(new Qualification("q1", source.sourceId(), "r2", digest, at,
                "instance", "task", "action", "actor", "Synthetic Reviewer", at, "def", 1, "type", "purpose")));
        assertTrue(service().resolve(source.sourceId(), source.revisionId(), "q1", at, SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
    }
    @Test void corruptionIsAnErrorRatherThanAnAbsentSource() {
        when(repository.findStored(any(), any())).thenThrow(new InvalidCustodyValueException("corrupt synthetic payload"));
        assertThrows(InvalidCustodyValueException.class, () -> service().resolve(source.sourceId(), source.revisionId(), "q1", at,
                SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE));
    }
}
