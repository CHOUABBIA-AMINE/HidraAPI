/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProtectedEmployeeAppointmentRepositoryAdapter
 * @CreatedOn   : 2026-10-22
 * @UpdatedOn   : 2026-10-23
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Retains protected owner admission and discovers immutable appointment ownership descriptors.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.domain.model.CompanyBoundEmployeeAppointment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitCompanyBinding;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentOwnershipPolicy;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import tools.jackson.databind.json.JsonMapper;

/**
 * An isolated first JDBC slice of the registered protected repository. This is
 * intentionally NOT a component, and does NOT implement the protected repository
 * port until its complete lifecycle, final database guard and receipt are
 * implemented. Preview data is only an UNTRUSTED selector for the native
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
    private final DataSource source;
    private final OrganizationProtectedAppointmentAuthorizationContract identity;

    public JdbcProtectedEmployeeAppointmentRepositoryAdapter(JdbcTemplate jdbc) {
        this(jdbc, null);
    }

    public JdbcProtectedEmployeeAppointmentRepositoryAdapter(JdbcTemplate jdbc,
            OrganizationProtectedAppointmentAuthorizationContract identity) {
        this.jdbc = Objects.requireNonNull(jdbc, "JDBC access required");
        this.source = jdbc.getDataSource();
        this.identity = identity;
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
        try {
            var replay = replayPreview(command, null);
            if (replay != null) return replay;
        } catch (RuntimeException unavailable) { throw denied(); }
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


    // Derived from the CURRENT hosted catalog dictionary; no inferred physical metadata.
    private static final Map<String, String> CATALOG_PINS = Map.ofEntries(
            Map.entry("public.hidra_org_employee", "1a2b849ba9dca9575a80f4351f2d4c4d6cfa5d397f847ed3bb94109f1ad0193e"),
            Map.entry("public.hidra_org_unit", "afd811b6baa899a7f5647848694592fde824a15fd5b7b4e3af7062da7a1dbfcb"),
            Map.entry("public.hidra_org_position", "f6a88ae626ae77f49d5adbadc25c4acf4781f3a6cf05c96adc6e4d63d2f22c68"),
            Map.entry("public.hidra_org_appointment_binding_revision", "9b58d4f707daf02f27fd0eee49471589c82dfe4d1127279f8eb2945f06b200b8"),
            Map.entry("public.hidra_org_appointment_binding_head", "1a4a34ca838aa14f1007df01e8aeca90c6c1960b0535cc153550e3a495f32be7"),
            Map.entry("public.hidra_org_appointment_policy_revision", "41c925412d7ea47a3d0e54538f6e45cdd1f379ee8221f05065521a4c25ff072a"),
            Map.entry("public.hidra_org_appointment_policy_head", "faea4511d597c0f39f0fc4a28a2e99568daf7fb06ffec1182f721f41af3a6e0e"),
            Map.entry("public.hidra_org_appointment_configuration_event", "f068a3d5c8aad7d23e4d7d407582649c4e58a26b58133a64d1dbaa511918c9ea"),
            Map.entry("public.hidra_org_appointment_lock_key", "38259a05133312a2ad0c8fa205f073aabea8f2b781ba85de03929f8b1d6444fa"),
            Map.entry("public.hidra_org_employee_assignment", "32d8113ab0f7d677837cd66c471351efd25ca3b0c05f76aacaeda9aab4b8c406"),
            Map.entry("public.hidra_org_appointment_event", "c919adf7b59aef43d49ad3bdb2ddcc98c63f0529776132e0edc081aec2669e76"),
            Map.entry("public.hidra_org_appointment_receipt", "2c6a183766db9b11b96f4441bf5f3fc68baf4b794b7effedcefdf7243c03ab65"));
    // Exact latest authored prosrc bytes, including accepted forward replacements.
    private static final Map<String, String> FUNCTION_PINS = Map.ofEntries(
            Map.entry("hidra_org_appointment_exact_id", "ef75cca90db3f80338e710d62d8db04ed4b8e9d000f3e78b878058592761e5d2"),
            Map.entry("hidra_org_appointment_policy_valid", "62e41666f14398fd36f19184ff7e77ea094f8110fb67b306a7b2f609dc3fffda"),
            Map.entry("hidra_org_appointment_configuration_immutable", "77db52e487758bad1904239e7d83f166a3e1f6089fd2ac0be07b1f5d90e8e713"),
            Map.entry("hidra_org_appointment_configuration_context", "7b2d44b63fbaebcffa7aef92e5478027415fd17a4ed2179c91760c244d168607"),
            Map.entry("hidra_org_appointment_configuration_locks", "3473858642398f81ad5bbbd876ca78b4f0ba6a18167a145182546505d5d71383"),
            Map.entry("hidra_org_activate_appointment_binding", "af95d48f202326b5d48594c8e6f87cf93521ab8c3194faa7c2f467cb46dcfe1b"),
            Map.entry("hidra_org_activate_appointment_policy", "e2741065f63d483473cbb12d9ea8a81bfd47931321acd04a7107adfda9cfe921"),
            Map.entry("hidra_org_appointment_configuration_complete", "7608688dcabd3fdafb256155e5e739d2e6146226fe5530138ea9915dbedb34d7"),
            Map.entry("hidra_org_appointment_head_transition", "cd1dd4aa3bc5a258109189ada8c68b2d1ed227f06d0e631212c00a6e4ccdb553"),
            Map.entry("hidra_org_appointment_projection_transition", "525d6eef06bb05e09fa6bfe32dc04ff953286d2cf4fdf2b548d1626b4c160ec9"),
            Map.entry("hidra_org_appointment_snapshot", "4d5932127c531b2fb2906dee47c9f98607fa080cd6d7a57c94bb3902026c8962"),
            Map.entry("hidra_org_appointment_projection_complete", "0f6d9118185119c3239b052d2ba53e969e4ada9a4353e7a0e6d351702b1a97a6"),
            Map.entry("hidra_org_appointment_ledger_complete", "2ad3f0c7c64d18e0934454232c72afc1808e5ca8f2ad6b66e93f0f25d09a238b"),
            Map.entry("hidra_org_appointment_command_time_matches", "0c6b5b29cdda3e556ead9fdbea9fc7ce41dba5af4e5280f343f1d3d27fcbf5ff"),
            Map.entry("hidra_org_appointment_typed_command_complete", "9a7129f9c2971fd12bc7572714cfdda5e79dc798b8131b50dd32529636967ce9"));

    private static final List<ProtectedRelationFence> ORGANIZATION_FENCES = List.of(
            new ProtectedRelationFence("public.hidra_org_employee", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_unit", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_position", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_appointment_binding_revision", ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_org_appointment_binding_head", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_appointment_policy_revision", ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_org_appointment_policy_head", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_appointment_configuration_event", ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_org_appointment_lock_key", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_employee_assignment", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_org_appointment_event", ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_org_appointment_receipt", ProtectedRelationFence.Mode.ACCESS_SHARE));
    private static final List<ProtectedRelationFence> NATIVE_FENCES = List.of(
            new ProtectedRelationFence("public.hidra_identity_attribute_definition", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_authorization_policy", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_authorization_policy_rule", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_authorization_policy_version", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_external_group_mapping", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_external_identity", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_external_permission_mapping", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_external_role_mapping", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_group", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_group_role_grant", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_local_credential", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_login_session", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_operation_binding", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_permission", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_provider", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_role", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_role_permission_grant", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_subject_security_attribute", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_user", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_user_group_membership", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_user_permission_grant", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_user_role_grant", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_identity_external_mapping_generation", ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_identity_bound_source_claim_evidence", ProtectedRelationFence.Mode.SHARE)
    );
    private static final List<ProtectedRelationFence> PROOF_FENCES = List.of(
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_event", ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_head", ProtectedRelationFence.Mode.SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_proof_use", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
    private static final List<ProtectedRelationFence> PARTY_FENCES = List.of(
            new ProtectedRelationFence("public.hidra_party_party", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new ProtectedRelationFence("public.hidra_party_eligibility_generation", ProtectedRelationFence.Mode.ACCESS_SHARE));

    private static final String PHYSICAL_CONTEXT = """
            SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
               AND pg_catalog.current_setting('transaction_read_only')='off'
               AND pg_catalog.current_setting('session_replication_role')='origin'
               AND current_user=session_user
               AND pg_catalog.current_setting('role') IN ('none',session_user::text)
               AND pg_catalog.array_to_json(pg_catalog.current_schemas(true))::text='["pg_catalog","public"]'
               AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
               AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
               AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r
                   WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
                     AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
                       OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files',
                                       'pg_read_all_data','pg_write_all_data','pg_signal_backend','pg_checkpoint')))
               AND NOT pg_catalog.has_function_privilege(current_user,
                   'public.hidra_org_activate_appointment_binding(text,text,text,bigint)','EXECUTE')
               AND NOT pg_catalog.has_function_privilege(current_user,
                   'public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)','EXECUTE')
               AND NOT pg_catalog.has_function_privilege(current_user,
                   'public.hidra_org_appointment_configuration_locks()','EXECUTE')
               AND NOT pg_catalog.has_function_privilege(current_user,
                   'public.hidra_org_appointment_configuration_context(text)','EXECUTE')
            """;

    private static final String RELATION_CONTEXT = """
            SELECT c.relkind='r' AND c.relpersistence='p' AND NOT c.relispartition
               AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
               AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')
               AND c.relowner=(SELECT relowner FROM pg_catalog.pg_class
                   WHERE oid='public.hidra_org_employee'::regclass)
               AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_inherits
                   WHERE inhrelid=c.oid OR inhparent=c.oid)
               AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
              FROM pg_catalog.pg_class c WHERE c.oid=?::regclass
            """;

    private static final String CATALOG_FINGERPRINT = """
            WITH target AS (SELECT ?::regclass AS oid), lines AS (
                SELECT pg_catalog.concat_ws('|','C',a.attname,
                    pg_catalog.format_type(a.atttypid,a.atttypmod),(NOT a.attnotnull)::text,
                    COALESCE(pg_catalog.pg_get_expr(d.adbin,d.adrelid),''),
                    a.attidentity::text,a.attgenerated::text) AS value
                  FROM pg_catalog.pg_attribute a LEFT JOIN pg_catalog.pg_attrdef d
                    ON d.adrelid=a.attrelid AND d.adnum=a.attnum
                 WHERE a.attrelid=(SELECT oid FROM target) AND a.attnum>0 AND NOT a.attisdropped
                UNION
                SELECT pg_catalog.concat_ws('|','K',k.conname,pg_catalog.pg_get_constraintdef(k.oid,true),
                    k.contype::text,k.convalidated::text,k.condeferrable::text,k.condeferred::text)
                  FROM pg_catalog.pg_constraint k WHERE k.conrelid=(SELECT oid FROM target)
                UNION
                SELECT pg_catalog.concat_ws('|','I',c.relname,pg_catalog.pg_get_indexdef(i.indexrelid),
                    i.indisprimary::text,i.indisunique::text,i.indisvalid::text,i.indisready::text)
                  FROM pg_catalog.pg_index i JOIN pg_catalog.pg_class c ON c.oid=i.indexrelid
                 WHERE i.indrelid=(SELECT oid FROM target)
                UNION
                SELECT pg_catalog.concat_ws('|','T',t.tgname,pg_catalog.pg_get_triggerdef(t.oid,true),
                    t.tgenabled::text,n.nspname||'.'||p.proname||'('||
                    pg_catalog.pg_get_function_identity_arguments(p.oid)||')')
                  FROM pg_catalog.pg_trigger t JOIN pg_catalog.pg_proc p ON p.oid=t.tgfoid
                  JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
                 WHERE t.tgrelid=(SELECT oid FROM target) AND NOT t.tgisinternal
                UNION
                SELECT 'F|'||pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(
                    pg_catalog.pg_get_functiondef(p.oid),'UTF8')),'hex')
                  FROM pg_catalog.pg_trigger t JOIN pg_catalog.pg_proc p ON p.oid=t.tgfoid
                 WHERE t.tgrelid=(SELECT oid FROM target) AND NOT t.tgisinternal
            )
            SELECT pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(
                pg_catalog.string_agg(value,E'\\n' ORDER BY value COLLATE "C"),'UTF8')),'hex') FROM lines
            """;

    /**
     * Retains Organization fences after native/Party acquisition. The returned
     * private ticket is UNQUALIFIED until contribute consumes its genuine native
     * issuer after the coordinator's locked descriptor equality check.
     */
    public ProtectedEmployeeAppointmentRepositoryPort.Admission acquire(
            OrganizationProtectedAppointmentAuthorizationContract.Admission nativeAdmission,
            List<OrganizationProtectedPartyEligibilityContract.Admission> partyAdmissions,
            DescriptorPreview preview) {
        StorageAdmission ticket = null;
        try {
            if (identity == null || nativeAdmission == null || preview == null) throw denied();
            preview.requireBounded();
            var holder = context();
            var actual = SecurityContextHolder.getContext().getAuthentication();
            if (actual == null || !actual.isAuthenticated()) throw denied();
            var parties = List.copyOf(Objects.requireNonNull(partyAdmissions));
            if (parties.size() > 1) throw denied();
            check(PHYSICAL_CONTEXT);
            if (!parties.isEmpty()) requireHeld(PARTY_FENCES);
            for (var fence : ORGANIZATION_FENCES) {
                String mode = fence.mode() == ProtectedRelationFence.Mode.ACCESS_SHARE
                        ? "ACCESS SHARE" : "SHARE ROW EXCLUSIVE";
                jdbc.execute("LOCK TABLE " + fence.relation() + " IN " + mode + " MODE NOWAIT");
            }
            requireCatalog();
            requireHeld(ORGANIZATION_FENCES);
            ticket = jdbc.queryForObject(
                    "SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                    (row, index) -> new StorageAdmission(holder, actual, nativeAdmission, parties, preview,
                            row.getInt(1), row.getLong(2), row.getString(3)));
            if (ticket == null) throw denied();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch (RuntimeException failure) {
            poison(ticket);
            throw denied();
        }
    }

    /** One native consumption, privately retained; no caller-supplied observation is accepted. */
    public OrganizationProtectedAppointmentAuthorizationContract.Contribution contribute(
            ProtectedEmployeeAppointmentRepositoryPort.Admission admission) {
        StorageAdmission ticket = null;
        try {
            ticket = ticket(admission);
            if (!ticket.used.compareAndSet(false, true)) throw denied();
            var sample = Objects.requireNonNull(identity.contribute(ticket.nativeAdmission));
            if (!sample.request().companyUnits().equals(ticket.preview.companyUnits())
                    || !Objects.equals(sample.request().employeeId(), ticket.preview.employeeId())
                    || sample.overallDecision()
                        != OrganizationProtectedAppointmentAuthorizationContract.Decision.PERMIT
                    || sample.backendPid() != ticket.pid || sample.transactionId() != ticket.xid) throw denied();
            boolean read = sample.request().action().name().startsWith("READ");
            if (read != ticket.parties.isEmpty()) throw denied();
            ticket.sample = sample;
            ticket.nativeFences = List.copyOf(identity.finalFences(ticket.nativeAdmission));
            if (!NATIVE_FENCES.equals(ticket.nativeFences)) throw denied();
            if (!read) {
                ticket.proofFences = List.copyOf(identity.commitProofFences(ticket.nativeAdmission));
                if (!PROOF_FENCES.equals(ticket.proofFences)) throw denied();
            }
            requireCurrent(ticket);
            return sample;
        } catch (RuntimeException failure) {
            poison(ticket == null ? admission : ticket);
            throw denied();
        }
    }

    /** Rechecks the issuing native owner and actual retained physical/catalog state. */
    public void requireCurrent(ProtectedEmployeeAppointmentRepositoryPort.Admission admission) {
        StorageAdmission ticket = null;
        try {
            ticket = ticket(admission);
            if (ticket.sample == null || !ticket.used.get()) throw denied();
            identity.requireCurrent(ticket.nativeAdmission);
            requireCatalog();
            requireHeld(ORGANIZATION_FENCES);
            requireHeld(ticket.nativeFences);
            requireHeld(ticket.proofFences);
            if (!ticket.parties.isEmpty()) requireHeld(PARTY_FENCES);
            var expected = ticket;
            if (!Boolean.TRUE.equals(jdbc.queryForObject("""
                    SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=?
                       AND current_user=? AND clock_timestamp()>=?::timestamptz
                       AND clock_timestamp()<?::timestamptz
                    """, Boolean.class, ticket.pid, ticket.xid, ticket.role,
                    java.sql.Timestamp.from(ticket.sample.sampledAt()),
                    java.sql.Timestamp.from(ticket.sample.validUntil())))) throw denied();
            if (ticket.plan != null && !Boolean.TRUE.equals(jdbc.queryForObject(
                    "SELECT clock_timestamp()<?::timestamptz",Boolean.class,
                    java.sql.Timestamp.from(ticket.plan.validUntil())))) throw denied();
            if (ticket(expected) != expected) throw denied();
        } catch (RuntimeException failure) {
            poison(ticket == null ? admission : ticket);
            throw denied();
        }
    }

    private static final String POLICY_SQL = """
            SELECT p.policy_id,p.revision,(p.policy->>'maxConcurrentPrimary')::integer,
                   p.policy->>'positionCapacity',f.key,s.key,s.value::text,
                   b.binding_id,b.revision,bh.last_event_id,ph.last_event_id
              FROM public.hidra_org_appointment_binding_head bh
              JOIN public.hidra_org_appointment_binding_revision b
                ON b.organization_unit_id=bh.organization_unit_id AND b.binding_id=bh.binding_id
               AND b.revision=bh.revision AND b.company_party_id=bh.company_party_id
              JOIN public.hidra_org_appointment_policy_head ph ON ph.company_party_id=b.company_party_id
              JOIN public.hidra_org_appointment_policy_revision p
                ON p.company_party_id=ph.company_party_id AND p.policy_id=ph.policy_id AND p.revision=ph.revision
              JOIN public.hidra_org_appointment_configuration_event be ON be.event_id=bh.last_event_id
              JOIN public.hidra_org_appointment_configuration_event pe ON pe.event_id=ph.last_event_id
             CROSS JOIN LATERAL pg_catalog.jsonb_each(p.policy->'overlaps') f
             CROSS JOIN LATERAL pg_catalog.jsonb_each(f.value) s
             WHERE bh.organization_unit_id=? AND bh.company_party_id=?
               AND bh.generation=bh.revision AND ph.generation=ph.revision
               AND bh.event_kind='BINDING' AND ph.event_kind='POLICY'
               AND be.kind='BINDING' AND be.scope_id=b.organization_unit_id
               AND be.identity_id=b.binding_id AND be.revision=b.revision
               AND be.company_party_id=b.company_party_id AND be.after_payload=pg_catalog.to_jsonb(b)
               AND pe.kind='POLICY' AND pe.scope_id=p.company_party_id
               AND pe.identity_id=p.policy_id AND pe.revision=p.revision
               AND pe.company_party_id=p.company_party_id AND pe.after_payload=pg_catalog.to_jsonb(p)
               AND p.canonical_payload=pg_catalog.convert_to(p.policy::text,'UTF8')
               AND p.sha256=pg_catalog.encode(pg_catalog.sha256(p.canonical_payload),'hex')
               AND pg_catalog.jsonb_typeof(p.policy)='object'
               AND (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p.policy))=3
               AND pg_catalog.jsonb_exists_all(p.policy,ARRAY['overlaps','maxConcurrentPrimary','positionCapacity'])
               AND pg_catalog.jsonb_typeof(p.policy->'maxConcurrentPrimary')='number'
               AND (p.policy->>'maxConcurrentPrimary') ~ '^[1-9][0-9]{0,9}$'
               AND pg_catalog.jsonb_typeof(p.policy->'positionCapacity')='string'
               AND (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p.policy->'overlaps'))=4
               AND (SELECT count(*) FROM pg_catalog.jsonb_object_keys(f.value))=4
               AND pg_catalog.jsonb_typeof(s.value)='boolean'
             ORDER BY f.key COLLATE "C",s.key COLLATE "C"
             LIMIT 17
            """;

    private record PolicyCell(String policyId, long revision, int primaryLimit, String capacity,
                              String first, String second, String allowed, String bindingId,
                              long bindingRevision, String bindingEvent, String policyEvent) { }

    private record PolicyObservation(EmployeeAppointmentQuery.Policy selector,
                                     EmployeeAppointmentResult.PolicyView view,
                                     String bindingId, long bindingRevision,
                                     String bindingEvent, String policyEvent) { }

    /**
     * Materializes a detached policy only for the exact genuine READ_POLICY
     * sample. Current immutable binding/policy revisions and both administrator
     * activation events must agree under the retained owner fences. This is an
     * internal admission-phase read, not a final guarded/committed HTTP result.
     * No Party ACTIVE check or personnel-policy default is inferred.
     */
    public EmployeeAppointmentResult.PolicyView readPolicy(
            ProtectedEmployeeAppointmentRepositoryPort.Admission admission,
            EmployeeAppointmentQuery.Policy query) {
        StorageAdmission ticket = null;
        try {
            ticket = ticket(admission);
            requireCurrent(ticket);
            Objects.requireNonNull(query);
            var expected = new OrganizationProtectedAppointmentAuthorizationContract.Request(
                    OrganizationProtectedAppointmentAuthorizationContract.Action.READ_POLICY,
                    List.of(new CompanyUnit(query.companyPartyId(), query.unitId())), null);
            if (!expected.equals(ticket.sample.request()) || !ticket.parties.isEmpty()
                    || !preview(query).equals(ticket.preview)
                    || (ticket.policy != null && !query.equals(ticket.policy.selector()))) throw denied();
            var cells = jdbc.query(POLICY_SQL, (r, i) -> new PolicyCell(
                    r.getString(1),r.getLong(2),r.getInt(3),r.getString(4),r.getString(5),r.getString(6),
                    r.getString(7),r.getString(8),r.getLong(9),r.getString(10),r.getString(11)),
                    query.unitId(),query.companyPartyId());
            if (cells == null || cells.size() != 16) throw denied();
            var first = cells.get(0);
            var value = decodePolicy(cells);
            var view = new EmployeeAppointmentResult.PolicyView(query.companyPartyId(),query.unitId(),
                    first.policyId(),first.revision(),value);
            var observed = new PolicyObservation(query,view,first.bindingId(),first.bindingRevision(),
                    first.bindingEvent(),first.policyEvent());
            if (ticket.policy != null && !ticket.policy.equals(observed)) throw denied();
            ticket.policy = observed;
            requireCurrent(ticket);
            return view;
        } catch (RuntimeException failure) {
            poison(ticket == null ? admission : ticket);
            throw denied();
        }
    }

    private static EmployeeAppointmentPolicy decodePolicy(List<PolicyCell> cells) {
        if (cells==null || cells.size()!=16) throw denied();
        var first=cells.get(0);
        var matrix=new java.util.HashMap<EmployeeAppointmentPolicy.TypePair,Boolean>();
        for(var cell:cells){
            if (!Objects.equals(first.policyId(),cell.policyId()) || first.revision()!=cell.revision()
                    || first.primaryLimit()!=cell.primaryLimit() || !Objects.equals(first.capacity(),cell.capacity())
                    || !Objects.equals(first.bindingId(),cell.bindingId()) || first.bindingRevision()!=cell.bindingRevision()
                    || !Objects.equals(first.bindingEvent(),cell.bindingEvent()) || !Objects.equals(first.policyEvent(),cell.policyEvent())
                    || !("true".equals(cell.allowed()) || "false".equals(cell.allowed()))) throw denied();
            var pair=new EmployeeAppointmentPolicy.TypePair(AssignmentType.valueOf(cell.first()),AssignmentType.valueOf(cell.second()));
            if(matrix.put(pair,Boolean.valueOf(cell.allowed()))!=null)throw denied();
        }
        return new EmployeeAppointmentPolicy(matrix,first.primaryLimit(),
                EmployeeAppointmentPolicy.PositionCapacity.valueOf(first.capacity()));
    }

    private StorageAdmission ticket(ProtectedEmployeeAppointmentRepositoryPort.Admission admission) {
        if (!(admission instanceof StorageAdmission t) || t.issuer != this || !t.active
                || !t.sameContext() || context() != t.holder
                || !TransactionSynchronizationManager.getSynchronizations().contains(t)) throw denied();
        return t;
    }

    private ConnectionHolder context() {
        if (source == null || !TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h)
                || h.isRollbackOnly()) throw denied();
        return h;
    }

    private void check(String sql, Object... args) {
        if (!Boolean.TRUE.equals(jdbc.queryForObject(sql, Boolean.class, args))) throw denied();
    }

    private void requireHeld(List<ProtectedRelationFence> fences) {
        var held = new java.util.HashSet<>(jdbc.query("""
                SELECT n.nspname||'.'||c.relname||'|'||l.mode
                  FROM pg_catalog.pg_locks l JOIN pg_catalog.pg_class c ON c.oid=l.relation
                  JOIN pg_catalog.pg_namespace n ON n.oid=c.relnamespace
                 WHERE l.pid=pg_catalog.pg_backend_pid() AND l.granted AND l.locktype='relation'
                """, (row, index) -> row.getString(1)));
        for (var fence : fences)
            if (!held.contains(fence.relation() + "|" + fence.mode().catalog())) throw denied();
    }

    private void requireCatalog() {
        check(PHYSICAL_CONTEXT);
        for (var fence : ORGANIZATION_FENCES) {
            String relation = fence.relation();
            check(RELATION_CONTEXT, relation);
            if (!CATALOG_PINS.get(relation).equals(
                    jdbc.queryForObject(CATALOG_FINGERPRINT, String.class, relation))) throw denied();
            boolean immutableConfig = relation.endsWith("_binding_revision") || relation.endsWith("_policy_revision")
                    || relation.endsWith("_configuration_event") || relation.endsWith("_appointment_receipt");
            if (immutableConfig) {
                check("SELECT NOT pg_catalog.has_table_privilege(current_user,?::regclass,'INSERT,UPDATE,REFERENCES')"
                        + " AND NOT pg_catalog.has_any_column_privilege(current_user,?::regclass,'INSERT,UPDATE,REFERENCES')",
                        relation, relation);
            }
            if (relation.endsWith("_binding_head") || relation.endsWith("_policy_head")) {
                // PostgreSQL LOCK requires table-level UPDATE. Pinned transition/deferred
                // guards still require a same-XID administrator configuration event.
                check("""
                        SELECT NOT pg_catalog.has_table_privilege(current_user,?::regclass,'INSERT,REFERENCES')
                           AND NOT pg_catalog.has_any_column_privilege(current_user,?::regclass,'INSERT,REFERENCES')
                        """, relation, relation);
            }
        }
        var functions = jdbc.query("""
                SELECT p.proname,pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex'),
                       NOT pg_catalog.pg_has_role(current_user,p.proowner,'MEMBER')
                       AND p.proowner=(SELECT relowner FROM pg_catalog.pg_class
                           WHERE oid='public.hidra_org_employee'::regclass)
                       AND NOT EXISTS(SELECT 1 FROM pg_catalog.aclexplode(
                           COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
                           WHERE a.grantee=0 AND a.privilege_type='EXECUTE') AS safe
                  FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
                 WHERE n.nspname='public' AND (p.proname LIKE 'hidra_org_appointment_%'
                     OR p.proname IN ('hidra_org_activate_appointment_binding','hidra_org_activate_appointment_policy'))
                """, (row, index) -> {
                    if (!row.getBoolean(3)) throw denied();
                    return Map.entry(row.getString(1), row.getString(2));
                });
        var actual = new java.util.HashMap<String, String>();
        for (var function : functions)
            if (actual.put(function.getKey(), function.getValue()) != null) throw denied();
        if (!FUNCTION_PINS.equals(actual)) throw denied();
    }

    private void poison(Object admission) {
        if (admission instanceof JdbcProtectedEmployeeAppointmentRepositoryAdapter.StorageAdmission t) {
            t.active = false;
            t.holder.setRollbackOnly();
        }
        if (source != null && TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h)
            h.setRollbackOnly();
        try { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch (org.springframework.transaction.NoTransactionException absent) { /* Transaction-template caller. */ }
    }

    private final class StorageAdmission implements ProtectedEmployeeAppointmentRepositoryPort.Admission,
            TransactionSynchronization {
        private final JdbcProtectedEmployeeAppointmentRepositoryAdapter issuer =
                JdbcProtectedEmployeeAppointmentRepositoryAdapter.this;
        private final ConnectionHolder holder;
        private final Connection connection;
        private final Authentication authentication;
        private final Thread thread = Thread.currentThread();
        private final OrganizationProtectedAppointmentAuthorizationContract.Admission nativeAdmission;
        private final List<OrganizationProtectedPartyEligibilityContract.Admission> parties;
        private final DescriptorPreview preview;
        private final int pid;
        private final long xid;
        private final String role;
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile boolean active = true;
        private OrganizationProtectedAppointmentAuthorizationContract.Contribution sample;
        private PolicyObservation policy;
        private MutationClassification mutation;
        private MutationPlan plan;
        private List<ProtectedRelationFence> nativeFences = List.of(), proofFences = List.of();

        private StorageAdmission(ConnectionHolder holder, Authentication authentication,
                OrganizationProtectedAppointmentAuthorizationContract.Admission nativeAdmission,
                List<OrganizationProtectedPartyEligibilityContract.Admission> parties, DescriptorPreview preview,
                int pid, long xid, String role) {
            this.holder=holder;this.connection=holder.getConnection();this.authentication=authentication;
            this.nativeAdmission=nativeAdmission;this.parties=parties;this.preview=preview;
            this.pid=pid;this.xid=xid;this.role=role;
            if (pid<=0 || xid<=0 || role==null) throw denied();
        }
        private boolean sameContext() {
            return active && thread==Thread.currentThread() && !holder.isRollbackOnly()
                    && authentication==SecurityContextHolder.getContext().getAuthentication()
                    && authentication.isAuthenticated() && holder.getConnection()==connection;
        }
        @Override public void beforeCommit(boolean readOnly) {
            if (readOnly || sample==null || !sameContext()) { poison(this); throw denied(); }
        }
        @Override public void suspend() { poison(this); }
        @Override public void afterCompletion(int status) { active=false; }
        @Override public String toString() { return "StorageAdmission[opaque]"; }
    }

    private static final JsonMapper COMMAND_JSON = JsonMapper.builder().build();

    /** Explicit membership; preserve nullable fields, exact identifiers/reasons and microsecond instants. */
    private static String commandJson(EmployeeAppointmentCommand command) {
        var fields = new java.util.LinkedHashMap<String,Object>();
        fields.put("kind",command.kind().name());fields.put("requestId",command.requestId());
        fields.put("companyPartyId",command.companyPartyId());
        if (command instanceof EmployeeAppointmentCommand.Create c) {
            fields.put("employeeId",c.employeeId());fields.put("organizationUnitId",c.organizationUnitId());
            fields.put("positionId",c.positionId());fields.put("assignmentType",c.assignmentType().name());
            fields.put("validFrom",c.validFrom().toString());
            fields.put("validTo",c.validTo()==null?null:c.validTo().toString());
            fields.put("expectedBindingRevision",c.expectedBindingRevision());
            fields.put("expectedPolicyRevision",c.expectedPolicyRevision());
        } else {
            fields.put("assignmentId",sourceId(command));
            if (command instanceof EmployeeAppointmentCommand.End c) {
                fields.put("expectedVersion",c.expectedVersion());fields.put("endAt",c.endAt().toString());
                fields.put("reason",c.reason());
            } else if (command instanceof EmployeeAppointmentCommand.Transfer c) {
                fields.put("expectedVersion",c.expectedVersion());
                fields.put("destinationOrganizationUnitId",c.destinationOrganizationUnitId());
                fields.put("destinationPositionId",c.destinationPositionId());
                fields.put("destinationType",c.destinationType().name());fields.put("effectiveAt",c.effectiveAt().toString());
                fields.put("successorValidTo",c.successorValidTo()==null?null:c.successorValidTo().toString());
                fields.put("expectedDestinationBindingRevision",c.expectedDestinationBindingRevision());
                fields.put("expectedDestinationPolicyRevision",c.expectedDestinationPolicyRevision());
                fields.put("reason",c.reason());
            } else if (command instanceof EmployeeAppointmentCommand.Correct c) {
                fields.put("expectedVersion",c.expectedVersion());
                fields.put("correctedOrganizationUnitId",c.correctedOrganizationUnitId());
                fields.put("correctedPositionId",c.correctedPositionId());fields.put("correctedType",c.correctedType().name());
                fields.put("correctedStatus",c.correctedStatus().name());
                fields.put("correctedValidFrom",c.correctedValidFrom().toString());
                fields.put("correctedValidTo",c.correctedValidTo()==null?null:c.correctedValidTo().toString());
                fields.put("expectedDestinationBindingRevision",c.expectedDestinationBindingRevision());
                fields.put("expectedDestinationPolicyRevision",c.expectedDestinationPolicyRevision());
                fields.put("reason",c.reason());
            } else throw denied();
        }
        return COMMAND_JSON.writeValueAsString(fields);
    }

    private static String sourceId(EmployeeAppointmentCommand command) {
        return command instanceof EmployeeAppointmentCommand.End c ? c.assignmentId()
                : command instanceof EmployeeAppointmentCommand.Transfer c ? c.assignmentId()
                : command instanceof EmployeeAppointmentCommand.Correct c ? c.assignmentId() : null;
    }

    /* Actor is intentionally unknown during preview. Candidate receipts are only
     * untrusted selectors; actual replay is selected by the genuine sampled user.
     * Exact command matching and complete snapshot closure prevent mutable source
     * ownership from replacing the original receipt's historical descriptors. */
    private DescriptorPreview replayPreview(EmployeeAppointmentCommand command, String eventId) {
        String predicate = eventId == null ? """
                e.event_id IN (SELECT r.event_id FROM public.hidra_org_appointment_receipt r
                    WHERE r.request_id=? AND e.company_party_id=?
                      AND pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'command'=?::jsonb)
                """ : "e.event_id=?::uuid";
        Object[] values = eventId == null
                ? new Object[]{command.requestId(),command.companyPartyId(),commandJson(command)}
                : new Object[]{eventId};
        var rows = jdbc.query(HISTORICAL_SQL.formatted(predicate,"TRUE"),
                (r,i) -> new Historical(r.getString(1),r.getString(2),r.getString(3),r.getString(4),
                        r.getLong(5),r.getLong(6),r.getBoolean(7)),values);
        if (rows == null) throw denied();
        if (rows.isEmpty()) { if (eventId != null) throw denied(); return null; }
        var pairs = new TreeSet<>(OWNER_ORDER);
        String employee = null;
        boolean sourceSeen = sourceId(command)==null;
        for (var row : rows) {
            if (row.incomplete() || row.eventCount()<1 || row.eventCount()>1024 || row.version()<1
                    || row.employee()==null || row.assignment()==null || row.unit()==null
                    || !command.companyPartyId().equals(row.company())
                    || employee!=null && !employee.equals(row.employee())) throw denied();
            employee=row.employee();pairs.add(new CompanyUnit(row.company(),row.unit()));
            sourceSeen |= Objects.equals(sourceId(command),row.assignment());
        }
        if (!sourceSeen || pairs.size()>2 || employee==null
                || command instanceof EmployeeAppointmentCommand.Create c && !c.employeeId().equals(employee)) throw denied();
        return new DescriptorPreview(new ArrayList<>(pairs),employee,sourceId(command),false);
    }

    /* Stable company namespace, independent of mutable/current or historical unit
     * sets. Actor and request ID remain separate typed unique-key components.
     * PostgreSQL jsonb text is the same canonical byte framing used by the ledger. */
    private static final String COMMAND_FRAME_SQL = """
            SELECT pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(?::jsonb::text,'UTF8')),'hex'),
                   pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.jsonb_build_object(
                       'family','organization-appointment-v1','companyPartyId',?::text)::text,'UTF8')),'hex')
            """;
    private record CommandFrame(String commandDigest, String scopeDigest) { }

    private static final String RECEIPT_SQL = """
            SELECT r.event_id::text,
                   (r.command_sha256=? AND e.command_sha256=r.command_sha256
                    AND e.command_sha256=pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(
                        (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'command')::text,'UTF8')),'hex')
                    AND pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'command'=?::jsonb
                    AND e.canonical_payload=pg_catalog.convert_to(
                        pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb::text,'UTF8')
                    AND r.canonical_result=e.canonical_payload AND e.actor_user_id=r.actor_user_id
                    AND e.company_party_id=? AND e.operation=?
                    AND r.request_id=pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'command'->>'requestId'
                    AND r.backend_pid=e.backend_pid AND r.transaction_id=e.transaction_id
                    AND r.guarded_at>=e.sampled_at AND r.guarded_at<e.valid_until) AS valid
              FROM public.hidra_org_appointment_receipt r
              LEFT JOIN public.hidra_org_appointment_event e ON e.event_id=r.event_id
             WHERE r.actor_user_id=? AND r.scope_key=? AND r.request_id=?
             LIMIT 2
            """;
    private record ReceiptCandidate(String eventId, boolean valid) { }

    private static final String CLASSIFICATION_SOURCE_SQL = """
            SELECT a.appointment_version,a.organization_unit_id,a.binding_revision,
                   pg_catalog.jsonb_build_object('assignment',pg_catalog.to_jsonb(a),
                       'binding',pg_catalog.to_jsonb(b),'policy',pg_catalog.to_jsonb(p))::text
              FROM public.hidra_org_employee_assignment a
              JOIN public.hidra_org_appointment_binding_revision b ON b.binding_id=a.binding_id
               AND b.revision=a.binding_revision AND b.organization_unit_id=a.organization_unit_id
              JOIN public.hidra_org_appointment_policy_revision p ON p.policy_id=a.policy_id
               AND p.revision=a.policy_revision AND p.company_party_id=b.company_party_id
             WHERE a.id=? AND a.employee_id=? AND b.company_party_id=?
               AND a.appointment_version>0 AND a.last_event_id IS NOT NULL
             LIMIT 2
            """;
    private record ClassificationSource(long version, String unit, long bindingRevision, String snapshot) { }
    private record MutationClassification(EmployeeAppointmentCommand command, CommandFrame frame,
            ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse use, String retainedEvidence) { }

    /**
     * Classifies only under the genuine native sample and retained owner fences.
     * Receipts precede current projection CAS. This grants no write/result permit:
     * policy/conflict/eligibility/proposal/final-proof checks remain mandatory.
     */
    public ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse referenceUse(
            ProtectedEmployeeAppointmentRepositoryPort.Admission admission, EmployeeAppointmentCommand command,
            OrganizationProtectedAppointmentAuthorizationContract.Contribution contribution) {
        StorageAdmission ticket = null;
        try {
            ticket=ticket(admission);requireCurrent(ticket);Objects.requireNonNull(command);
            if (contribution!=ticket.sample || ticket.parties.size()!=1
                    || !command.kind().name().equals(ticket.sample.request().action().name())
                    || ticket.mutation!=null && !ticket.mutation.command().equals(command)
                    || !ticket.preview.equals(preview(command))) throw denied();
            String json=commandJson(command);
            var frame=jdbc.queryForObject(COMMAND_FRAME_SQL,
                    (r,i) -> new CommandFrame(r.getString(1),r.getString(2)),json,command.companyPartyId());
            if (frame==null) throw denied();
            var receipts=jdbc.query(RECEIPT_SQL,(r,i) -> new ReceiptCandidate(r.getString(1),r.getBoolean(2)),
                    frame.commandDigest(),json,command.companyPartyId(),command.kind().name(),
                    ticket.sample.userId(),frame.scopeDigest(),command.requestId());
            if (receipts==null || receipts.size()>1) throw denied();
            ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse use;
            String evidence;
            if (!receipts.isEmpty()) {
                var receipt=receipts.get(0);
                if (!receipt.valid() || receipt.eventId()==null
                        || !ticket.preview.equals(replayPreview(command,receipt.eventId()))) throw denied();
                use=ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY;
                evidence=receipt.eventId();
            } else if (command instanceof EmployeeAppointmentCommand.Create) {
                use=ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT;evidence=null;
            } else {
                var rows=jdbc.query(CLASSIFICATION_SOURCE_SQL,(r,i) -> new ClassificationSource(
                        r.getLong(1),r.getString(2),r.getLong(3),r.getString(4)),
                        sourceId(command),ticket.preview.employeeId(),command.companyPartyId());
                if (rows==null || rows.size()!=1) throw denied();
                var row=rows.get(0);
                long version=command instanceof EmployeeAppointmentCommand.End c ? c.expectedVersion()
                        : command instanceof EmployeeAppointmentCommand.Transfer c ? c.expectedVersion()
                        : ((EmployeeAppointmentCommand.Correct)command).expectedVersion();
                if (row.version()!=version) throw denied();
                boolean retained=command instanceof EmployeeAppointmentCommand.End
                        || command instanceof EmployeeAppointmentCommand.Correct c
                           && c.correctedOrganizationUnitId().equals(row.unit());
                if (retained && command instanceof EmployeeAppointmentCommand.Correct c
                        && c.expectedDestinationBindingRevision()!=row.bindingRevision()) throw denied();
                use=retained ? ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.RETAINED
                        : ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT;
                evidence=row.snapshot();
            }
            var observed=new MutationClassification(command,frame,use,evidence);
            if (ticket.mutation!=null && !ticket.mutation.equals(observed)) throw denied();
            ticket.mutation=observed;requireCurrent(ticket);return use;
        } catch (RuntimeException failure) {
            poison(ticket==null?admission:ticket);throw denied();
        }
    }

    private static final String APPOINTMENT_SNAPSHOT_SQL = """
            SELECT a.id,a.employee_id,a.organization_unit_id,a.position_id,a.assignment_type,
                   a.valid_from,a.valid_to,a.status,a.created_at,a.updated_at,a.appointment_version,
                   b.binding_id,b.revision,b.organization_unit_id,b.company_party_id,b.recorded_at,
                   p.policy_id,p.revision,a.last_event_id,
                   pg_catalog.jsonb_build_object('assignment',pg_catalog.to_jsonb(a),
                       'binding',pg_catalog.to_jsonb(b),'policy',pg_catalog.to_jsonb(p))::text
              FROM public.hidra_org_employee_assignment a
              LEFT JOIN public.hidra_org_appointment_binding_revision b ON b.binding_id=a.binding_id
               AND b.revision=a.binding_revision AND b.organization_unit_id=a.organization_unit_id
              LEFT JOIN public.hidra_org_appointment_policy_revision p ON p.policy_id=a.policy_id
               AND p.revision=a.policy_revision AND p.company_party_id=b.company_party_id
            """;
    private record AppointmentObservation(EmployeeAppointmentResult.PinnedVersion value,String evidence) { }

    private static Instant instant(ResultSet row,int column) throws SQLException {
        var value=row.getTimestamp(column);return value==null?null:value.toInstant();
    }
    private static String exact(String value) {
        if(value==null || value.isBlank() || value.length()>80 || !value.equals(value.strip())
                || Character.isSpaceChar(value.codePointAt(0)) || Character.isSpaceChar(value.codePointBefore(value.length()))
                || value.codePoints().anyMatch(c -> Character.isISOControl(c) || Character.getType(c)==Character.SURROGATE)) throw denied();
        return value;
    }
    private static AppointmentObservation appointment(ResultSet r,int index) throws SQLException {
        if(r.getObject(19)==null)throw denied();
        var assignment=new EmployeeAssignment(exact(r.getString(1)),exact(r.getString(2)),exact(r.getString(3)),exact(r.getString(4)),
                AssignmentType.valueOf(r.getString(5)),instant(r,6),instant(r,7),AssignmentStatus.valueOf(r.getString(8)),instant(r,9),instant(r,10));
        var binding=new OrganizationUnitCompanyBinding(exact(r.getString(12)),r.getLong(13),exact(r.getString(14)),exact(r.getString(15)),instant(r,16));
        var bound=new CompanyBoundEmployeeAppointment(new EmployeeAppointmentChange.VersionedAssignment(r.getLong(11),assignment),binding);
        return new AppointmentObservation(new EmployeeAppointmentResult.PinnedVersion(bound,exact(r.getString(17)),r.getLong(18)),r.getString(20));
    }

    private static final String CURRENT_BINDING_SQL = """
            SELECT b.binding_id,b.revision,b.organization_unit_id,b.company_party_id,b.recorded_at,
                   pg_catalog.jsonb_build_object('head',pg_catalog.to_jsonb(h),'revision',pg_catalog.to_jsonb(b),'event',pg_catalog.to_jsonb(e))::text
              FROM public.hidra_org_appointment_binding_head h
              JOIN public.hidra_org_appointment_binding_revision b ON b.organization_unit_id=h.organization_unit_id
               AND b.binding_id=h.binding_id AND b.revision=h.revision AND b.company_party_id=h.company_party_id
              JOIN public.hidra_org_appointment_configuration_event e ON e.event_id=h.last_event_id
             WHERE h.organization_unit_id=? AND h.company_party_id=? AND h.generation=h.revision
               AND h.event_kind='BINDING' AND e.kind='BINDING' AND e.scope_id=b.organization_unit_id
               AND e.identity_id=b.binding_id AND e.revision=b.revision AND e.company_party_id=b.company_party_id
               AND e.after_payload=pg_catalog.to_jsonb(b)
             LIMIT 2
            """;
    private record BindingObservation(OrganizationUnitCompanyBinding value,String evidence) { }

    private static final String WRITE_POLICY_SQL = """
            SELECT p.policy_id,p.revision,(p.policy->>'maxConcurrentPrimary')::integer,
                   p.policy->>'positionCapacity',f.key,s.key,s.value::text,h.last_event_id::text,
                   pg_catalog.jsonb_build_object('head',pg_catalog.to_jsonb(h),'revision',pg_catalog.to_jsonb(p),'event',pg_catalog.to_jsonb(e))::text
              FROM public.hidra_org_appointment_policy_head h
              JOIN public.hidra_org_appointment_policy_revision p ON p.company_party_id=h.company_party_id
               AND p.policy_id=h.policy_id AND p.revision=h.revision
              JOIN public.hidra_org_appointment_configuration_event e ON e.event_id=h.last_event_id
             CROSS JOIN LATERAL pg_catalog.jsonb_each(p.policy->'overlaps') f
             CROSS JOIN LATERAL pg_catalog.jsonb_each(f.value) s
             WHERE h.company_party_id=? AND h.generation=h.revision AND h.event_kind='POLICY'
               AND e.kind='POLICY' AND e.scope_id=p.company_party_id AND e.identity_id=p.policy_id
               AND e.revision=p.revision AND e.company_party_id=p.company_party_id AND e.after_payload=pg_catalog.to_jsonb(p)
               AND p.canonical_payload=pg_catalog.convert_to(p.policy::text,'UTF8')
               AND p.sha256=pg_catalog.encode(pg_catalog.sha256(p.canonical_payload),'hex')
               AND pg_catalog.jsonb_typeof(p.policy)='object'
               AND (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p.policy))=3
               AND pg_catalog.jsonb_exists_all(p.policy,ARRAY['overlaps','maxConcurrentPrimary','positionCapacity'])
               AND pg_catalog.jsonb_typeof(p.policy->'maxConcurrentPrimary')='number'
               AND (p.policy->>'maxConcurrentPrimary') ~ '^[1-9][0-9]{0,9}$'
               AND pg_catalog.jsonb_typeof(p.policy->'positionCapacity')='string'
               AND (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p.policy->'overlaps'))=4
               AND (SELECT count(*) FROM pg_catalog.jsonb_object_keys(f.value))=4
               AND pg_catalog.jsonb_typeof(s.value)='boolean'
             ORDER BY f.key COLLATE "C",s.key COLLATE "C" LIMIT 17
            """;
    private record WritePolicyCell(PolicyCell cell,String evidence) { }
    private record WritePolicy(String id,long revision,EmployeeAppointmentPolicy value,String evidence) { }
    private WritePolicy writePolicy(String company) {
        var rows=jdbc.query(WRITE_POLICY_SQL,(r,i) -> new WritePolicyCell(new PolicyCell(
                r.getString(1),r.getLong(2),r.getInt(3),r.getString(4),r.getString(5),r.getString(6),r.getString(7),
                null,0,null,r.getString(8)),r.getString(9)),company);
        if(rows==null || rows.size()!=16)throw denied();
        var first=rows.get(0);
        if(rows.stream().anyMatch(c -> !first.evidence().equals(c.evidence())))throw denied();
        return new WritePolicy(exact(first.cell().policyId()),first.cell().revision(),decodePolicy(rows.stream().map(WritePolicyCell::cell).toList()),first.evidence());
    }

    private record Occupancy(String unit,String position) { }
    private record Eligibility(Occupancy occupancy,Instant until,String evidence) { }
    private static final String ELIGIBILITY_SQL = """
            SELECT e.status='ACTIVE' AND (e.hired_at IS NULL OR e.hired_at<=?::timestamptz)
                   AND (e.terminated_at IS NULL OR e.terminated_at>?::timestamptz)
                   AND u.status='ACTIVE' AND u.valid_from<=?::timestamptz
                   AND (u.valid_to IS NULL OR u.valid_to>?::timestamptz) AND p.status='ACTIVE',
                   e.terminated_at,u.valid_to,
                   pg_catalog.jsonb_build_object('employee',pg_catalog.jsonb_build_object('id',e.id,'status',e.status,
                       'hiredAt',e.hired_at,'terminatedAt',e.terminated_at),'unit',pg_catalog.to_jsonb(u),'position',pg_catalog.to_jsonb(p))::text
              FROM public.hidra_org_employee e JOIN public.hidra_org_unit u ON u.id=?
              JOIN public.hidra_org_position p ON p.id=? WHERE e.id=? LIMIT 2
            """;
    private static Instant earlier(Instant first,Instant second){return second!=null && second.isBefore(first)?second:first;}
    private Eligibility eligibility(String employee,Occupancy occupancy,Instant sample,Instant until) {
        var at=java.sql.Timestamp.from(sample);
        var rows=jdbc.query(ELIGIBILITY_SQL,(r,i) -> {
            if(!r.getBoolean(1) || r.wasNull())throw denied();
            return new Eligibility(occupancy,earlier(earlier(until,instant(r,2)),instant(r,3)),r.getString(4));
        },at,at,at,at,occupancy.unit(),occupancy.position(),employee);
        if(rows==null || rows.size()!=1)throw denied();return rows.get(0);
    }

    private record MutationPlan(EmployeeAppointmentCommand command,String newAssignmentId,
            EmployeeAppointmentResult.PinnedVersion before,EmployeeAppointmentResult.PinnedVersion after,
            EmployeeAppointmentResult.PinnedVersion successor,WritePolicy policy,BindingObservation destination,
            List<AppointmentObservation> peers,List<Eligibility> eligibility,Instant validUntil) { }

    /**
     * SELECT-only storage planning, never a write/result credential. Genuine
     * Party CURRENT eligibility, the executor permit, provisional DML, independent
     * final native proof/receipt and observed commit remain separate obligations.
     * Complete internal foreign-peer observations never escape this private plan.
     */
    public void prepareMutation(ProtectedEmployeeAppointmentRepositoryPort.Admission admission,EmployeeAppointmentCommand command) {
        StorageAdmission ticket=null;
        try {
            ticket=ticket(admission);requireCurrent(ticket);
            var use=referenceUse(ticket,command,ticket.sample);
            if(use==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)return;
            Instant sampled=ticket.sample.sampledAt();
            var policy=writePolicy(command.companyPartyId());
            AppointmentObservation source=null;
            if(sourceId(command)!=null){
                var rows=jdbc.query(APPOINTMENT_SNAPSHOT_SQL+" WHERE a.id=? LIMIT 2",
                        JdbcProtectedEmployeeAppointmentRepositoryAdapter::appointment,sourceId(command));
                if(rows==null || rows.size()!=1)throw denied();source=rows.get(0);
                if(!command.companyPartyId().equals(source.value().companyPartyId())
                        || !ticket.preview.employeeId().equals(source.value().snapshot().appointment().assignment().employeeId()))throw denied();
            }
            String targetUnit=command instanceof EmployeeAppointmentCommand.Create c?c.organizationUnitId()
                    :command instanceof EmployeeAppointmentCommand.Transfer c?c.destinationOrganizationUnitId()
                    :command instanceof EmployeeAppointmentCommand.Correct c?c.correctedOrganizationUnitId()
                    :source.value().snapshot().appointment().assignment().organizationUnitId();
            String targetPosition=command instanceof EmployeeAppointmentCommand.Create c?c.positionId()
                    :command instanceof EmployeeAppointmentCommand.Transfer c?c.destinationPositionId()
                    :command instanceof EmployeeAppointmentCommand.Correct c?c.correctedPositionId()
                    :source.value().snapshot().appointment().assignment().positionId();
            BindingObservation destination=null;
            OrganizationUnitCompanyBinding targetBinding;
            if(use==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT){
                var rows=jdbc.query(CURRENT_BINDING_SQL,(r,i) -> new BindingObservation(new OrganizationUnitCompanyBinding(
                        exact(r.getString(1)),r.getLong(2),exact(r.getString(3)),exact(r.getString(4)),instant(r,5)),r.getString(6)),targetUnit,command.companyPartyId());
                if(rows==null || rows.size()!=1)throw denied();destination=rows.get(0);targetBinding=destination.value();
            } else targetBinding=source.value().snapshot().binding();
            long wantedBinding=command instanceof EmployeeAppointmentCommand.Create c?c.expectedBindingRevision()
                    :command instanceof EmployeeAppointmentCommand.Transfer c?c.expectedDestinationBindingRevision()
                    :command instanceof EmployeeAppointmentCommand.Correct c?c.expectedDestinationBindingRevision():targetBinding.revision();
            long wantedPolicy=command instanceof EmployeeAppointmentCommand.Create c?c.expectedPolicyRevision()
                    :command instanceof EmployeeAppointmentCommand.Transfer c?c.expectedDestinationPolicyRevision()
                    :command instanceof EmployeeAppointmentCommand.Correct c?c.expectedDestinationPolicyRevision():policy.revision();
            if(wantedBinding!=targetBinding.revision() || wantedPolicy!=policy.revision())throw denied();

            var occupancies=new TreeSet<Occupancy>(Comparator.comparing(Occupancy::unit).thenComparing(Occupancy::position));
            occupancies.add(new Occupancy(targetUnit,targetPosition));
            EmployeeAppointmentResult.PinnedVersion before=source==null?null:source.value();
            if(before!=null){var old=before.snapshot().appointment().assignment();occupancies.add(new Occupancy(old.organizationUnitId(),old.positionId()));}
            var first=occupancies.first();var last=occupancies.last();
            var peers=jdbc.query(APPOINTMENT_SNAPSHOT_SQL+"""
                     WHERE a.id=?::varchar OR (a.status='ACTIVE' AND (a.employee_id=?
                        OR (a.organization_unit_id=? AND a.position_id=?) OR (a.organization_unit_id=? AND a.position_id=?)))
                     ORDER BY a.id COLLATE "C" LIMIT 1025
                    """,JdbcProtectedEmployeeAppointmentRepositoryAdapter::appointment,sourceId(command),ticket.preview.employeeId(),
                    first.unit(),first.position(),last.unit(),last.position());
            if(peers==null || peers.size()>1024 || before!=null && !peers.contains(source))throw denied();
            var facts=new ArrayList<Eligibility>();Instant until=ticket.sample.validUntil();
            for(var occupancy:occupancies){var fact=eligibility(ticket.preview.employeeId(),occupancy,sampled,ticket.sample.validUntil());facts.add(fact);until=earlier(until,fact.until());}
            String freshId=ticket.plan==null?java.util.UUID.randomUUID().toString():ticket.plan.newAssignmentId();
            EmployeeAppointmentResult.PinnedVersion after,successor=null;
            var ownership=new EmployeeAppointmentOwnershipPolicy();
            var observed=peers.stream().map(p -> p.value().snapshot().appointment()).toList();
            if(command instanceof EmployeeAppointmentCommand.Create c){
                var value=new EmployeeAssignment(freshId,c.employeeId(),targetUnit,targetPosition,c.assignmentType(),c.validFrom(),c.validTo(),
                        c.validTo()!=null && !c.validTo().isAfter(sampled)?AssignmentStatus.ENDED:AssignmentStatus.ACTIVE,sampled,sampled);
                var bound=new CompanyBoundEmployeeAppointment(new EmployeeAppointmentChange.VersionedAssignment(1,value),targetBinding);
                ownership.validateCreate(bound);after=new EmployeeAppointmentResult.PinnedVersion(bound,policy.id(),policy.revision());
                var proposed=new ArrayList<>(observed);proposed.add(bound.appointment());policy.value().validate(proposed);
            } else {
                var old=before.snapshot().appointment();EmployeeAppointmentChange change;
                if(command instanceof EmployeeAppointmentCommand.End c)change=EmployeeAppointmentChange.end(old,c.endAt(),sampled,c.reason());
                else if(command instanceof EmployeeAppointmentCommand.Transfer c)change=EmployeeAppointmentChange.transfer(old,freshId,targetUnit,targetPosition,
                        c.destinationType(),c.effectiveAt(),c.successorValidTo(),sampled,c.reason());
                else {
                    var c=(EmployeeAppointmentCommand.Correct)command;
                    var replacement=new EmployeeAssignment(old.assignment().id(),old.assignment().employeeId(),targetUnit,targetPosition,c.correctedType(),
                            c.correctedValidFrom(),c.correctedValidTo(),c.correctedStatus(),old.assignment().createdAt(),sampled);
                    change=EmployeeAppointmentChange.correct(old,replacement,sampled,c.reason());
                }
                var afterBinding=change.kind()==EmployeeAppointmentChange.Kind.TRANSFER?before.snapshot().binding():targetBinding;
                var boundAfter=new CompanyBoundEmployeeAppointment(change.after(),afterBinding);
                CompanyBoundEmployeeAppointment boundSuccessor=change.successor()==null?null:new CompanyBoundEmployeeAppointment(change.successor(),targetBinding);
                ownership.validateChange(change,before.snapshot(),boundAfter,boundSuccessor);policy.value().apply(observed,change);
                after=new EmployeeAppointmentResult.PinnedVersion(boundAfter,policy.id(),policy.revision());
                if(boundSuccessor!=null)successor=new EmployeeAppointmentResult.PinnedVersion(boundSuccessor,policy.id(),policy.revision());
            }
            if((command instanceof EmployeeAppointmentCommand.Create || command instanceof EmployeeAppointmentCommand.Transfer)
                    && !Boolean.TRUE.equals(jdbc.queryForObject("SELECT NOT EXISTS(SELECT 1 FROM public.hidra_org_employee_assignment WHERE id=?)",Boolean.class,freshId)))throw denied();
            var planned=new MutationPlan(command,freshId,before,after,successor,policy,destination,List.copyOf(peers),List.copyOf(facts),until);
            if(ticket.plan!=null && !ticket.plan.equals(planned))throw denied();
            ticket.plan=planned;requireCurrent(ticket);
        }catch(RuntimeException failure){poison(ticket==null?admission:ticket);throw denied();}
    }

    private record Observed(String employeeId, String unitId, String companyId) { }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected appointment admission unavailable");
    }
}
