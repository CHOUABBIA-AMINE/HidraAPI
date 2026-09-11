/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.in
 *
 * @Description : Provides active/history/detail alarm read models and shelving history.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.in;

import java.time.Instant;
import java.util.List;

public interface AlarmQueryUseCase {

    Page<AlarmView> alarms(
            String view,
            String state,
            String severityId,
            String topologyAssetId,
            Instant from,
            Instant to,
            int page,
            int size
    );

    AlarmView alarm(String id);

    List<ShelvingView> shelvings(String alarmId);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record AlarmView(
            String id,
            String alarmNumber,
            String alarmTypeId,
            String severityId,
            String priorityId,
            String titleAr,
            String titleFr,
            String titleEn,
            String sourceType,
            String sourceReferenceId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetName,
            String currentState,
            Instant raisedAt,
            Instant firstDetectedAt,
            Instant lastUpdatedAt,
            Instant clearedAt,
            Instant closedAt,
            Instant acknowledgedAt,
            String acknowledgedByActorId,
            String owningOrganizationUnitId,
            String workflowInstanceId,
            String incidentId,
            String correlationId
    ) { }

    record ShelvingView(
            String id,
            String alarmId,
            String shelvingReasonId,
            String reasonText,
            String shelvedByActorId,
            Instant shelvedAt,
            Instant shelvedUntil,
            Instant unshelvedAt,
            String unshelvedByActorId,
            String status,
            String correlationId
    ) { }
}
