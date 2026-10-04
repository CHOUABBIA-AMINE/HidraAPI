/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriodSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Custody Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.semantic
 *
 * @Description : Verifies HMR-018 CustodyMeasurementPeriod agreement/transfer-point coherence.
 *
 */
package dz.sh.hidra.modules.custody.semantic;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementPeriod;
import dz.sh.hidra.modules.custody.domain.value.CustodyPeriodStatus;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.JpaCustodyMeasurementPeriodRepositoryAdapter;
import dz.sh.hidra.modules.custody.infrastructure.persistence.entity.CustodyAgreementJpaEntity;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyAgreementJpaRepository;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyMeasurementPeriodJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
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

class CustodyMeasurementPeriodSemanticRemediationTest {

    private static final Instant START = Instant.parse("2026-10-04T00:00:00Z");
    private static final Instant END = Instant.parse("2026-10-04T01:00:00Z");

    @Test
    void repositoryRejectsMissingAgreement() {
        var periodRepository = mock(CustodyMeasurementPeriodJpaRepository.class);
        var agreementRepository = mock(CustodyAgreementJpaRepository.class);
        var adapter = new JpaCustodyMeasurementPeriodRepositoryAdapter(
                periodRepository,
                agreementRepository
        );
        when(agreementRepository.findById("agreement-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.save(period("transfer-point-1")))
                .isInstanceOf(InvalidCustodyValueException.class)
                .hasMessageContaining("existing CustodyAgreement");

        verify(periodRepository, never()).save(any());
    }

    @Test
    void repositoryRejectsAgreementTransferPointMismatch() {
        var periodRepository = mock(CustodyMeasurementPeriodJpaRepository.class);
        var agreementRepository = mock(CustodyAgreementJpaRepository.class);
        var adapter = new JpaCustodyMeasurementPeriodRepositoryAdapter(
                periodRepository,
                agreementRepository
        );
        var agreement = mock(CustodyAgreementJpaEntity.class);
        when(agreement.transferPointId()).thenReturn("transfer-point-2");
        when(agreementRepository.findById("agreement-1")).thenReturn(Optional.of(agreement));

        assertThatThrownBy(() -> adapter.save(period("transfer-point-1")))
                .isInstanceOf(InvalidCustodyValueException.class)
                .hasMessageContaining("must match");

        verify(periodRepository, never()).save(any());
    }

    @Test
    void repositoryAllowsCoherentAgreementTransferPointPair() {
        var periodRepository = mock(CustodyMeasurementPeriodJpaRepository.class);
        var agreementRepository = mock(CustodyAgreementJpaRepository.class);
        var adapter = new JpaCustodyMeasurementPeriodRepositoryAdapter(
                periodRepository,
                agreementRepository
        );
        var agreement = mock(CustodyAgreementJpaEntity.class);
        when(agreement.transferPointId()).thenReturn("transfer-point-1");
        when(agreementRepository.findById("agreement-1")).thenReturn(Optional.of(agreement));
        when(periodRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CustodyMeasurementPeriod saved = adapter.save(period("transfer-point-1"));

        assertThat(saved.agreementId()).isEqualTo("agreement-1");
        assertThat(saved.transferPointId()).isEqualTo("transfer-point-1");
    }

    @Test
    void migrationEnforcesCompositeAgreementTransferPointRelationship() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql"
        ));

        assertThat(sql).contains("uk_hmr018_custody_agreement_id_transfer_point");
        assertThat(sql).contains("FOREIGN KEY (agreement_id, transfer_point_id)");
        assertThat(sql).contains("REFERENCES hidra_custody_agreement (id, transfer_point_id)");
        assertThat(sql).contains("VALIDATE CONSTRAINT fk_hmr018_custody_period_agreement_transfer_point");
    }

    private static CustodyMeasurementPeriod period(String transferPointId) {
        return new CustodyMeasurementPeriod(
                "period-1",
                "2026-10-04-H01",
                "agreement-1",
                transferPointId,
                START,
                END,
                CustodyPeriodStatus.OPEN,
                null,
                null,
                null,
                null,
                START,
                START
        );
    }
}
