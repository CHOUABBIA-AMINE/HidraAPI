/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioBaselineReceiptContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.simulation
 * @Description : Exposes only exact verified immutable baseline receipt facts to Simulation.
 */
package dz.sh.hidra.modules.topology.application.contract.simulation;


import java.util.UUID;
public interface ScenarioBaselineReceiptContract {
    record Exact(UUID receiptId,UUID manifestId,long epoch,String manifestDigest) {
        public Exact {
            java.util.Objects.requireNonNull(receiptId);
            java.util.Objects.requireNonNull(manifestId);
            if(epoch<1||manifestDigest==null||!manifestDigest.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Invalid owner receipt proof");
        }
    }
    Exact exact(String companyId,String pipelineSystemId,UUID receiptId);
}
