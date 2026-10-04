/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrixCellSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Risk Test
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.semantic
 *
 * @Description : Verifies HMR-029 matrix-coordinate, score and catalog-family semantics.
 *
 */
package dz.sh.hidra.modules.risk.semantic;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.model.RiskMatrixCell;
import dz.sh.hidra.modules.risk.infrastructure.persistence.adapter.JpaRiskMatrixCellRepositoryAdapter;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskMatrixCellJpaRepository;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RiskMatrixCellSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void domainRejectsNegativeScoreValue() {
        assertThatThrownBy(() -> cell(new BigDecimal("-0.000001")))
                .isInstanceOf(InvalidRiskValueException.class)
                .hasMessageContaining("score value must be non-negative");
    }

    @Test
    void adapterRejectsWrongLikelihoodCatalogFamily() {
        RiskMatrixCellJpaRepository repository = mock(RiskMatrixCellJpaRepository.class);
        JpaRiskMatrixCellRepositoryAdapter adapter =
                new JpaRiskMatrixCellRepositoryAdapter(repository);

        when(repository.existsCatalogEntryInFamily(
                "likelihood-1",
                "RISK_LIKELIHOOD_LEVEL"
        )).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(cell(BigDecimal.ONE)))
                .isInstanceOf(InvalidRiskValueException.class)
                .hasMessageContaining("RISK_LIKELIHOOD_LEVEL");
    }

    @Test
    void adapterRejectsWrongConsequenceCatalogFamily() {
        RiskMatrixCellJpaRepository repository = mock(RiskMatrixCellJpaRepository.class);
        JpaRiskMatrixCellRepositoryAdapter adapter =
                new JpaRiskMatrixCellRepositoryAdapter(repository);

        when(repository.existsCatalogEntryInFamily(
                "likelihood-1",
                "RISK_LIKELIHOOD_LEVEL"
        )).thenReturn(true);
        when(repository.existsCatalogEntryInFamily(
                "consequence-1",
                "RISK_CONSEQUENCE_LEVEL"
        )).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(cell(BigDecimal.ONE)))
                .isInstanceOf(InvalidRiskValueException.class)
                .hasMessageContaining("RISK_CONSEQUENCE_LEVEL");
    }

    @Test
    void adapterRejectsDuplicateMatrixCoordinate() {
        RiskMatrixCellJpaRepository repository = mock(RiskMatrixCellJpaRepository.class);
        JpaRiskMatrixCellRepositoryAdapter adapter =
                new JpaRiskMatrixCellRepositoryAdapter(repository);

        when(repository.existsCatalogEntryInFamily(
                "likelihood-1",
                "RISK_LIKELIHOOD_LEVEL"
        )).thenReturn(true);
        when(repository.existsCatalogEntryInFamily(
                "consequence-1",
                "RISK_CONSEQUENCE_LEVEL"
        )).thenReturn(true);
        when(repository.existsOtherAtCoordinate(
                "cell-1",
                "matrix-1",
                "likelihood-1",
                "consequence-1"
        )).thenReturn(true);

        assertThatThrownBy(() -> adapter.save(cell(BigDecimal.ONE)))
                .isInstanceOf(InvalidRiskValueException.class)
                .hasMessageContaining("coordinate must be unique");
    }

    @Test
    void migrationMakesAllThreeSemanticsAuthoritative() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_029__hmr_029_risk_risk_matrix_cell.sql"
        ));

        assertThat(sql).contains("uq_hmr029_risk_matrix_cell_coordinate");
        assertThat(sql).contains("ck_hmr029_risk_matrix_cell_score_nonnegative");
        assertThat(sql).contains("score_value >= 0");
        assertThat(sql).contains("RISK_LIKELIHOOD_LEVEL");
        assertThat(sql).contains("RISK_CONSEQUENCE_LEVEL");
        assertThat(sql).contains("trg_hmr029_risk_matrix_cell_catalog_families");
    }

    private static RiskMatrixCell cell(BigDecimal score) {
        return new RiskMatrixCell(
                "cell-1",
                "matrix-1",
                "likelihood-1",
                "consequence-1",
                score,
                "rating-1",
                null,
                true,
                false,
                false,
                NOW,
                NOW
        );
    }
}
