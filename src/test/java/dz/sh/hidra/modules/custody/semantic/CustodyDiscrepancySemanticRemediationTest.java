/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancySemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Custody Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.semantic
 *
 * @Description : Verifies HMR-027 optional quantity-unit integrity.
 *
 */
package dz.sh.hidra.modules.custody.semantic;

import dz.sh.hidra.modules.custody.domain.model.CustodyDiscrepancy;
import dz.sh.hidra.modules.custody.domain.value.CustodyDiscrepancyStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustodyDiscrepancySemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void quantityUnitRemainsOptionalAtTheDomainBoundary() {
        CustodyDiscrepancy discrepancy = new CustodyDiscrepancy(
                "discrepancy-1",
                "DISC-1",
                "reconciliation-1",
                "discrepancy-type-1",
                CustodyDiscrepancyStatus.OPEN,
                null,
                null,
                null,
                null,
                null,
                null,
                NOW,
                null,
                null,
                NOW,
                NOW
        );

        assertThat(discrepancy.quantityUnitId()).isNull();
    }

    @Test
    void migrationProtectsEveryPopulatedQuantityUnitReference() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_027__hmr_027_custody_custody_discrepancy.sql"
        ));

        assertThat(sql).contains("fk_hmr027_custody_discrepancy_quantity_unit");
        assertThat(sql).contains("FOREIGN KEY (quantity_unit_id)");
        assertThat(sql).contains("REFERENCES hidra_custody_catalog_entry (id)");
        assertThat(sql).contains("ON DELETE RESTRICT");
        assertThat(sql).contains("NOT VALID");
        assertThat(sql).contains("VALIDATE CONSTRAINT");
    }
}
