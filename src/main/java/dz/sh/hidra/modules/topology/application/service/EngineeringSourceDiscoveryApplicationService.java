/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourceDiscoveryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Reuses authoritative protected Topology publication reads for exact source discovery.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/** Does not query protected publication tables or start an ambient transaction. */
@Service
public final class EngineeringSourceDiscoveryApplicationService implements EngineeringSourceDiscoveryUseCase {
    private final EngineeringRevisionPublicationUseCase publications;

    public EngineeringSourceDiscoveryApplicationService(EngineeringRevisionPublicationUseCase publications) {
        this.publications=Objects.requireNonNull(publications);
    }
    @Override
    public Optional<Source> current(Lookup lookup) {
        Objects.requireNonNull(lookup);
        if (lookup.contextId()!=null) throw new IllegalArgumentException("Current lookup accepts only a draft key");
        var result=publications.executePublication(command(lookup,EngineeringRevisionPublicationUseCase.Kind.READ_CURRENT));
        var head=result.observedHead();
        if (head==null || head.state()!=EngineeringRevisionPublication.State.ACTIVE)
            return Optional.empty();
        if (result.publication()==null)
            throw new dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure(
                    dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason.UNAVAILABLE);
        return Optional.of(source(lookup,result));
    }
    @Override
    public Source exact(Lookup lookup) {
        Objects.requireNonNull(lookup);
        if (lookup.contextId()==null) throw new IllegalArgumentException("Exact lookup requires context and revision");
        var result=publications.executePublication(command(lookup,EngineeringRevisionPublicationUseCase.Kind.READ_EXACT));
        if(result.publication()==null) throw new AccessDeniedException("Engineering source unavailable");
        var snapshot=result.publication().snapshot();
        if(!lookup.contextId().equals(snapshot.scope().contextId()) ||
                !lookup.draftId().equals(snapshot.draftId()))
            throw new AccessDeniedException("Engineering source unavailable");
        return source(lookup,result);
    }
    private static EngineeringRevisionPublicationUseCase.Command command(
            Lookup lookup,EngineeringRevisionPublicationUseCase.Kind kind) {
        return new EngineeringRevisionPublicationUseCase.Command(kind,lookup.companyPartyId(),
                lookup.pipelineSystemId(),lookup.draftId(),null,null,null,null,
                kind==EngineeringRevisionPublicationUseCase.Kind.READ_EXACT?lookup.revisionId():null,
                null,null,null);
    }
    private static Source source(Lookup lookup,EngineeringRevisionPublicationUseCase.Result result) {
        var publication=Objects.requireNonNull(result.publication());
        var snapshot=publication.snapshot();
        var head=result.observedHead();
        var scope=snapshot.scope();
        if(!lookup.companyPartyId().equals(scope.companyPartyId()) ||
                !lookup.pipelineSystemId().equals(scope.pipelineSystemId()) ||
                !lookup.draftId().equals(snapshot.draftId()) ||
                lookup.contextId()!=null && !lookup.contextId().equals(scope.contextId()) ||
                lookup.revisionId()!=null && !lookup.revisionId().equals(publication.revisionId()))
            throw new AccessDeniedException("Engineering source scope mismatch");
        boolean eligible=head!=null && head.state()==EngineeringRevisionPublication.State.ACTIVE
                && head.revisionId().equals(publication.revisionId());
        return new Source(lookup.kind(),scope.contextId(),snapshot.draftId(),publication.revisionId(),
                snapshot.version(),snapshot.metadata().sha256(),eligible?"ACTIVE":"HISTORICAL",
                head==null?null:head.effectiveAt(),eligible,false);
    }
}
