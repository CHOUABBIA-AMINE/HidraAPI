/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeRegistrationValidatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies read-only operational-scope target registration validation.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort.ResolvedTarget;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationalScopeRegistrationValidatorTest {

    @Test
    void globalHasNoUnderlyingTargetAndRequiresNoOwnerLookup() {
        OperationalScopeRegistrationValidator validator = new OperationalScopeRegistrationValidator(resolver(false, null));
        var global = validator.validate(OperationalScopeType.GLOBAL, "  ");
        assertEquals(OperationalScopeType.GLOBAL, global.type());
        assertNull(global.targetId());
        assertThrows(IllegalArgumentException.class, () -> validator.validate(OperationalScopeType.GLOBAL, "global-id"));
    }

    @Test
    void preservesOwnersStringIdAndIgnoresDisplayLabelsAsIdentity() {
        OperationalScopeRegistrationValidator validator = new OperationalScopeRegistrationValidator(resolver(true,
                new ResolvedTarget(OperationalScopeType.PIPELINE, "pipeline-009", "PL-009", "Current label", true)));
        var target = validator.validate(OperationalScopeType.PIPELINE, " pipeline-009 ");
        assertEquals(OperationalScopeType.PIPELINE, target.type());
        assertEquals("pipeline-009", target.targetId());
    }

    @Test
    void rejectsUnsupportedOwnerAndMissingTarget() {
        var validator = new OperationalScopeRegistrationValidator(resolver(false, null));
        assertThrows(IllegalStateException.class, () -> validator.validate(OperationalScopeType.PIPELINE, "pipeline-009"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(OperationalScopeType.PIPELINE, "  "));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(OperationalScopeType.CUSTOM, "arbitrary"));
        assertThrows(NullPointerException.class, () -> validator.validate(null, "pipeline-009"));
    }

    @Test
    void rejectsMissingMismatchedAndUnassignableOwnerResults() {
        assertThrows(IllegalArgumentException.class, () ->
                new OperationalScopeRegistrationValidator(resolver(true, null))
                        .validate(OperationalScopeType.PIPELINE, "pipeline-009"));
        assertThrows(IllegalStateException.class, () ->
                new OperationalScopeRegistrationValidator(resolver(true,
                        new ResolvedTarget(OperationalScopeType.FACILITY, "pipeline-009", null, null, true)))
                        .validate(OperationalScopeType.PIPELINE, "pipeline-009"));
        assertThrows(IllegalStateException.class, () ->
                new OperationalScopeRegistrationValidator(resolver(true,
                        new ResolvedTarget(OperationalScopeType.PIPELINE, "another-pipeline", null, null, true)))
                        .validate(OperationalScopeType.PIPELINE, "pipeline-009"));
        assertThrows(IllegalStateException.class, () ->
                new OperationalScopeRegistrationValidator(resolver(true,
                        new ResolvedTarget(OperationalScopeType.PIPELINE, "pipeline-009", null, null, false)))
                        .validate(OperationalScopeType.PIPELINE, "pipeline-009"));
    }

    @Test
    void propagatesOwnerUnavailableInsteadOfTreatingItAsMissingTarget() {
        var resolver = new OperationalScopeTargetResolverPort() {
            public boolean supports(OperationalScopeType type) { return true; }
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                throw new IllegalStateException("Owner unavailable");
            }
        };
        assertThrows(IllegalStateException.class, () ->
                new OperationalScopeRegistrationValidator(resolver)
                        .validate(OperationalScopeType.PIPELINE, "pipeline-009"));
    }

    private static OperationalScopeTargetResolverPort resolver(boolean supported, ResolvedTarget response) {
        return new OperationalScopeTargetResolverPort() {
            public boolean supports(OperationalScopeType type) { return supported; }
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                return Optional.ofNullable(response);
            }
        };
    }
}
