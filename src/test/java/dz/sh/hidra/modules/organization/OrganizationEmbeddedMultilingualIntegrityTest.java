/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationEmbeddedMultilingualIntegrityTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Protects the organization-only Arabic/French/English embedded multilingual storage convention.
 *
 */
package dz.sh.hidra.modules.organization;

import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeDistrict;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeLocality;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeState;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitType;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.model.Shift;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.AdministrativeDistrictJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.AdministrativeLocalityJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.AdministrativeStateJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitTypeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.PositionJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ShiftJpaEntity;
import jakarta.persistence.Column;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

/**
 * Final ORG-040 guardrails for the organization embedded multilingual correction.
 *
 * <p>Business role: keeps Arabic, French, and English user-facing organization catalog
 * attributes on their owning entities.
 *
 * <p>Architecture role: prevents reintroduction of a separate organization translation
 * domain/JPA model and protects the post-ORG-039 schema direction.
 *
 * <p>Scope: explicit known translatable organization attributes only. Employee
 * transliteration fields and transactional/free-form text are intentionally excluded.
 */
class OrganizationEmbeddedMultilingualIntegrityTest {

    private static final String ORGANIZATION_PACKAGE = "dz.sh.hidra.modules.organization";
    private static final String LEGACY_TRANSLATION_TABLE = "hidra_org_unit_type_translation";
    private static final String RETIREMENT_MIGRATION =
            "V20260927_003__retire_organization_unit_type_translation_table.sql";

    private static final JavaClasses ORGANIZATION_PRODUCTION_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(ORGANIZATION_PACKAGE);

    @Test
    void canonicalDomainModelsExposeCompleteArabicFrenchEnglishTriplets() {
        Map<Class<?>, List<String>> expectedTriplets = Map.of(
                AdministrativeState.class, List.of("name"),
                AdministrativeDistrict.class, List.of("name"),
                AdministrativeLocality.class, List.of("name"),
                OrganizationUnit.class, List.of("name"),
                OrganizationUnitType.class, List.of("name", "description"),
                Position.class, List.of("title", "description"),
                Shift.class, List.of("name")
        );

        expectedTriplets.forEach(OrganizationEmbeddedMultilingualIntegrityTest::assertRecordTriplets);
    }

    @Test
    void canonicalJpaEntitiesPersistCompleteArabicFrenchEnglishTriplets() {
        Map<Class<?>, List<String>> expectedTriplets = Map.of(
                AdministrativeStateJpaEntity.class, List.of("name"),
                AdministrativeDistrictJpaEntity.class, List.of("name"),
                AdministrativeLocalityJpaEntity.class, List.of("name"),
                OrganizationUnitJpaEntity.class, List.of("name"),
                OrganizationUnitTypeJpaEntity.class, List.of("name", "description"),
                PositionJpaEntity.class, List.of("title", "description"),
                ShiftJpaEntity.class, List.of("name")
        );

        expectedTriplets.forEach((type, bases) ->
                bases.forEach(base -> assertJpaTriplet(type, base))
        );
    }

    @Test
    void existingOrganizationUnitApplicationAndApiContractsExposeAllThreeNames() {
        assertRecordTriplets(CreateOrganizationUnitRequest.class, List.of("name"));
        assertRecordTriplets(CreateOrganizationUnitCommand.class, List.of("name"));
        assertRecordTriplets(OrganizationUnitSummaryDto.class, List.of("name"));
        assertRecordTriplets(OrganizationUnitResponse.class, List.of("name"));
        assertRecordTriplets(
                OrganizationAdministrationQueryUseCase.OrganizationUnitView.class,
                List.of("name")
        );
    }

