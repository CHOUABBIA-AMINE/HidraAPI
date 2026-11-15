/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationGovernancePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Persists the nomination governance marker and immutable command receipts.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

import dz.sh.hidra.modules.planning.application.port.in.PlanningNominationCommandUseCase.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanningNominationGovernancePort {
    record Scope(String nominationId, String revisionId, String companyId, String sourceFacilityId,
            String destinationFacilityId, String normalizedCode, String authorId) { }

    record CommandReceipt(Event event, String fingerprint) { }

    void register(Scope scope);
    Optional<String> revisionId(String nominationId);
    Optional<Scope> lockScope(String nominationId);
    boolean codeExists(String revisionId, String normalizedCode);
    Optional<CommandReceipt> findCommand(String revisionId, UUID commandId);
    void append(Event event, String fingerprint);
    List<Event> history(String nominationId);
}
