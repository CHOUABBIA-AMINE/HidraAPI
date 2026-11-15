/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 * @Description : Authorized, attributable T16 Incident investigation and reviewed closure orchestration.
 */
package dz.sh.hidra.modules.incident.application.service;

import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;
import dz.sh.hidra.modules.incident.application.port.in.CloseIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.IncidentInvestigationUseCase;
import dz.sh.hidra.modules.incident.application.port.out.*;
import dz.sh.hidra.modules.incident.domain.service.IncidentInvestigationGuard;
import dz.sh.hidra.modules.incident.domain.value.IncidentEscalationSourceType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncidentInvestigationApplicationService implements IncidentInvestigationUseCase {
    private static final String READ="incident:incidents:read";
    private static final String EXECUTE="incident:incidents:execute";
    private static final String REVIEW="incident:incidents:review";
    private final IncidentInvestigationStorePort store;
    private final IncidentEscalationAuthorizationPort scope;
    private final IncidentEscalationStorePort escalation;
    private final IncidentEscalationSourcePort sources;
    private final IncidentReferencePolicyPort actors;
    private final CloseIncidentUseCase close;
    private final IncidentInvestigationGuard guard=new IncidentInvestigationGuard();

    public IncidentInvestigationApplicationService(IncidentInvestigationStorePort store,
            IncidentEscalationAuthorizationPort scope,IncidentEscalationStorePort escalation,
            IncidentEscalationSourcePort sources,IncidentReferencePolicyPort actors,
            CloseIncidentUseCase close) {
        this.store=Objects.requireNonNull(store);this.scope=Objects.requireNonNull(scope);
        this.escalation=Objects.requireNonNull(escalation);this.sources=Objects.requireNonNull(sources);
        this.actors=Objects.requireNonNull(actors);this.close=Objects.requireNonNull(close);
    }

    @Override @Transactional
    public Receipt execute(String rawId, Command command) {
        Objects.requireNonNull(command,"Investigation command required.");
        String id=required(rawId,80);
        String op=required(command.operation(),20);
        String key=required(command.requestId(),80);
        if(command.expectedVersion()<0)throw new IllegalArgumentException("Expected version cannot be negative.");
        if(!List.of("ASSIGN","EVIDENCE","ACTION","SUBMIT","REVIEW","CLOSE").contains(op))
            throw new IllegalArgumentException("Unknown investigation command.");
        scope.requireIncident("REVIEW".equals(op)?REVIEW:EXECUTE,id);
        requireCurrentSource(id);
        Instant at=Instant.now();
        var actor=actors.currentActor(at);
        String who=required(actor.id(),80);
        String digest=digest(command);
        var prior=store.replay(id,key,digest);
        if(prior.isPresent()) {
            if(!who.equals(prior.orElseThrow().actorId()))
                throw new IllegalStateException("Incident replay actor mismatch.");
            return replay(prior.orElseThrow());
        }
        var state=store.lock(id);
        // Serialize concurrent same-command submissions before testing expected version.
        prior=store.replay(id,key,digest);
        if(prior.isPresent()) {
            if(!who.equals(prior.orElseThrow().actorId()))
                throw new IllegalStateException("Incident replay actor mismatch.");
            return replay(prior.orElseThrow());
        }
        if(state.version()!=command.expectedVersion())
            throw new IllegalStateException("Stale Incident investigation revision.");
        guard.require(state.parentStatus(),state.state(),op,state.investigatorId(),
                who,state.hasEvidence(),state.hasAction());
        switch(op) {
            case "ASSIGN" -> required(command.detail(),1000);
            case "EVIDENCE" -> {
                String type=required(command.referenceType(),40);
                String ref=required(command.referenceId(),80);
                if(!List.of("SUPPORTING","CONTRADICTING").contains(command.direction()))
                    throw new IllegalArgumentException("Evidence direction required.");
                if("ROOT_CAUSE".equals(type)) {
                    required(command.detail(),1000);
                    break;
                }
                var matched=escalation.bySource(type,ref).orElseThrow(
                        ()->new NoSuchElementException("Protected Incident source unavailable."));
                if(!id.equals(matched.incidentId()))
                    throw new NoSuchElementException("Protected Incident source unavailable.");
                var source=sources.authorized(IncidentEscalationSourceType.valueOf(type),ref);
                if(!matched.evaluationId().equals(source.evaluationId()))
                    throw new NoSuchElementException("Incident source version mismatch.");
                required(command.detail(),1000);
            }
            case "ACTION" -> {
                required(command.referenceId(),80); // Incident owner RESPONSE_ACTION_TYPE catalog entry.
                required(command.detail(),1000);
            }
            case "SUBMIT" -> {
                required(command.resolutionTypeId(),80);
                required(command.detail(),1000);
            }
            case "REVIEW" -> {
                if(!List.of("APPROVE","REJECT").contains(command.direction()))
                    throw new IllegalArgumentException("Independent review verdict required.");
                required(command.detail(),1000);
            }
            case "CLOSE" -> required(command.detail(),1000);
            default -> throw new IllegalArgumentException("Unknown Incident investigation command.");
        }
        Receipt receipt=store.append(state,command,who,digest,at);
        if("CLOSE".equals(op)) {
            // HMR-090 owns policy, immutable evidence, actual Workflow approval and atomic closure.
            // Never create a competing closure authority in T16.
            close.closeIncident(new CloseIncidentCommand(id,command.detail(),true,true,
                    state.hasRootCause(),state.hasAction(),who,actor.label(),command.referenceId()));
        }
        return receipt;
    }

    @Override @Transactional
    public View history(String rawId) {
        String id=required(rawId,80);
        scope.requireIncident(READ,id);
        requireCurrentSource(id);
        var state=store.view(id);
        return new View(id,state.parentStatus(),state.state(),state.version(),
                state.investigatorId(),state.submittedById(),state.reviewedById(),
                state.resolutionSummary(),store.history(id));
    }

    private void requireCurrentSource(String id) {
        var correlation=escalation.byIncident(id);
        if(correlation.isEmpty())return; // Pre-T14 manual Incident retains its own history/authority.
        var expected=correlation.orElseThrow();
        var events=escalation.timeline(id);
        if(events.isEmpty())throw new NoSuchElementException("Protected Incident source unavailable.");
        for(var link:events) {
            var source=sources.authorized(link.sourceType(),link.sourceId());
            if(!expected.evaluationId().equals(source.evaluationId())
                    ||!expected.companyId().equals(source.companyId())
                    ||!expected.assetType().equals(source.assetType())
                    ||!expected.assetId().equals(source.assetId()))
                throw new NoSuchElementException("Protected Incident source unavailable.");
        }
    }

    private static Receipt replay(Receipt x) {
        return new Receipt(x.incidentId(),x.eventId(),x.operation(),x.version(),
                x.investigationState(),x.actorId(),x.recordedAt(),true);
    }
    private static String required(String s,int max) {
        if(s==null||s.isBlank()||s.length()>max||!s.equals(s.strip()))
            throw new IllegalArgumentException("Invalid investigation declaration.");
        return s;
    }
    /** Unambiguous length-prefixed canonical request hash, independent of caller JSON order. */
    private static String digest(Command c) {
        String[] v={c.requestId(),String.valueOf(c.expectedVersion()),c.operation(),
                c.referenceType(),c.referenceId(),c.direction(),c.detail(),c.resolutionTypeId(),
                String.valueOf(c.corrective()),String.valueOf(c.preventive())};
        StringBuilder b=new StringBuilder();
        for(String s:v){String x=s==null?"":s;b.append(s==null?"N":"V").append(x.length()).append(':').append(x);}
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(
                b.toString().getBytes(StandardCharsets.UTF_8)));}
        catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}
    }
}
