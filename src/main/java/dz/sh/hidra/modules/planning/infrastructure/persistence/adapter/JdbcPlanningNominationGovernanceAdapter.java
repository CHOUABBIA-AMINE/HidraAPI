/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcPlanningNominationGovernanceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Stores and retrieves Planning nomination governance and event evidence.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.in.PlanningNominationCommandUseCase.Event;
import dz.sh.hidra.modules.planning.application.port.out.PlanningNominationGovernancePort;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcPlanningNominationGovernanceAdapter implements PlanningNominationGovernancePort {
    private final JdbcTemplate jdbc;

    public JdbcPlanningNominationGovernanceAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void register(Scope scope) {
        jdbc.update("""
                INSERT INTO public.hidra_planning_nomination_governance
                    (nomination_id, revision_id, company_id, source_facility_id,
                     destination_facility_id, normalized_code, author_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """, scope.nominationId(), scope.revisionId(), scope.companyId(), scope.sourceFacilityId(),
                scope.destinationFacilityId(), scope.normalizedCode(), scope.authorId());
    }

    @Override
    public Optional<String> revisionId(String nominationId) {
        return jdbc.queryForList("""
                SELECT revision_id FROM public.hidra_planning_nomination_governance WHERE nomination_id = ?
                """, String.class, nominationId).stream().findFirst();
    }

    @Override
    public Optional<Scope> lockScope(String nominationId) {
        return jdbc.query("""
                SELECT nomination_id, revision_id, company_id, source_facility_id,
                       destination_facility_id, normalized_code, author_id
                FROM public.hidra_planning_nomination_governance
                WHERE nomination_id = ? FOR UPDATE
                """, (row, index) -> new Scope(row.getString("nomination_id"), row.getString("revision_id"),
                row.getString("company_id"), row.getString("source_facility_id"),
                row.getString("destination_facility_id"), row.getString("normalized_code"),
                row.getString("author_id")), nominationId).stream().findFirst();
    }

    @Override
    public boolean codeExists(String revisionId, String normalizedCode) {
        return Boolean.TRUE.equals(jdbc.queryForObject("""
                SELECT EXISTS (
                    SELECT 1 FROM public.hidra_planning_nomination
                    WHERE revision_id = ? AND upper(btrim(code) COLLATE "C") = ?
                )
                """, Boolean.class, revisionId, normalizedCode));
    }

    @Override
    public Optional<CommandReceipt> findCommand(String revisionId, UUID commandId) {
        return jdbc.query("""
                SELECT * FROM public.hidra_planning_nomination_event
                WHERE revision_id = ? AND command_id = ?
                """, (row, index) -> new CommandReceipt(event(row), row.getString("payload_fingerprint")),
                revisionId, commandId).stream().findFirst();
    }

    @Override
    public void append(Event event, String fingerprint) {
        jdbc.update("""
                INSERT INTO public.hidra_planning_nomination_event
                    (event_id, command_id, nomination_id, revision_id, event_version, actor_id,
                     previous_status, status, reason, recorded_at, payload_fingerprint)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, event.eventId(), event.commandId(), event.nominationId(), event.revisionId(), event.version(),
                event.actorId(), event.previousStatus() == null ? null : event.previousStatus().name(),
                event.status().name(), event.reason(), event.recordedAt().atOffset(ZoneOffset.UTC), fingerprint);
    }

    @Override
    public List<Event> history(String nominationId) {
        return jdbc.query("""
                SELECT * FROM public.hidra_planning_nomination_event
                WHERE nomination_id = ? ORDER BY event_version
                """, (row, index) -> event(row), nominationId);
    }

    private static Event event(ResultSet row) throws SQLException {
        String previous = row.getString("previous_status");
        return new Event(row.getObject("event_id", UUID.class), row.getObject("command_id", UUID.class),
                row.getString("nomination_id"), row.getString("revision_id"), row.getLong("event_version"),
                row.getString("actor_id"), previous == null ? null : NominationStatus.valueOf(previous),
                NominationStatus.valueOf(row.getString("status")), row.getString("reason"),
                row.getObject("recorded_at", OffsetDateTime.class).toInstant());
    }
}
