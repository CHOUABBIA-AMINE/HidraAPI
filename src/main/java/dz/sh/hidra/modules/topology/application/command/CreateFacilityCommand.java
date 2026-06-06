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
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to create a physical facility.
 */
public record CreateFacilityCommand(
        TopologyCode code,
        TopologyName name,
        FacilityTypeReference facilityType,
        ProductTypeReference productType,
        GeoCoordinate coordinate,
        OrganizationUnitReference organizationUnitReference) implements Command {

    public CreateFacilityCommand {
        Objects.requireNonNull(code, "Facility code must not be null.");
        Objects.requireNonNull(name, "Facility name must not be null.");
        Objects.requireNonNull(facilityType, "Facility type reference must not be null.");
        Objects.requireNonNull(productType, "Facility product type reference must not be null.");
    }
}
