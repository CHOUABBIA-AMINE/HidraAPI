/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForensicRemediationClosureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Test
 * @Layer       : Architecture Test
 * @Module      : repository
 * @Package     : dz.sh.hidra
 *
 * @Description : Replays HRA-120 closure-critical checks and rejects REST/domain representation leakage.
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ForensicRemediationClosureTest {

    private static final Path ROOT = Path.of("src/main/java/dz/sh/hidra");
    private static final Path MODULES = ROOT.resolve("modules");
    private static final Pattern PACKAGE =
            Pattern.compile("(?m)^\\s*package\\s+([\\w.]+)\\s*;");
    private static final Pattern IMPORT =
            Pattern.compile("(?m)^\\s*import\\s+(?:static\\s+)?([\\w.]+)\\s*;");
    private static final Pattern TYPE = Pattern.compile(
            "(?m)^\\s*(?:public\\s+)?(?:abstract\\s+|final\\s+|sealed\\s+|non-sealed\\s+)*"
                    + "(class|record|enum|interface)\\s+(\\w+)"
    );

    private static final Set<String> EXPORTED_PACKAGES = Set.of(
            "dz.sh.hidra.modules.workflow.application.contract.planning",
            "dz.sh.hidra.modules.workflow.application.contract.organization",
            "dz.sh.hidra.modules.workflow.application.contract.alarm",
            "dz.sh.hidra.modules.topology.application.contract.organization",
            "dz.sh.hidra.modules.audit.application.contract.organization",
            "dz.sh.hidra.modules.audit.application.contract.alarm"
    );

    private static final Set<String> EXPECTED_WIRE_BLOCKER = Set.of();

    @Test
    void fictionalModuleEventScaffoldingRemainsAbsent() throws IOException {
        List<String> violations = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(MODULES)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        String normalized = path.toString().replace('\\', '/');
                        String name = path.getFileName().toString();
                        if (normalized.contains("/domain/event/")) {
                            violations.add(normalized);
                        }
                        if (name.contains("DomainEventPublisher")
                                || name.matches(".*EventPublisher.*\\.java")) {
                            violations.add(normalized);
                        }
                    });
        }
        assertTrue(violations.isEmpty(), () -> "Fictional event scaffolding returned: " + violations);
    }

    @Test
    void crossModulePrivateImportsRemainClosed() throws IOException {
        List<String> violations = new ArrayList<>();
        for (SourceType source : sources().values()) {
            String sourceModule = module(source.packageName());
            if (sourceModule == null) {
                continue;
            }
            Matcher imports = IMPORT.matcher(source.source());
            while (imports.find()) {
                String imported = imports.group(1);
                String importedPackage = packageName(imported);
                String targetModule = module(importedPackage);
                if (targetModule == null || sourceModule.equals(targetModule)) {
                    continue;
                }
                if (!isPrivate(importedPackage)) {
                    continue;
                }
                if (EXPORTED_PACKAGES.stream().anyMatch(imported::startsWith)) {
                    continue;
                }
                violations.add(source.fqcn() + " -> " + imported);
            }
        }
        assertTrue(violations.isEmpty(), () -> "Unapproved cross-module imports: " + violations);
    }

    @Test
    void directWireDomainRepresentationLeakageRemainsAbsent() throws IOException {
        Map<Path, SourceType> sources = sources();
        Map<String, String> kinds = new HashMap<>();
        for (SourceType source : sources.values()) {
            kinds.put(source.fqcn(), source.kind());
        }

        Set<String> blockers = new HashSet<>();
        for (SourceType source : sources.values()) {
            String path = source.path().toString().replace('\\', '/');
            if (!path.contains("/api/rest/request/") && !path.contains("/api/rest/response/")) {
                continue;
            }
            Matcher imports = IMPORT.matcher(source.source());
            while (imports.find()) {
                String imported = imports.group(1);
                if (!imported.contains(".domain.") || "enum".equals(kinds.get(imported))) {
                    continue;
                }
                blockers.add(source.fqcn() + " -> " + imported);
            }
        }

        assertEquals(
                EXPECTED_WIRE_BLOCKER,
                blockers,
                "HRA-102 forbids REST request/response exposure of non-enum domain representation."
        );
    }

    @Test
    void residualProjectionVocabularyRemainsRemovedAndDocumented() throws IOException {
        Path projectionType = Path.of(
                "src/main/java/dz/sh/hidra/modules/topology/domain/value/ProjectionType.java"
        );
        String classification =
                Files.readString(Path.of("docs/architecture/static-orphan-classification.md"));
        String closure =
                Files.readString(Path.of("docs/architecture/forensic-remediation-closure.md"));

        assertTrue(
                Files.notExists(projectionType),
                "HRA-122 removed the zero-incoming Topology ProjectionType residual."
        );
        assertTrue(
                classification.contains("topology.ProjectionType")
                        && classification.contains("DELETE_RESIDUAL")
                        && classification.contains("REMOVED_HRA_122"),
                "ProjectionType removal must retain its evidence-backed orphan disposition."
        );
        assertTrue(
                closure.contains("## HRA-122 corrective follow-up"),
                "Forensic closure evidence must record the HRA-122 corrective removal."
        );
    }

    @Test
    void finalClosureEvidenceRecordsCompletedRerun() throws IOException {
        String closure =
                Files.readString(Path.of("docs/architecture/forensic-remediation-closure.md"));
        String roadmap =
                Files.readString(Path.of("docs/roadmap/repository-remediation.md"));

        assertTrue(
                closure.contains("**HRA-120 status: COMPLETED.**")
                        && closure.contains("d6d1454b816b819bc90c2c4ef3e382a59cd6d4c0"),
                "Final HRA-120 evidence must record the successful exact-head rerun."
        );
        assertTrue(
                roadmap.contains("| `HRA-120`")
                        && roadmap.contains("**Completed** — final rerun on exact head"),
                "Repository remediation roadmap must keep HRA-120 closed after the successful rerun."
        );
    }

    private static Map<Path, SourceType> sources() throws IOException {
        Map<Path, SourceType> result = new HashMap<>();
        try (Stream<Path> paths = Files.walk(ROOT)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        String source = read(path);
                        Matcher packageMatcher = PACKAGE.matcher(source);
                        Matcher typeMatcher = TYPE.matcher(source);
                        if (!packageMatcher.find() || !typeMatcher.find()) {
                            return;
                        }
                        String packageName = packageMatcher.group(1);
                        String simpleName = typeMatcher.group(2);
                        result.put(path, new SourceType(
                                path, source, packageName, typeMatcher.group(1),
                                packageName + "." + simpleName
                        ));
                    });
        }
        return Map.copyOf(result);
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read source: " + path, exception);
        }
    }

    private static boolean isPrivate(String packageName) {
        return packageName.contains(".domain.")
                || packageName.contains(".infrastructure.")
                || packageName.contains(".application.");
    }

    private static String module(String packageName) {
        String prefix = "dz.sh.hidra.modules.";
        if (!packageName.startsWith(prefix)) {
            return null;
        }
        String remainder = packageName.substring(prefix.length());
        int separator = remainder.indexOf('.');
        return separator < 0 ? remainder : remainder.substring(0, separator);
    }

    private static String packageName(String className) {
        int separator = className.lastIndexOf('.');
        return separator < 0 ? "" : className.substring(0, separator);
    }

    private record SourceType(
            Path path,
            String source,
            String packageName,
            String kind,
            String fqcn
    ) { }
}
