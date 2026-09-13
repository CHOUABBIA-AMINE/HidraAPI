/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalDocumentBinaryStorageAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.storage
 *
 * @Description : Verifies deterministic local document binary persistence and checksum evidence.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.storage;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LocalDocumentBinaryStorageAdapterTest {

    @TempDir
    Path tempDir;

    @Test
    void storesReadsAndChecksumsBinaryContent() throws Exception {
        LocalDocumentBinaryStorageAdapter adapter = new LocalDocumentBinaryStorageAdapter(tempDir.toString());
        byte[] payload = "HyFlo document evidence".getBytes(StandardCharsets.UTF_8);

        var stored = adapter.store("version-object-1", new ByteArrayInputStream(payload));

        assertThat(stored.storageProviderId()).isEqualTo("local-filesystem");
        assertThat(stored.bucketOrContainer()).isEqualTo("documents");
        assertThat(stored.objectKey()).isEqualTo("version-object-1");
        assertThat(stored.contentLengthBytes()).isEqualTo(payload.length);
        assertThat(stored.checksumAlgorithm()).isEqualTo("SHA-256");
        assertThat(stored.checksumValue()).hasSize(64);
        assertThat(adapter.available("version-object-1")).isTrue();
        try (var input = adapter.open("version-object-1")) {
            assertThat(input.readAllBytes()).isEqualTo(payload);
        }
    }
}
