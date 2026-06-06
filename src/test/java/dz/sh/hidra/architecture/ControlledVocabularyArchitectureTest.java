/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ControlledVocabularyArchitectureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : architecture
 * @Package     : dz.sh.hidra.architecture
 *
 * @Description : Architecture guardrail preventing multilingual business taxonomies from being
 *                modeled as Java enums.
 *
 */
package dz.sh.hidra.architecture;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Architecture guardrail for HidraAPI controlled vocabularies.
 *
 * <p>Business role:
 * Prevents multilingual, configurable, user-visible business taxonomies from being reintroduced as
 * Java enums after the catalog refactor.
 *
 * <p>Architecture role:
 * This test is intentionally dependency-light and does not require ArchUnit. It scans production
 * Java source files and fails when forbidden business taxonomy names are declared as enums.
 *
 * <p>Validation:
 * Lifecycle/state-machine enums remain allowed by naming convention, while business taxonomy names
 * such as facility type, node type, appurtenance type, valve type, equipment type, product type,
 * connection type, organization unit type, operational scope type, and reporting line type must be
 * implemented as catalog/reference-data models.
 */
class ControlledVocabularyArchitectureTest {

    private static final Path PRODUCTION_SOURCE_ROOT = Path.of("src", "main", "java");

    private static final Set<String> FORBIDDEN_BUSINESS_TAXONOMY_SUFFIXES = Set.of(
            "FacilityType",
            "PipelineAppurtenanceType",
            "ValveType",
            "NodeType",
            "EquipmentType",
            "ProductType",
            "ConnectionType",
            "OrganizationUnitType",
            "OperationalScopeType",
            "ReportingLineType");

    private static final Set<String> ALLOWED_TECHNICAL_ENUM_SUFFIXES = Set.of(
            "Status",
            "State",
            "Direction");

    @Test
    void businessTaxonomiesMustNotBeJavaEnums() throws IOException {
        List<String> violations = new ArrayList<>();

        try (Stream<Path> sourceFiles = Files.walk(PRODUCTION_SOURCE_ROOT)) {
            sourceFiles
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .filter(this::hasForbiddenBusinessTaxonomyName)
                    .forEach(path -> collectEnumViolation(path, violations));
        }

        assertTrue(
                violations.isEmpty(),
                "Business taxonomy concepts must be catalog/reference-data models, not Java enums. "
                        + "Replace these enum declarations with catalog entities and typed references:\n"
                        + String.join("\n", violations));
    }

    @Test
    void technicalStateMachineEnumsRemainAllowedByNamingConvention() {
        assertTrue(isAllowedTechnicalEnumName("TopologyStatus"));
        assertTrue(isAllowedTechnicalEnumName("SortDirection"));
        assertTrue(isAllowedTechnicalEnumName("WorkflowState"));
    }

    private boolean hasForbiddenBusinessTaxonomyName(Path path) {
        String className = path.getFileName().toString().replace(".java", "");
        if (isAllowedTechnicalEnumName(className)) {
            return false;
        }

        return FORBIDDEN_BUSINESS_TAXONOMY_SUFFIXES.stream().anyMatch(className::endsWith);
    }

    private boolean isAllowedTechnicalEnumName(String className) {
        return ALLOWED_TECHNICAL_ENUM_SUFFIXES.stream().anyMatch(className::endsWith);
    }

    private void collectEnumViolation(Path path, List<String> violations) {
        try {
            String className = path.getFileName().toString().replace(".java", "");
            Pattern enumDeclaration = Pattern.compile("(?m)^\\s*(public\\s+)?enum\\s+" + Pattern.quote(className) + "\\b");
            String source = Files.readString(path);

            if (enumDeclaration.matcher(source).find()) {
                violations.add(path.toString().replace('\\', '/'));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect source file: " + path, exception);
        }
    }
}
