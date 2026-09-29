/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeEvidenceInventoryTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Pins the read-only ORG-023 evidence inventory and remaining legacy compatibility consumers.
 *
 */
package dz.sh.hidra.modules.organization;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OperationalScopeEvidenceInventoryTest {

    private static final Path ORGANIZATION_SOURCE =
            Path.of("src/main/java/dz/sh/hidra/modules/organization");

    private static final Set<String> EXPECTED_LEGACY_SCOPE_CONSUMERS = Set.of(
            "domain/model/EmployeeAssignment.java",
            "domain/model/OrganizationUnit.java",
            "domain/model/ResponsibilityAssignment.java",
            "infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java",
            "infrastructure/persistence/entity/OrganizationUnitJpaEntity.java",
            "infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java",
            "infrastructure/persistence/mapper/OrganizationPersistenceMapper.java"
    );

    private static final Pattern FORBIDDEN_SQL =
            Pattern.compile("(?i)\\b(insert|update|delete|merge|alter|create|drop|truncate|grant|revoke|call|do)\\b");

    @Test
    void legacyOperationalScopeCompatibilityConsumersRemainExplicitAndBounded() throws IOException {
        Set<String> consumers = new TreeSet<>();

        try (Stream<Path> paths = Files.walk(ORGANIZATION_SOURCE)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        String source = read(path);
                        if (source.contains("operationalScopeId")
                                || source.contains("operationalScopeType")
                                || source.contains("operationalScopeCode")
                                || source.contains("operationalScopeName")) {
                            consumers.add(
                                    ORGANIZATION_SOURCE.relativize(path)
                                            .toString()
                                            .replace('\\', '/')
                            );
                        }
                    });
        }

        assertThat(consumers)
                .as("Any new legacy operational-scope consumer must be reviewed under ORG-023/031.")
                .containsExactlyInAnyOrderElementsOf(EXPECTED_LEGACY_SCOPE_CONSUMERS);
    }

    @Test
    void legacyAssessmentSqlRemainsReadOnly() throws IOException {
        String sql = Files.readString(
                Path.of(
                        "docs/data-provisioning/operational-scope/"
                                + "ORG-037-legacy-scope-audit.sql"
                )
        );

        String executable = stripLineComments(sql);
        assertThat(FORBIDDEN_SQL.matcher(executable).find())
                .as("ORG-023/037 assessment SQL must remain read-only.")
                .isFalse();

        for (String statement : executable.split(";")) {
            String normalized = statement.trim();
            if (normalized.isEmpty()) {
                continue;
            }
            assertThat(normalized)
                    .as("Every executable ORG-023 assessment statement must be SELECT/CTE-only.")
                    .matches("(?is)^(select|with)\\b.*");
        }
    }

    @Test
    void currentInventoryKeepsExternalEvidenceGateExplicit() throws IOException {
        String inventory = Files.readString(
                Path.of("docs/data-provisioning/org-023-operational-scope-inventory.md")
        );

        assertThat(inventory)
                .contains("Current source refresh head")
                .contains("Authorized tuple profile")
                .contains("Owner-certified typed-ID validation")
                .contains("External-consumer sign-off")
                .contains("ORG-023 overall status: Blocked");
    }

    private static String stripLineComments(String sql) {
        return sql.lines()
                .map(line -> {
                    int comment = line.indexOf("--");
                    return comment >= 0 ? line.substring(0, comment) : line;
                })
                .reduce("", (left, right) -> left + "\n" + right);
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read source: " + path, exception);
        }
    }
}
