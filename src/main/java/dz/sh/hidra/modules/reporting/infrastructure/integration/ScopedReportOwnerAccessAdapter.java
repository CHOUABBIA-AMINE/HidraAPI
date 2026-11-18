/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportOwnerAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.integration
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.infrastructure.integration;
import dz.sh.hidra.modules.analytics.application.contract.reporting.ReportingDeviationMetricContract;
import dz.sh.hidra.modules.documents.application.contract.reporting.ScopedReportDocumentContract;
import dz.sh.hidra.modules.identity.application.contract.reporting.ScopedReportAuthorizationContract;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Projection;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;
import dz.sh.hidra.modules.topology.application.contract.reporting.ReportingFacilityOwnershipContract;
import dz.sh.hidra.modules.workflow.application.contract.reporting.ScopedReportApprovalContract;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;

@Component
public final class ScopedReportOwnerAccessAdapter implements ScopedReportOwnerPort {
    private final ScopedReportAuthorizationContract identity;
    private final ReportingFacilityOwnershipContract topology;
    private final ReportingDeviationMetricContract analytics;
    private final ScopedReportApprovalContract workflow;
    private final ScopedReportDocumentContract documents;

    public ScopedReportOwnerAccessAdapter(ScopedReportAuthorizationContract identity,
            ReportingFacilityOwnershipContract topology, ReportingDeviationMetricContract analytics,
            ScopedReportApprovalContract workflow, ScopedReportDocumentContract documents) {
        this.identity=identity; this.topology=topology; this.analytics=analytics;
        this.workflow=workflow; this.documents=documents;
    }
    @Override
    public String require(Scope scope,Operation operation) {
        String actor=identity.require("reporting:scoped-report:"+operation.name().toLowerCase(Locale.ROOT),
                scope.companyId(),scope.facilityId());
        if (topology.current(scope.facilityId()).filter(f->scope.companyId().equals(f.companyId())
                && scope.facilityId().equals(f.id())).isEmpty()) throw hidden();
        return actor;
    }
    @Override
    public Projection metric(Scope s,String runId) {
        var m=analytics.completed(s.companyId(),s.facilityId(),runId);
        return new Projection(m.runId(),m.definitionVersionId(),m.methodVersion(),m.value(),m.unitId(),
                m.evaluationId(),m.ruleId(),m.ruleRevision(),m.targetId(),m.targetRevisionId(),
                m.approvalId(),m.trustedReadingId(),m.rawReadingId(),m.assessmentId(),m.bindingId(),
                m.evaluatedAt(),m.recordedAt());
    }
    @Override
    public Approval approval(String request,String instance,String action) {
        var a=workflow.approved(request,instance,action).orElseThrow(ScopedReportOwnerAccessAdapter::hidden);
        return new Approval(a.instanceId(),a.actionId(),a.reviewerId(),a.actedAt(),
                a.definitionId(),a.definitionVersion());
    }
    @Override public void requireDraftAuthority(Scope s) {
        documents.requireDraftAuthority(s.companyId(),s.facilityId());
    }
    @Override
    public Draft draft(Scope s,String run,String actor,byte[] bytes) {
        try {
            var d=documents.draft(s.companyId(),s.facilityId(),run,actor,bytes);
            return new Draft(d.documentId(),d.versionId(),d.checksum(),d.length());
        } catch (ScopedReportDocumentContract.StorageFailure failure) {
            throw new RenderFailure(failure);
        }
    }
    @Override public String seal(Scope s,String run,Draft d) {
        return documents.seal(s.companyId(),s.facilityId(),run,document(d));
    }
    @Override public byte[] content(Scope s,String run,Draft d,String acceptance) {
        return documents.content(s.companyId(),s.facilityId(),run,document(d),acceptance);
    }
    private static ScopedReportDocumentContract.Draft document(Draft d) {
        return new ScopedReportDocumentContract.Draft(d.documentId(),d.versionId(),d.checksum(),d.length());
    }
    private static NoSuchElementException hidden() { return new NoSuchElementException("Protected report unavailable."); }
}
