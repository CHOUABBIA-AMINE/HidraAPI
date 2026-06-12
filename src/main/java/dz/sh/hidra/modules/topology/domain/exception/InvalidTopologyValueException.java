/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidTopologyValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.exception
 *
 * @Description : Invalid topology value exception.
 *
 */
package dz.sh.hidra.modules.topology.domain.exception;

public class InvalidTopologyValueException extends TopologyDomainException {
    
	private static final long serialVersionUID = 2379876988455383433L;

	public InvalidTopologyValueException(String message) { super(message); }
}
