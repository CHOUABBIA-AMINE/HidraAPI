/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Permission
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Domain model for an identity permission catalog entry.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;

import java.util.Objects;

/**
 * Domain model representing one permission catalog entry.
 *
 * <p>Business role: defines one business access capability that may be granted to an
 * identity role and later evaluated for a user.</p>
 *
 * <p>Architecture role: immutable identity domain entity backed by identity value
 * objects. It has no persistence annotations and does not depend on platform security
 * plumbing.</p>
 *
 * <p>Validation responsibility: requires a non-null identifier, permission code, and
 * permission name. Optional descriptions are normalized by trimming blank values to
 * {@code null}.</p>
 *
 * <p>Usage: create permission catalog entries with {@link #create(PermissionId,
 * PermissionCode, PermissionName, String)} and compare permissions by stable
 * identifier.</p>
 */
public final class Permission implements Entity<PermissionId> {

    private final PermissionId id;
    private final PermissionCode code;
    private final PermissionName name;
    private final String description;

    private Permission(
            PermissionId id,
            PermissionCode code,
            PermissionName name,
            String description
    ) {
        this.id = requireNonNull(id, "PermissionId");
        this.code = requireNonNull(code, "PermissionCode");
        this.name = requireNonNull(name, "PermissionName");
        this.description = normalizeDescription(description);
    }

    public static Permission create(
            PermissionId id,
            PermissionCode code,
            PermissionName name,
            String description
    ) {
        return new Permission(id, code, name, description);
    }

    @Override
    public PermissionId id() {
        return id;
    }

    public PermissionCode code() {
        return code;
    }

    public PermissionName name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean hasCode(PermissionCode candidateCode) {
        return code.equals(candidateCode);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Permission permission)) {
            return false;
        }

        return id.equals(permission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }

    private static String normalizeDescription(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
