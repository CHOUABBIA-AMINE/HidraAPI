/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationAuditContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : Enforces the accepted Simulation owner boundary.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.contract.simulation.SimulationRecommendationAuditContract;
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
public final class SimulationRecommendationAuditContractAdapter implements SimulationRecommendationAuditContract {
    private static final JsonMapper JSON = JsonMapper.builder().build();
    private final AuditCatalogEntryJpaRepository catalogs;
    private final RecordAuditEventUseCase recorder;
    private final AuditInputPolicy policy;

    public SimulationRecommendationAuditContractAdapter(AuditCatalogEntryJpaRepository catalogs,
            RecordAuditEventUseCase recorder, AuditInputPolicy policy) {
        this.catalogs=Objects.requireNonNull(catalogs);
        this.recorder=Objects.requireNonNull(recorder);
        this.policy=Objects.requireNonNull(policy);
    }

    @Override
    public String appendPublished(PublicationEvidence evidence) {
        Objects.requireNonNull(evidence);
        String recommendation=required(evidence.recommendationId());
        String run=required(evidence.runId());
        String type=required(evidence.recommendationTypeId());
        String candidate=policy.text(evidence.candidateId(),80);
        String actor=policy.text(evidence.actorId(),80);
        Objects.requireNonNull(evidence.occurredAt(),"Publication time must not be null.");
        var payload=new LinkedHashMap<String,Object>();
        payload.put("recommendationId",recommendation);
        payload.put("runId",run);
        payload.put("candidateId",candidate);
        payload.put("recommendationTypeId",type);
        payload.put("actorId",actor);
        payload.put("publishedAt",evidence.occurredAt().toString());
        String sanitized=policy.json(JSON.writeValueAsString(payload),true);
        var result=recorder.recordAuditEvent(new RecordAuditEventCommand(
                catalog("EVENT_TYPE","SIMULATION_RECOMMENDATION_PUBLISHED"),
                catalog("EVENT_CATEGORY","BUSINESS"),null,
                "simulation","SimulationApplicationService",recommendation,
                "PUBLISH_SIMULATION_RECOMMENDATION",null,actor,AuditActorType.USER,null,null,
                "simulation","SIMULATION_RECOMMENDATION",recommendation,null,null,
                AuditOperation.CREATE,null,null,"Simulation recommendation published",
                null,null,null,null,null,null,evidence.occurredAt(),sanitized));
        return required(Objects.requireNonNull(result,"Audit publication result must not be null.").id());
    }

    private String required(String value) {
        String sanitized=policy.text(value,80);
        if(sanitized==null)throw new IllegalArgumentException("Required Audit publication identity is missing.");
        return sanitized;
    }
    private String catalog(String family,String code) {
        return catalogs.findFirstByCatalogNameAndCodeAndActiveTrue(family,code)
                .orElseThrow(() -> new IllegalStateException("Required active Audit taxonomy is unavailable: "+family+"/"+code)).id();
    }
}
