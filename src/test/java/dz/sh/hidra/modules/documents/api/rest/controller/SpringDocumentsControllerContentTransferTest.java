/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringDocumentsControllerContentTransferTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : API Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.controller
 *
 * @Description : Verifies documents multipart upload and streaming download HTTP adapter semantics.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentBinaryVersionRequest;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.application.port.in.DownloadDocumentVersionContentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.LinkDocumentToTargetUseCase;
import dz.sh.hidra.modules.documents.application.port.in.RegisterDocumentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentBinaryVersionUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

class SpringDocumentsControllerContentTransferTest {

    @Test
    void acceptsMultipartMetadataAndFileWithoutClientStorageEvidence() {
        AtomicReference<UploadDocumentBinaryVersionCommand> captured = new AtomicReference<>();
        UploadDocumentBinaryVersionUseCase uploadBinary = command -> {
            captured.set(command);
            return new DocumentVersionSummaryDto(
                    "version-1",
                    command.documentId(),
                    command.versionNumber(),
                    command.versionLabel(),
                    command.contentType(),
                    command.originalFilename(),
                    7L,
                    "checksum",
                    DocumentVersionStatus.DRAFT,
                    Instant.parse("2026-09-13T12:00:00Z")
            );
        };

        SpringDocumentsController controller = controller(uploadBinary, versionId -> {
            throw new AssertionError("download should not be called");
        });
        UploadDocumentBinaryVersionRequest metadata = new UploadDocumentBinaryVersionRequest(
                "document-1",
                2,
                "v2",
                null,
                "Titre",
                "Title",
                "Evidence",
                "en",
                null,
                null,
                null,
                "actor-1",
                "Operator"
        );
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "evidence.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "payload".getBytes(StandardCharsets.UTF_8)
        );

        controller.uploadDocumentVersionContent(metadata, file);

        assertThat(captured.get()).isNotNull();
        assertThat(captured.get().documentId()).isEqualTo("document-1");
        assertThat(captured.get().versionNumber()).isEqualTo(2);
        assertThat(captured.get().originalFilename()).isEqualTo("evidence.pdf");
        assertThat(captured.get().contentType()).isEqualTo(MediaType.APPLICATION_PDF_VALUE);
    }

    @Test
    void downloadsAsAttachmentWithExplicitNoRangeSemantics() throws Exception {
        byte[] payload = "binary-evidence".getBytes(StandardCharsets.UTF_8);
        DownloadDocumentVersionContentUseCase download = versionId -> new DocumentVersionContentDto(
                versionId,
                "evidence report.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                payload.length,
                "SHA-256",
                "checksum",
                new ByteArrayInputStream(payload)
        );
        SpringDocumentsController controller = controller(command -> {
            throw new AssertionError("upload should not be called");
        }, download);

        var response = controller.downloadDocumentVersionContent("version-1");

        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(response.getHeaders().getContentLength()).isEqualTo(payload.length);
        assertThat(response.getHeaders().getFirst(HttpHeaders.ACCEPT_RANGES)).isEqualTo("none");
        assertThat(response.getHeaders().getContentDisposition().getType()).isEqualTo("attachment");
        assertThat(response.getHeaders().getContentDisposition().getFilename()).isEqualTo("evidence report.pdf");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getInputStream().readAllBytes()).isEqualTo(payload);
    }

    private static SpringDocumentsController controller(
            UploadDocumentBinaryVersionUseCase uploadBinary,
            DownloadDocumentVersionContentUseCase download
    ) {
        LinkDocumentToTargetUseCase link = command -> {
            throw new AssertionError("link should not be called");
        };
        RegisterDocumentUseCase register = command -> {
            throw new AssertionError("register should not be called");
        };
        UploadDocumentVersionUseCase metadataUpload = command -> {
            throw new AssertionError("metadata upload should not be called");
        };
        return new SpringDocumentsController(link, register, metadataUpload, uploadBinary, download, 1024L);
    }
}
