/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterOperationalScopeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to register one canonical operational-scope owner reference.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Objects;

/**
 * Registration request for a canonical operational-scope owner reference.
 *
 * <p>Business role: carries the owner-native scope reference that must be validated
 * before it can receive a generated registry identity.</p>
 *
 * <p>Architecture role: application command that preserves the domain value object as
 * one unit instead of duplicating type/target-ID shape validation.</p>
 *
 * <p>Validation: {@link OperationalScopeReference} owns scope-reference shape rules;
 * this command requires the already constructed reference to be non-null.</p>
 *
 * <p>Usage: pass this command to {@code RegisterOperationalScopeUseCase}. The command
 * never contains or predicts the generated {@code OperationalScope.id}.</p>
 *
 * @param reference canonical target-owner reference; GLOBAL has a null target ID
 */
public record RegisterOperationalScopeCommand(
        OperationalScopeReference reference
) {

    public RegisterOperationalScopeCommand {
        Objects.requireNonNull(reference, "Operational scope reference must not be null.");
    }

    /**
     * Transitional constructor for callers that still provide separate type/target-ID values.
     */
    @Deprecated(forRemoval = true)
    public RegisterOperationalScopeCommand(
            OperationalScopeType type,
            String targetId
    ) {
        this(new OperationalScopeReference(type, targetId));
    }

    /**
     * Transitional type projection.
     *
     * @return canonical reference type
     */
    @Deprecated(forRemoval = true)
    public OperationalScopeType type() {
        return reference.type();
    }

    /**
     * Transitional target-ID projection.
     *
     * @return canonical owner-native target ID, null only for GLOBAL
     */
    @Deprecated(forRemoval = true)
    public String targetId() {
        return reference.targetId();
    }
}
