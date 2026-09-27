/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeRegistrationValidatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies read-only validation of canonical operational-scope references.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort.ResolvedTarget;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationalScopeRegistrationValidatorTest {

    @Test
    void globalReferenceRequiresNoOwnerLookup() {
        OperationalScopeRegistrationValidator validator =
                new OperationalScopeRegistrationValidator(resolver(false, null));
        OperationalScopeReference global =
                new OperationalScopeReference(OperationalScopeType.GLOBAL, null);

        assertEquals(global, validator.validate(global));
    }

    @Test
    void preservesOwnerNativeReferenceAndIgnoresDisplayLabelsAsIdentity() {
        OperationalScopeRegistrationValidator validator =
                new OperationalScopeRegistrationValidator(resolver(
                        true,
                        new ResolvedTarget(
                                OperationalScopeType.PIPELINE,
                                "pipeline-009",
                                "PL-009",
                                "Current label",
                                true
                        )
                ));

        OperationalScopeReference reference =
                new OperationalScopeReference(
                        OperationalScopeType.PIPELINE,
                        " pipeline-009 "
                );

        assertEquals(
                new OperationalScopeReference(
                        OperationalScopeType.PIPELINE,
                        "pipeline-009"
                ),
                validator.validate(reference)
        );
    }

    @Test
    void rejectsUnsupportedOwner() {
        var validator =
                new OperationalScopeRegistrationValidator(resolver(false, null));

        assertThrows(
                IllegalStateException.class,
                () -> validator.validate(
                        new OperationalScopeReference(
                                OperationalScopeType.PIPELINE,
                                "pipeline-009"
                        )
                )
        );
        assertThrows(
                NullPointerException.class,
                () -> validator.validate((OperationalScopeReference) null)
        );
    }

    @Test
    void rejectsMissingMismatchedAndUnassignableOwnerResults() {
        OperationalScopeReference reference =
                new OperationalScopeReference(
                        OperationalScopeType.PIPELINE,
                        "pipeline-009"
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> new OperationalScopeRegistrationValidator(
                        resolver(true, null)
                ).validate(reference)
        );

        assertThrows(
                IllegalStateException.class,
                () -> new OperationalScopeRegistrationValidator(
                        resolver(
                                true,
                                new ResolvedTarget(
                                        OperationalScopeType.FACILITY,
                                        "pipeline-009",
                                        null,
                                        null,
                                        true
                                )
                        )
                ).validate(reference)
        );

        assertThrows(
                IllegalStateException.class,
                () -> new OperationalScopeRegistrationValidator(
                        resolver(
                                true,
                                new ResolvedTarget(
                                        OperationalScopeType.PIPELINE,
                                        "another-pipeline",
                                        null,
                                        null,
                                        true
                                )
                        )
                ).validate(reference)
        );

        assertThrows(
                IllegalStateException.class,
                () -> new OperationalScopeRegistrationValidator(
                        resolver(
                                true,
                                new ResolvedTarget(
                                        OperationalScopeType.PIPELINE,
                                        "pipeline-009",
                                        null,
                                        null,
                                        false
                                )
                        )
                ).validate(reference)
        );
    }

    @Test
    void propagatesOwnerUnavailableInsteadOfTreatingItAsMissingTarget() {
        var resolver = new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return true;
            }

            @Override
            public Optional<ResolvedTarget> resolve(
                    OperationalScopeType type,
                    String targetId
            ) {
                throw new IllegalStateException("Owner unavailable");
            }
        };

        assertThrows(
                IllegalStateException.class,
                () -> new OperationalScopeRegistrationValidator(resolver)
                        .validate(
                                new OperationalScopeReference(
                                        OperationalScopeType.PIPELINE,
                                        "pipeline-009"
                                )
                        )
        );
    }

    private static OperationalScopeTargetResolverPort resolver(
            boolean supported,
            ResolvedTarget response
    ) {
        return new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return supported;
            }

            @Override
            public Optional<ResolvedTarget> resolve(
                    OperationalScopeType type,
                    String targetId
            ) {
                return Optional.ofNullable(response);
            }
        };
    }
}
