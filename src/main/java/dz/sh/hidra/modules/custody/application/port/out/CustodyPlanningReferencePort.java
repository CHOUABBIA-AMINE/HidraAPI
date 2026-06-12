/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPlanningReferencePort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Outbound reference port CustodyPlanningReferencePort.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

/**
 * Outbound reference port.
 */
public interface CustodyPlanningReferencePort {

    boolean available(String referenceId);
}
