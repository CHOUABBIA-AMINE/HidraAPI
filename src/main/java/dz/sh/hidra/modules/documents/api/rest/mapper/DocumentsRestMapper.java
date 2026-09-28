/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.mapper
 *
 * @Description : Maps documents REST models to application models.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.mapper;
import dz.sh.hidra.modules.documents.api.rest.request.LinkDocumentToTargetRequest;
import dz.sh.hidra.modules.documents.api.rest.request.RegisterDocumentRequest;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentTargetLinkResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentVersionResponse;
import dz.sh.hidra.modules.documents.application.command.LinkDocumentToTargetCommand;
import dz.sh.hidra.modules.documents.application.command.RegisterDocumentCommand;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentTargetLinkSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import java.util.Objects;

/**
 * Maps documents REST models to application models.
 */
public final class DocumentsRestMapper {

    private static final DocumentsGeneratedRestMapper GENERATED = DocumentsGeneratedRestMapper.INSTANCE;

    private DocumentsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static LinkDocumentToTargetCommand toCommand(LinkDocumentToTargetRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "LinkDocumentToTargetRequest must not be null."));
    }

    public static RegisterDocumentCommand toCommand(RegisterDocumentRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterDocumentRequest must not be null."));
    }

    public static UploadDocumentVersionCommand toCommand(UploadDocumentVersionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "UploadDocumentVersionRequest must not be null."));
    }

    public static DocumentTargetLinkResponse toResponse(DocumentTargetLinkSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "DocumentTargetLinkSummaryDto must not be null."));
    }

    public static DocumentResponse toResponse(DocumentSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "DocumentSummaryDto must not be null."));
    }

    public static DocumentVersionResponse toResponse(DocumentVersionSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "DocumentVersionSummaryDto must not be null."));
    }
}
