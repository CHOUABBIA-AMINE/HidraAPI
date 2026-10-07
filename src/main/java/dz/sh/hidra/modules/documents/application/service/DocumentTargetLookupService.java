/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLookupService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.contract.target.DocumentsOwnedTargetLookup;
import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLookupPort;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class DocumentTargetLookupService implements DocumentTargetLookupPort {
    private final List<DocumentsOwnedTargetLookup> owners;
    public DocumentTargetLookupService(List<DocumentsOwnedTargetLookup> owners){this.owners=List.copyOf(owners);}
    public DocumentsOwnedTargetLookup.Target requireTarget(String module,String typeCode,String targetId){
        if(module==null || module.isBlank() || typeCode==null || typeCode.isBlank() || targetId==null || targetId.isBlank())
            throw new IllegalArgumentException("Complete target module/type/id required.");
        String m=module.trim(),type=typeCode.trim(),id=targetId.trim();
        var matches=owners.stream().filter(o->m.equals(o.module()) && o.targetTypeCodes().contains(type)).toList();
        if(matches.size()!=1)throw new IllegalArgumentException("Exactly one registered target owner required.");
        return matches.get(0).resolve(type,id).filter(t->id.equals(t.id()))
            .orElseThrow(()->new IllegalArgumentException("Existing owner target required."));
    }
}
