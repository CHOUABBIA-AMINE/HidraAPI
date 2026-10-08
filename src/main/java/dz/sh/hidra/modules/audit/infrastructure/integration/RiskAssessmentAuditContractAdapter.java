/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentAuditContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Enforces the accepted Risk approval owner boundary.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.contract.risk.RiskAssessmentAuditContract;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.application.service.AuditInputPolicy;
import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public final class RiskAssessmentAuditContractAdapter implements RiskAssessmentAuditContract {
    private static final JsonMapper JSON = JsonMapper.builder().build();
    private final AuditCatalogEntryJpaRepository catalogs;
    private final RecordAuditEventUseCase recorder;
    private final AuditInputPolicy policy;

    public RiskAssessmentAuditContractAdapter(AuditCatalogEntryJpaRepository catalogs,
            RecordAuditEventUseCase recorder, AuditInputPolicy policy) {
        this.catalogs=Objects.requireNonNull(catalogs);
        this.recorder=Objects.requireNonNull(recorder);
        this.policy=Objects.requireNonNull(policy);
    }

    @Override
    public String appendApproved(ApprovalEvidence evidence) {
        Objects.requireNonNull(evidence);
        String assessment=required(evidence.assessmentId());
        String actor=required(evidence.actorId());
        Objects.requireNonNull(evidence.occurredAt(), "Approval time must not be null.");
        var payload=new LinkedHashMap<String,Object>();
        payload.put("assessmentId",assessment);
        payload.put("reviewerId",required(evidence.reviewerId()));
        payload.put("reviewActionId",required(evidence.reviewActionId()));
        payload.put("workflowActionId",required(evidence.workflowActionId()));
        payload.put("approvedAt",evidence.occurredAt().toString());
        String sanitized=policy.json(JSON.writeValueAsString(payload),true);
        var result=recorder.recordAuditEvent(new RecordAuditEventCommand(
                catalog("EVENT_TYPE","RISK_ASSESSMENT_APPROVED"), catalog("EVENT_CATEGORY","BUSINESS"),null,
                "risk","RiskAssessmentGovernanceService",assessment,"APPROVE_RISK_ASSESSMENT",null,
                actor,AuditActorType.USER,policy.text(evidence.actorDisplayName(),255),policy.text(evidence.actorUsername(),255),
                "risk","RISK_ASSESSMENT",assessment,policy.text(evidence.assessmentNumber(),80),null,
                AuditOperation.APPROVE,"APPROVE",null,"Risk assessment approved",
                required(evidence.workflowInstanceId()),required(evidence.workflowTaskId()),required(evidence.workflowActionId()),
                null,null,null,evidence.occurredAt(),sanitized));
        return required(Objects.requireNonNull(result,"Audit approval result must not be null.").id());
    }

    private String required(String value) {
        String sanitized=policy.text(value,80);
        if(sanitized==null)throw new IllegalArgumentException("Required Audit approval identity is missing.");
        return sanitized;
    }
    private String catalog(String family,String code) {
        return catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(family,code)
                .orElseThrow(() -> new IllegalStateException("Required active Audit taxonomy is unavailable: "+family+"/"+code)).id();
    }
}
