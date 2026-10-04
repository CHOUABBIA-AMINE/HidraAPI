/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartySemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Party Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.semantic
 *
 * @Description : Verifies HMR-004 Party semantic remediation.
 *
 */
package dz.sh.hidra.modules.party.semantic;

import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.application.service.PartyApplicationService;
import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PartySemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsBlankLegalName() {
        assertThatThrownBy(() -> new Party(
                "party-1", "P001", "TYPE", " ", null, null, "DZA", null,
                PartyStatus.DRAFT, null, NOW, NOW
        ))
                .isInstanceOf(InvalidPartyValueException.class)
                .hasMessageContaining("legal name");
    }

    @Test
    void rejectsDuplicateCodeBeforeRegistrationSave() {
        PartyRepositoryPort repository = mock(PartyRepositoryPort.class);
        PartyApplicationService service = new PartyApplicationService(repository);
        RegisterPartyCommand command = new RegisterPartyCommand(
                "P001", "TYPE", "Legal Name", null, null, "DZA", null, null
        );
        when(repository.existsByCode("P001")).thenReturn(true);

        assertThatThrownBy(() -> service.registerParty(command))
                .isInstanceOf(InvalidPartyValueException.class)
                .hasMessageContaining("unique");

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void additiveMigrationEnforcesPartyCodeUniqueness() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_004__hmr_004_party_party.sql"
        ));

        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr004_party_code");
        assertThat(sql).contains("ON hidra_party_party (code)");
    }
}
