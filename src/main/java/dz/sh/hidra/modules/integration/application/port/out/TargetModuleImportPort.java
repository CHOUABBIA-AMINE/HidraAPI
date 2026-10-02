/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TargetModuleImportPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Outbound integration port TargetModuleImportPort.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

/**
 * Outbound integration port.
 */
public interface TargetModuleImportPort {

    boolean available(String referenceId);
}
