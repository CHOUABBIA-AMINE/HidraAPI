/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetVersionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Analytics Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.semantic
 *
 * @Description : Verifies HMR-037 published dataset-version immutability.
 *
 */
package dz.sh.hidra.modules.analytics.semantic;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDatasetVersion;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.adapter.JpaAnalyticsDatasetVersionRepositoryAdapter;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsDatasetVersionJpaEntity;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.repository.AnalyticsDatasetVersionJpaRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AnalyticsDatasetVersionSemanticRemediationTest {

    private static final Instant CREATED_AT = Instant.parse("2026-10-05T00:00:00Z");
    private static final Instant PUBLISHED_AT = Instant.parse("2026-10-05T01:00:00Z");

    @Test
    void publishedPersistedVersionRejectsMutation() {
        AnalyticsDatasetVersionJpaRepository repository =
                mock(AnalyticsDatasetVersionJpaRepository.class);
        when(repository.findById("version-1")).thenReturn(Optional.of(entity(true, "data-a")));

        JpaAnalyticsDatasetVersionRepositoryAdapter adapter =
                new JpaAnalyticsDatasetVersionRepositoryAdapter(repository);

        assertThatThrownBy(() -> adapter.save(model(true, "data-b")))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("immutable");

        verify(repository, never()).save(any());
    }

    @Test
    void identicalPublishedResaveIsNoOp() {
        AnalyticsDatasetVersionJpaRepository repository =
                mock(AnalyticsDatasetVersionJpaRepository.class);
        when(repository.findById("version-1")).thenReturn(Optional.of(entity(true, "data-a")));

        JpaAnalyticsDatasetVersionRepositoryAdapter adapter =
                new JpaAnalyticsDatasetVersionRepositoryAdapter(repository);

        AnalyticsDatasetVersion saved = adapter.save(model(true, "data-a"));

        assertThat(saved).isEqualTo(model(true, "data-a"));
        verify(repository, never()).save(any());
    }

    @Test
    void unpublishedVersionMayTransitionToPublished() {
        AnalyticsDatasetVersionJpaRepository repository =
                mock(AnalyticsDatasetVersionJpaRepository.class);
        when(repository.findById("version-1")).thenReturn(Optional.of(entity(false, "data-a")));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        JpaAnalyticsDatasetVersionRepositoryAdapter adapter =
                new JpaAnalyticsDatasetVersionRepositoryAdapter(repository);

        AnalyticsDatasetVersion saved = adapter.save(model(true, "data-a"));

        assertThat(saved.published()).isTrue();
        verify(repository).save(any());
    }

    private static AnalyticsDatasetVersion model(boolean published, String dataHash) {
        return new AnalyticsDatasetVersion(
                "version-1",
                "dataset-1",
                1,
                "schema-a",
                dataHash,
                10L,
                CREATED_AT,
                CREATED_AT.plusSeconds(60),
                new BigDecimal("0.990000"),
                published,
                published ? PUBLISHED_AT : null,
                published ? "actor-1" : null,
                CREATED_AT
        );
    }

    private static AnalyticsDatasetVersionJpaEntity entity(boolean published, String dataHash) {
        return new AnalyticsDatasetVersionJpaEntity(
                "version-1",
                "dataset-1",
                1,
                "schema-a",
                dataHash,
                10L,
                CREATED_AT,
                CREATED_AT.plusSeconds(60),
                new BigDecimal("0.990000"),
                published,
                published ? PUBLISHED_AT : null,
                published ? "actor-1" : null,
                CREATED_AT
        );
    }
}
