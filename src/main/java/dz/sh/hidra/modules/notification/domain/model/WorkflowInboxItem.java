/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxItem
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 * @Description : Immutable single-recipient T27 Workflow assignment and its durable WEB acknowledgement.
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.NotificationAcknowledgementStatus;
import dz.sh.hidra.modules.notification.domain.value.NotificationChannelType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public record WorkflowInboxItem(String id, String eventCode, String messageCode,
        String companyId, String facilityId, String recipientActorId,
        String workflowTaskId, Instant assignedAt, Instant deliveredAt, Instant acknowledgedAt) {
    public static final String EVENT="WORKFLOW_REPORT_REVIEW_TASK_ASSIGNED_V1";
    public static final String MESSAGE="REPORT_REVIEW_TASK_ASSIGNED_V1";
    public static final String DELIVERY="DELIVERED";
    public record Scope(String companyId, String facilityId) {
        public Scope { bounded(companyId,96); bounded(facilityId,120); }
    }
    public WorkflowInboxItem {
        uuid(id);
        if (!EVENT.equals(eventCode) || !MESSAGE.equals(messageCode))
            throw new IllegalArgumentException("Unsupported workflow inbox event.");
        bounded(companyId,96); bounded(facilityId,120);
        bounded(recipientActorId,80); bounded(workflowTaskId,80);
        Objects.requireNonNull(assignedAt); Objects.requireNonNull(deliveredAt);
        if (deliveredAt.isBefore(assignedAt) || (acknowledgedAt!=null && acknowledgedAt.isBefore(deliveredAt)))
            throw new IllegalArgumentException("Invalid inbox chronology.");
        if (!assignedAt.equals(assignedAt.truncatedTo(ChronoUnit.MICROS))
                || !deliveredAt.equals(deliveredAt.truncatedTo(ChronoUnit.MICROS)))
            throw new IllegalArgumentException("Inbox timestamps require microsecond precision.");
    }
    public static String bounded(String text,int length) {
        if (text==null || text.isBlank() || text.length()>length
                || !text.equals(text.strip()) || text.chars().anyMatch(Character::isISOControl))
            throw new IllegalArgumentException("Invalid workflow inbox selector.");
        return text;
    }
    public static String uuid(String text) {
        bounded(text,36);
        if (!UUID.fromString(text).toString().equals(text))
            throw new IllegalArgumentException("Expected canonical inbox UUID.");
        return text;
    }
    public NotificationChannelType channel(){ return NotificationChannelType.WEB; }
    public String deliveryStatus(){ return DELIVERY; }
    public NotificationAcknowledgementStatus acknowledgementStatus(){
        return acknowledgedAt==null ? NotificationAcknowledgementStatus.REQUIRED
                : NotificationAcknowledgementStatus.ACKNOWLEDGED;
    }
}
