/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalDocumentBinaryStorageAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.storage
 *
 * @Description : Configurable local-filesystem adapter for document binary content.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.storage;

import dz.sh.hidra.modules.documents.application.port.out.DocumentBinaryStoragePort;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class LocalDocumentBinaryStorageAdapter implements DocumentBinaryStoragePort {

    private static final String PROVIDER_ID = "local-filesystem";
    private static final String CONTAINER = "documents";
    private static final String CHECKSUM_ALGORITHM = "SHA-256";

    private final Path root;

    public LocalDocumentBinaryStorageAdapter(@Value("${hidra.documents.storage.root:./data/documents}") String root) {
        this.root = Path.of(root).toAbsolutePath().normalize();
    }

    @Override
    public StoredBinary store(String referenceId, InputStream content) {
        Path target = resolve(referenceId);
        try {
            Files.createDirectories(root);
            MessageDigest digest = MessageDigest.getInstance(CHECKSUM_ALGORITHM);
            long total = 0L;
            try (InputStream input = content; OutputStream output = Files.newOutputStream(target)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = input.read(buffer)) >= 0) {
                    if (read == 0) {
                        continue;
                    }
                    output.write(buffer, 0, read);
                    digest.update(buffer, 0, read);
                    total += read;
                }
            }
            return new StoredBinary(
                    PROVIDER_ID,
                    CONTAINER,
                    referenceId,
                    false,
                    null,
                    total,
                    CHECKSUM_ALGORITHM,
                    HexFormat.of().formatHex(digest.digest())
            );
        } catch (IOException | NoSuchAlgorithmException exception) {
            try {
                Files.deleteIfExists(target);
            } catch (IOException ignored) {
                // Preserve the original storage failure.
            }
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.STORAGE_FAILURE,
                    "Document content could not be stored.",
                    exception
            );
        }
    }

    @Override
    public InputStream open(String referenceId) {
        Path target = resolve(referenceId);
        if (!Files.isRegularFile(target)) {
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.NOT_FOUND,
                    "Document content was not found."
            );
        }
        try {
            return Files.newInputStream(target);
        } catch (IOException exception) {
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.STORAGE_FAILURE,
                    "Document content could not be opened.",
                    exception
            );
        }
    }

    @Override
    public boolean available(String referenceId) {
        return Files.isRegularFile(resolve(referenceId));
    }

    @Override
    public void delete(String referenceId) {
        try {
            Files.deleteIfExists(resolve(referenceId));
        } catch (IOException exception) {
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.STORAGE_FAILURE,
                    "Document content could not be deleted.",
                    exception
            );
        }
    }

    private Path resolve(String referenceId) {
        if (referenceId == null || referenceId.isBlank()) {
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.INVALID_CONTENT,
                    "Storage reference id must not be blank."
            );
        }
        Path resolved = root.resolve(referenceId.trim()).normalize();
        if (!resolved.startsWith(root)) {
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.INVALID_CONTENT,
                    "Storage reference id is invalid."
            );
        }
        return resolved;
    }
}
