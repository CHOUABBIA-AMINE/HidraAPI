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
 * @Description : Organization unit aggregate supporting hierarchy, catalog unit type, and optional operational scope.
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
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

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
 * station assets belong to topology and can only be referenced through neutral scope references.
 *
 * <p>Validation:
 * The aggregate requires identifier, code, name, status, catalog type reference, creation instant,
 * and update instant. It rejects self-parenting, prevents invalid station scope assignment, and
 * controls lifecycle transitions through behavior methods.
 */
public final class OrganizationUnit implements AggregateRoot<OrganizationUnitId> {

    private final OrganizationUnitId id;
    private final OrganizationUnitCode code;
    private final OrganizationUnitName name;
    private final OrganizationUnitStatus status;
    private final OrganizationUnitTypeReference type;
    private final OrganizationUnitId parentId;
    private final OperationalScopeReference operationalScopeReference;
    private final Instant createdAt;
    private final Instant updatedAt;

    private OrganizationUnit(
            OrganizationUnitId id,
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitStatus status,
            OrganizationUnitTypeReference type,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Organization unit id must not be null.");
        this.code = Objects.requireNonNull(code, "Organization unit code must not be null.");
        this.name = Objects.requireNonNull(name, "Organization unit name must not be null.");
        this.status = Objects.requireNonNull(status, "Organization unit status must not be null.");
        this.type = Objects.requireNonNull(type, "Organization unit type reference must not be null.");
        this.parentId = parentId;
        this.operationalScopeReference = operationalScopeReference;
        this.createdAt = Objects.requireNonNull(createdAt, "Organization unit creation instant must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Organization unit update instant must not be null.");

        ensureUpdatedAtIsValid();
        ensureNotSelfParent();
        ensureOperationalScopeMatchesUnitType();
    }

    public static OrganizationUnit create(
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitTypeReference type,
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
     * @deprecated use {@link #create(OrganizationUnitCode, OrganizationUnitName, OrganizationUnitTypeReference, OrganizationUnitId, OperationalScopeReference)}
     */
    @Deprecated(forRemoval = false)
    public static OrganizationUnit create(
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitType type,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference) {

        return create(code, name, OrganizationUnitTypeReference.from(type), parentId, operationalScopeReference);
    }

    public static OrganizationUnit createStation(
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference) {

        if (operationalScopeReference == null) {
            throw new BusinessRuleViolationException("Station organization unit requires an operational scope reference.");
        }

        return create(code, name, OrganizationUnitTypeReference.STATION, parentId, operationalScopeReference);
    }

    public static OrganizationUnit restore(
            OrganizationUnitId id,
            OrganizationUnitCode code,
            OrganizationUnitName name,
            OrganizationUnitStatus status,
            OrganizationUnitTypeReference type,
            OrganizationUnitId parentId,
            OperationalScopeReference operationalScopeReference,
            Instant createdAt,
            Instant updatedAt) {

        return new OrganizationUnit(id, code, name, status, type, parentId, operationalScopeReference, createdAt, updatedAt);
    }

    /**
     * @deprecated use {@link #restore(OrganizationUnitId, OrganizationUnitCode, OrganizationUnitName, OrganizationUnitStatus, OrganizationUnitTypeReference, OrganizationUnitId, OperationalScopeReference, Instant, Instant)}
     */
    @Deprecated(forRemoval = false)
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

        return restore(id, code, name, status, OrganizationUnitTypeReference.from(type), parentId, operationalScopeReference, createdAt, updatedAt);
    }

    @Override
    public OrganizationUnitId id() {
        return id;
    }

    public OrganizationUnitCode code() {
        return code;
    }

    public OrganizationUnitName name() {
        return name;
    }

    public OrganizationUnitStatus status() {
        return status;
    }

    public OrganizationUnitTypeReference type() {
        return type;
    }

    public Optional<OrganizationUnitId> parentId() {
        return Optional.ofNullable(parentId);
    }

    public Optional<OperationalScopeReference> operationalScopeReference() {
        return Optional.ofNullable(operationalScopeReference);
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public boolean isStationOrganizationUnit() {
        return type.isStationOrganizationUnit();
    }

    public OrganizationUnit rename(OrganizationUnitName newName) {
        return new OrganizationUnit(id, code, newName, status, type, parentId, operationalScopeReference, createdAt, Instant.now());
    }

    public OrganizationUnit changeParent(OrganizationUnitId newParentId) {
        return new OrganizationUnit(id, code, name, status, type, newParentId, operationalScopeReference, createdAt, Instant.now());
    }

    public OrganizationUnit clearParent() {
        return changeParent(null);
    }

    public OrganizationUnit changeOperationalScope(OperationalScopeReference newOperationalScopeReference) {
        return new OrganizationUnit(id, code, name, status, type, parentId, newOperationalScopeReference, createdAt, Instant.now());
    }

    public OrganizationUnit activate() {
        if (status == OrganizationUnitStatus.ACTIVE) {
            return this;
        }
        return changeStatus(OrganizationUnitStatus.ACTIVE);
    }

    public OrganizationUnit disable() {
        if (status == OrganizationUnitStatus.DISABLED) {
            return this;
        }
        return changeStatus(OrganizationUnitStatus.DISABLED);
    }

    public OrganizationUnit archive() {
        if (status == OrganizationUnitStatus.ARCHIVED) {
            return this;
        }
        return changeStatus(OrganizationUnitStatus.ARCHIVED);
    }

    public void ensureCanReceiveEmployeeAssignment() {
        if (!status.allowsEmployeeAssignment()) {
            throw new BusinessRuleViolationException("Organization unit cannot receive employee assignments in status " + status + ".");
        }
    }

    private OrganizationUnit changeStatus(OrganizationUnitStatus newStatus) {
        return new OrganizationUnit(id, code, name, newStatus, type, parentId, operationalScopeReference, createdAt, Instant.now());
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
        if (type.isStationOrganizationUnit()
                && operationalScopeReference != null
                && !operationalScopeReference.isStationScope()) {
            throw new BusinessRuleViolationException("Station organization unit must reference a station-like operational scope.");
        }
    }
}
