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
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
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
 * Facility and product classifications are catalog references so multilingual labels and configurable
 * taxonomies are resolved outside the asset aggregate.
 *
 * <p>Validation:
 * Identifier, code, name, facility type reference, product type reference, status, creation instant,
 * and update instant are mandatory.
 */
public final class Facility implements AggregateRoot<FacilityId> {

    private final FacilityId id;
    private final TopologyCode code;
    private final TopologyName name;
    private final FacilityTypeReference facilityType;
    private final ProductTypeReference productType;
    private final TopologyStatus status;
    private final GeoCoordinate coordinate;
    private final OrganizationUnitReference organizationUnitReference;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Facility(
            FacilityId id,
            TopologyCode code,
            TopologyName name,
            FacilityTypeReference facilityType,
            ProductTypeReference productType,
            TopologyStatus status,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Facility id must not be null.");
        this.code = Objects.requireNonNull(code, "Facility code must not be null.");
        this.name = Objects.requireNonNull(name, "Facility name must not be null.");
        this.facilityType = Objects.requireNonNull(facilityType, "Facility type reference must not be null.");
        this.productType = Objects.requireNonNull(productType, "Facility product type reference must not be null.");
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
            FacilityTypeReference facilityType,
            ProductTypeReference productType,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference) {

        Instant now = Instant.now();
        return new Facility(FacilityId.newId(), code, name, facilityType, productType, TopologyStatus.PLANNED, coordinate, organizationUnitReference, now, now);
    }

    @Deprecated(forRemoval = true)
    public static Facility create(
            TopologyCode code,
            TopologyName name,
            FacilityType facilityType,
            ProductType productType,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference) {

        return create(code, name, FacilityTypeReference.from(facilityType), ProductTypeReference.from(productType), coordinate, organizationUnitReference);
    }

    public static Facility restore(
            FacilityId id,
            TopologyCode code,
            TopologyName name,
            FacilityTypeReference facilityType,
            ProductTypeReference productType,
            TopologyStatus status,
            GeoCoordinate coordinate,
            OrganizationUnitReference organizationUnitReference,
            Instant createdAt,
            Instant updatedAt) {

        return new Facility(id, code, name, facilityType, productType, status, coordinate, organizationUnitReference, createdAt, updatedAt);
    }

    @Deprecated(forRemoval = true)
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

        return restore(id, code, name, FacilityTypeReference.from(facilityType), ProductTypeReference.from(productType), status, coordinate, organizationUnitReference, createdAt, updatedAt);
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

    public FacilityTypeReference facilityType() {
        return facilityType;
    }

    public ProductTypeReference productType() {
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

    public Facility activate() {
        return withStatus(TopologyStatus.ACTIVE);
    }

    public Facility deactivate() {
        return withStatus(TopologyStatus.INACTIVE);
    }

    public Facility markUnderMaintenance() {
        return withStatus(TopologyStatus.UNDER_MAINTENANCE);
    }

    public Facility retire() {
        return withStatus(TopologyStatus.RETIRED);
    }

    public Facility decommission() {
        return withStatus(TopologyStatus.DECOMMISSIONED);
    }

    private Facility withStatus(TopologyStatus newStatus) {
        return new Facility(id, code, name, facilityType, productType, Objects.requireNonNull(newStatus, "Facility status must not be null."), coordinate, organizationUnitReference, createdAt, Instant.now());
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
