/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.mapper
 *
 * @Description : Maps audit REST models to application models.
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
import java.util.Objects;

/**
 * Maps audit REST models to application models.
 */
public final class AuditRestMapper {

    private static final AuditGeneratedRestMapper GENERATED = AuditGeneratedRestMapper.INSTANCE;

    private AuditRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RecordAuditAccessCommand toCommand(RecordAuditAccessRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordAuditAccessRequest must not be null."));
    }

    public static RecordAuditEventCommand toCommand(RecordAuditEventRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordAuditEventRequest must not be null."));
    }

    public static RequestAuditExportCommand toCommand(RequestAuditExportRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RequestAuditExportRequest must not be null."));
    }

    public static AuditAccessRecordResponse toResponse(AuditAccessRecordSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AuditAccessRecordSummaryDto must not be null."));
    }

    public static AuditEventResponse toResponse(AuditEventSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AuditEventSummaryDto must not be null."));
    }

    public static AuditExportRequestResponse toResponse(AuditExportRequestSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AuditExportRequestSummaryDto must not be null."));
    }
}
