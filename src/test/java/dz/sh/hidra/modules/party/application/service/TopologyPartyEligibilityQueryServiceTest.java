/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPartyEligibilityQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Party Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Verifies exact active-reference eligibility independently from existence and authorization.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyEligibilityContract;
import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopologyPartyEligibilityQueryServiceTest {
    private static final Instant NOW = Instant.parse("2026-10-11T00:00:00.123456789Z");

    @Test
    void onlyActivePartyIsEligibleWhileEveryStoredStatusStillExists() {
        var repository = mock(PartyRepositoryPort.class);
        TopologyPartyEligibilityContract eligibility = new TopologyPartyEligibilityQueryService(repository);
        var existence = new TopologyPartyReferenceQueryService(repository);
        for (var status : PartyStatus.values()) {
            String id = "synthetic-party-" + status.name();
            when(repository.findById(id)).thenReturn(Optional.of(party(id, status)));
            assertEquals(status == PartyStatus.ACTIVE, eligibility.eligibleForNewReference(id), status.name());
            assertTrue(existence.exists(id), status.name());
        }
    }

    @Test
    void independentPartyIdentitiesDoNotShareEligibility() {
        var repository = mock(PartyRepositoryPort.class);
        when(repository.findById("synthetic-a")).thenReturn(Optional.of(party("synthetic-a", PartyStatus.ACTIVE)));
        when(repository.findById("synthetic-b")).thenReturn(Optional.of(party("synthetic-b", PartyStatus.BLOCKED)));
        var service = new TopologyPartyEligibilityQueryService(repository);
        assertTrue(service.eligibleForNewReference("synthetic-a"));
        assertFalse(service.eligibleForNewReference("synthetic-b"));
        verify(repository).findById("synthetic-a");
        verify(repository).findById("synthetic-b");
    }

    @Test
    void trimsLookupIdentityWithoutChangingItsCase() {
        var repository = mock(PartyRepositoryPort.class);
        when(repository.findById("Synthetic-A")).thenReturn(Optional.of(party("Synthetic-A", PartyStatus.ACTIVE)));
        var service = new TopologyPartyEligibilityQueryService(repository);
        assertTrue(service.eligibleForNewReference("  Synthetic-A "));
        verify(repository).findById("Synthetic-A");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void blankIdentityDoesNotAccessRepository() {
        var repository = mock(PartyRepositoryPort.class);
        var service = new TopologyPartyEligibilityQueryService(repository);
        assertFalse(service.eligibleForNewReference(null));
        assertFalse(service.eligibleForNewReference(""));
        assertFalse(service.eligibleForNewReference("  "));
        assertFalse(service.eligibleForNewReference("\t\n"));
        verifyNoInteractions(repository);
    }

    @Test
    void missingExactReferenceIsIneligible() {
        var repository = mock(PartyRepositoryPort.class);
        when(repository.findById("missing")).thenReturn(Optional.empty());
        assertFalse(new TopologyPartyEligibilityQueryService(repository).eligibleForNewReference("missing"));
        verify(repository).findById("missing");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mismatchedRepositoryIdentityFailsClosed() {
        var repository = mock(PartyRepositoryPort.class);
        when(repository.findById("synthetic-a")).thenReturn(Optional.of(party("synthetic-b", PartyStatus.ACTIVE)));
        assertFalse(new TopologyPartyEligibilityQueryService(repository).eligibleForNewReference("synthetic-a"));
    }

    @Test
    void newLookupObservesStatusChangeWithoutCachedEligibility() {
        var repository = mock(PartyRepositoryPort.class);
        when(repository.findById("synthetic-a"))
                .thenReturn(Optional.of(party("synthetic-a", PartyStatus.ACTIVE)))
                .thenReturn(Optional.of(party("synthetic-a", PartyStatus.SUSPENDED)));
        var service = new TopologyPartyEligibilityQueryService(repository);
        assertTrue(service.eligibleForNewReference("synthetic-a"));
        assertFalse(service.eligibleForNewReference("synthetic-a"));
        verify(repository, times(2)).findById("synthetic-a");
    }

    @Test
    void repositoryFailurePropagatesWithoutReportingAbsence() {
        var repository = mock(PartyRepositoryPort.class);
        var failure = new IllegalStateException("Synthetic repository unavailable");
        when(repository.findById("synthetic-a")).thenThrow(failure);
        var service = new TopologyPartyEligibilityQueryService(repository);
        assertSame(failure, assertThrows(IllegalStateException.class,
                () -> service.eligibleForNewReference("synthetic-a")));
    }

    @Test
    void nullRepositoryIsRejected() {
        assertThrows(NullPointerException.class, () -> new TopologyPartyEligibilityQueryService(null));
    }

    private static Party party(String id, PartyStatus status) {
        return new Party(id, "code-" + id, "synthetic-type", "Synthetic " + id,
                null, null, "DZA", null, status, null, NOW, NOW);
    }
}
