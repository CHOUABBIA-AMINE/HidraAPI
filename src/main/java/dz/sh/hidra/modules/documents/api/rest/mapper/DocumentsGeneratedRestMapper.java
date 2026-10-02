/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.mapper
 *
 * @Description : Generates exact documents API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact documents boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DocumentsGeneratedRestMapper {

    DocumentsGeneratedRestMapper INSTANCE = Mappers.getMapper(DocumentsGeneratedRestMapper.class);

    LinkDocumentToTargetCommand toCommand(LinkDocumentToTargetRequest request);

    RegisterDocumentCommand toCommand(RegisterDocumentRequest request);

    UploadDocumentVersionCommand toCommand(UploadDocumentVersionRequest request);

    DocumentResponse toResponse(DocumentSummaryDto dto);

    DocumentTargetLinkResponse toResponse(DocumentTargetLinkSummaryDto dto);

    DocumentVersionResponse toResponse(DocumentVersionSummaryDto dto);
}
