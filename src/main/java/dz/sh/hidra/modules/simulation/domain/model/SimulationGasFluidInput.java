/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationGasFluidInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Pins declared gas composition and property-method references without calculating properties.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

/** Complete declared mole-fraction basis; source declarations do not verify physical truth. */
public record SimulationGasFluidInput(
        String id,
        SimulationInputSourceVersion sourceVersion,
        String productReference,
        String propertyMethodReference,
        String propertyMethodRevisionId,
        String propertyMethodEvidenceReference,
        List<Component> components
) {
    public SimulationGasFluidInput {
        id = required(id, "Fluid identity");
        productReference = required(productReference, "Product reference");
        propertyMethodReference = required(propertyMethodReference, "Property method reference");
        propertyMethodRevisionId = required(propertyMethodRevisionId, "Property method revision");
        propertyMethodEvidenceReference = required(propertyMethodEvidenceReference, "Property method evidence");
        if (sourceVersion == null || sourceVersion.kind() != SourceKind.FLUID_MODEL) {
            throw new InvalidSimulationValueException("Fluid input requires a FLUID_MODEL source version.");
        }
        if (components == null || components.isEmpty() || components.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException("Fluid components must be nonempty without null entries.");
        }
        components = List.copyOf(components);
        var identities = new HashSet<String>();
        var sum = BigDecimal.ZERO;
        for (var component : components) {
            if (!identities.add(component.componentReference())) {
                throw new InvalidSimulationValueException("Duplicate gas component reference.");
            }
            sum = sum.add(component.moleFraction());
        }
        if (sum.compareTo(BigDecimal.ONE) != 0) {
            throw new InvalidSimulationValueException("Declared mole fractions must sum exactly to one.");
        }
    }

    public record Component(String componentReference, BigDecimal moleFraction) {
        public Component {
            componentReference = required(componentReference, "Component reference");
            if (moleFraction == null || moleFraction.signum() <= 0 || moleFraction.compareTo(BigDecimal.ONE) > 0) {
                throw new InvalidSimulationValueException("Mole fraction must be greater than zero and at most one.");
            }
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }
}
