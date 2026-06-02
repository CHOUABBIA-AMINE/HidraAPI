/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnit
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Organization unit aggregate supporting hierarchy, unit type, and optional operational scope.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;

/**
 * Represents an organization unit in the Hidra operational organization structure.
 *
 * <p>Business role:
 * An organization unit can represent a company, division, direction, department, region, area,
 * district, station, team, project team, or another responsibility structure. A station can be
 * represented as an organization unit when it describes the people, responsibility, and reporting
 * structure attached to a physical station.
 *
 * <p>Architecture role:
 * This is an organization domain aggregate root. It does not represent a topology asset. Physical
 * station assets belong to the future topology module and can only be referenced through
 * <code>OperationalScopeReference</code>.
 *
 * <p>Validation:
 * The aggregate requires identifier, code, name, status, type, creation instant, and update
 * instant. It rejects self-parenting, prevents invalid station scope assignment, and controls
 * lifecycle transitions through behavior methods.
 *
 * <p>Usage:
 * Use this aggregate to model operational responsibility units and hierarchy. Do not use it to
 * manage identity users, identity roles, permissions, physical station equipment, or topology
 * assets.
 */
public final class OrganizationUnit implements AggregateRoot<OrganizationUnitId> {

    /**
     * Stable identifier of the organization unit.
     */
    private final OrganizationUnitId id;

    /**
     * Unique business code of the organization unit.
     */
    private final OrganizationUnitCode code;

    /**
     * Human-readable name of the organization unit.
     */
    private final OrganizationUnitName name;

    /**
     * Lifecycle status of the organization unit.
     */
    private final OrganizationUnitStatus status;

    /**
     * Business type of the organization unit, such as REGION, STATION, or TEAM.
     */
    private final OrganizationUnitType type;

    /**
     * Optional parent organization unit identifier.
     */
    private final OrganizationUnitId parentId;

    /**
     * Optional neutral operational scope reference for station or asset-linked responsibility.
     */
    private final OperationalScopeReference operationalScopeReference;

    /**
     * Instant when the organization unit was created.
     */
    private final Instant createdAt;

    /**
     * Instant when the organization unit was last updated.
     */
    private final Instant updatedAt;

    private OrganizationUnit(
            OrganizationUnitId id,
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitStatus status,
            OrganizationUnitType type,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Organization unit id must not be null.");
        this.code = Objects.requireNonNull(code, "Organization unit code must not be null.");
        this.name = Objects.requireNonNull(name, "Organization unit name must not be null.");
        this.status = Objects.requireNonNull(status, "Organization unit status must not be null.");
        this.type = Objects.requireNonNull(type, "Organization unit type must not be null.");
        this.parentId = parentId;
        this.operationalScopeReference = operationalScopeReference;
        this.createdAt = Objects.requireNonNull(createdAt, "Organization unit creation instant must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Organization unit update instant must not be null.");

        ensureUpdatedAtIsValid();
        ensureNotSelfParent();
        ensureOperationalScopeMatchesUnitType();
    }

    /**
     * Creates a new active organization unit.
     *
     * @param code business code
     * @param name display name
     * @param type organization unit type
     * @param parentId optional parent organization unit identifier
     * @param operationalScopeReference optional neutral operational scope reference
     * @return created organization unit in active status
     */
    public static OrganizationUnit create(
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitType type,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference) {

        Instant now = Instant.now();
        return new OrganizationUnit(
                OrganizationUnitId.newId(),
                code,
                name,
                OrganizationUnitStatus.ACTIVE,
                type,
                parentId,
                operationalScopeReference,
                now,
                now);
    }

    /**
     * Creates a station organization unit linked to a station-like operational scope reference.
     *
     * @param code station organization unit code
     * @param name station organization unit name
     * @param parentId optional parent organization unit identifier
     * @param operationalScopeReference required station-like operational scope reference
     * @return created station organization unit
     */
    public static OrganizationUnit createStation(
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference) {

        if (operationalScopeReference == null) {
            throw new BusinessRuleViolationException("Station organization unit requires an operational scope reference.");
        }

        return create(code, name, OrganizationUnitType.STATION, parentId, operationalScopeReference);
    }

    /**
     * Rehydrates an existing organization unit from persistence without importing persistence code.
     *
     * @param id organization unit identifier
     * @param code business code
     * @param name display name
     * @param status lifecycle status
     * @param type organization unit type
     * @param parentId optional parent identifier
     * @param operationalScopeReference optional operational scope reference
     * @param createdAt creation instant
     * @param updatedAt update instant
     * @return restored organization unit
     */
    public static OrganizationUnit restore(
            OrganizationUnitId id,
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitStatus status,
            OrganizationUnitType type,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference,
            Instant createdAt,
            Instant updatedAt) {

        return new OrganizationUnit(id, code, name, status, type, parentId, operationalScopeReference, createdAt, updatedAt);
    }

    @Override
    public OrganizationUnitId id() {
        return id;
    }

