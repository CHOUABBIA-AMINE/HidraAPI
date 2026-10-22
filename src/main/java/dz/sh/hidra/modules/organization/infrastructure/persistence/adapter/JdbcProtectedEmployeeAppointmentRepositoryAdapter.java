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
 * @Description : Bounded SQL-only appointment mutation descriptor discovery; not an active writer.
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
                SELECT e.event_id,e.employee_id,e.company_party_id,
                       pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb AS body
                  FROM public.hidra_org_appointment_event e
                 WHERE %s
                 ORDER BY e.sampled_at,e.event_id
                 LIMIT 1025
            ), snapshots AS (
                SELECT e.event_id,e.employee_id,e.company_party_id,s.snapshot
                  FROM sampled e
                 CROSS JOIN LATERAL (VALUES
                     (e.body->'before'),(e.body->'after'),(e.body->'successor')
                 ) AS s(snapshot)
                 WHERE s.snapshot IS NOT NULL AND s.snapshot <> 'null'::jsonb
            )
            SELECT s.employee_id,b.company_party_id,b.organization_unit_id,
                   s.snapshot->'assignment'->>'id' AS assignment_id,
                   (s.snapshot->'assignment'->>'appointment_version')::bigint AS version,
                   (SELECT count(*) FROM sampled) AS observed_events
              FROM snapshots s
              JOIN public.hidra_org_appointment_binding_revision b
                ON b.binding_id=s.snapshot->'assignment'->>'binding_id'
               AND b.revision=(s.snapshot->'assignment'->>'binding_revision')::bigint
               AND b.organization_unit_id=s.snapshot->'assignment'->>'organization_unit_id'
               AND b.company_party_id=s.company_party_id
             WHERE s.snapshot->'assignment'->>'employee_id'=s.employee_id
               AND s.snapshot->'binding'->>'company_party_id'=b.company_party_id
               AND s.snapshot->'binding'->>'binding_id'=b.binding_id
               AND %s
            """;

    private record Historical(String employee, String company, String unit,
                              String assignment, long version, long eventCount) { }

    /**
     * Internal read-descriptor discovery for policy/position selectors and
     * exact protected history. Every event's before/after/successor binding is
     * matched against the immutable revision; no mutable binding head, cursor,
     * current employee directory, or JPA entity becomes authority.
     *
     * The caller MUST later reacquire and compare the complete owner closure
     * under native Identity and Organization relation fences. No result/count/
     * existence may escape before authorization. POSITION and POLICY are only
     * supplied selectors; actual target/position matching is deferred to the
     * guarded read adapter. This class is deliberately not wired.
     */
    public DescriptorPreview preview(EmployeeAppointmentQuery query) {
        Objects.requireNonNull(query, "Read selector required");
        if (query instanceof EmployeeAppointmentQuery.Policy policy) {
            return new DescriptorPreview(List.of(
                    new CompanyUnit(policy.companyPartyId(), policy.unitId())), null, null, false);
        }
        if (query instanceof EmployeeAppointmentQuery.History position
                && position.scope() == EmployeeAppointmentQuery.Scope.POSITION) {
            return new DescriptorPreview(List.of(
                    new CompanyUnit(position.companyPartyId(), position.unitId())),
                    null, null, false);
        }

        final String statement;
        final Object[] parameters;
        final String wantedEmployee;
        final String wantedAssignment;
        final Long wantedVersion;
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
                    "s.snapshot->'assignment'->>'id'=?");
            parameters = new Object[]{history.assignmentId(), history.assignmentId(),
                    history.assignmentId()};
            wantedAssignment = history.assignmentId();
            wantedEmployee = null;
            wantedVersion = null;
        } else if (query instanceof EmployeeAppointmentQuery.History history
                && history.scope() == EmployeeAppointmentQuery.Scope.EMPLOYEE) {
            statement = HISTORICAL_SQL.formatted(
                    "e.employee_id=?",
                    "s.snapshot->'assignment'->>'employee_id'=?");
            parameters = new Object[]{history.employeeId(), history.employeeId()};
            wantedAssignment = null;
            wantedEmployee = history.employeeId();
            wantedVersion = null;
        } else {
            throw denied();
        }

        try {
            List<Historical> records = jdbc.query(statement,
                    (rs, row) -> new Historical(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getLong(5), rs.getLong(6)),
                    parameters);
            if (records == null || records.isEmpty()) throw denied();
            var owners = new TreeSet<>(OWNER_ORDER);
            String employee = null;
            boolean selectedCompany = false;
            for (Historical record : records) {
                if (record == null || record.eventCount() > 1024 || record.eventCount() < 1
                        || record.employee() == null || record.assignment() == null
                        || record.company() == null || record.unit() == null
                        || wantedEmployee != null && !wantedEmployee.equals(record.employee())
                        || wantedAssignment != null && !wantedAssignment.equals(record.assignment())
                        || wantedVersion != null && wantedVersion.longValue() != record.version()
                        || employee != null && !employee.equals(record.employee())) throw denied();
                employee = record.employee();
                owners.add(new CompanyUnit(record.company(), record.unit()));
                selectedCompany |= query.companyPartyId().equals(record.company());
                if (owners.size() > OrganizationProtectedAppointmentAuthorizationContract.MAX_READ_PAIRS)
                    throw denied();
            }
            if (!selectedCompany || employee == null) throw denied();
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
