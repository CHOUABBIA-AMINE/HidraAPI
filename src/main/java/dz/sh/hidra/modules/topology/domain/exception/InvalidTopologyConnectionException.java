/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidTopologyConnectionException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.exception
 *
 * @Description : Invalid topology connection exception.
 *
 */
package dz.sh.hidra.modules.topology.domain.exception;

public class InvalidTopologyConnectionException extends TopologyDomainException {
    
	private static final long serialVersionUID = 466258106083992217L;

	public InvalidTopologyConnectionException(String message) { super(message); }
}
