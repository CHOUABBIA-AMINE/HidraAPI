/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : User
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : User aggregate representing HidraAPI security identity.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.EmployeeReference;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate root representing a HidraAPI security identity.
 *
 * <p>Business role: models who can use HidraAPI, their lifecycle status, and their role
 * assignments inside the identity bounded context.</p>
 *
 * <p>Architecture role: domain aggregate root that owns user lifecycle transitions and
 * role assignment collection without depending on Spring Security, persistence, platform
 * infrastructure, or organization employee aggregates.</p>
 *
 * <p>Validation responsibility: requires valid user identity values, controls status
 * transitions, keeps employee linkage as a generic reference, and rejects duplicate role
 * assignments.</p>
 *
 * <p>Usage: register users with {@link #register(UserId, Username, EmailAddress)},
 * activate or suspend them through lifecycle methods, assign roles through
 * {@link #assignRole(Role, Clock)}, and persist through a domain repository contract.</p>
 */
public final class User implements AggregateRoot<UserId> {

    private final UserId id;
    private final Username username;
    private EmailAddress emailAddress;
    private UserStatus status;
    private EmployeeReference employeeReference;
    private final List<UserRoleAssignment> roleAssignments;

    private User(
            UserId id,
            Username username,
            EmailAddress emailAddress,
            UserStatus status,
            EmployeeReference employeeReference,
            List<UserRoleAssignment> roleAssignments
    ) {
        this.id = requireNonNull(id, "UserId");
        this.username = requireNonNull(username, "Username");
        this.emailAddress = requireNonNull(emailAddress, "EmailAddress");
        this.status = requireNonNull(status, "UserStatus");
        this.employeeReference = employeeReference;
        this.roleAssignments = new ArrayList<>(requireNonNull(roleAssignments, "roleAssignments"));
        rejectForeignAssignments(this.roleAssignments);
        rejectDuplicateAssignments(this.roleAssignments);
    }

    public static User register(UserId id, Username username, EmailAddress emailAddress) {
        return new User(id, username, emailAddress, UserStatus.REGISTERED, null, List.of());
    }

    public static User rehydrate(
            UserId id,
            Username username,
            EmailAddress emailAddress,
            UserStatus status,
            EmployeeReference employeeReference,
            List<UserRoleAssignment> roleAssignments
    ) {
        return new User(id, username, emailAddress, status, employeeReference, roleAssignments);
    }

    @Override
    public UserId id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public EmailAddress emailAddress() {
        return emailAddress;
    }

    public UserStatus status() {
        return status;
    }

    public EmployeeReference employeeReference() {
        return employeeReference;
    }

    public List<UserRoleAssignment> roleAssignments() {
        return Collections.unmodifiableList(roleAssignments);
    }

    public boolean isRegistered() {
        return UserStatus.REGISTERED.equals(status);
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(status);
    }

    public boolean isSuspended() {
        return UserStatus.SUSPENDED.equals(status);
    }

    public boolean isDisabled() {
        return UserStatus.DISABLED.equals(status);
    }

    public void changeEmailAddress(EmailAddress newEmailAddress) {
        rejectDisabledUserChange();
        this.emailAddress = requireNonNull(newEmailAddress, "EmailAddress");
    }

    public void linkEmployee(EmployeeReference newEmployeeReference) {
        rejectDisabledUserChange();
        this.employeeReference = requireNonNull(newEmployeeReference, "EmployeeReference");
    }

    public void unlinkEmployee() {
        rejectDisabledUserChange();
        this.employeeReference = null;
    }

    public void activate() {
        if (isDisabled()) {
            throw new BusinessRuleViolationException("Disabled users cannot be activated.");
        }

        this.status = UserStatus.ACTIVE;
    }

    public void suspend() {
        if (!isActive()) {
            throw new BusinessRuleViolationException("Only active users can be suspended.");
        }

        this.status = UserStatus.SUSPENDED;
    }

    public void disable() {
        this.status = UserStatus.DISABLED;
    }

    public void assignRole(Role role, Clock clock) {
        requireAssignableUser();

        Role requiredRole = requireNonNull(role, "Role");
        Clock requiredClock = requireNonNull(clock, "Clock");

        if (!requiredRole.isActive()) {
            throw new BusinessRuleViolationException("Only active roles can be assigned to users.");
        }

        assignRole(
                requiredRole.id(),
                requiredRole.code(),
                Instant.now(requiredClock)
        );
    }

    public void assignRole(RoleId roleId, RoleCode roleCode, Instant assignedAt) {
        requireAssignableUser();

        UserRoleAssignment assignment = UserRoleAssignment.assign(
                id,
                roleId,
                roleCode,
                assignedAt
        );

        if (hasRole(roleId)) {
            throw new BusinessRuleViolationException("User already has role: " + roleCode.value() + ".");
        }

        roleAssignments.add(assignment);
    }

    public boolean revokeRole(RoleId roleId) {
        rejectDisabledUserChange();

        RoleId requiredRoleId = requireNonNull(roleId, "RoleId");
        return roleAssignments.removeIf(assignment -> assignment.references(requiredRoleId));
    }

    public boolean hasRole(RoleId roleId) {
        RoleId requiredRoleId = requireNonNull(roleId, "RoleId");
        return roleAssignments.stream()
                .anyMatch(assignment -> assignment.references(requiredRoleId));
    }

    public boolean hasRole(RoleCode roleCode) {
        RoleCode requiredRoleCode = requireNonNull(roleCode, "RoleCode");
        return roleAssignments.stream()
                .anyMatch(assignment -> assignment.references(requiredRoleCode));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof User user)) {
            return false;
        }

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void requireAssignableUser() {
        if (!isActive()) {
            throw new BusinessRuleViolationException("Only active users can receive role assignments.");
        }
    }

    private void rejectDisabledUserChange() {
        if (isDisabled()) {
            throw new BusinessRuleViolationException("Disabled users cannot be changed.");
        }
    }

    private void rejectForeignAssignments(List<UserRoleAssignment> assignments) {
        boolean containsForeignAssignment = assignments.stream()
                .anyMatch(assignment -> !id.equals(assignment.userId()));

        if (containsForeignAssignment) {
            throw new BusinessRuleViolationException("User cannot contain role assignments for another user.");
        }
    }

    private static void rejectDuplicateAssignments(List<UserRoleAssignment> assignments) {
        long distinctRoleIds = assignments.stream()
                .map(UserRoleAssignment::roleId)
                .distinct()
                .count();

        if (distinctRoleIds != assignments.size()) {
            throw new BusinessRuleViolationException("User cannot contain duplicate role assignments.");
        }
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
