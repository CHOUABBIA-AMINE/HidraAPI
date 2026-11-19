/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ReportReviewTaskInboxContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.contract.reporting
 * @Description : Exact Reporting-owned assignment publication into a durable Notification WEB inbox.
 */
package dz.sh.hidra.modules.notification.application.contract.reporting;

import java.time.Instant;

public interface ReportReviewTaskInboxContract {
    record Assignment(String workflowTaskId,String recipientActorId,String companyId,
            String facilityId,Instant assignedAt) {}
    void assigned(Assignment event);
}
