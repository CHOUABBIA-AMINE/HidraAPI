/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentIntegrationReferencePort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Outbound documents port DocumentIntegrationReferencePort.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

/**
 * Outbound documents port.
 */
public interface DocumentIntegrationReferencePort {

    boolean available(String referenceId);
}
