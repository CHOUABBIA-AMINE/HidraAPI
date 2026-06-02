/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Position
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Domain model for an organizational position or operational function.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;
import dz.sh.hidra.modules.organization.domain.value.PositionTitle;

/**
 * Represents an organizational position or operational function.
 *
 * <p>Business role:
 * A position defines what an employee does inside the operational organization, such as
 * Station Team Leader, Station Boss, Region Director, Gas Flux Director, or Department Chief.
 * It is not an identity role. Identity roles and permissions belong to the identity module.
 *
 * <p>Architecture role:
 * This class belongs to the organization domain model. It models a reusable business position
 * independently from REST DTOs, JPA entities, Spring, platform security, identity, and topology.
 *
 * <p>Validation:
 * The identifier, code, and title are mandatory and validated by their value objects. Description
 * is optional but bounded. Activation and deactivation are controlled through behavior methods.
 *
 * <p>Usage:
 * Use this model when assigning employees to operational responsibilities. Do not use it to model
 * login roles, permissions, physical topology assets, or station equipment.
 */
public final class Position implements Entity<PositionId> {

    private static final int DESCRIPTION_MAX_LENGTH = 500;

    /**
     * Stable identifier of the position.
     */
    private final PositionId id;

    /**
     * Unique business code of the position, for example STATION_TEAM_LEADER.
     */
    private final PositionCode code;

    /**
     * Human-readable position title, for example Station Team Leader.
     */
    private final PositionTitle title;

    /**
     * Optional business description of the position.
     */
    private final String description;

    /**
     * Indicates whether the position can be used for new assignments.
     */
    private final boolean active;

    /**
     * Instant when the position was created.
     */
    private final Instant createdAt;

    /**
     * Instant when the position was last updated.
     */
    private final Instant updatedAt;

    private Position(
            PositionId id,
            PositionCode code,
            PositionTitle title,
            String description,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Position id must not be null.");
        this.code = Objects.requireNonNull(code, "Position code must not be null.");
        this.title = Objects.requireNonNull(title, "Position title must not be null.");
        this.description = normalizeDescription(description);
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "Position creation instant must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Position update instant must not be null.");

        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Position updatedAt must not be before createdAt.");
        }
    }

    /**
     * Creates a new active position.
     *
     * @param code unique business code of the position
     * @param title display title of the position
     * @param description optional business description
     * @return created active position
     */
    public static Position create(PositionCode code, PositionTitle title, String description) {
        Instant now = Instant.now();
        return new Position(PositionId.newId(), code, title, description, true, now, now);
    }

    /**
     * Rehydrates an existing position from persistence without importing persistence classes.
     *
     * @param id stable position identifier
     * @param code unique business code
     * @param title display title
     * @param description optional business description
     * @param active whether the position is active
     * @param createdAt creation instant
     * @param updatedAt update instant
     * @return rehydrated position
     */
    public static Position restore(
            PositionId id,
            PositionCode code,
            PositionTitle title,
            String description,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new Position(id, code, title, description, active, createdAt, updatedAt);
    }

    @Override
    public PositionId id() {
        return id;
    }

    /**
     * Returns the unique business code of the position.
     *
     * @return position code
     */
    public PositionCode code() {
        return code;
    }

    /**
     * Returns the human-readable title of the position.
     *
     * @return position title
     */
    public PositionTitle title() {
        return title;
    }

    /**
     * Returns the optional business description.
     *
     * @return optional description, or null when not defined
     */
    public String description() {
        return description;
    }

    /**
     * Indicates whether this position can be used for new assignments.
     *
     * @return true when active
     */
    public boolean active() {
        return active;
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
     * Renames the position by changing its display title.
     *
     * @param newTitle new display title
     * @return updated position
     */
    public Position rename(PositionTitle newTitle) {
        return new Position(id, code, newTitle, description, active, createdAt, Instant.now());
    }

    /**
     * Changes the optional business description.
     *
     * @param newDescription new optional description
     * @return updated position
     */
    public Position changeDescription(String newDescription) {
        return new Position(id, code, title, newDescription, active, createdAt, Instant.now());
    }

    /**
     * Activates the position so it can be used for new assignments.
     *
     * @return active position
     */
    public Position activate() {
        if (active) {
            return this;
        }
        return new Position(id, code, title, description, true, createdAt, Instant.now());
    }

    /**
     * Deactivates the position so it cannot be used for new assignments.
     *
     * @return inactive position
     */
    public Position deactivate() {
        if (!active) {
            return this;
        }
        return new Position(id, code, title, description, false, createdAt, Instant.now());
    }

    /**
     * Ensures the position can be used in an employee assignment.
     */
    public void ensureAssignable() {
        if (!active) {
            throw new BusinessRuleViolationException("Inactive position cannot be used for a new employee assignment.");
        }
    }

    private static String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        String normalized = description.trim();
        if (normalized.length() > DESCRIPTION_MAX_LENGTH) {
            throw new InvalidValueObjectException("Position description must not exceed 500 characters.");
        }
        return normalized;
    }
}
