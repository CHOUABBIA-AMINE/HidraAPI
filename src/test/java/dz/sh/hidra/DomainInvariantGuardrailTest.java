/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainInvariantGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Test
 * @Layer       : Architecture Test
 * @Module      : repository
 * @Package     : dz.sh.hidra
 *
 * @Description : Locks the surviving HRA-051 domain invariants after HRA-061 retires persistence-only mirrors.
 *
 */
package dz.sh.hidra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DomainInvariantGuardrailTest {

    private static final String REQUIRED_MARKER = "// HRA-051 required:";
    private static final String ORDER_MARKER = "// HRA-051 order:";
    private static final String SELF_REFERENCE_MARKER = "// HRA-051 self-reference:";

    @Test
    void hra051ClassifiedDomainInvariantBatchRemainsComplete() throws IOException {
        Path domainRoot = Path.of("src/main/java/dz/sh/hidra/modules");
        AtomicInteger required = new AtomicInteger();
        AtomicInteger ordering = new AtomicInteger();
        AtomicInteger selfReference = new AtomicInteger();
        AtomicInteger touchedRecords = new AtomicInteger();

        try (Stream<Path> paths = Files.walk(domainRoot)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().contains("/domain/model/"))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        String source = read(path);
                        int requiredCount = occurrences(source, REQUIRED_MARKER);
                        int orderCount = occurrences(source, ORDER_MARKER);
                        int selfReferenceCount = occurrences(source, SELF_REFERENCE_MARKER);
                        if (requiredCount + orderCount + selfReferenceCount > 0) {
                            touchedRecords.incrementAndGet();
                        }
                        required.addAndGet(requiredCount);
                        ordering.addAndGet(orderCount);
                        selfReference.addAndGet(selfReferenceCount);
                    });
        }

        // HRA-061 deliberately retired 1,497 HRA-051 markers with 343 persistence-only domain mirrors.
        assertEquals(583, required.get(), "Surviving HRA-051 required-field guard count drifted.");
        assertEquals(24, ordering.get(), "Surviving HRA-051 temporal-order guard count drifted.");
        assertEquals(3, selfReference.get(), "Surviving HRA-051 self-reference guard count drifted.");
        assertEquals(610, required.get() + ordering.get() + selfReference.get(),
                "Surviving HRA-051 total invariant guard count drifted.");
        assertEquals(114, touchedRecords.get(), "Surviving HRA-051 touched-record inventory drifted.");
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read domain source: " + path, exception);
        }
    }

    private static int occurrences(String source, String marker) {
        int count = 0;
        int fromIndex = 0;
        while ((fromIndex = source.indexOf(marker, fromIndex)) >= 0) {
            count++;
            fromIndex += marker.length();
        }
        return count;
    }
}
