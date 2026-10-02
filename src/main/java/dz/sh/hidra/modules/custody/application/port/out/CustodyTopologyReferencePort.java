/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTopologyReferencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Outbound reference port CustodyTopologyReferencePort.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

/**
 * Outbound reference port.
 */
public interface CustodyTopologyReferencePort {

    boolean available(String referenceId);
}
