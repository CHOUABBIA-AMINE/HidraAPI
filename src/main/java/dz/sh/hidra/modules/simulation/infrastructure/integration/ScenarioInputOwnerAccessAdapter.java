/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputOwnerAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 * @Description : The sole scalar Identity/Topology export consumer for scenario inputs.
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;


import dz.sh.hidra.modules.identity.application.contract.simulation.ScenarioInputAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.simulation.ScenarioBaselineReceiptContract;
import dz.sh.hidra.modules.simulation.application.port.out.ScenarioInputOwnerAccessPort;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Baseline;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Scope;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** Topology runs its own EXACT protected transaction; no private aggregate crosses this adapter. */
@Component
public final class ScenarioInputOwnerAccessAdapter implements ScenarioInputOwnerAccessPort {
    private final ScenarioInputAuthorizationContract identity;
    private final ScenarioBaselineReceiptContract topology;
    public ScenarioInputOwnerAccessAdapter(ScenarioInputAuthorizationContract identity,
                                          ScenarioBaselineReceiptContract topology) {
        this.identity=Objects.requireNonNull(identity);
        this.topology=Objects.requireNonNull(topology);
    }
    @Override public String requireCurrentRead(Scope s) {
        return identity.require(ScenarioInputAuthorizationContract.READ,s.companyId(),s.pipelineSystemId());
    }
    @Override public String requireCurrentWrite(Scope s) {
        // The write permission is additional: never substitute it for required read.
        String reader=requireCurrentRead(s);
        String writer=identity.require(ScenarioInputAuthorizationContract.WRITE,s.companyId(),s.pipelineSystemId());
        if(!reader.equals(writer))throw new NoSuchElementException("Scenario inputs unavailable");
        return reader;
    }
    @Override public Baseline exactBaseline(Scope s,UUID id) {
        var proof=topology.exact(s.companyId(),s.pipelineSystemId(),id);
        if(proof==null || !id.equals(proof.receiptId()))
            throw new NoSuchElementException("Scenario baseline unavailable");
        return new Baseline(proof.receiptId(),proof.manifestId(),proof.epoch(),proof.manifestDigest());
    }
}
