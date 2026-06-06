/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Deprecated compatibility constant class for the product type catalog.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import java.util.Arrays;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Deprecated compatibility constant class for the product type controlled vocabulary.
 *
 * <p>Business role:
 * Preserves legacy source compatibility while this business taxonomy is governed by catalog
 * entities with multilingual labels.
 *
 * <p>Architecture role:
 * This is intentionally not a Java enum. New code should use ProductTypeReference instead.
 *
 * <p>Validation:
 * Only the predefined compatibility constants are accepted through valueOf(String).
 */
@Deprecated(forRemoval = false)
public final class ProductType implements ValueObject {

    public static final ProductType GAS = new ProductType("GAS");
    public static final ProductType CRUDE_OIL = new ProductType("CRUDE_OIL");
    public static final ProductType CONDENSATE = new ProductType("CONDENSATE");
    public static final ProductType LPG = new ProductType("LPG");
    public static final ProductType REFINED_PRODUCT = new ProductType("REFINED_PRODUCT");
    public static final ProductType MULTIPHASE = new ProductType("MULTIPHASE");
    public static final ProductType UNKNOWN = new ProductType("UNKNOWN");

    private static final ProductType[] VALUES = {
            GAS,
            CRUDE_OIL,
            CONDENSATE,
            LPG,
            REFINED_PRODUCT,
            MULTIPHASE,
            UNKNOWN
    };

    private final String name;

    private ProductType(String name) {
        this.name = requireName(name);
    }

    public static ProductType valueOf(String name) {
        String normalizedName = requireName(name);
        return Arrays.stream(VALUES)
                .filter(value -> value.name.equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> new InvalidValueObjectException("ProductType is not supported: " + name));
    }

    public static ProductType[] values() {
        return VALUES.clone();
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProductType that)) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private static String requireName(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("ProductType name must not be null or blank.");
        }
        return value.trim().toUpperCase();
    }
}
