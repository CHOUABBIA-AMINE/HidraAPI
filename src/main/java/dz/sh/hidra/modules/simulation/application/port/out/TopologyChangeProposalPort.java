/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyChangeProposalPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Outbound simulation port TopologyChangeProposalPort.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

/**
 * Outbound simulation port.
 */
public interface TopologyChangeProposalPort {

    boolean available(String referenceId);
}
