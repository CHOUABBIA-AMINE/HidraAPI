/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Verifies HMR-020 Position level nullability alignment.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.value.PositionStatus;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.PositionJpaEntity;
import jakarta.persistence.Column;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void domainStillRequiresPositionLevel() {
        assertThatThrownBy(() -> new Position(
                "position-1",
                "PIPELINE_ENGINEER",
                null,
                "Ingénieur pipeline",
                null,
                null,
                null,
                null,
                null,
                PositionStatus.ACTIVE,
                NOW,
                NOW
        )).isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("level is required");
    }

    @Test
    void jpaColumnMarksLevelAsNonNull() throws Exception {
        Column column = PositionJpaEntity.class
                .getDeclaredField("level")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isFalse();
    }

    @Test
    void additiveMigrationBlocksNewNullLevelsAndPromotesCleanSchemaToNotNull() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_020__hmr_020_organization_position.sql"
        ));

        assertThat(sql).contains("ck_hmr020_position_level_not_null");
        assertThat(sql).contains("CHECK (level IS NOT NULL)");
        assertThat(sql).contains("NOT VALID");
        assertThat(sql).contains("WHERE level IS NULL");
        assertThat(sql).contains("ALTER COLUMN level SET NOT NULL");
        assertThat(sql).doesNotContain("UPDATE hidra_org_position");
        assertThat(sql).doesNotContain("DROP COLUMN");
    }
}
