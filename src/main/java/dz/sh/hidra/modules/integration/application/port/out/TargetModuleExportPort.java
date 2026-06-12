/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TargetModuleExportPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Outbound integration port TargetModuleExportPort.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

/**
 * Outbound integration port.
 */
public interface TargetModuleExportPort {

    boolean available(String referenceId);
}
