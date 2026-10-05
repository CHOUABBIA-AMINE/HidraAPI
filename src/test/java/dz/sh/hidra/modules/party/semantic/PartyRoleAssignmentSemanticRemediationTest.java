/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleAssignmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Party Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.semantic
 *
 * @Description : Verifies HMR-041 ACTIVE PartyRoleAssignment uniqueness.
 *
 */
package dz.sh.hidra.modules.party.semantic;

import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;
import dz.sh.hidra.modules.party.application.port.out.PartyRoleAssignmentRepositoryPort;
import dz.sh.hidra.modules.party.application.service.PartyRoleAssignmentApplicationService;
import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PartyRoleAssignmentSemanticRemediationTest {

    @Test
    void rejectsDuplicateActiveAssignmentBeforeSave() {
        PartyRoleAssignmentRepositoryPort repository = new PartyRoleAssignmentRepositoryPort() {
            @Override
            public PartyRoleAssignment save(PartyRoleAssignment model) {
                throw new AssertionError("save must not be called for duplicate ACTIVE assignment");
            }

            @Override
            public Optional<PartyRoleAssignment> findById(String id) {
                return Optional.empty();
            }

            @Override
            public boolean existsActiveByPartyIdAndRoleId(String partyId, String roleId) {
                return "party-1".equals(partyId) && "role-1".equals(roleId);
            }
        };

        PartyRoleAssignmentApplicationService service =
                new PartyRoleAssignmentApplicationService(repository);

        AssignPartyRoleCommand command = new AssignPartyRoleCommand(
                "party-1",
                "role-1",
                Instant.parse("2026-10-05T00:00:00Z"),
                null,
                false
        );

        assertThatThrownBy(() -> service.assignRole(command))
                .isInstanceOf(InvalidPartyValueException.class)
                .hasMessageContaining("ACTIVE assignment");
    }

    @Test
    void migrationUsesConcurrencySafePartialUniqueIndex() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_041__hmr_041_party_party_role_assignment.sql"
        ));

        org.assertj.core.api.Assertions.assertThat(sql)
                .contains("CREATE UNIQUE INDEX")
                .contains("ON hidra_party_role_assignment (party_id, role_id)")
                .contains("WHERE status = 'ACTIVE'");
    }
}
