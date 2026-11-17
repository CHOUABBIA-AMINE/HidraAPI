/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 * @Description : Owner-export-mediated live paired scope and facility authorization.
 */
package dz.sh.hidra.modules.documents.application.port.out;

public interface ControlledDocumentAuthorizationPort {
    record Facility(String id,String companyId,String code,String label){}
    String currentLocalActor();
    void require(String permission,String companyId,String facilityId);
    Facility requireActiveFacility(String facilityId);
}