    /**
     * Returns the business code.
     *
     * @return organization unit code
     */
    public OrganizationUnitCode code() {
        return code;
    }

    /**
     * Returns the display name.
     *
     * @return organization unit name
     */
    public OrganizationUnitName name() {
        return name;
    }

    /**
     * Returns the lifecycle status.
     *
     * @return organization unit status
     */
    public OrganizationUnitStatus status() {
        return status;
    }

    /**
     * Returns the organization unit type.
     *
     * @return organization unit type
     */
    public OrganizationUnitType type() {
        return type;
    }

    /**
     * Returns the optional parent organization unit identifier.
     *
     * @return optional parent identifier
     */
    public Optional<OrganizationUnitId> parentId() {
        return Optional.ofNullable(parentId);
    }

    /**
     * Returns the optional operational scope reference.
     *
     * @return optional operational scope reference
     */
    public Optional<OperationalScopeReference> operationalScopeReference() {
        return Optional.ofNullable(operationalScopeReference);
    }

    /**
     * Returns the creation instant.
     *
     * @return creation instant
     */
    public Instant createdAt() {
        return createdAt;
    }

    /**
     * Returns the last update instant.
     *
     * @return update instant
     */
    public Instant updatedAt() {
        return updatedAt;
    }

    /**
     * Indicates whether this organization unit represents a station as an operational unit.
     *
     * @return true when the unit type is STATION
     */
    public boolean isStationOrganizationUnit() {
        return type.isStationOrganizationUnit();
    }

    /**
     * Renames the organization unit.
     *
     * @param newName new display name
     * @return updated organization unit
     */
    public OrganizationUnit rename(OrganizationUnitName newName) {
        return new OrganizationUnit(
                id,
                code,
                newName,
                status,
                type,
                parentId,
                operationalScopeReference,
                createdAt,
                Instant.now());
    }

    /**
     * Changes the parent organization unit.
     *
     * @param newParentId new optional parent identifier
     * @return updated organization unit
     */
    public OrganizationUnit changeParent(OrganizationUnitId newParentId) {
        return new OrganizationUnit(
                id,
                code,
                name,
                status,
                type,
                newParentId,
                operationalScopeReference,
                createdAt,
                Instant.now());
    }

    /**
     * Clears the parent organization unit.
     *
     * @return updated root organization unit
     */
    public OrganizationUnit clearParent() {
        return changeParent(null);
    }

    /**
     * Assigns or changes the operational scope reference.
     *
     * @param newOperationalScopeReference new optional operational scope reference
     * @return updated organization unit
     */
    public OrganizationUnit changeOperationalScope(OperationalScopeReference newOperationalScopeReference) {
        return new OrganizationUnit(
                id,
                code,
                name,
                status,
                type,
                parentId,
                newOperationalScopeReference,
                createdAt,
                Instant.now());
    }

    /**
     * Activates the organization unit so it can receive employee assignments.
     *
     * @return active organization unit
     */
    public OrganizationUnit activate() {
        if (status == OrganizationUnitStatus.ACTIVE) {
            return this;
        }

        return changeStatus(OrganizationUnitStatus.ACTIVE);
    }

    /**
     * Disables the organization unit so it cannot receive new employee assignments.
     *
     * @return disabled organization unit
     */
    public OrganizationUnit disable() {
        if (status == OrganizationUnitStatus.DISABLED) {
            return this;
        }

        return changeStatus(OrganizationUnitStatus.DISABLED);
    }

    /**
     * Archives the organization unit for historical traceability.
     *
     * @return archived organization unit
     */
    public OrganizationUnit archive() {
        if (status == OrganizationUnitStatus.ARCHIVED) {
            return this;
        }

        return changeStatus(OrganizationUnitStatus.ARCHIVED);
    }

    /**
     * Ensures the organization unit can receive a new employee assignment.
     */
    public void ensureCanReceiveEmployeeAssignment() {
        if (!status.allowsEmployeeAssignment()) {
            throw new BusinessRuleViolationException("Organization unit cannot receive employee assignments in status " + status + ".");
        }
    }

    private OrganizationUnit changeStatus(OrganizationUnitStatus newStatus) {
        return new OrganizationUnit(
                id,
                code,
                name,
                newStatus,
                type,
                parentId,
                operationalScopeReference,
                createdAt,
                Instant.now());
    }

    private void ensureUpdatedAtIsValid() {
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Organization unit updatedAt must not be before createdAt.");
        }
    }

    private void ensureNotSelfParent() {
        if (parentId != null && id.equals(parentId)) {
            throw new BusinessRuleViolationException("Organization unit cannot be its own parent.");
        }
    }

    private void ensureOperationalScopeMatchesUnitType() {
        if (type == OrganizationUnitType.STATION
                && operationalScopeReference != null
                && !operationalScopeReference.isStationScope()) {
            throw new BusinessRuleViolationException("Station organization unit must reference a station-like operational scope.");
        }
    }
}
