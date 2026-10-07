/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsOwnedTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.contract.target
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.contract.target;
import java.util.Optional;
import java.util.Set;
public interface DocumentsOwnedTargetLookup {
    record Target(String id,String code,String label) {}
    String module();
    Set<String> targetTypeCodes();
    Optional<Target> resolve(String typeCode,String targetId);
}
