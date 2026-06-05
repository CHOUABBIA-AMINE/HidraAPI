/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateFacilityCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a physical facility.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to create a physical facility.
 *
 * <p>Business role:
 * This command creates a physical topology facility such as a compression station, terminal,
 * processing plant, production field interface, gathering center, storage facility, receipt facility,
 * or delivery facility.
 *
 * <p>Architecture role:
 * This is an application command. It may carry a neutral organization unit reference but must not
 * import organization implementation classes.
 *
 * <p>Validation:
 * Code, name, facility type, and product type are mandatory. Coordinate and organization reference
 * are optional.
 *
 * @param code business code
 * @param name display name
 * @param facilityType facility type
 * @param productType product type
 * @param coordinate optional coordinate
 * @param organizationUnitReference optional neutral organization unit reference
 */
public record CreateFacilityCommand(
        TopologyCode code,
        TopologyName name,
        FacilityType facilityType,
        ProductType productType,
        GeoCoordinate coordinate,
        OrganizationUnitReference organizationUnitReference) implements Command {

    public CreateFacilityCommand {
        Objects.requireNonNull(code, "Facility code must not be null.");
        Objects.requireNonNull(name, "Facility name must not be null.");
        Objects.requireNonNull(facilityType, "Facility type must not be null.");
        Objects.requireNonNull(productType, "Facility product type must not be null.");
    }
}