    @Test
    void organizationProductionCodeMustNotContainSeparateTranslationTypesOrJpaTables()
            throws IOException {
        List<String> translationTypes = StreamSupport
                .stream(ORGANIZATION_PRODUCTION_CLASSES.spliterator(), false)
                .filter(javaClass -> javaClass.getPackageName().startsWith(ORGANIZATION_PACKAGE))
                .filter(javaClass ->
                        javaClass.getSimpleName().toLowerCase(Locale.ROOT).contains("translation")
                )
                .map(javaClass -> javaClass.getName())
                .sorted()
                .toList();

        assertThat(translationTypes)
                .as("Organization must not reintroduce a separate translation production type")
                .isEmpty();

        Path sourceRoot = Path.of("src/main/java/dz/sh/hidra/modules/organization");
        assertThat(sourceRoot).isDirectory();

        List<Path> translationTableMappings;
        try (var paths = Files.walk(sourceRoot)) {
            translationTableMappings = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(OrganizationEmbeddedMultilingualIntegrityTest::mapsLegacyTranslationTable)
                    .sorted()
                    .toList();
        }

        assertThat(translationTableMappings)
                .as("Organization JPA/runtime code must not map the retired translation table")
                .isEmpty();
    }

    @Test
    void migrationsAfterOrg039MustNotReintroduceTheRetiredTranslationTable() throws IOException {
        Path migrationRoot = Path.of("src/main/resources/db/migration");
        Path retirementMigration = migrationRoot.resolve(RETIREMENT_MIGRATION);

        assertThat(retirementMigration).exists();
        assertThat(Files.readString(retirementMigration))
                .containsIgnoringCase("DROP TABLE " + LEGACY_TRANSLATION_TABLE);

        List<Path> violations;
        try (var paths = Files.list(migrationRoot)) {
            violations = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("V"))
                    .filter(path -> path.getFileName().toString().compareTo(RETIREMENT_MIGRATION) > 0)
                    .filter(OrganizationEmbeddedMultilingualIntegrityTest::referencesLegacyTranslationTable)
                    .sorted()
                    .toList();
        }

        assertThat(violations)
                .as("No migration after ORG-039 may restore or depend on the retired translation table")
                .isEmpty();
    }

    private static void assertRecordTriplets(Class<?> type, List<String> bases) {
        assertThat(type.isRecord())
                .as("%s must remain a record for record-component integrity inspection", type.getName())
                .isTrue();

        List<String> components = Arrays.stream(type.getRecordComponents())
                .map(RecordComponent::getName)
                .toList();

        for (String base : bases) {
            assertThat(components)
                    .as("%s must expose complete %sAr/%sFr/%sEn fields", type.getName(), base, base, base)
                    .contains(base + "Ar", base + "Fr", base + "En");
        }
    }

    private static void assertJpaTriplet(Class<?> type, String base) {
        assertJpaLanguageField(type, base, "Ar", "ar");
        assertJpaLanguageField(type, base, "Fr", "fr");
        assertJpaLanguageField(type, base, "En", "en");
    }

    private static void assertJpaLanguageField(
            Class<?> type,
            String base,
            String javaSuffix,
            String columnSuffix
    ) {
        Field field;
        try {
            field = type.getDeclaredField(base + javaSuffix);
        } catch (NoSuchFieldException exception) {
            throw new AssertionError(
                    type.getName() + " must declare " + base + javaSuffix,
                    exception
            );
        }

        Column column = field.getAnnotation(Column.class);
        assertThat(column)
                .as("%s.%s must be mapped with @Column", type.getName(), field.getName())
                .isNotNull();
        assertThat(column.name())
                .as("%s.%s must use the explicit language-specific database column",
                        type.getName(), field.getName())
                .isEqualTo(toSnakeCase(base) + "_" + columnSuffix);
    }

    private static String toSnakeCase(String value) {
        return value.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
    }

    private static boolean mapsLegacyTranslationTable(Path path) {
        try {
            String content = Files.readString(path).toLowerCase(Locale.ROOT);
            return content.contains("@table")
                    && content.contains(LEGACY_TRANSLATION_TABLE);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect " + path, exception);
        }
    }

    private static boolean referencesLegacyTranslationTable(Path path) {
        try {
            return Files.readString(path)
                    .toLowerCase(Locale.ROOT)
                    .contains(LEGACY_TRANSLATION_TABLE);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect " + path, exception);
        }
    }
}
