/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaDocumentsPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 * @Description : Accepted immutable T24 document version, scope and byte hash attestation.
 */
package dz.sh.hidra.modules.hse.application.port.out;

import java.util.Optional;
public interface HseCapaDocumentsPort {
    record Evidence(String documentId,String versionId,String acceptanceId,
                    String sha256,long contentLength,String companyId,String facilityId){}
    Optional<Evidence> accepted(String companyId,String facilityId,String documentId,String versionId);
}
