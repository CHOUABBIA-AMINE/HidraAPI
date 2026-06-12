/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsDocumentReferencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Outbound reference port AssetsDocumentReferencePort.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

/**
 * Outbound reference port.
 */
public interface AssetsDocumentReferencePort {

    boolean available(String referenceId);
}
