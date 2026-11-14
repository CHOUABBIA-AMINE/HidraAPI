/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EvidenceLinkedIncidentEscalationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 * @Description : T14 protected operator-origin Incident case escalation.
 */
package dz.sh.hidra.modules.incident.application.service;

import dz.sh.hidra.modules.incident.application.port.in.EscalateEvidenceToIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.out.*;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.domain.model.IncidentSourceEscalation;
import dz.sh.hidra.modules.incident.domain.value.*;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvidenceLinkedIncidentEscalationApplicationService implements EscalateEvidenceToIncidentUseCase {
    private static final String WRITE="incident:incidents:execute";
    private static final String READ="incident:incidents:read";
    private final IncidentEscalationSourcePort sources;
    private final IncidentEscalationAuthorizationPort scope;
    private final IncidentEscalationStorePort records;
    private final IncidentRepositoryPort incidents;
    private final IncidentReferencePolicyPort references;
    public EvidenceLinkedIncidentEscalationApplicationService(IncidentEscalationSourcePort sources,
            IncidentEscalationAuthorizationPort scope, IncidentEscalationStorePort records,
            IncidentRepositoryPort incidents, IncidentReferencePolicyPort references) {
        this.sources=Objects.requireNonNull(sources);
        this.scope=Objects.requireNonNull(scope);
        this.records=Objects.requireNonNull(records);
        this.incidents=Objects.requireNonNull(incidents);
        this.references=Objects.requireNonNull(references);
    }

    @Override @Transactional
    public Result escalate(Command c) {
        Objects.requireNonNull(c,"Escalation declaration required.");
        String sourceId=required(c.sourceId(),80);
        String classification=required(c.classificationId(),80);
        String severity=required(c.severityId(),80);
        String title=required(c.titleFr(),255);
        String reason=required(c.reason(),500);
        IncidentEscalationSourceType type=IncidentEscalationSourceType.valueOf(required(c.sourceType(),40));
        // Source owner reads are dual-authorized before any protected case fact can be returned.
        var source=sources.authorized(type,sourceId);
        scope.requireAsset(WRITE,source.companyId(),source.assetType(),source.assetId());
        if(source.evaluationId()==null || source.evaluationId().isBlank()
                ||source.evaluatedAt()==null || source.evaluatedAt().isAfter(Instant.now()))
            throw new IllegalArgumentException("Missing or future-qualified source evaluation.");
        if(type==IncidentEscalationSourceType.ALARM && !java.util.Set.of("RAISED","ACTIVE","ACKNOWLEDGED","ESCALATED").contains(source.sourceStatus())
                ||type==IncidentEscalationSourceType.LEAK_CANDIDATE && !"NEW".equals(source.sourceStatus()))
            throw new IllegalArgumentException("Ineligible terminal or unqualified source.");
        var at=Instant.now();
        var actor=references.currentActor(at);
        records.serialize(source.evaluationId());
        var prior=records.byEvaluation(source.evaluationId());
        boolean created=prior.isEmpty();
        String incidentId;
        if(prior.isPresent()) {
            var old=prior.orElseThrow();
            if(!Objects.equals(old.companyId(),source.companyId())
                    ||!Objects.equals(old.assetType(),source.assetType())
                    ||!Objects.equals(old.assetId(),source.assetId())
                    ||!old.classificationId().equals(classification)
                    ||!old.severityId().equals(severity)
                    ||!old.title().equals(title)
                    ||!old.reason().equals(reason))
                throw new IllegalStateException("Conflicting Incident escalation request.");
            incidentId=old.incidentId();
            var parent=incidents.findByIdForUpdate(incidentId).orElseThrow(NoSuchElementException::new);
            if(parent.closedLifecycle())throw new IllegalStateException("Closed Incident cannot receive escalation evidence.");
        } else {
            var asset=references.topologyAsset(source.assetType(),source.assetId());
            incidentId=UUID.randomUUID().toString();
            var incident=new Incident(incidentId,"T14-"+UUID.randomUUID(),title,reason,
                    classification,severity,null,IncidentStatus.OPEN,
                    type==IncidentEscalationSourceType.ALARM
                        ?IncidentSourceType.ALARM:IncidentSourceType.LEAK_CANDIDATE,
                    sourceId,null,source.evaluatedAt(),at,null,
                    source.assetType(),source.assetId(),asset.code(),asset.label(),
                    null,null,null,null,null,null,null,null,null,null,0,
                    null,null,null,null,actor.id(),actor.label(),at,at);
            incidents.save(incident);
            incidents.flush();
            records.create(new IncidentEscalationStorePort.Correlation(
                    source.evaluationId(),incidentId,source.companyId(),source.assetType(),
                    source.assetId(),classification,severity,title,reason,actor.id(),at));
        }
        var link=records.bySource(type.name(),sourceId);
        IncidentSourceEscalation effective;
        if(link.isPresent()) {
            effective=link.orElseThrow();
            if(!incidentId.equals(effective.incidentId())
                  ||!source.evaluationId().equals(effective.evaluationId()))
                throw new IllegalStateException("Source already attached to a different Incident.");
        } else {
            effective=new IncidentSourceEscalation(incidentId,source.evaluationId(),type,
                    sourceId,actor.id(),reason,at);
            records.link(effective);
        }
        return new Result(incidentId,source.evaluationId(),type.name(),sourceId,
                "OPEN",effective.actorId(),effective.at(),created);
    }

    @Override @Transactional
    public Result receipt(String incidentId) {
        List<Result> entries=timeline(incidentId);
        return entries.isEmpty()?throwUnavailable():entries.get(0);
    }
    @Override @Transactional
    public List<Result> timeline(String incidentId) {
        scope.requireIncident(READ,required(incidentId,80));
        var correlation=records.byIncident(incidentId).orElseThrow(NoSuchElementException::new);
        var links=records.timeline(incidentId);
        for(var link:links) {
            var current=sources.authorized(link.sourceType(),link.sourceId());
            if(!correlation.evaluationId().equals(current.evaluationId())
                    ||!correlation.companyId().equals(current.companyId())
                    ||!correlation.assetType().equals(current.assetType())
                    ||!correlation.assetId().equals(current.assetId()))
                throw new NoSuchElementException("Protected Incident evidence unavailable.");
        }
        return links.stream().map(l->new Result(incidentId,l.evaluationId(),l.sourceType().name(),
                l.sourceId(),"OPEN",l.actorId(),l.at(),false)).toList();
    }
    private static Result throwUnavailable() {throw new NoSuchElementException("Protected Incident unavailable.");}
    private static String required(String text,int max) {
        if(text==null||text.isBlank()||text.length()>max||!text.equals(text.strip()))
            throw new IllegalArgumentException("Invalid Incident escalation declaration.");
        return text;
    }
}
