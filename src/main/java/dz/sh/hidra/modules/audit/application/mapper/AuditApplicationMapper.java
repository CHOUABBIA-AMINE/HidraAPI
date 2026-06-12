/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.mapper
 *
 * @Description : Maps audit domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.audit.application.mapper;

import dz.sh.hidra.modules.audit.application.dto.AuditAccessRecordSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditEventSummaryDto;
import dz.sh.hidra.modules.audit.application.dto.AuditExportRequestSummaryDto;
import dz.sh.hidra.modules.audit.domain.model.AuditAccessRecord;
import dz.sh.hidra.modules.audit.domain.model.AuditEvent;
import dz.sh.hidra.modules.audit.domain.model.AuditExportRequest;

/**
 * Maps audit domain models to DTOs.
 */
public final class AuditApplicationMapper {

    private AuditApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AuditEventSummaryDto toSummary(AuditEvent event) {
        return new AuditEventSummaryDto(event.id(), event.sourceModule(), event.actionCode(), event.eventStatus(), event.actorId(), event.actorType(), event.targetModule(), event.targetType(), event.targetId(), event.operation(), event.correlationId(), event.occurredAt(), event.recordedAt());
    }

    public static AuditExportRequestSummaryDto toSummary(AuditExportRequest exportRequest) {
        return new AuditExportRequestSummaryDto(exportRequest.id(), exportRequest.requestedByActorId(), exportRequest.purposeId(), exportRequest.format(), exportRequest.status(), exportRequest.recordCount(), exportRequest.requestedAt(), exportRequest.completedAt());
    }

    public static AuditAccessRecordSummaryDto toSummary(AuditAccessRecord accessRecord) {
        return new AuditAccessRecordSummaryDto(accessRecord.id(), accessRecord.actorId(), accessRecord.accessType(), accessRecord.auditEventId(), accessRecord.exportRequestId(), accessRecord.resultCount(), accessRecord.accessedAt());
    }
}
