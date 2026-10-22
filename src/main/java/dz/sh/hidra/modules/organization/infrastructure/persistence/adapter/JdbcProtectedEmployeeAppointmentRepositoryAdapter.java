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

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
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

    private record Observed(String employeeId, String unitId, String companyId) { }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected appointment admission unavailable");
    }
}
