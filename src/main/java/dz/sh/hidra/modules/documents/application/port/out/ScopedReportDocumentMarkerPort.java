/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportDocumentMarkerPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.documents.application.port.out;
import java.util.Optional;

public interface ScopedReportDocumentMarkerPort {
    record Marker(String documentId,String runId,String companyId,String facilityId,
            String versionId,String acceptanceId,String checksum,long length) {}
    Optional<Marker> find(String documentId,boolean lock);
    String catalog(String family);
    void bind(Marker marker);
    void uploaded(String document,String version);
    void sealed(String document,String version,String acceptance);
}
