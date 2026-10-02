/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainPersistenceMirrorGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Test
 * @Layer       : Architecture Test
 * @Module      : repository
 * @Package     : dz.sh.hidra
 *
 * @Description : Protects the HRA-060/HRA-061 domain-versus-persistence mirror disposition.
 *
 */
package dz.sh.hidra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DomainPersistenceMirrorGuardrailTest {

    private static final Path CLASSIFICATION =
            Path.of("docs/architecture/domain-persistence-mirror-classification.md");
    private static final Path MODULES_ROOT = Path.of("src/main/java/dz/sh/hidra/modules");

    private static final Pattern MODULE_LINE =
            Pattern.compile("(?m)^- \\*\\*([a-z]+) \\(\\d+\\):\\*\\* (.+)$");
    private static final Pattern BACKTICK_NAME = Pattern.compile("`([^`]+)`");

    @Test
    void hra061RetiredMirrorsStayRetiredAndPersistenceModelsStayOwnedByInfrastructure() throws IOException {
        String markdown = Files.readString(CLASSIFICATION);
        List<Pair> realDomain = pairs(
                markdown,
                "## 6. Pair-by-pair REAL_DOMAIN disposition",
                "## 7. Pair-by-pair READ_PERSISTENCE_MODEL disposition"
        );
        List<Pair> retired = pairs(
                markdown,
                "## 7. Pair-by-pair READ_PERSISTENCE_MODEL disposition",
                "## 8. Boundary-model disposition"
        );

        assertEquals(51, realDomain.size(), "HRA-060 REAL_DOMAIN inventory drifted.");
        assertEquals(343, retired.size(), "HRA-060 READ_PERSISTENCE_MODEL inventory drifted.");

        List<String> violations = new ArrayList<>();
        Map<String, String> mapperSources = new HashMap<>();

        for (Pair pair : retired) {
            mustNotExist(violations, domainPath(pair), "retired domain mirror");
            mustNotExist(violations, portPath(pair), "retired repository port");
            mustNotExist(violations, adapterPath(pair), "retired JPA adapter");
            mustExist(violations, entityPath(pair), "retained JPA entity");
            mustExist(violations, repositoryPath(pair), "retained Spring Data repository");

            String mapper = mapperSources.computeIfAbsent(pair.module(), DomainPersistenceMirrorGuardrailTest::mapperSource);
            if (Pattern.compile("\\b" + Pattern.quote(pair.name()) + "\\b").matcher(mapper).find()) {
                violations.add("retired mirror still referenced by persistence mapper: " + pair.module() + "." + pair.name());
            }
        }

        for (Pair pair : realDomain) {
            mustExist(violations, domainPath(pair), "REAL_DOMAIN record");
            mustExist(violations, entityPath(pair), "REAL_DOMAIN JPA entity");
        }

        assertTrue(violations.isEmpty(), () -> "HRA-061 mirror disposition drifted: " + violations);
    }

    private static List<Pair> pairs(String markdown, String startHeading, String endHeading) {
        int start = markdown.indexOf(startHeading);
        int end = markdown.indexOf(endHeading, start + startHeading.length());
        if (start < 0 || end < 0) {
            throw new IllegalStateException("Classification section is missing: " + startHeading);
        }

        String section = markdown.substring(start + startHeading.length(), end);
        List<Pair> result = new ArrayList<>();
        Matcher moduleMatcher = MODULE_LINE.matcher(section);
        while (moduleMatcher.find()) {
            String module = moduleMatcher.group(1);
            Matcher nameMatcher = BACKTICK_NAME.matcher(moduleMatcher.group(2));
            while (nameMatcher.find()) {
                result.add(new Pair(module, nameMatcher.group(1)));
            }
        }
        return List.copyOf(result);
    }

    private static Path domainPath(Pair pair) {
        return moduleRoot(pair).resolve("domain/model").resolve(pair.name() + ".java");
    }

    private static Path portPath(Pair pair) {
        return moduleRoot(pair).resolve("application/port/out").resolve(pair.name() + "RepositoryPort.java");
    }

    private static Path adapterPath(Pair pair) {
        return moduleRoot(pair).resolve("infrastructure/persistence/adapter")
                .resolve("Jpa" + pair.name() + "RepositoryAdapter.java");
    }

    private static Path entityPath(Pair pair) {
        return moduleRoot(pair).resolve("infrastructure/persistence/entity")
                .resolve(pair.name() + "JpaEntity.java");
    }

    private static Path repositoryPath(Pair pair) {
        return moduleRoot(pair).resolve("infrastructure/persistence/repository")
                .resolve(pair.name() + "JpaRepository.java");
    }

    private static Path moduleRoot(Pair pair) {
        return MODULES_ROOT.resolve(pair.module());
    }

    private static String mapperSource(String module) {
        Path mapperRoot = MODULES_ROOT.resolve(module).resolve("infrastructure/persistence/mapper");
        try (Stream<Path> paths = Files.list(mapperRoot)) {
            List<Path> mappers = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith("PersistenceMapper.java"))
                    .toList();
            if (mappers.size() != 1) {
                throw new IllegalStateException("Expected one persistence mapper for " + module + ", found " + mappers);
            }
            return Files.readString(mappers.getFirst());
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot inspect persistence mapper for " + module, exception);
        }
    }

    private static void mustExist(List<String> violations, Path path, String label) {
        if (!Files.isRegularFile(path)) {
            violations.add(label + " missing: " + path);
        }
    }

    private static void mustNotExist(List<String> violations, Path path, String label) {
        if (Files.exists(path)) {
            violations.add(label + " still present: " + path);
        }
    }

    private record Pair(String module, String name) { }
}
