/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Generic organization employee reference value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Generic reference to a future organization employee.
 *
 * <p>Business role: allows an identity user to reference an operational employee without
 * making identity own the employee aggregate or organization structure.</p>
 *
 * <p>Architecture role: immutable boundary value object that intentionally stores only a
 * generic reference value and does not import the organization module.</p>
 *
 * <p>Validation responsibility: rejects null or blank reference values and trims accepted
 * values.</p>
 *
 * <p>Usage: create with {@link #of(String)} when a user must be linked to an external or
 * future organization employee reference.</p>
 */
public record EmployeeReference(String value) implements ValueObject {

    public EmployeeReference {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("EmployeeReference must not be blank.");
        }

        value = value.trim();
    }

    public static EmployeeReference of(String value) {
        return new EmployeeReference(value);
    }
}
