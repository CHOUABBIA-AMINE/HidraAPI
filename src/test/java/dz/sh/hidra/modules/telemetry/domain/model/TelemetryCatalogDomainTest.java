/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogDomainTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Domain tests for telemetry catalog and translations.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeTranslationId;

/**
 * Domain tests for telemetry catalog and translations.
 */
class TelemetryCatalogDomainTest {

    @Test
    void shouldNormalizeCatalogNameAndResolveTranslationsByLocale() {
        TelemetryTypeCatalog catalog = TelemetryDomainTestFixtures.catalogWithMandatoryTranslations(true);

        assertEquals("POINT_TYPE", catalog.catalogName());
        assertTrue(catalog.matchesCatalog("point type"));
        assertEquals("Pression", catalog.defaultTranslation().orElseThrow().name().value());
        assertEquals("Pressure", catalog.translationForLocale("en_DZ").orElseThrow().name().value());
    }

    @Test
    void shouldNormalizeTranslationLocaleToShortLanguageTag() {
        TelemetryTypeCatalogId typeId = TelemetryTypeCatalogId.of("catalog-point-type-pressure");

        TelemetryTypeTranslation translation = TelemetryTypeTranslation.restore(
                TelemetryTypeTranslationId.of("translation-fr-dz"),
                typeId,
                "fr_DZ",
                TelemetryName.of("Pression"),
                " Description ",
                TelemetryDomainTestFixtures.CREATED_AT,
                TelemetryDomainTestFixtures.UPDATED_AT);

        assertEquals("fr", translation.locale());
        assertEquals("Description", translation.description());
    }

    @Test
    void shouldRejectTranslationsThatBelongToAnotherCatalogEntry() {
        TelemetryTypeCatalogId catalogId = TelemetryTypeCatalogId.of("catalog-point-type-pressure");
        TelemetryTypeTranslation foreignTranslation = TelemetryDomainTestFixtures.translation(
                TelemetryTypeCatalogId.of("catalog-point-type-temperature"),
                "fr",
                "Température");

        assertThrows(BusinessRuleViolationException.class, () -> TelemetryTypeCatalog.restore(
                catalogId,
                "POINT_TYPE",
                TelemetryCode.of("PRESSURE"),
                true,
                10,
                true,
                List.of(foreignTranslation),
                TelemetryDomainTestFixtures.CREATED_AT,
                TelemetryDomainTestFixtures.UPDATED_AT));
    }
}
