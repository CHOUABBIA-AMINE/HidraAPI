/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Application service for party registration.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.dto.PartySummaryDto;
import dz.sh.hidra.modules.party.application.mapper.PartyApplicationMapper;
import dz.sh.hidra.modules.party.application.port.in.RegisterPartyUseCase;
import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.domain.value.PartyId;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for party registration.
 */
@Service
public final class PartyApplicationService implements RegisterPartyUseCase {

    private final PartyRepositoryPort partyRepositoryPort;

    public PartyApplicationService(PartyRepositoryPort partyRepositoryPort) {
        this.partyRepositoryPort = Objects.requireNonNull(partyRepositoryPort, "Party repository port must not be null.");
    }

    @Override
    public PartySummaryDto registerParty(RegisterPartyCommand command) {
        Objects.requireNonNull(command, "Register party command must not be null.");
        Instant now = Instant.now();
        Party party = new Party(
                PartyId.newId().value(),
                command.code(),
                command.partyTypeId(),
                command.legalName(),
                command.tradeName(),
                command.shortName(),
                command.countryCode(),
                command.jurisdictionCode(),
                PartyStatus.DRAFT,
                command.primaryRoleCodeSnapshot(),
                now,
                now
        );
        return PartyApplicationMapper.toSummary(partyRepositoryPort.save(party));
    }
}
