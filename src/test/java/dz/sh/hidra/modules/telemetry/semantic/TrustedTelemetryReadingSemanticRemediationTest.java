/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Telemetry Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Verifies trust policy, source identity, lifecycle, quality and binding selection.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import dz.sh.hidra.modules.telemetry.application.service.TrustedTelemetryReadingApplicationService;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustEvidencePort;
import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class TrustedTelemetryReadingSemanticRemediationTest {
    private static final Instant NOW = Instant.parse("2026-10-06T00:00:00Z");
    private final TelemetryTrustEvidencePort evidence = mock(TelemetryTrustEvidencePort.class);
    private final TrustedTelemetryReadingRepositoryPort repository = mock(TrustedTelemetryReadingRepositoryPort.class);
    private final TrustedTelemetryReadingApplicationService service = new TrustedTelemetryReadingApplicationService(evidence, repository);
    private final TelemetryTrustEvidencePort.Binding binding = new TelemetryTrustEvidencePort.Binding(
            "binding", "PIPELINE", "asset", "ASSET-1", "snapshot");

    @Test
    void derivesValuesAndBindingFromEvidenceWithoutTrustUpgrade() {
        for (var level : new TrustLevel[]{TrustLevel.MEDIUM, TrustLevel.HIGH, TrustLevel.CERTIFIED}) {
            stub(facts("reading", "point", AssessmentStatus.PASSED, level, TelemetryLifecycleStatus.ACTIVE,
                    true, true, true, List.of(binding)));
            when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
            var result = service.trust("reading", "assessment", null);
            assertThat(result.readingId()).isEqualTo("reading");
            assertThat(result.pointId()).isEqualTo("point");
            assertThat(result.numericValue()).isEqualByComparingTo("42");
            assertThat(result.unitId()).isEqualTo("unit");
            assertThat(result.qualityCodeId()).isEqualTo("resolved-quality");
            assertThat(result.trustLevel()).isEqualTo(level);
            assertThat(result.ingestionBatchId()).isEqualTo("batch");
            assertThat(result.topologyAssetId()).isEqualTo("asset");
            assertThat(result.topologySnapshotId()).isEqualTo("snapshot");
            assertThat(result.sourceTimestamp()).isEqualTo(NOW);
        }
    }
    @Test
    void rejectsEveryNonPassingAssessmentAndUnacceptableLevelBeforeSave() {
        for (var status : AssessmentStatus.values()) if (status != AssessmentStatus.PASSED) {
            rejected(facts("reading", "point", status, TrustLevel.HIGH, TelemetryLifecycleStatus.ACTIVE,
                    true, true, true, List.of()));
        }
        for (var level : new TrustLevel[]{TrustLevel.UNTRUSTED, TrustLevel.LOW}) {
            rejected(facts("reading", "point", AssessmentStatus.PASSED, level, TelemetryLifecycleStatus.ACTIVE,
                    true, true, true, List.of()));
        }
        verifyNoInteractions(repository);
    }
    @Test
    void rejectsPointLifecycleIdentityAndCatalogProvenanceMismatches() {
        for (var status : TelemetryLifecycleStatus.values()) if (status != TelemetryLifecycleStatus.ACTIVE) {
            rejected(facts("reading", "point", AssessmentStatus.PASSED, TrustLevel.HIGH, status,
                    true, true, true, List.of()));
        }
        rejected(facts("other", "point", AssessmentStatus.PASSED, TrustLevel.HIGH, TelemetryLifecycleStatus.ACTIVE,true,true,true,List.of()));
        rejected(facts("reading", "other", AssessmentStatus.PASSED, TrustLevel.HIGH, TelemetryLifecycleStatus.ACTIVE,true,true,true,List.of()));
        rejected(facts("reading", "point", AssessmentStatus.PASSED, TrustLevel.HIGH, TelemetryLifecycleStatus.ACTIVE,false,true,true,List.of()));
        rejected(facts("reading", "point", AssessmentStatus.PASSED, TrustLevel.HIGH, TelemetryLifecycleStatus.ACTIVE,true,false,true,List.of()));
        rejected(facts("reading", "point", AssessmentStatus.PASSED, TrustLevel.HIGH, TelemetryLifecycleStatus.ACTIVE,true,true,false,List.of()));
        verifyNoInteractions(repository);
    }
    @Test
    void supportsUnboundPointsButRequiresSelectionForMultipleApplicableBindings() {
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        stub(facts("reading","point",AssessmentStatus.PASSED,TrustLevel.HIGH,TelemetryLifecycleStatus.ACTIVE,true,true,true,List.of()));
        assertThat(service.trust("reading","assessment",null).topologyAssetId()).isNull();
        var other = new TelemetryTrustEvidencePort.Binding("other","NODE","node","NODE-1",null);
        stub(facts("reading","point",AssessmentStatus.PASSED,TrustLevel.HIGH,TelemetryLifecycleStatus.ACTIVE,true,true,true,List.of(binding,other)));
        assertThatThrownBy(() -> service.trust("reading","assessment",null)).isInstanceOf(InvalidTelemetryValueException.class);
        assertThatThrownBy(() -> service.trust("reading","assessment","missing")).isInstanceOf(InvalidTelemetryValueException.class);
        assertThat(service.trust("reading","assessment","other").topologyAssetId()).isEqualTo("node");
    }
    private void stub(TelemetryTrustEvidencePort.Evidence facts) {
        when(evidence.load(eq("reading"),eq("assessment"),any(Instant.class))).thenReturn(facts);
    }
    private void rejected(TelemetryTrustEvidencePort.Evidence facts) {
        stub(facts);
        assertThatThrownBy(() -> service.trust("reading","assessment",null)).isInstanceOf(InvalidTelemetryValueException.class);
    }
    private TelemetryTrustEvidencePort.Evidence facts(String assessedReading, String assessedPoint,
            AssessmentStatus status, TrustLevel level, TelemetryLifecycleStatus pointStatus,
            boolean quality, boolean unit, boolean batch, List<TelemetryTrustEvidencePort.Binding> bindings) {
        var raw = new TelemetryReading("reading","point",new BigDecimal("42"),null,null,"raw-quality",
                NOW,NOW,ReadingState.ACCEPTED,"batch",null,null,null,null,null,NOW);
        return new TelemetryTrustEvidencePort.Evidence(raw,"assessment",assessedReading,assessedPoint,status,
                level,"resolved-quality",pointStatus,"unit",quality,unit,batch,bindings);
    }
}
