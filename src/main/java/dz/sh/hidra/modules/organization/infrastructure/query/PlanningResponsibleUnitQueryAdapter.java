/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningResponsibleUnitQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.query
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.query;
import dz.sh.hidra.modules.organization.application.contract.planning.PlanningResponsibleUnitContract;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public final class PlanningResponsibleUnitQueryAdapter implements PlanningResponsibleUnitContract {
    private final WorkflowOrganizationContract units;
    public PlanningResponsibleUnitQueryAdapter(WorkflowOrganizationContract units) {this.units=Objects.requireNonNull(units);}
    @Override public Optional<Unit> availableUnit(String id, Instant at) {
        return units.availableUnit(id,at).map(unit -> new Unit(unit.id(),unit.name()));
    }
}
