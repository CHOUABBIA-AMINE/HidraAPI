/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputOwnerAccessPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 * @Description : Distinct current Identity authority and exact Topology baseline access.
 */
package dz.sh.hidra.modules.simulation.application.port.out;


import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Baseline;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Scope;
import java.util.UUID;
/** Identity decisions run in Simulation's current transaction; Topology EXACT runs separately. */
public interface ScenarioInputOwnerAccessPort {
    String requireCurrentRead(Scope scope);
    String requireCurrentWrite(Scope scope);
    Baseline exactBaseline(Scope scope,UUID receiptId);
}
