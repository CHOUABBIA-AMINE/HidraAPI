/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.in
 * @Description : Exact create, append, read, and deterministic validation use-case contract.
 */
package dz.sh.hidra.modules.simulation.application.port.in;


import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.*;
import java.util.List;
import java.util.UUID;
public interface ScenarioInputRevisionUseCase {
    record Create(Scope scope,Inputs inputs) {}
    record Append(Scope scope,UUID scenarioId,long expectedRevision,Inputs inputs) {
        public Append {if(expectedRevision<1)throw new IllegalArgumentException("Positive expected revision required");}
    }
    record Read(Scope scope,UUID scenarioId,long revision) {
        public Read {if(revision<1)throw new IllegalArgumentException("Positive revision required");}
    }
    record Result(ScenarioInputRevision revision,boolean replay) {}
    record Validation(UUID scenarioId,long revision,String policyId,String status,String numericalCapability,
                      List<String> diagnostics,String inputDigest) {
        public Validation { diagnostics=List.copyOf(diagnostics); }
    }
    Result create(Create command);
    Result append(Append command);
    Result read(Read query);
    Validation validate(Read query);
}
