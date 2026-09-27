/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationEmbeddedMultilingualPersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Verifies embedded Arabic/French/English organization model persistence mappings.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitType;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.model.Shift;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitKind;
import dz.sh.hidra.modules.organization.domain.value.PositionLevel;
import dz.sh.hidra.modules.organization.domain.value.PositionStatus;
import dz.sh.hidra.modules.organization.domain.value.ShiftType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitTypeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.PositionJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ShiftJpaEntity;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * Proves canonical multilingual fields survive organization domain/JPA round trips.
 */
class OrganizationEmbeddedMultilingualPersistenceMapperTest {

    private static final Instant CREATED = Instant.parse("2026-09-27T10:00:00Z");
    private static final Instant UPDATED = Instant.parse("2026-09-27T10:30:00Z");

    @Test
    void mapsOrganizationUnitTypeEmbeddedLanguages() {
        OrganizationUnitType model = new OrganizationUnitType(
                "type-station",
                "STATION",
                OrganizationUnitKind.STATION_UNIT,
                " محطة ",
                " Station ",
                " Station ",
                " وصف عربي ",
                " Description française ",
                " English description ",
                true,
                CREATED,
                UPDATED
        );

        OrganizationUnitTypeJpaEntity entity = OrganizationPersistenceMapper.toEntity(model);
        OrganizationUnitType roundTrip = OrganizationPersistenceMapper.toDomain(entity);

        assertThat(entity.nameAr()).isEqualTo("محطة");
        assertThat(entity.nameFr()).isEqualTo("Station");
        assertThat(entity.nameEn()).isEqualTo("Station");
        assertThat(entity.descriptionAr()).isEqualTo("وصف عربي");
        assertThat(entity.descriptionFr()).isEqualTo("Description française");
        assertThat(entity.descriptionEn()).isEqualTo("English description");
        assertThat(roundTrip).isEqualTo(model);
    }

    @Test
    void mapsPositionEmbeddedDescriptions() {
        Position model = new Position(
                "position-operator",
                "OPERATOR",
                " مشغل ",
                " Opérateur ",
                " Operator ",
                PositionLevel.OPERATOR,
                " وصف ",
                " Exploite les installations ",
                " Operates facilities ",
                PositionStatus.ACTIVE,
                CREATED,
                UPDATED
        );

        PositionJpaEntity entity = OrganizationPersistenceMapper.toEntity(model);
        Position roundTrip = OrganizationPersistenceMapper.toDomain(entity);

        assertThat(entity.descriptionAr()).isEqualTo("وصف");
        assertThat(entity.descriptionFr()).isEqualTo("Exploite les installations");
        assertThat(entity.descriptionEn()).isEqualTo("Operates facilities");
        assertThat(roundTrip).isEqualTo(model);
    }

    @Test
    void mapsShiftEmbeddedNamesAfterCompatibilityColumnRetirement() {
        Shift model = new Shift(
                "shift-day",
                "DAY_SHIFT",
                " الوردية النهارية ",
                " Poste de jour ",
                " Day Shift ",
                ShiftType.DAY,
                "08:00",
                "16:00",
                "Africa/Algiers",
                true,
                CREATED,
                UPDATED
        );

        ShiftJpaEntity entity = OrganizationPersistenceMapper.toEntity(model);
        Shift roundTrip = OrganizationPersistenceMapper.toDomain(entity);

        assertThat(entity.nameAr()).isEqualTo("الوردية النهارية");
        assertThat(entity.nameFr()).isEqualTo("Poste de jour");
        assertThat(entity.nameEn()).isEqualTo("Day Shift");
        assertThat(roundTrip).isEqualTo(model);
    }
}
