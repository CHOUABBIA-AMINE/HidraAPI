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
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class HseControlledDocumentEvidenceQueryService implements HseControlledDocumentEvidenceContract {
    private final DocumentVersionRepositoryPort versions;
    private final ControlledDocumentRepositoryPort decisions;
    private final ControlledDocumentAccessService access;
    public HseControlledDocumentEvidenceQueryService(
            DocumentVersionRepositoryPort versions,ControlledDocumentAccessService access,
            ControlledDocumentRepositoryPort decisions){
        this.versions=versions;this.access=access;this.decisions=decisions;
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
        var decision = decisions.acceptedVersion(document, version).orElse(null);
        if (decision == null || !company.equals(decision.companyId())
                || !facility.equals(decision.facilityId())
                || !source.checksumValue().equalsIgnoreCase(decision.checksumSha256())
                || source.fileSizeBytes() != decision.contentLength()) return Optional.empty();
        return Optional.of(new Evidence(document, version, decision.id(), decision.checksumSha256(),
                decision.contentLength(), company, facility));
    }
}
