/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductTypeCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Product-type specific domain view over topology multilingual catalog entries.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Product-type specific domain view over a multilingual topology catalog entry.
 *
 * <p>Business role:
 * Represents hydrocarbon product categories such as gas, crude oil, condensate, LPG, refined
 * product, multiphase service, and unknown product classification.
 *
 * <p>Architecture role:
 * This class does not reintroduce a Java enum. It wraps the generic {@link TopologyTypeCatalog}
 * model and guarantees that the entry belongs to the PRODUCT_TYPE catalog family. Localized labels
 * remain catalog data, not hardcoded reference-object behavior.
 *
 * <p>Validation:
 * The wrapped catalog entry must have catalog name PRODUCT_TYPE. Future product types can be added
 * as catalog rows without changing this class.
 */
public final class ProductTypeCatalog implements AggregateRoot<String> {

    public static final String CATALOG_NAME = "PRODUCT_TYPE";

    private static final List<String> REQUIRED_SEED_CODES = List.of(
            "GAS",
            "CRUDE_OIL",
            "CONDENSATE",
            "LPG",
            "REFINED_PRODUCT",
            "MULTIPHASE",
            "UNKNOWN");

    private final TopologyTypeCatalog catalogEntry;

    private ProductTypeCatalog(TopologyTypeCatalog catalogEntry) {
        this.catalogEntry = Objects.requireNonNull(catalogEntry, "Product type catalog entry must not be null.");
        if (!CATALOG_NAME.equals(catalogEntry.catalogName().toUpperCase(Locale.ROOT))) {
            throw new BusinessRuleViolationException("Product type catalog entry must belong to PRODUCT_TYPE catalog.");
        }
    }

    public static ProductTypeCatalog from(TopologyTypeCatalog catalogEntry) {
        return new ProductTypeCatalog(catalogEntry);
    }

    public static List<String> requiredSeedCodes() {
        return REQUIRED_SEED_CODES;
    }

    @Override
    public String id() {
        return catalogEntry.id();
    }

    public String catalogName() {
        return catalogEntry.catalogName();
    }

    public TopologyCode code() {
        return catalogEntry.code();
    }

    public TopologyStatus status() {
        return catalogEntry.status();
    }

    public int sortOrder() {
        return catalogEntry.sortOrder();
    }

    public boolean systemDefined() {
        return catalogEntry.systemDefined();
    }

    public List<TopologyTypeTranslation> translations() {
        return catalogEntry.translations();
    }

    public Instant createdAt() {
        return catalogEntry.createdAt();
    }

    public Instant updatedAt() {
        return catalogEntry.updatedAt();
    }

    public boolean isActive() {
        return catalogEntry.isActive();
    }

    public ProductTypeReference toReference() {
        return ProductTypeReference.of(id(), code());
    }

    public TopologyTypeCatalog asTopologyTypeCatalog() {
        return catalogEntry;
    }
}
