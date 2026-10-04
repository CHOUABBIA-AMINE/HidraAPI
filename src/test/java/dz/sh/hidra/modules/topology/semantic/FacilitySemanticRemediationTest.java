package dz.sh.hidra.modules.topology.semantic;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyReferenceContract;
import dz.sh.hidra.modules.topology.application.command.RegisterFacilityCommand;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.service.FacilityApplicationService;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FacilitySemanticRemediationTest {

    @Test
    void rejectsUnknownPopulatedOwnerPartyReference() {
        FacilityRepositoryPort repository = new FacilityRepositoryPort() {
            @Override
            public Facility save(Facility model) {
                return model;
            }

            @Override
            public Optional<Facility> findById(String id) {
                return Optional.empty();
            }
        };
        TopologyPartyReferenceContract partyContract = partyId -> false;
        FacilityApplicationService service =
                new FacilityApplicationService(repository, partyContract);

        RegisterFacilityCommand command = new RegisterFacilityCommand(
                "FAC-1",
                null,
                "Facility 1",
                null,
                "facility-type-1",
                FacilityKind.OTHER,
                "party-404",
                "P404",
                "Unknown Party",
                null,
                null,
                null
        );

        assertThatThrownBy(() -> service.registerFacility(command))
                .isInstanceOf(InvalidTopologyValueException.class)
                .hasMessageContaining("existing Party");
    }
}
