/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleAssignmentApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Application service for party role assignment.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;
import dz.sh.hidra.modules.party.application.port.in.AssignPartyRoleUseCase;
import dz.sh.hidra.modules.party.application.port.out.PartyRoleAssignmentRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment;
import dz.sh.hidra.modules.party.domain.value.PartyId;
import dz.sh.hidra.modules.party.domain.value.PartyRoleAssignmentStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for party role assignment.
 */
public class PartyRoleAssignmentApplicationService implements AssignPartyRoleUseCase {

    private final PartyRoleAssignmentRepositoryPort repositoryPort;

    public PartyRoleAssignmentApplicationService(PartyRoleAssignmentRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Party role assignment repository port must not be null.");
    }

    @Override
    public String assignRole(AssignPartyRoleCommand command) {
        Objects.requireNonNull(command, "Assign party role command must not be null.");
        Instant now = Instant.now();
        PartyRoleAssignment assignment = new PartyRoleAssignment(
                PartyId.newId().value(),
                command.partyId(),
                command.roleId(),
                command.validFrom() == null ? now : command.validFrom(),
                command.validTo(),
                PartyRoleAssignmentStatus.ACTIVE,
                command.qualificationRequired(),
                now,
                now
        );
        return repositoryPort.save(assignment).id();
    }
}
