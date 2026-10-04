/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Configuration Test
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.semantic
 *
 * @Description : Verifies HMR-017 ConfigurationDefinition secret-reference remediation.
 *
 */
package dz.sh.hidra.modules.configuration.semantic;

import dz.sh.hidra.modules.configuration.domain.exception.ConfigurationBoundaryViolationException;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDefinition;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationDefinitionStatus;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueType;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.adapter.JpaConfigurationDefinitionRepositoryAdapter;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.repository.ConfigurationDefinitionJpaRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfigurationDefinitionSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void secretReferenceOnlyDefaultCannotPersistSecretMaterial() {
        var repository = mock(ConfigurationDefinitionJpaRepository.class);
        var adapter = new JpaConfigurationDefinitionRepositoryAdapter(repository);

        assertThatThrownBy(() -> adapter.save(definition(
                ConfigurationSensitivity.SECRET_REFERENCE_ONLY,
                "password=actual-secret"
        ))).isInstanceOf(ConfigurationBoundaryViolationException.class)
                .hasMessageContaining("must not persist secret material");

        verify(repository, never()).save(any());
    }

    @Test
    void generalDefinitionDefaultUsesSameSecretBoundary() {
        var repository = mock(ConfigurationDefinitionJpaRepository.class);
        var adapter = new JpaConfigurationDefinitionRepositoryAdapter(repository);

        assertThatThrownBy(() -> adapter.save(definition(
                ConfigurationSensitivity.INTERNAL,
                "token=actual-token"
        ))).isInstanceOf(ConfigurationBoundaryViolationException.class)
                .hasMessageContaining("must not persist secret material");

        verify(repository, never()).save(any());
    }

    @Test
    void referenceLikeDefaultRemainsAllowedWithoutInventingReferenceSyntax() {
        var repository = mock(ConfigurationDefinitionJpaRepository.class);
        var adapter = new JpaConfigurationDefinitionRepositoryAdapter(repository);
        var model = definition(
                ConfigurationSensitivity.SECRET_REFERENCE_ONLY,
                "vault-ref:config/db-main"
        );
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ConfigurationDefinition saved = adapter.save(model);

        assertThat(saved.defaultValue()).isEqualTo("vault-ref:config/db-main");
        assertThat(saved.secretReferenceOnly()).isTrue();
    }

    private static ConfigurationDefinition definition(
            ConfigurationSensitivity sensitivity,
            String defaultValue
    ) {
        return new ConfigurationDefinition(
                "definition-1",
                "namespace-1",
                "runtime.setting",
                "Paramètre",
                null,
                null,
                ConfigurationValueType.STRING,
                sensitivity,
                ConfigurationDefinitionStatus.DRAFT,
                false,
                false,
                defaultValue,
                null,
                NOW,
                NOW
        );
    }
}
