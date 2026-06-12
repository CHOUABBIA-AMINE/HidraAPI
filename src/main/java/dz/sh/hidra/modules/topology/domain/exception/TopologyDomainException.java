/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.exception
 *
 * @Description : Base topology domain exception.
 *
 */
package dz.sh.hidra.modules.topology.domain.exception;

public class TopologyDomainException extends RuntimeException {
    
	private static final long serialVersionUID = 6074924429029542590L;
	
	public TopologyDomainException(String message) { 
		super(requireMessage(message)); 
	}
    
	public TopologyDomainException(String message, Throwable cause) { 
		super(requireMessage(message), cause); 
	}
    
	private static String requireMessage(String message) {
        if (message == null || message.isBlank()) throw new IllegalArgumentException("Topology exception message must not be null or blank.");
        return message.trim();
    }
}
