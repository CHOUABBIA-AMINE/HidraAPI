/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseControlledDocumentEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.contract.hse
 * @Description : T24 exact controlled version, acceptance link and immutable binary integrity projection.
 */
package dz.sh.hidra.modules.documents.application.contract.hse;

import java.util.Optional;
public interface HseControlledDocumentEvidenceContract {
    record Evidence(String documentId,String versionId,String acceptedLinkId,
                    String checksumSha256,long contentLength,String companyId,String facilityId){}
    Optional<Evidence> accepted(String companyId,String facilityId,String documentId,String versionId);
}
