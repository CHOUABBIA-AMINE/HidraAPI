/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEligibilityLookupPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Outbound workflow port WorkflowEligibilityLookupPort.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

/**
 * Outbound workflow port.
 */
public interface WorkflowEligibilityLookupPort {

    boolean available(String referenceId);
}
