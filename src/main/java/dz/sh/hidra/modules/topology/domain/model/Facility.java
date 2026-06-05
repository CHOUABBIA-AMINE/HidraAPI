/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Facility
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Facility aggregate representing a physical topology facility.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Represents a physical facility connected to the hydrocarbon network.
 *
 * <p>Business role:
 * A facility is a physical topology asset such as a compression station, pumping station, terminal,
 * processing plant, production field interface, gathering center, storage facility, receipt facility,
 * or delivery facility.
 *
 * <p>Architecture role:
 * This is a pure topology domain aggregate. It is not an organization unit and does not own people,
 * reporting lines, identity users, telemetry values, process calculations, inventory, or production
 * allocation.
 *
 * <p>Validation:
 * Identifier, code, name, facility type, product type, status, creation instant, and update instant
 * are mandatory.
 */
public final class Facility implements AggregateRoot<FacilityId> {

    private final FacilityId id;
    private final TopologyCode code;
    private final TopologyName name;
    private final FacilityType facilityType;
    private final ProductType productType;
    private final TopologyStatus status;
    private final GeoCoordinate coordinate;
    private final OrganizationUnitReference organizationUnitReference;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Facility(
            FacilityId id,
            TopologyCode code,
            TopologyName name,
            FacilityType facilityType,
            ProductType productType,
            TopologyStatus status,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Facility id must not be null.");
        this.code = Objects.requireNonNull(code, "Facility code must not be null.");
        this.name = Objects.requireNonNull(name, "Facility name must not be null.");
        this.facilityType = Objects.requireNonNull(facilityType, "Facility type must not be null.");
        this.productType = Objects.requireNonNull(productType, "Facility product type must not be null.");
        this.status = Objects.requireNonNull(status, "Facility status must not be null.");
        this.coordinate = coordinate;
        this.organizationUnitReference = organizationUnitReference;
        this.createdAt = requireInstant(createdAt, "Facility createdAt");
        this.updatedAt = requireInstant(updatedAt, "Facility updatedAt");

        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "Facility");
    }

    public static Facility create(
            TopologyCode code,
            TopologyName name,
            FacilityType facilityType,
            ProductType productType,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference) {

        Instant now = Instant.now();
        return new Facility(
                FacilityId.newId(),
                code,
                name,
                facilityType,
                productType,
                TopologyStatus.PLANNED,
                coordinate,
                organizationUnitReference,
                now,
                now);
    }

    public static Facility restore(
            FacilityId id,
            TopologyCode code,
            TopologyName name,
            FacilityType facilityType,
            ProductType productType,
            TopologyStatus status,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference,
            Instant createdAt,
            Instant updatedAt) {

        return new Facility(
                id,
                code,
                name,
                facilityType,
                productType,
                status,
                coordinate,
                organizationUnitReference,
                createdAt,
                updatedAt);
    }

    @Override
    public FacilityId id() {
        return id;
    }

    public TopologyCode code() {
        return code;
    }

    public TopologyName name() {
        return name;
    }

    public FacilityType facilityType() {
        return facilityType;
    }

    public ProductType productType() {
        return productType;
    }

    public TopologyStatus status() {
        return status;
    }

    public GeoCoordinate coordinate() {
        return coordinate;
    }

    public OrganizationUnitReference organizationUnitReference() {
        return organizationUnitReference;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }


    /**
     * Activates this topology asset.
     *
     * @return active topology asset
     */
    public Facility activate() {
        return withStatus(TopologyStatus.ACTIVE);
    }

    /**
     * Deactivates this topology asset.
     *
     * @return inactive topology asset
     */
    public Facility deactivate() {
        return withStatus(TopologyStatus.INACTIVE);
    }

    /**
     * Marks this topology asset as under maintenance.
     *
     * @return topology asset under maintenance
     */
    public Facility markUnderMaintenance() {
        return withStatus(TopologyStatus.UNDER_MAINTENANCE);
    }

    /**
     * Retires this topology asset.
     *
     * @return retired topology asset
     */
    public Facility retire() {
        return withStatus(TopologyStatus.RETIRED);
    }

    /**
     * Decommissions this topology asset.
     *
     * @return decommissioned topology asset
     */
    public Facility decommission() {
        return withStatus(TopologyStatus.DECOMMISSIONED);
    }

    private Facility withStatus(TopologyStatus newStatus) {
        return new Facility(
                id,
                code,
                name,
                facilityType,
                productType,
                Objects.requireNonNull(newStatus, "Facility status must not be null."),
                coordinate,
                organizationUnitReference,
                createdAt,
                Instant.now());
    }

    private static String normalizeOptionalText(String value, int maxLength, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > maxLength) {
            throw new BusinessRuleViolationException(fieldName + " length must not exceed " + maxLength + " characters.");
        }

        return normalized;
    }

    private static Instant requireInstant(Instant value, String fieldName) {
        return Objects.requireNonNull(value, fieldName + " must not be null.");
    }

    private static void ensureUpdatedAtIsValid(Instant createdAt, Instant updatedAt, String modelName) {
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException(modelName + " updatedAt must not be before createdAt.");
        }
    }

}
