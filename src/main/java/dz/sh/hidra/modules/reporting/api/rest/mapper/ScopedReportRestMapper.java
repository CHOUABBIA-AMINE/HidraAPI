/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Api
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.mapper
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.api.rest.mapper;
import dz.sh.hidra.modules.reporting.api.rest.request.ScopedReportRequests;
import dz.sh.hidra.modules.reporting.api.rest.response.ScopedReportResponses.T25ReportReceipt;
import dz.sh.hidra.modules.reporting.application.port.in.ScopedReportUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public final class ScopedReportRestMapper {
    private ScopedReportRestMapper() {}
    public static ScopedReportUseCase.Submit command(ScopedReportRequests.T25SubmitReport in) {
        return new ScopedReportUseCase.Submit(in.requestKey(),in.definitionId(),in.templateVersionId(),in.metricRunId());
    }
    public static ScopedReportUseCase.Render command(ScopedReportRequests.T25RenderReport in) {
        return new ScopedReportUseCase.Render(in.requestKey(),in.workflowInstanceId(),in.workflowActionId());
    }
    public static ScopedReportUseCase.Seal command(ScopedReportRequests.T25SealReport in) {
        return new ScopedReportUseCase.Seal(in.requestKey());
    }
    public static T25ReportReceipt receipt(ScopedReportUseCase.Receipt r) {
        return new T25ReportReceipt(r.requestId(),r.runId(),r.status(),r.definitionId(),r.templateVersionId(),
                r.templateHash(),r.metricRunId(),r.sourceHash(),r.workflowInstanceId(),r.workflowActionId(),
                r.reviewerId(),r.custodianId(),r.documentId(),r.documentVersionId(),r.acceptanceId(),
                r.checksumSha256(),r.contentLength(),r.failureCode(),r.replayed());
    }
    public static ResponseEntity<byte[]> content(ScopedReportUseCase.Content c) {
        byte[] bytes=c.bytes();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).contentLength(bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"absolute-deviation.json\"")
                .header("X-Content-SHA256",c.checksumSha256()).header(HttpHeaders.CACHE_CONTROL,"no-store").body(bytes);
    }
}
