/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainPersistenceMirrorGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Test
 * @Layer       : Architecture Test
 * @Module      : repository
 * @Package     : dz.sh.hidra
 *
 * @Description : Protects the historical mirror disposition and exact approved live-domain reclassifications.
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
    private static final Pair ALARM_LIFECYCLE_EVENT = new Pair("alarm", "AlarmLifecycleEvent");

    private static final Pattern MODULE_LINE =
            Pattern.compile("(?m)^- \\*\\*([a-z]+) \\(\\d+\\):\\*\\* (.+)$");
    private static final Pattern BACKTICK_NAME = Pattern.compile("`([^`]+)`");

    @Test
    void hra061RetiredMirrorsStayRetiredAndPersistenceModelsStayOwnedByInfrastructure() throws IOException {
        String markdown = Files.readString(CLASSIFICATION);
        List<Pair> realDomain = new ArrayList<>(pairs(
                markdown,
                "## 6. Pair-by-pair REAL_DOMAIN disposition",
                "## 7. Pair-by-pair READ_PERSISTENCE_MODEL disposition"
        ));
        List<Pair> retired = new ArrayList<>(pairs(
                markdown,
                "## 7. Pair-by-pair READ_PERSISTENCE_MODEL disposition",
                "## 8. Boundary-model disposition"
        ));

        assertEquals(51, realDomain.size(), "HRA-060 REAL_DOMAIN inventory drifted.");
        assertEquals(343, retired.size(), "HRA-060 READ_PERSISTENCE_MODEL inventory drifted.");

        // Accepted ALRM-PREREQ-01 restored this one split; the historical cohort stays intact.
        assertTrue(retired.remove(ALARM_LIFECYCLE_EVENT), "Historical AlarmLifecycleEvent entry is missing.");
        assertTrue(!realDomain.contains(ALARM_LIFECYCLE_EVENT), "Historical cohort contains a duplicate reclassification.");
        realDomain.add(ALARM_LIFECYCLE_EVENT);
        assertEquals(52, realDomain.size(), "Effective REAL_DOMAIN inventory drifted.");
        assertEquals(342, retired.size(), "Effective retired inventory drifted.");

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

        mustExist(violations, portPath(ALARM_LIFECYCLE_EVENT), "approved lifecycle event port");
        mustExist(violations, adapterPath(ALARM_LIFECYCLE_EVENT), "approved lifecycle event adapter");
        mustExist(violations, repositoryPath(ALARM_LIFECYCLE_EVENT), "approved lifecycle event repository");
        String alarmMapper = mapperSources.computeIfAbsent("alarm", DomainPersistenceMirrorGuardrailTest::mapperSource);
        if (!Pattern.compile("\\bAlarmLifecycleEvent\\b").matcher(alarmMapper).find()) {
            violations.add("approved lifecycle event mapper is missing");
        }

        Path policy = moduleRoot(ALARM_LIFECYCLE_EVENT).resolve("domain/policy/AlarmShelvingPolicy.java");
        mustExist(violations, policy, "live lifecycle event domain consumer");
        if (Files.isRegularFile(policy) && !Pattern.compile(
                "restorationState\\s*\\(\\s*Alarm\\s+\\w+\\s*,\\s*AlarmLifecycleEvent\\s+\\w+\\s*\\)"
        ).matcher(Files.readString(policy)).find()) {
            violations.add("AlarmShelvingPolicy no longer consumes authoritative lifecycle event evidence");
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
