/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityProviderSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Verifies HMR-010 IdentityProvider semantic remediation.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.model.IdentityProvider;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaIdentityProviderRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentityProviderJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IdentityProviderSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsBlankProviderName() {
        assertThatThrownBy(() -> provider(" ", ProviderType.LOCAL, null, IdentityProviderStatus.INACTIVE, NOW, NOW))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("name");
    }

    @Test
    void rejectsMissingPersistenceTimestamps() {
        assertThatThrownBy(() -> provider("Local", ProviderType.LOCAL, null, IdentityProviderStatus.INACTIVE, null, NOW))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("createdAt");
    }

    @Test
    void repositoryAdapterRejectsDuplicateCodeBeforeSave() {
        var repository = mock(IdentityProviderJpaRepository.class);
        var adapter = new JpaIdentityProviderRepositoryAdapter(repository);
        var model = provider("Local", ProviderType.LOCAL, null, IdentityProviderStatus.INACTIVE, NOW, NOW);

        when(repository.existsByCodeAndIdNot("LOCAL_MAIN", "provider-1")).thenReturn(true);

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("unique");

        verify(repository, never()).save(any());
    }

    @Test
    void activeOidcRequiresIssuerBeforePersistence() {
        var repository = mock(IdentityProviderJpaRepository.class);
        var adapter = new JpaIdentityProviderRepositoryAdapter(repository);
        var model = provider("OIDC", ProviderType.OIDC, null, IdentityProviderStatus.ACTIVE, NOW, NOW);

        when(repository.existsByCodeAndIdNot("LOCAL_MAIN", "provider-1")).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("issuer URI");

        verify(repository, never()).save(any());
    }

    @Test
    void issuerLookupDelegatesToActiveProviderSelection() {
        var repository = mock(IdentityProviderJpaRepository.class, CALLS_REAL_METHODS);
        when(repository.findByProviderTypeAndIssuerUriAndStatus(
                ProviderType.OIDC,
                "https://issuer.example",
                IdentityProviderStatus.ACTIVE
        )).thenReturn(Optional.empty());

        assertThat(repository.findByProviderTypeAndIssuerUri(
                ProviderType.OIDC,
                "https://issuer.example"
        )).isEmpty();

        verify(repository).findByProviderTypeAndIssuerUriAndStatus(
                ProviderType.OIDC,
                "https://issuer.example",
                IdentityProviderStatus.ACTIVE
        );
    }

    @Test
    void migrationEnforcesProviderConfigurationIntegrity() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql"
        ));

        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr010_identity_provider_code");
        assertThat(sql).contains("CHECK (btrim(name) <> '')");
        assertThat(sql).contains("uk_hmr010_identity_provider_active_oidc_issuer");
        assertThat(sql).contains("uk_hmr010_identity_provider_active_local");
        assertThat(sql).contains("uk_hmr010_identity_provider_active_directory");
        assertThat(sql).contains("provider_type IN ('LDAP', 'ACTIVE_DIRECTORY')");
    }

    private static IdentityProvider provider(
            String name,
            ProviderType providerType,
            String issuerUri,
            IdentityProviderStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new IdentityProvider(
                "provider-1",
                "LOCAL_MAIN",
                name,
                providerType,
                issuerUri,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                status,
                null,
                null,
                createdAt,
                updatedAt
        );
    }
}
