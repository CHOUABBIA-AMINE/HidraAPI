/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DigitalTwinReadinessAssessmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Analytics Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.semantic
 *
 * @Description : Verifies HMR-025 readiness scope requiredness and bounded status semantics.
 *
 */
package dz.sh.hidra.modules.analytics.semantic;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.DigitalTwinReadinessAssessment;
import dz.sh.hidra.modules.analytics.domain.value.DigitalTwinReadinessStatus;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.DigitalTwinReadinessAssessmentJpaEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DigitalTwinReadinessAssessmentSemanticRemediationTest {

    private static final Instant START = Instant.parse("2026-10-01T00:00:00Z");
    private static final Instant END = Instant.parse("2026-10-02T00:00:00Z");

    @Test
    void scopeTypeIsMandatoryWhileScopeIdRemainsOptional() {
        assertThatThrownBy(() -> assessment(" ", null))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("scope type must not be blank");

        DigitalTwinReadinessAssessment assessment = assessment(" PIPELINE ", null);

        assertThat(assessment.scopeType()).isEqualTo("PIPELINE");
        assertThat(assessment.scopeId()).isNull();
    }

    @Test
    void readinessStatusIsTheBoundedFiveValueEnum() {
        Set<String> codes = Stream.of(DigitalTwinReadinessStatus.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        assertThat(codes).containsExactlyInAnyOrder(
                "NOT_READY",
                "PARTIAL",
                "READY",
                "ADVANCED",
                "UNKNOWN"
        );
    }

    @Test
    void jpaPersistsReadinessStatusAsEnumString() throws Exception {
        var field = DigitalTwinReadinessAssessmentJpaEntity.class
                .getDeclaredField("readinessStatus");

        Enumerated enumerated = field.getAnnotation(Enumerated.class);

        assertThat(enumerated).isNotNull();
        assertThat(enumerated.value()).isEqualTo(EnumType.STRING);
    }

    @Test
    void migrationEnforcesScopeAndBoundedStatusWithoutCreatingCatalogSemantics()
            throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql"
        ));

        assertThat(sql).contains("ck_hmr025_readiness_scope_type_nonblank");
        assertThat(sql).contains("ck_hmr025_readiness_status_bounded");
        assertThat(sql).contains("'NOT_READY'", "'PARTIAL'", "'READY'", "'ADVANCED'", "'UNKNOWN'");
        assertThat(sql).doesNotContain("INSERT INTO hidra_analytics_catalog_entry");
    }

    private static DigitalTwinReadinessAssessment assessment(
            String scopeType,
            String scopeId
    ) {
        return new DigitalTwinReadinessAssessment(
                "readiness-1",
                scopeType,
                scopeId,
                "topology-snapshot-1",
                START,
                END,
                null,
                null,
                null,
                null,
                null,
                null,
                DigitalTwinReadinessStatus.UNKNOWN,
                END,
                END
        );
    }
}
