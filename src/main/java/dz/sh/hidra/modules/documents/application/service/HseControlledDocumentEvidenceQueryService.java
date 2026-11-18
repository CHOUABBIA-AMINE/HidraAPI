/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseControlledDocumentEvidenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 * @Description : Scope-checked accepted T24 exact version and physically sealed custodian-link proof.
 */
package dz.sh.hidra.modules.documents.application.service;

import dz.sh.hidra.modules.documents.application.contract.hse.HseControlledDocumentEvidenceContract;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class HseControlledDocumentEvidenceQueryService implements HseControlledDocumentEvidenceContract {
    private final ControlledDocumentsUseCase controlled;
    private final DocumentVersionRepositoryPort versions;
    private final ControlledDocumentAccessService access;
    public HseControlledDocumentEvidenceQueryService(ControlledDocumentsUseCase controlled,
            DocumentVersionRepositoryPort versions,ControlledDocumentAccessService access){
        this.controlled=controlled;this.versions=versions;this.access=access;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Evidence> accepted(String company,String facility,String document,String version){
        if(company==null||facility==null||document==null||version==null)return Optional.empty();
        access.requireBound(document,company,facility,"documents:controlled:read");
        var source=versions.findById(version).filter(v->document.equals(v.documentId()))
                .orElse(null);
        if(source==null||!java.util.Set.of(DocumentVersionStatus.CURRENT,
                DocumentVersionStatus.SUPERSEDED).contains(source.versionStatus())
                ||source.fileSizeBytes()<0||source.checksumValue()==null
                ||!source.checksumValue().matches("(?i)[a-f0-9]{64}"))
            return Optional.empty();
        // T24's deferred database consistency requires an accepted decision for each
        // CURRENT/SUPERSEDED version and an immutable, exact FACILITY target link.
        var links=controlled.links(company,facility,document,0,100);
        var link=links.items().stream().filter(l->version.equals(l.versionId())
                &&"topology".equals(l.targetModule())&&"FACILITY".equals(l.targetType())
                &&facility.equals(l.targetId())).findFirst().orElse(null);
        if(link==null||!link.primary())return Optional.empty();
        return Optional.of(new Evidence(document,version,link.id(),source.checksumValue().toLowerCase(),
                source.fileSizeBytes(),company,facility));
    }
}
