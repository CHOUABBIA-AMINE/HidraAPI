/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import dz.sh.hidra.modules.topology.api.rest.request.EngineeringBaselinePublicationRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselineDraftResponses.SourcePin;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselinePublicationResponses.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import java.util.UUID;

public final class EngineeringBaselinePublicationRestMapper {
    private EngineeringBaselinePublicationRestMapper() {}

    public static Command submit(UUID manifest, Submit body) {
        return new Command(Action.SUBMIT, body.companyPartyId(), body.pipelineSystemId(), body.requestId(),
                manifest, body.version(), body.canonicalSha256(), null, 0, null);
    }

    public static Command publish(UUID submission, Publish body) {
        var d = body.decision();
        return new Command(Action.PUBLISH, body.companyPartyId(), body.pipelineSystemId(), submission,
                null, 0, null, body.requestId(), body.expectedEpoch(),
                new Decision(d.definitionId(), d.definitionVersion(), d.targetTypeId(), d.purposeId(),
                        d.instanceId(), d.taskId(), d.actionId()));
    }

    public static Submitted submitted(Submission s) {
        var m = s.manifest();
        return new Submitted(s.id(), m.manifestId(), m.version(), s.sha256(), m.companyPartyId(),
                m.pipelineSystemId(), "t06-baseline-target", "t06-baseline-purpose",
                "ENGINEERING_BASELINE_REVISION", "ENGINEERING_BASELINE_APPROVAL",
                s.workflowLocator(), s.id().toString(), s.submittedBy(), s.submittedAt());
    }

    public static Published published(Receipt r) {
        var m = r.submission().manifest();
        var decision = r.approval().decision();
        var pins = m.pins().stream()
                .map(p -> new SourcePin(p.kind().name(), p.ownerId(), p.sourceId(), p.revisionId(), p.sha256()))
                .toList();
        return new Published(r.id(), r.epoch(), "PUBLISHED", submitted(r.submission()), pins, m.effectiveFrom(),
                m.effectiveUntil(), r.approval().reviewerId(), decision.instanceId(), decision.taskId(),
                decision.actionId(), r.approval().reviewedAt(), r.publishedBy(), r.publishedAt(), false);
    }
}
