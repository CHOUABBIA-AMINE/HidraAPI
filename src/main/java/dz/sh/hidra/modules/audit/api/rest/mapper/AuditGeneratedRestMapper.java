/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.mapper
 *
 * @Description : Generates exact audit API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.mapper;

import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditAccessRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditEventRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RequestAuditExportRequest;
import dz.sh.hidra.modules.audit.api.rest.response.AuditAccessRecordResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditEventResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditExportRequestResponse;
import dz.sh.hidra.modules.audit.application.command.RecordAuditAccessCommand;
import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.command.RequestAuditExportCommand;
import dz.sh.hidra.modules.audit.application.dto.AuditAccessRecordSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditExportRequestSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact audit boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuditGeneratedRestMapper {

    AuditGeneratedRestMapper INSTANCE = Mappers.getMapper(AuditGeneratedRestMapper.class);

    RecordAuditAccessCommand toCommand(RecordAuditAccessRequest request);

    RecordAuditEventCommand toCommand(RecordAuditEventRequest request);

    RequestAuditExportCommand toCommand(RequestAuditExportRequest request);

    AuditAccessRecordResponse toResponse(AuditAccessRecordSummaryDto dto);

    AuditEventResponse toResponse(AuditEventSummaryDto dto);

    AuditExportRequestResponse toResponse(AuditExportRequestSummaryDto dto);
}
