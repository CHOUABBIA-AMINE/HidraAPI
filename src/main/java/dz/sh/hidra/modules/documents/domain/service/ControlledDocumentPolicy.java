/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.service
 * @Description : Pure domain decision and version transition invariants.
 */
package dz.sh.hidra.modules.documents.domain.service;

import dz.sh.hidra.modules.documents.domain.model.Document;
import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;
import dz.sh.hidra.modules.documents.domain.model.ControlledDocumentDecision;
import dz.sh.hidra.modules.documents.domain.value.DocumentStatus;
import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;
import java.util.Objects;

public final class ControlledDocumentPolicy {
    public void requireAcceptance(Document document, DocumentVersion version,
            String custodianActor, DocumentVersion previous) {
        Objects.requireNonNull(document);
        Objects.requireNonNull(version);
        if (!document.id().equals(version.documentId())
                || version.versionStatus()!=DocumentVersionStatus.DRAFT
                || version.storageObjectId()==null
                || !version.checksumAlgorithm().equalsIgnoreCase("SHA-256")
                || !version.checksumValue().matches("(?i)[0-9a-f]{64}"))
            throw new IllegalStateException("An eligible same-document DRAFT version is required.");
        if (custodianActor==null || custodianActor.equals(document.createdByActorId())
                || custodianActor.equals(version.uploadedByActorId()))
            throw new IllegalStateException("A distinct independent custodian is required.");
        if (previous==null) {
            if (document.currentVersionId()!=null || document.status()!=DocumentStatus.DRAFT)
                throw new IllegalStateException("Initial acceptance requires a draft without a current head.");
        } else {
            if (!previous.id().equals(document.currentVersionId())
                    || !previous.documentId().equals(document.id())
                    || previous.versionStatus()!=DocumentVersionStatus.CURRENT
                    || version.versionNumber()<=previous.versionNumber()
                    || document.status()!=DocumentStatus.ACTIVE)
                throw new IllegalStateException("Supersession must advance the exact current version.");
        }
    }
    public void requireReplay(ControlledDocumentDecision earlier,String requestedDigest) {
        if(!earlier.payloadDigest().equals(requestedDigest))
            throw new IllegalStateException("Reused request identity has different content.");
    }
}
