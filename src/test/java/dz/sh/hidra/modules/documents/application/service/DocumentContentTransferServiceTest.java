/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentContentTransferServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Verifies HMR-008 storage-provider validation and cleanup.
 *
 */
package dz.sh.hidra.modules.documents.application.service;

import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import dz.sh.hidra.modules.documents.application.port.out.DocumentBinaryStoragePort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentStorageObjectRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentVersionRepositoryPort;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DocumentContentTransferServiceTest {

    @Test
    void rejectsInactiveOrWrongFamilyStorageProviderAndDeletesStoredBinary() {
        DocumentBinaryStoragePort binaryStorage = mock(DocumentBinaryStoragePort.class);
        DocumentStorageObjectRepositoryPort storageRepository = mock(DocumentStorageObjectRepositoryPort.class);
        DocumentVersionRepositoryPort versionRepository = mock(DocumentVersionRepositoryPort.class);
        UploadDocumentVersionUseCase uploadVersion = mock(UploadDocumentVersionUseCase.class);
        DocumentContentTransferService service = new DocumentContentTransferService(
                binaryStorage, storageRepository, versionRepository, uploadVersion
        );

        when(binaryStorage.store(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(new DocumentBinaryStoragePort.StoredBinary(
                        "provider-1", "documents", "opaque-object-1",
                        false, null, 3L, "SHA-256", "abc123"
                ));
        when(storageRepository.isActiveStorageProvider("provider-1")).thenReturn(false);

        UploadDocumentBinaryVersionCommand command = new UploadDocumentBinaryVersionCommand(
                "doc-1", 1, "v1", null, "Version", null, null,
                "fr", null, null, null, "actor-1", null,
                "evidence.bin", "application/octet-stream",
                new ByteArrayInputStream(new byte[] {1, 2, 3})
        );

        assertThatThrownBy(() -> service.uploadDocumentBinaryVersion(command))
                .isInstanceOf(DocumentContentTransferException.class)
                .hasMessageContaining("DOCUMENT_STORAGE_PROVIDER");

        verify(binaryStorage).delete(org.mockito.ArgumentMatchers.anyString());
    }
}
