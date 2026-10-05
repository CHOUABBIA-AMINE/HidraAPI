/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : AssetsPartyReferenceQueryServiceTest
 * @CreatedOn : 2025-06-26
 * @UpdatedOn : 2026-10-05
 * @Type : Class
 * @Layer : Party Test
 * @Module : party
 * @Package : dz.sh.hidra.modules.party.application.service
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.Party;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssetsPartyReferenceQueryServiceTest {

    @Test
    void resolvesExistenceThroughPartyOwnerRepository() {
        PartyRepositoryPort repository = mock(PartyRepositoryPort.class);
        Party party = mock(Party.class);
        when(repository.findById("party-1")).thenReturn(Optional.of(party));

        AssetsPartyReferenceQueryService service = new AssetsPartyReferenceQueryService(repository);

        assertThat(service.exists(" party-1 ")).isTrue();
        assertThat(service.exists(null)).isFalse();
    }
}
