/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProtectedEmployeeAppointmentRepositoryAdapter
 * @CreatedOn   : 2026-10-22
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Discovers complete bounded ownership descriptors from immutable appointment events.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;

/**
 * An isolated first JDBC slice of the registered protected repository. This is
 * intentionally NOT a component, and does NOT implement the protected repository
 * port until its native tickets, owner fences, final database guard and receipt
 * are implemented. Preview data is only an UNTRUSTED selector for the native
 * admission request; it is not authorization, current Party eligibility, or
 * permission to return a record. It never changes the database.
 */
public final class JdbcProtectedEmployeeAppointmentRepositoryAdapter {
    private static final String SOURCE_DESCRIPTOR_SQL = """
            SELECT a.employee_id, a.organization_unit_id, b.company_party_id
              FROM public.hidra_org_employee_assignment a
              JOIN public.hidra_org_appointment_binding_revision b
                ON b.binding_id=a.binding_id AND b.revision=a.binding_revision
               AND b.organization_unit_id=a.organization_unit_id
             WHERE a.id=? AND a.appointment_version>0 AND a.policy_id IS NOT NULL
               AND a.policy_revision>0 AND a.last_event_id IS NOT NULL
             LIMIT 2
            """;

    private static final Comparator<CompanyUnit> OWNER_ORDER =
            Comparator.comparing(CompanyUnit::companyPartyId)
                    .thenComparing(CompanyUnit::organizationUnitId);

    private final JdbcTemplate jdbc;

    public JdbcProtectedEmployeeAppointmentRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc, "JDBC access required");
    }

    /**
     * Discover exact pinned company/unit source descriptors for the four
     * mutation selectors, without using a mutable binding head or inferring a
     * company for retained legacy rows. Callers MUST execute this only inside
     * the registered protected executor's preliminary SELECT-only phase and
     * repeat the entire closure under Identity -> Party -> Organization fences.
     *
     * No failure discloses whether a source assignment exists or belongs to
     * another company. A CREATE preview is likewise only a caller selector.
     */
    public DescriptorPreview preview(EmployeeAppointmentCommand command) {
        Objects.requireNonNull(command, "Mutation command required");
        if (command instanceof EmployeeAppointmentCommand.Create create) {
            return new DescriptorPreview(List.of(new CompanyUnit(
                    create.companyPartyId(), create.organizationUnitId())),
                    create.employeeId(), null, false);
        }

        String sourceId = command instanceof EmployeeAppointmentCommand.End end
                ? end.assignmentId()
                : command instanceof EmployeeAppointmentCommand.Transfer transfer
                    ? transfer.assignmentId()
                    : ((EmployeeAppointmentCommand.Correct) command).assignmentId();
        try {
            List<Observed> rows = jdbc.query(SOURCE_DESCRIPTOR_SQL,
                    (rs, index) -> new Observed(rs.getString(1),
                            rs.getString(2), rs.getString(3)), sourceId);
            if (rows == null || rows.size() != 1) throw denied();
            Observed source = rows.get(0);
            if (source == null || source.employeeId() == null
                    || source.unitId() == null
                    || !command.companyPartyId().equals(source.companyId())) throw denied();
            var pairs = new TreeSet<>(OWNER_ORDER);
            pairs.add(new CompanyUnit(source.companyId(), source.unitId()));
            if (command instanceof EmployeeAppointmentCommand.Transfer transfer) {
                pairs.add(new CompanyUnit(source.companyId(),
                        transfer.destinationOrganizationUnitId()));
            } else if (command instanceof EmployeeAppointmentCommand.Correct correct) {
                pairs.add(new CompanyUnit(source.companyId(),
                        correct.correctedOrganizationUnitId()));
            }
            return new DescriptorPreview(new ArrayList<>(pairs),
                    source.employeeId(), sourceId, false);
        } catch (RuntimeException inaccessible) {
            // A failed/foreign/unbound lookup is indistinguishable to the caller.
            throw denied();
        }
    }


    /*
     * Only hardcoded predicates are appended. These are immutable event-snapshot
     * descriptors for an untrusted PREVIEW, not a history reader or an owner
     * fence. Require complete history: 1025 events indicates that the fixed
     * 1024-event discovery bound was exceeded, never an authorized partial set.
     */
    private static final String HISTORICAL_SQL = """
            WITH sampled AS (
                SELECT e.event_id,e.operation,e.assignment_id,e.successor_id,
                       e.employee_id,e.company_party_id,
                       pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb AS body
                  FROM public.hidra_org_appointment_event e
                 WHERE %s
                 ORDER BY e.sampled_at,e.event_id
                 LIMIT 1025
            ), snapshots AS (
                SELECT e.event_id,e.assignment_id,e.successor_id,
                       e.employee_id,e.company_party_id,s.slot,s.snapshot
                  FROM sampled e
                 CROSS JOIN LATERAL (VALUES
                     ('before',e.body->'before'),('after',e.body->'after'),
                     ('successor',e.body->'successor')
                 ) AS s(slot,snapshot)
                 WHERE s.snapshot IS NOT NULL AND s.snapshot <> 'null'::jsonb
            ), checked AS (
                SELECT s.*,b.company_party_id AS pinned_company,
                       b.organization_unit_id AS pinned_unit,
                       (jsonb_typeof(s.snapshot->'assignment')='object'
                        AND s.snapshot->'binding'=pg_catalog.to_jsonb(b)
                        AND s.snapshot->'assignment'->>'employee_id'=s.employee_id
                        AND s.snapshot->'assignment'->>'id'=
                            CASE WHEN s.slot='successor' THEN s.successor_id ELSE s.assignment_id END
                        AND (s.snapshot->'assignment'->>'appointment_version')::bigint>0
                        AND r.policy_id IS NOT NULL) AS valid
                  FROM snapshots s
                  LEFT JOIN public.hidra_org_appointment_binding_revision b
                    ON b.binding_id=s.snapshot->'assignment'->>'binding_id'
                   AND b.revision=(s.snapshot->'assignment'->>'binding_revision')::bigint
                   AND b.organization_unit_id=s.snapshot->'assignment'->>'organization_unit_id'
                   AND b.company_party_id=s.company_party_id
                  LEFT JOIN public.hidra_org_appointment_policy_revision r
                    ON r.policy_id=s.snapshot->'assignment'->>'policy_id'
                   AND r.revision=(s.snapshot->'assignment'->>'policy_revision')::bigint
                   AND r.company_party_id=b.company_party_id
            ), integrity AS (
                SELECT EXISTS(SELECT 1 FROM checked WHERE valid IS DISTINCT FROM TRUE)
                    OR EXISTS(SELECT 1 FROM sampled e WHERE (
                        jsonb_typeof(e.body)='object'
                        AND pg_catalog.jsonb_exists_all(e.body,ARRAY['before','after','successor'])
                        AND jsonb_typeof(e.body->'after')='object'
                        AND CASE e.operation
                            WHEN 'CREATE' THEN e.body->'before'='null'::jsonb
                                AND e.body->'successor'='null'::jsonb AND e.successor_id IS NULL
                            WHEN 'END' THEN jsonb_typeof(e.body->'before')='object'
                                AND e.body->'successor'='null'::jsonb AND e.successor_id IS NULL
                            WHEN 'CORRECT' THEN jsonb_typeof(e.body->'before')='object'
                                AND e.body->'successor'='null'::jsonb AND e.successor_id IS NULL
                            WHEN 'TRANSFER' THEN jsonb_typeof(e.body->'before')='object'
                                AND jsonb_typeof(e.body->'successor')='object' AND e.successor_id IS NOT NULL
                            ELSE FALSE END) IS DISTINCT FROM TRUE) AS incomplete
            )
            SELECT s.employee_id,s.pinned_company,s.pinned_unit,
                   s.snapshot->'assignment'->>'id' AS assignment_id,
                   (s.snapshot->'assignment'->>'appointment_version')::bigint AS version,
                   (SELECT count(*) FROM sampled) AS observed_events,
                   (SELECT incomplete FROM integrity)
              FROM checked s
             WHERE %s
            """;

    private record Historical(String employee, String company, String unit,
                              String assignment, long version, long eventCount,
                              boolean incomplete) { }

    /**
     * Internal read-descriptor discovery for policy/position selectors and
     * exact protected history. Every event's before/after/successor binding is
     * matched against the immutable revision; no mutable binding head, cursor,
     * current employee directory, or JPA entity becomes authority. History covers
     * every snapshot of a selected event, including a transfer's other unit;
     * exact-version lookup selects only that version after event integrity checks.
     *
     * The caller MUST later reacquire and compare the complete owner closure
     * under native Identity and Organization relation fences. No result/count/
     * existence may escape before authorization. Position history discovers all
     * owners of matching events without returning an employee selector. POLICY
     * remains a supplied selector. This class is deliberately not wired.
     */
    public DescriptorPreview preview(EmployeeAppointmentQuery query) {
        Objects.requireNonNull(query, "Read selector required");
        if (query instanceof EmployeeAppointmentQuery.Policy policy) {
            return new DescriptorPreview(List.of(
                    new CompanyUnit(policy.companyPartyId(), policy.unitId())), null, null, false);
        }
        final String statement;
        final Object[] parameters;
        final String wantedEmployee;
        final String wantedAssignment;
        final Long wantedVersion;
        final boolean positionView = query instanceof EmployeeAppointmentQuery.History history
                && history.scope() == EmployeeAppointmentQuery.Scope.POSITION;
        if (query instanceof EmployeeAppointmentQuery.ExactVersion exact) {
            statement = HISTORICAL_SQL.formatted(
                    "e.assignment_id=? OR e.successor_id=?",
                    "s.snapshot->'assignment'->>'id'=? AND (s.snapshot->'assignment'->>'appointment_version')::bigint=?");
            parameters = new Object[]{exact.assignmentId(), exact.assignmentId(),
                    exact.assignmentId(), exact.version()};
            wantedAssignment = exact.assignmentId();
            wantedEmployee = null;
            wantedVersion = exact.version();
        } else if (query instanceof EmployeeAppointmentQuery.History history
                && history.scope() == EmployeeAppointmentQuery.Scope.ASSIGNMENT) {
            statement = HISTORICAL_SQL.formatted(
                    "e.assignment_id=? OR e.successor_id=?",
                    "TRUE");
            parameters = new Object[]{history.assignmentId(), history.assignmentId()};
            wantedAssignment = history.assignmentId();
            wantedEmployee = null;
            wantedVersion = null;
        } else if (query instanceof EmployeeAppointmentQuery.History history
                && history.scope() == EmployeeAppointmentQuery.Scope.EMPLOYEE) {
            statement = HISTORICAL_SQL.formatted(
                    "e.employee_id=?", "TRUE");
            parameters = new Object[]{history.employeeId()};
            wantedAssignment = null;
            wantedEmployee = history.employeeId();
            wantedVersion = null;
        } else if (query instanceof EmployeeAppointmentQuery.History history && positionView) {
            statement = HISTORICAL_SQL.formatted("""
                    EXISTS (SELECT 1 FROM (VALUES
                        (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'before'),
                        (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'after'),
                        (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'successor')
                    ) AS candidate(snapshot)
                    WHERE candidate.snapshot->'assignment'->>'organization_unit_id'=?
                      AND candidate.snapshot->'assignment'->>'position_id'=?)
                    """, "TRUE");
            parameters = new Object[]{history.unitId(), history.positionId()};
            wantedAssignment = null;
            wantedEmployee = null;
            wantedVersion = null;
        } else {
            throw denied();
        }

        try {
            List<Historical> records = jdbc.query(statement,
                    (rs, row) -> new Historical(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6), rs.getBoolean(7)),
                    parameters);
            if (records == null || records.isEmpty()) throw denied();
            var owners = new TreeSet<>(OWNER_ORDER);
            String employee = null;
            boolean selectedCompany = false;
            for (Historical record : records) {
                if (record == null || record.incomplete()
                        || record.eventCount() > 1024 || record.eventCount() < 1 || record.version() < 1
                        || record.employee() == null || record.assignment() == null
                        || record.company() == null || record.unit() == null
                        || wantedEmployee != null && !wantedEmployee.equals(record.employee())
                        || wantedVersion != null && !wantedAssignment.equals(record.assignment())
                        || wantedVersion != null && wantedVersion.longValue() != record.version()
                        || !positionView && employee != null && !employee.equals(record.employee())) throw denied();
                if (!positionView) employee = record.employee();
                owners.add(new CompanyUnit(record.company(), record.unit()));
                selectedCompany |= query.companyPartyId().equals(record.company());
                if (owners.size() > OrganizationProtectedAppointmentAuthorizationContract.MAX_READ_PAIRS)
                    throw denied();
            }
            if (!selectedCompany || !positionView && employee == null) throw denied();
            if (query instanceof EmployeeAppointmentQuery.History history && positionView
                    && !owners.contains(new CompanyUnit(query.companyPartyId(), history.unitId()))) throw denied();
            return new DescriptorPreview(new ArrayList<>(owners), employee,
                    wantedAssignment, false);
        } catch (RuntimeException unavailable) {
            // Missing/foreign/legacy/incomplete source is intentionally indistinguishable.
            throw denied();
        }
    }

    private record Observed(String employeeId, String unitId, String companyId) { }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected appointment admission unavailable");
    }
}
