/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Api
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.response
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.api.rest.response;
public final class ScopedReportResponses {
    private ScopedReportResponses() {}
    public record T25ReportReceipt(String requestId,String runId,String status,String definitionId,
            String templateVersionId,String templateHash,String metricRunId,String sourceHash,
            String workflowInstanceId,String workflowActionId,String reviewerId,String custodianId,
            String documentId,String documentVersionId,String acceptanceId,String checksumSha256,
            Long contentLength,String failureCode,boolean replayed) {}
}
