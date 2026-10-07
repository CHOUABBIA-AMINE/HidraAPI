/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsCatalogEligibilityPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;
public interface DocumentsCatalogEligibilityPort {
    void requireActive(String entryId,String family);
}
