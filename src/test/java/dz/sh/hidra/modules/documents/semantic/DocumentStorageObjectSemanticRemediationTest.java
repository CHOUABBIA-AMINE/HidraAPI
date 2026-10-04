/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageObjectSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Documents Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.semantic
 *
 * @Description : Verifies HMR-008 DocumentStorageObject semantic remediation.
 *
 */
package dz.sh.hidra.modules.documents.semantic;

import dz.sh.hidra.modules.documents.application.port.out.DocumentBinaryStoragePort;
import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import dz.sh.hidra.modules.documents.domain.model.DocumentStorageObject;
import dz.sh.hidra.modules.documents.domain.value.DocumentStorageStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentStorageObjectSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsMissingIntegrityMetadataAndCreationTime() {
        assertThatThrownBy(() -> object("application/pdf", null, "abc", NOW))
                .isInstanceOf(InvalidDocumentValueException.class)
                .hasMessageContaining("checksum algorithm");

        assertThatThrownBy(() -> object("application/pdf", "SHA-256", "abc", null))
                .isInstanceOf(InvalidDocumentValueException.class)
                .hasMessageContaining("createdAt");
    }

    @Test
    void storedBinaryRejectsSignedUrlOrCredentialBearingObjectKey() {
        assertThatThrownBy(() -> new DocumentBinaryStoragePort.StoredBinary(
                "provider-1", "documents",
                "https://storage.example/object?X-Amz-Signature=secret",
                false, null, 1L, "SHA-256", "abc"
        )).isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("opaque");
    }

    @Test
    void migrationEnforcesStorageMetadataConstraints() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql"
        ));

        assertThat(sql).contains("CHECK (content_length_bytes >= 0)");
        assertThat(sql).contains("btrim(content_type) <> ''");
        assertThat(sql).contains("btrim(checksum_algorithm) <> ''");
        assertThat(sql).contains("btrim(checksum_value) <> ''");
    }

    private static DocumentStorageObject object(
            String contentType,
            String checksumAlgorithm,
            String checksumValue,
            Instant createdAt
    ) {
        return new DocumentStorageObject(
                "storage-1", "provider-1", "documents", "opaque-object-1", null,
                false, null, 1L, contentType, checksumAlgorithm, checksumValue,
                DocumentStorageStatus.AVAILABLE, createdAt, null
        );
    }
}
