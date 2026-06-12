/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentIdentityReferencePort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Outbound documents port DocumentIdentityReferencePort.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

/**
 * Outbound documents port.
 */
public interface DocumentIdentityReferencePort {

    boolean available(String referenceId);
}
