/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.query
 *
 * @Description : Verifies deterministic plan-target scoping and paging for monitoring deviation reads.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;
import dz.sh.hidra.modules.monitoring.domain.value.DeviationStatus;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.PlanActualDeviationJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class JpaMonitoringQueryAdapterTest {

    private static final String DEVIATION_QUERY =
            "select e from PlanActualDeviationJpaEntity e order by e.detectedAt desc";

    @Test
    void scopesDeviationsByPlanTargetAndPreservesAuthoritativeComparisonValues() {
        EntityManager entityManager = mock(EntityManager.class);
        TypedQuery<PlanActualDeviationJpaEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(DEVIATION_QUERY, PlanActualDeviationJpaEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(
                deviation("dev-1", "target-1", "reading-1", new BigDecimal("105"), new BigDecimal("100"),
                        new BigDecimal("5"), new BigDecimal("5"), "m3/h", DeviationSeverity.HIGH,
                        Instant.parse("2026-09-12T10:00:00Z")),
                deviation("dev-2", "target-2", "reading-2", new BigDecimal("80"), new BigDecimal("100"),
                        new BigDecimal("-20"), new BigDecimal("-20"), "m3/h", DeviationSeverity.CRITICAL,
                        Instant.parse("2026-09-12T09:30:00Z")),
                deviation("dev-3", "target-1", "reading-3", new BigDecimal("95"), new BigDecimal("100"),
                        new BigDecimal("-5"), new BigDecimal("-5"), "m3/h", DeviationSeverity.MEDIUM,
                        Instant.parse("2026-09-12T09:00:00Z"))
        ));

        JpaMonitoringQueryAdapter adapter = new JpaMonitoringQueryAdapter(entityManager);

        var firstPage = adapter.deviations("target-1", null, null, null, null, null, null, 0, 1);

        assertEquals(1, firstPage.content().size());
        assertEquals(2, firstPage.totalElements());
        assertEquals(2, firstPage.totalPages());
        assertTrue(firstPage.hasNext());
        assertEquals("dev-1", firstPage.content().getFirst().id());
        assertEquals("target-1", firstPage.content().getFirst().planTargetId());
        assertEquals("reading-1", firstPage.content().getFirst().trustedTelemetryReadingId());
        assertEquals(new BigDecimal("105"), firstPage.content().getFirst().actualValue());
        assertEquals(new BigDecimal("100"), firstPage.content().getFirst().expectedValue());
        assertEquals(new BigDecimal("5"), firstPage.content().getFirst().differenceValue());
        assertEquals(new BigDecimal("5"), firstPage.content().getFirst().differencePercent());
        assertEquals("m3/h", firstPage.content().getFirst().unitId());

        var secondPage = adapter.deviations("target-1", null, null, null, null, null, null, 1, 1);

        assertEquals(1, secondPage.content().size());
        assertEquals("dev-3", secondPage.content().getFirst().id());
        assertFalse(secondPage.hasNext());

        var emptyPage = adapter.deviations("missing-target", null, null, null, null, null, null, 0, 50);

        assertTrue(emptyPage.content().isEmpty());
        assertEquals(0, emptyPage.totalElements());
        assertEquals(0, emptyPage.totalPages());
        assertFalse(emptyPage.hasNext());
    }

    private static PlanActualDeviationJpaEntity deviation(
            String id,
            String planTargetId,
            String readingId,
            BigDecimal actualValue,
            BigDecimal expectedValue,
            BigDecimal differenceValue,
            BigDecimal differencePercent,
            String unitId,
            DeviationSeverity severity,
            Instant detectedAt
    ) {
        return new PlanActualDeviationJpaEntity(
                id,
                "evaluation-1",
                planTargetId,
                "expected-flow-1",
                readingId,
                "telemetry-point-1",
                "PIPELINE",
                "pipeline-1",
                "PL-001",
                actualValue,
                expectedValue,
                differenceValue,
                differencePercent,
                unitId,
                severity,
                DeviationStatus.OPEN,
                detectedAt,
                null,
                "TARGET_DEVIATION",
                "Authoritative monitoring comparison"
        );
    }
}
