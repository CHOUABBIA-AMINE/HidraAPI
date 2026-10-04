package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.Party;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopologyPartyReferenceQueryServiceTest {

    @Test
    void resolvesPartyExistenceThroughOwnerRepositoryBoundary() {
        PartyRepositoryPort repository = mock(PartyRepositoryPort.class);
        Party party = mock(Party.class);
        when(repository.findById("party-1")).thenReturn(Optional.of(party));
        when(repository.findById("missing")).thenReturn(Optional.empty());

        TopologyPartyReferenceQueryService service =
                new TopologyPartyReferenceQueryService(repository);

        assertThat(service.exists(" party-1 ")).isTrue();
        assertThat(service.exists("missing")).isFalse();
        assertThat(service.exists(" ")).isFalse();
        assertThat(service.exists(null)).isFalse();
    }
}
